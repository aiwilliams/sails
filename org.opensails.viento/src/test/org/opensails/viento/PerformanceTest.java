package org.opensails.viento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

public class PerformanceTest extends TestCase {

	public void testPerformance() {
		Binding binding = new Binding();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10000; i++)
			list.add(String.valueOf(i));
		binding.put("list", list);
		String source = "$list.before[[before]].each(each)[[$if(true)[[$each.length]]]].after[[after]]";
		VientoTemplate template = new VientoTemplate(source);
		Date startTime = new Date();
		template.render(binding);
		Date endTime = new Date();
		fail("Elapsed time: " + (endTime.getTime() - startTime.getTime()) + "ms");
	}

}
