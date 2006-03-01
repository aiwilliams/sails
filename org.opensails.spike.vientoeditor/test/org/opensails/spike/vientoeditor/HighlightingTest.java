package org.opensails.spike.vientoeditor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.custom.StyleRange;
import org.opensails.viento.VientoTemplate;

import junit.framework.TestCase;

public class HighlightingTest extends TestCase {
	HighlightingTester t = new HighlightingTester();

	public void testIt() throws Exception {
		t.viento("asdf");
		t.colors("____");
		
		t.viento("asdf$$one.two;;asdf$two() asdf");
		t.colors("_____---------_____------_____");

		t.viento("$one.two [[]]");
		t.colors("-------------");

		t.viento("$one.two [[ asdf $three asdf]]");
		t.colors("-----------______------_____--");
		
		t.viento("asdf$one('asdf', :asdf).two(asdf) [[asdf]]");
		t.colors("____-----++++++--+++++------++++----____--");
		
		t.viento("$one(true, [false, null], {asdf: ''})");
		t.colors("_____----__$-----$$----$__%++++%%++%_");
	}
	
	public void testLongFile() throws Exception {
		StringBuilder b1 = new StringBuilder(4540);
		StringBuilder b2 = new StringBuilder(4540);
		b1.append("asdf$works.here;");
		b2.append("____------------");
		for (int i = 0; i < 4500; i++) {
			b1.append("a");
			b2.append("_");
		}
		b1.append("$works.out.here;too");
		b2.append("----------------___");
		t.viento(b1.toString());
		t.colors(b2.toString());
	}
	
	class HighlightingTester {
		VientoHighlighter highlighter = new VientoHighlighter(new TestHighlightingConfiguration());
		String viento;

		public void colors(String string) {
			assertRanges(viento, string);
		}

		public void viento(String string) {
			viento = string;
		}

		private void assertRanges(String viento, String colors) {
			StyleRange[] ranges = highlighter.rangesFor(new VientoTemplate(viento).getAst());

			Map<Character, StyleRange> styles = new HashMap<Character, StyleRange>();

			int i = 0;
			for (; i < colors.length(); i++) {
				char c = colors.charAt(i);
				if (!styles.containsKey(c))
					styles.put(c, rangeForPos(ranges, i));
				for (Map.Entry<Character, StyleRange> entry : styles.entrySet())
					assertFalse(entry.getKey().equals(c) ^ entry.getValue().similarTo(rangeForPos(ranges, i)));
			}
			assertNull("Shouldn't be a range past the end", rangeForPos(ranges, i));
		}

		private StyleRange rangeForPos(StyleRange[] ranges, int i) {
			for (StyleRange range : ranges)
				if (range.start <= i && (i - range.start) < range.length)
					return range;
			return null;
		}
	}
	
	class TestHighlightingConfiguration implements HighlightingConfiguration {
		public ElementStyle styleFor(HighlightedElement element) {
			ElementStyle style = new ElementStyle();
			// make it unique
			style.rise = element.ordinal();
			return style;
		}
	}
}
