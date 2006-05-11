package org.opensails.sails.tester.form;

import junit.framework.TestCase;

import org.apache.tools.ant.filters.StringInputStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.opensails.sails.SailsException;

public abstract class TesterElementTestCase extends TestCase {
	protected static final SAXReader READER = new SAXReader();

	protected Element elementForHtml(String html) {
		try {
			Document document = READER.read(new StringInputStream(html));
			return document.getRootElement();
		} catch (Exception e) {
			throw new SailsException("Failure creating an Element from html", e);
		}
	}
}
