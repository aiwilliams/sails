package org.opensails.sails.tester.html;

public class XPathString implements CharSequence {

	private final String xpathExpression;

	public XPathString(String xpathExpression) {
		this.xpathExpression = xpathExpression;
	}

	public XPathString(String xpathExpression, String... values) {
		this(String.format(xpathExpression, (Object[]) values));
	}

	public char charAt(int index) {
		return xpathExpression.charAt(index);
	}

	public int length() {
		return xpathExpression.length();
	}

	public CharSequence subSequence(int beginIndex, int endIndex) {
		return xpathExpression.subSequence(beginIndex, endIndex);
	}

	public String toString() {
		return xpathExpression.toString();
	}

}
