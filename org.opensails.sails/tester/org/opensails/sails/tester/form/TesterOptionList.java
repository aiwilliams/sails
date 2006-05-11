package org.opensails.sails.tester.form;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;

public class TesterOptionList extends AbstractList<TesterOption> {

	protected Element container;
	protected List<TesterOption> options;

	public TesterOptionList(Element container) {
		this.container = container;
		this.options = loadOptions(container);
	}

	public TesterOptionList assertLabels(String... expected) {
		List<String> labelsPresent = new ArrayList<String>();
		for (TesterOption option : options)
			labelsPresent.add(option.getLabel());
		if (labelsPresent.size() != expected.length) throw new TesterElementError(String.format("Expected %s labels but there were %s", expected.length, labelsPresent.size()), container);
		if (!Arrays.asList(expected).equals(labelsPresent)) throw new TesterElementError("Label count was same but not order or content", container);
		return this;
	}

	public void assertSize(int expected) {
		if (expected != size()) throw new TesterElementError(String.format("Expected to have %s options but there are only %s", expected, size()), container);
	}

	@Override
	public TesterOption get(int index) {
		return options.get(index);
	}

	public List<TesterOption> selected() {
		List<TesterOption> selected = new ArrayList<TesterOption>(options.size() / 2);
		for (TesterOption option : options)
			if (option.isSelected()) selected.add(option);
		return selected;
	}

	public int size() {
		return options.size();
	}

	private List<TesterOption> loadOptions(Element container) {
		List<TesterOption> options = new ArrayList<TesterOption>();
		for (Object optionNode : container.selectNodes("//option")) {
			Element optionElement = (Element) optionNode;
			options.add(new TesterOption(container, optionElement));
		}
		return options;
	}

}
