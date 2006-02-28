package org.opensails.spike.vientoeditor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class HardCodedHighlightingConfiguration implements HighlightingConfiguration {
	private Map<HighlightedElement, ElementStyle> map;
	protected final Display display;

	public HardCodedHighlightingConfiguration(Display display) {
		this.display = display;
	}
	
	public ElementStyle styleFor(HighlightedElement element) {
		return getMap().get(element);
	}

	protected Map<HighlightedElement, ElementStyle> getMap() {
		if (map == null) {
			map = new HashMap<HighlightedElement, ElementStyle>();
			map.put(HighlightedElement.TEXT, color(30, 35, 190));
			map.put(HighlightedElement.STATEMENT, color(115, 190, 30));
			map.put(HighlightedElement.KEYWORD, boldColor(127, 0, 85));
			map.put(HighlightedElement.QUOTED_STRING, color(30, 115, 190));
			map.put(HighlightedElement.LIST, color(190, 104, 30));
			map.put(HighlightedElement.MAP, color(104, 30, 190));
		}
		return map;
	}
	
	protected ElementStyle color(int r, int g, int b) {
		return new ElementStyle(new Color(display, r, g, b));
	}
	
	protected ElementStyle boldColor(int r, int g, int b) {
		ElementStyle style = color(r, g, b);
		style.fontStyle = SWT.BOLD;
		return style;
	}

	public void dispose() {
		if (display.isDisposed())
			return;
		for (ElementStyle style : getMap().values()) {
			if (style.background != null)
				style.background.dispose();
			if (style.foreground != null)
				style.foreground.dispose();
		}
	}
}
