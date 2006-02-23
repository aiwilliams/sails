package org.opensails.viento;

import java.util.Arrays;

import junit.framework.TestCase;

public class BuiltinsTest extends TestCase {
    Binding binding = new Binding();

    public void testIf() throws Exception {
        verifyRender("$if(true)[[here]]", "here");
        verifyRender("$if(false)[[here]]", "");
        verifyRender("$if(true)[[here]].else[[there]]", "here");
        verifyRender("$if(false)[[here]].else[[there]]", "there");
        verifyRender("$if(true)[[here]].elseif(false)[[there]]", "here");
        verifyRender("$if(false)[[here]].elseif(true)[[there]]", "there");
        verifyRender("$if(false)[[here]].elseif(false)[[there]]", "");
        verifyRender("$if(false)[[here]].elseif(true)[[there]].else[[nowhere]]", "there");
        verifyRender("$if(false)[[here]].elseif(false)[[there]].else[[nowhere]]", "nowhere");
        verifyRender("$if(true)[[here]].elseif(true)[[there]].else[[nowhere]]", "here");

        verifyRender("$if('asdf')[[here]]", "here");
        
        verifyRender("$if($notThere)[[here]]", "");
        binding.put("notThere", null);
        verifyRender("$if($notThere)[[here]]", "");
        verifyRender("$if(!$notThere)[[here]]", "here");

        // just for fun
        verifyRender("$set(joe)[[here]]$if(true, $joe)", "here");
    }

    public void testEach() throws Exception {
        binding.put("list", Arrays.asList(new String[] { "one", "two", "three" }));
        verifyRender("$list.each(item)[[<h1>$item</h1>]]", "<h1>one</h1><h1>two</h1><h1>three</h1>");
        verifyRender("$list.each(item)[[<h$index;>$item</h$index;>]]", "<h1>one</h1><h2>two</h2><h3>three</h3>");
        verifyRender("$list.each(item, indexB)[[<h$indexB;>$item</h$indexB;>]]", "<h1>one</h1><h2>two</h2><h3>three</h3>");

		verifyRender("$list.each(item)[[\t<h1>$item</h1>\r\n  ]].trim", "<h1>one</h1><h1>two</h1><h1>three</h1>");
        
        verifyRender("$list.each(item)[[<h1>$item</h1>]].sans(one)", "<h1>two</h1><h1>three</h1>");
        verifyRender("$list.each(item)[[<h1>$item</h1>]].sans([one, three])", "<h1>two</h1>");
        verifyRender("$list.each(item)[[<h1>$item</h1>]].sans({length: 3})", "<h1>three</h1>");
        verifyRender("$list.each(item)[[<h1>$item</h1>]].sans({length: [3, 5]})", "");
        verifyRender("$list.before[[before]].each(item)[[<h1>$item</h1>]].sans({length: [3, 5]})", "");
        
        verifyRender("$set(list, [])$list.before[[before ]].each(each)[[$each]].delimiter[[, ]].after[[ after]]", "");
        verifyRender("$set(list, [one])$list.before[[before ]].each(each)[[$each]].delimiter[[, ]].after[[ after]]", "before one after");
        verifyRender("$set(list, [one, two])$list.before[[before ]].each(each)[[$each]].delimiter[[, ]].after[[ after]]", "before one, two after");
        verifyRender("$set(list, [one, two])$list.before[[before ]].each(each)[[$each]].delimiter(', ').after[[ after]]", "before one, two after");

        verifyRender("$default(newlist, [one])$newlist.each(each)[[$each]]", "one");
        verifyRender("$set(anotherlist, [])$default(anotherlist, [one])$anotherlist.each(each)[[$each]]", "");

        verifyRender("$list.add('asdf')", "true");
        verifyRender("$do [> $list.add('asdf')", "");

        binding.put("array", new String[] { "one", "two", "three" });
        verifyRender("$array.each(item)[[<h1>$item</h1>]]", "<h1>one</h1><h1>two</h1><h1>three</h1>");
    }
    
    public void testSilentBlock() throws Exception {
        verifyRender("$![[asdf$notHere]]", "");

        binding.put("key", "value");
        verifyRender("$![[asdf$key]]", "asdfvalue");

        verifyRender("$![[asdf$notHere]].?[[not here]]", "not here");

        binding.setExceptionHandler(new ShamExceptionHandler());
        verifyRender("$![[$notHere]]", "");
    }
    
    public void testSilence() throws Exception {
		verifyRender("$notThere.?", "");
		binding.put("key", "value");
		verifyRender("$key.?", "value");
		verifyRender("$key.?.length", "5");
		verifyRender("$key.length.?", "5");
		verifyRender("$notThere.?.length", "");
		verifyRender("$notThere.length.?", "");
	}
    
    public void testSet() throws Exception {
    	verifyRender("$set(string, 'string')$string", "string");
    	// Blocks just work. I love this stuff.
    	verifyRender("$set(name, 'Fred')$set(greeting)[[Welcome $name;!]]$greeting", "Welcome Fred!");
    }

    public void testProperties() throws Exception {
		binding.put("bean", new Bean());
		verifyRender("$bean.properties.each(property)[[<p>$property.name: $property.value</p>]]", "<p>one: 1</p><p>two: 2</p>");
	}

    protected void verifyRender(String input, String output) {
        VientoTemplate template = new VientoTemplate(input);
        assertEquals(output, template.render(binding));
    }
    
    public class Bean {
    	public int getOne() {
    		return 1;
    	}
    	
    	public int getTwo() {
    		return 2;
    	}
    }
}
