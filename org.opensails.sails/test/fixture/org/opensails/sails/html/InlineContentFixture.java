package org.opensails.sails.html;

public class InlineContentFixture {
	public static IInlineContent create(final String andReturn) {
		return new IInlineContent() {
			public String render() {
				return andReturn;
			}
		};
	}
}
