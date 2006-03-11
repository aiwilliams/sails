package org.opensails.spike.vientoeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.swt.custom.StyleRange;
import org.opensails.viento.VientoVisitor;
import org.opensails.viento.ast.BooleanLiteral;
import org.opensails.viento.ast.INode;
import org.opensails.viento.ast.ListLiteral;
import org.opensails.viento.ast.MapLiteral;
import org.opensails.viento.ast.NullLiteral;
import org.opensails.viento.ast.NumberLiteral;
import org.opensails.viento.ast.Statement;
import org.opensails.viento.ast.StringBlock;
import org.opensails.viento.ast.StringLiteral;
import org.opensails.viento.ast.Template;
import org.opensails.viento.ast.Text;

/**
 * Does all the highlighting work.
 * 
 * Implementation: The HighlightingVisitor visits the AST and builds a flat list
 * of overlapping ranges. VientoHighlighter#processRanges then resolves the
 * overlapping to build a normalized list of StyleRanges, which are suitable to
 * pass to a TextViewer. Thus, support for new nodes is easily added to the
 * visitor, without tying us to a one-to-one mapping between node types and
 * ranges (i.e. you can inspect the node to further qualify how it ought to be
 * styled).
 * 
 * TODO: ## (need to change the AST), differentiate :string and string from 'string'
 */
public class VientoHighlighter {
	protected HighlightingConfiguration configuration;

	public VientoHighlighter(HighlightingConfiguration configuration) {
		this.configuration = configuration;
	}

	public StyleRange[] rangesFor(Template ast) {
		HighlightingVisitor visitor = new HighlightingVisitor();
		ast.visit(visitor);
		List<StyleRange> ranges = processRanges(visitor.ranges);
		return (StyleRange[]) ranges.toArray(new StyleRange[ranges.size()]);
	}
	
	private StyleRange peak(Range peak) {
		return configuration.styleFor(peak.element).createRange(peak.offset(), peak.length());
	}
	
	private void processRange(Range range, ListIterator<Range> iter, List<StyleRange> list) {
		if (!iter.hasNext()) {
			list.add(peak(range));
			return;
		}
		
		Range next = iter.next();
		if (!next.overlaps(range)) {
			list.add(peak(range));
			iter.previous();
			return;
		}
		
		Range last = null;
		while (next.overlaps(range)) {
			if (last == null || !next.touches(last))
				list.add(valley(range, last, next));
			processRange(next, iter, list);
			last = next;
			if (!iter.hasNext()) {
				list.add(valley(range, last, null));
				return;
			}
			next = iter.next();
		}
		list.add(valley(range, last, null));
		iter.previous();
	}

	private List<StyleRange> processRanges(List<Range> ranges) {
		List<StyleRange> styleRanges = new ArrayList<StyleRange>();
		ListIterator<Range> iter = ranges.listIterator();
		while (iter.hasNext())
			processRange(iter.next(), iter, styleRanges);
		return styleRanges;
	}

	private StyleRange valley(Range valley, Range left, Range right) {
		int leftBound = left == null ? valley.offset() : left.endOffset();
		int rightBound = right == null ? valley.endOffset() : right.offset();
		
		return configuration.styleFor(valley.element).createRange(leftBound, rightBound - leftBound);
	}

	protected class HighlightingVisitor extends VientoVisitor {
		public List<Range> ranges = new ArrayList<Range>();
		
		@Override public void visit(Statement statement) {
			setRange(statement, HighlightedElement.STATEMENT);
		}
		
		@Override public void visit(StringLiteral node) {
			setRange(node, HighlightedElement.QUOTED_STRING);
		}
		
		@Override public void visit(StringBlock node) {
			setRange(node, HighlightedElement.INTERPOLATED_STRING);
		}
		
		@Override public void visit(Text text) {
			setRange(text, HighlightedElement.TEXT);
		}
		
		@Override public void visit(BooleanLiteral node) {
			setRange(node, HighlightedElement.KEYWORD);
		}
		
		@Override public void visit(NullLiteral node) {
			setRange(node, HighlightedElement.KEYWORD);
		}
		
		@Override public void visit(ListLiteral node) {
			setRange(node, HighlightedElement.LIST);
		}
		
		@Override public void visit(MapLiteral node) {
			setRange(node, HighlightedElement.MAP);
		}
		
		@Override public void visit(NumberLiteral node) {
			setRange(node, HighlightedElement.STATEMENT);
		}

		private Range setRange(INode node, HighlightedElement element) {
			Range range = new Range(node);
			range.element = element;
			ranges.add(range);
			return range;
		}
	}

	protected class Range {
		private final INode node;
		public HighlightedElement element;
		
		public Range(INode node) {
			this.node = node;
		}
		
		public int endOffset() {
			return node.endOffset() + 1;
		}

		public int length() {
			return node.length();
		}

		public int offset() {
			return node.startOffset();
		}
		
		public boolean overlaps(Range range) {
			return range.endOffset() > this.offset();
		}
		
		public boolean touches(Range range) {
			return range.endOffset() == this.offset();
		}
	}
}
