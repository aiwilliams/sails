package org.opensails.sails.tester;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.dom4j.Document;
import org.dom4j.Element;
import org.opensails.sails.tester.form.TesterElementError;

public class TesterScriptList extends AbstractList<TesterScript> {
	protected List<TesterScript> backingList = new ArrayList<TesterScript>();

	public TesterScriptList(Document document) {
		List<Element> scriptNodes = document.selectNodes("//script");
		for (Element scriptElement : scriptNodes) {
			backingList.add(new TesterScript(scriptElement.getParent(), scriptElement));
		}
		if (size() == 0) throw new TesterElementError("There are no scripts in the document", document.getRootElement());
	}

	public void assertContains(String script, int expectedOccurrances) {
		int matches = 0;
		for (TesterScript s : this)
			if (s.getSrc() != null && s.getSrc().endsWith(script)) matches++;
		Assert.assertEquals(expectedOccurrances, matches);
	}

	public void assertLength(int expected) {
		Assert.assertEquals("Wrong number of scripts", expected, size());
	}

	@Override
	public TesterScript get(int index) {
		return backingList.get(index);
	}

	@Override
	public int size() {
		return backingList.size();
	}
}
