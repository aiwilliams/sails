package org.opensails.spike.vientoeditor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.custom.StyleRange;
import org.opensails.viento.VientoTemplate;

import junit.framework.TestCase;

public class HighlightingTest extends TestCase {
	HighlightingTester t = new HighlightingTester();

	public void testIt() throws Exception {
		t.viento("asdf$$one.two;;asdf$two() asdf");
		t.colors("_____---------_____------_____");

		t.viento("$one.two [[]]");
		t.colors("-------------");

		t.viento("$one.two [[ asdf $three asdf]]");
		t.colors("-----------______------_____--");
		
		t.viento("asdf$one('asdf', :asdf).two(asdf) [[asdf]]");
		t.colors("____-----++++++--+++++------++++----____--");
	}

	class HighlightingTester {
		VientoHighlighter highlighter = new VientoHighlighter();
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

			for (int i = 0; i < colors.length(); i++) {
				char c = colors.charAt(i);
				if (!styles.containsKey(c))
					styles.put(c, rangeForPos(ranges, i));
				for (Map.Entry<Character, StyleRange> entry : styles.entrySet())
					assertFalse(entry.getKey().equals(c) ^ entry.getValue().similarTo(rangeForPos(ranges, i)));
			}
		}

		private StyleRange rangeForPos(StyleRange[] ranges, int lastPos) {
			for (StyleRange range : ranges)
				if (range.start <= lastPos && (lastPos - range.start) < range.length)
					return range;
			return null;
		}
	}
}
