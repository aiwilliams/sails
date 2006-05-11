package org.opensails.sails.tester.form;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.opensails.sails.tester.html.XPathString;

public class TesterSelect extends TesterNamedElement<TesterSelect> {

	public TesterSelect(Element container, String name) {
		super(container, new XPathString("//select[@name='%s']", name), name);
	}

	public TesterSelect assertLabelsSelected(String... expected) {
		List<TesterOption> selected = options().selected();
		if (selected.size() != expected.length) throw new TesterElementError(String.format("Expected %d selected options but there were only %d", expected.length, selected.size()), element);

		List<String> found = new ArrayList<String>(expected.length);
		for (String expectation : expected)
			for (TesterOption option : selected)
				if (option.getLabel().equals(expectation)) found.add(expectation);

		if (found.size() != expected.length) throw new TesterElementError(String.format("Expected to have selected %s but had %s", expected, found), element);

		return this;
	}

	public TesterOptionList options() {
		return new TesterOptionList(element);
	}
}
