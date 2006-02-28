package org.opensails.spike.vientoeditor;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextStyle;

public class ElementStyle extends TextStyle {
	public int fontStyle;

	public ElementStyle() {
		super(null, null, null);
	}

	public ElementStyle(Color foreground) {
		super(null, foreground, null);
	}

	public ElementStyle(Font font, Color foreground, Color background) {
		super(font, foreground, background);
	}
	
	public StyleRange createRange(int start, int length) {
		StyleRange range = new StyleRange(start, length, foreground, background, fontStyle);
		range.font = this.font;
		range.metrics = this.metrics;
		range.rise = this.rise;
		range.strikeout = this.strikeout;
		range.underline = this.underline;
		return range;
	}
}
