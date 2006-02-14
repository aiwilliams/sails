package org.opensails.sails.tester;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.opensails.sails.tester.html.SourceContentError;

public class ScriptList extends AbstractList<TestScript> {
	protected static final int SCRIPT_BODY = 6;
	protected static final int SCRIPT_SRC = 2;
	protected static final int COMPLETE_SCRIPT = 0;

	public static final Pattern PATTERN = Pattern.compile("<script(.*?src=[\"|'](.*?)[\"|'])?.*?((/>)|(>(.*?)</script>))", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	protected List<TestScript> backingList = new ArrayList<TestScript>();

	public ScriptList(String pageContent) {
		Matcher matcher = PATTERN.matcher(pageContent);
		while (matcher.find())
			backingList.add(new TestScript(matcher.group(COMPLETE_SCRIPT), matcher.group(SCRIPT_SRC), matcher.group(SCRIPT_BODY)));
		if (size() == 0) throw new SourceContentError(pageContent, "There are no scripts in the content");
	}

	public void assertLength(int expected) {
		Assert.assertEquals("Wrong number of scripts", expected, size());
	}

	@Override
	public TestScript get(int index) {
		return backingList.get(index);
	}

	@Override
	public int size() {
		return backingList.size();
	}

	public void assertContains(String script, int expectedOccurrances) {
		int matches = 0;
		for (TestScript s : this)
			if (s.src != null && s.src.endsWith(script)) matches++;
		Assert.assertEquals(expectedOccurrances, matches);
	}
}
