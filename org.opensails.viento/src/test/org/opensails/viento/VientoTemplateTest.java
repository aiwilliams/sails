package org.opensails.viento;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class VientoTemplateTest extends TestCase {
	Binding binding = new Binding();

	public void testArguments() throws Exception {
		binding.put("first", "aaaabbbbcccc");
		binding.put("second", "b+");
		binding.put("third", "d");

		verifyRender("stuff$first.replaceAll($second, $third)\nstuff", "stuffaaaadcccc\nstuff");
		verifyRender("stuff$first.replaceAll($second, $third.toUpperCase)\nstuff", "stuffaaaaDcccc\nstuff");
		verifyRender("stuff$first.replaceAll($second, $third.toUpperCase())\nstuff", "stuffaaaaDcccc\nstuff");
	}

	public void testBlock() throws Exception {
		binding.put("tool", new Tool());
		verifyRender("stuff$tool.twice [[{block}]]stuff", "stuff{block}{block}stuff");
		verifyRender("stuff$tool.twice [[{$tool.timesTwo(34)}]]stuff", "stuff{68}{68}stuff");
		verifyRender("stuff$tool.loop (3) [[{$tool.timesTwo(34)}]]stuff", "stuff{68}{68}{68}stuff");
		verifyRender("stuff$tool.timesTwo (3) [{$tool.timesTwo(34)}]stuff", "stuff6 [{68}]stuff");
	}

	public void testBoolean() throws Exception {
		binding.put("tool", new Tool());

		verifyRender("stuff$tool.yesno(true)stuff", "stuffyesstuff");
		verifyRender("stuff$tool.yesno(false)stuff", "stuffnostuff");
	}

	public void testBooleanNot() throws Exception {
		binding.put("yes", true);
		binding.put("no", false);
		verifyRender("$if(!$no)[[here]]", "here");
		verifyRender("$if(!$yes)[[here]]", "");

		verifyRender("$if($yes && !$no)[[here]]", "here");

		binding.put("key", "value");
		verifyRender("$if(!$key.startsWith('asdf'))[[here]]", "here");
	}

	public void testBooleanOperators() throws Exception {
		binding.put("yes", true);
		binding.put("no", false);
		verifyRender("$if($yes && $yes)[[here]]", "here");
		verifyRender("$if($yes && $no)[[here]]", "");
		verifyRender("$if($no && $no)[[here]]", "");
		verifyRender("$if($yes || $yes)[[here]]", "here");
		verifyRender("$if($yes || $no)[[here]]", "here");
		verifyRender("$if($no || $no)[[here]]", "");
		verifyRender("$if($no || $no || false || true)[[here]]", "here");
	}

	public void testComment() throws Exception {
		verifyRender("stuff##comment\nstuff", "stuff\nstuff");

		// Watch that excess whitespace, now.
		verifyRender("#stuff##comment\n\t##comment\n\tstuff", "#stuff\n\tstuff");
		// verifyRender("stuff ##comment\n\t##comment\n\tstuff",
		// "stuff\n\tstuff");

		binding.put("key", "value");
		verifyRender("stuff##comment$key\nstuff", "stuff\nstuff");

		binding.put("tool", new Tool());
		verifyRender("$tool.twice[[block##comment\nblock]]", "block\nblockblock\nblock");
		// verifyRender("$tool.twice[[block ##comment\nblock]]",
		// "block\nblockblock\nblock");
		verifyRender("$tool.twice[>block##comment\n", "blockblock\n");
		// verifyRender("$tool.twice[>block ##comment\n", "blockblock\n");
	}

	public void testComparisons() {
		binding.put("i", 3);
		verifyRender("$if($i < 4)[[here]]", "here");
		verifyRender("$if($i < 3)[[here]]", "");
		verifyRender("$if($i < 2)[[here]]", "");
		verifyRender("$if($i <= 4)[[here]]", "here");
		verifyRender("$if($i <= 3)[[here]]", "here");
		verifyRender("$if($i <= 2)[[here]]", "");

		verifyRender("$if($i > 4)[[here]]", "");
		verifyRender("$if($i > 3)[[here]]", "");
		verifyRender("$if($i > 2)[[here]]", "here");
		verifyRender("$if($i >= 4)[[here]]", "");
		verifyRender("$if($i >= 3)[[here]]", "here");
		verifyRender("$if($i >= 2)[[here]]", "here");
	}

	public void testEquals() {
		binding.put("one", "value");
		binding.put("two", "value");
		verifyRender("$if($one == $two)[[here]]", "here");
		verifyRender("$if($one == 0)[[here]]", "");
		verifyRender("$if($one.length == 5)[[here]]", "here");

		verifyRender("$if($one != $two)[[here]]", "");
		verifyRender("$if($one != 0)[[here]]", "here");
		verifyRender("$if($one.length != 5)[[here]]", "");
	}

	public void testEscaping() throws Exception {
		verifyRender("\\$name", "$name");
		verifyRender("\\\\\\$name", "\\$name");

		binding.put("key", "value");
		verifyRender("\\\\$key", "\\value");
	}

	public void testFailure() throws Exception {
		try {
			VientoTemplate template = new VientoTemplate("$whenThisIsUnresolvable($andSoIsThis)");
			template.render(binding);
		} catch (ResolutionFailedException expected) {
			try {
				expected.getMessage();
			} catch (Exception notExpected) {
				fail("Should not get exception when looking at the message of a ResolutionFailedException");
			}
		}

		binding.setExceptionHandler(new ShamExceptionHandler());

		verifyRender("$notHere", "here");

		binding.put("key", "value");
		verifyRender("$key.notHere", "here");
	}

	public void testIRenderable() throws Exception {
		binding.put("key", new IRenderable() {
			public String renderThyself() {
				return "one thing";
			}

			@Override public String toString() {
				return "another";
			}
		});
		verifyRender("$key", "one thing");
		/*
		 * Wish this didn't work. Annotations on methods are not inherited.
		 * Bummer.
		 */
		verifyRender("$key.renderThyself.?", "one thing");
	}

	public void testLineNumbers() throws Exception {
		try {
			verifyRender("line1\r\nline2$breaks\nline3", "");
		} catch (ResolutionFailedException e) {
			assertTrue(e.getMessage().contains("Line: 2, Offset: 6"));
		}
		try {
			verifyRender("abc$breaks", "");
		} catch (ResolutionFailedException e) {
			assertTrue(e.getMessage().contains("Line: 1, Offset: 4"));
		}
		try {
			verifyRender("$breaks", "");
		} catch (ResolutionFailedException e) {
			assertTrue(e.getMessage().contains("Line: 1, Offset: 1"));
		}
	}

	public void testList() throws Exception {
		binding.put("tool", new Tool());
		binding.put("first", "value");
		verifyRender("stuff$tool.takesList([$first, 'second'])stuff", "stuffvalue - second - stuff");
	}

	public void testMap() throws Exception {
		binding.put("tool", new Tool());
		binding.put("first", "value");
		verifyRender("stuff$tool.takesMap({$first : true, 'second':29})stuff", "stuff[value => true][second => 29]stuff");
	}

	public void testMethodCall() throws Exception {
		binding.put("key", "VALUE");

		verifyRender("stuff$key.toLowerCase\nstuff", "stuffvalue\nstuff");
		verifyRender("stuff$key.toLowerCase()\nstuff", "stuffvalue\nstuff");
		verifyRender("stuff$key.toLowerCase ()\nstuff", "stuffvalue\nstuff");
		verifyRender("stuff$key.toLowerCase.toUpperCase.toLowerCase\nstuff", "stuffvalue\nstuff");
		verifyRender("stuff$key.toLowerCase()stuff", "stuffvaluestuff");
	}

	public void testMultilineComment() throws Exception {
		verifyRender("stuff#*comment\n *more #comment\n *# more stuff", "stuff more stuff");
		verifyRender("one#**#two", "onetwo");
	}

	public void testNull() throws Exception {
		binding.put("tool", new Tool());
		verifyRender("stuff$tool.toString(null)stuff", "stuffnullstuff");
	}

	public void testNumber() throws Exception {
		binding.put("key", "value");
		verifyRender("stuff$key.substring(0, 3)stuff", "stuffvalstuff");

		binding.put("tool", new Tool());
		verifyRender("stuff$tool.timesTwo(3)stuff", "stuff6stuff");
		verifyRender("stuff$tool.timesTwo(-3)stuff", "stuff-6stuff");
	}

	public void testNumbersInIdentifiers() throws Exception {
		binding.put("tool", new Tool());
		verifyRender("$tool.numbers123", "here");
	}

	public void testPrototype() {
		verifyRender("$('id')", "$('id')");
	}

	public void testQuickBlock() throws Exception {
		binding.put("tool", new Tool());
		verifyRender("stuff$tool.twice[>[block]\nstuff", "stuff[block][block]\nstuff");
	}

	public void testRender_CachesAST() throws Exception {
		VientoTemplate template = new VientoTemplate("testing");
		assertEquals("testing", template.render(binding));
		assertEquals("testing", template.render(binding));
	}

	public void testSemicolon() throws Exception {
		binding.put("key", "value");
		verifyRender("stuff$key;stuff", "stuffvaluestuff");
		verifyRender("$key.length();.", "5.");
	}

	public void testSilence() throws Exception {
		verifyRender("$!notHere", "");

		binding.put("tool", new Tool());
		verifyRender("$!tool.notHere", "");
		verifyRender("$!tool.yesno(true)", "yes");
	}

	public void testSimpleSubstitution() throws Exception {
		binding.put("key", "value");
		verifyRender("stuff$key\nstuff", "stuffvalue\nstuff");
	}

	public void testSlashR() throws Exception {
		binding.put("key", "value");
		verifyRender("$key\r\nasdf", "value\r\nasdf");
	}

	public void testString() throws Exception {
		binding.put("first", "aaaabbbbcccc");
		verifyRender("stuff$first.replaceAll('b+', 'd')\nstuff", "stuffaaaadcccc\nstuff");
		verifyRender("stuff$first.replaceAll('b+', '')\nstuff", "stuffaaaacccc\nstuff");
	}

	public void testStringBlock() throws Exception {
		binding.put("tool", new Tool());
		verifyRender("stuff$tool.stringTwice(\"{$tool.timesTwo(34)}\")stuff", "stuff{68}{68}stuff");
		verifyRender("stuff$tool.stringTwice(\"hehe\\\"{$tool.timesTwo(34)}\")stuff", "stuffhehe\"{68}hehe\"{68}stuff");
	}

	public void testStringEscape() throws Exception {
		verifyRender("$set(:key, '\\'\\\\\\n\\r\\t')$key", "'\\\n\r\t");
	}

	public void testSubstitution_SpaceAfter() throws Exception {
		binding.put("key", "value");
		verifyRender("stuff $key stuff", "stuff value stuff");

		binding.put("tool", new Tool());
		verifyRender("stuff $tool.yesno(true) stuff", "stuff yes stuff");
		verifyRender("stuff $tool.loop(3)[[block]] stuff", "stuff blockblockblock stuff");
	}

	public void testSymbol() throws Exception {
		binding.put("first", "aaaabbbbcccc");
		verifyRender("stuff$first.replaceAll('b+', d)\nstuff", "stuffaaaadcccc\nstuff");
		verifyRender("stuff$first.replaceAll('b+', :d)\nstuff", "stuffaaaadcccc\nstuff");
	}

	public void testTopLevelHelpers() throws Exception {
		binding.mixin(new Tool());
		verifyRender("stuff$loop (3) [[{$timesTwo(34)}]]stuff", "stuff{68}{68}{68}stuff");
	}

	protected void verifyRender(String input, String output) {
		VientoTemplate template = new VientoTemplate(input);
		assertEquals(output, template.render(binding));
	}

	class Tool {
		public String loop(int times, Block block) {
			StringBuilder buffer = new StringBuilder();
			for (int i = 0; i < times; i++)
				buffer.append(block.evaluate());
			return buffer.toString();
		}

		public String numbers123() {
			return "here";
		}

		public String stringTwice(String string) {
			return string + string;
		}

		public String takesList(List list) {
			StringBuilder buffer = new StringBuilder();
			for (Object each : list) {
				buffer.append(each);
				buffer.append(" - ");
			}
			return buffer.toString();
		}

		public String takesMap(Map<Object, Object> map) {
			StringBuilder buffer = new StringBuilder();
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				buffer.append("[");
				buffer.append(entry.getKey());
				buffer.append(" => ");
				buffer.append(entry.getValue());
				buffer.append("]");
			}
			return buffer.toString();
		}

		public String timesTwo(int i) {
			return String.valueOf(i * 2);
		}

		public String toString(Object object) {
			return String.valueOf(object);
		}

		public String twice(Block block) {
			return block.evaluate() + block.evaluate();
		}

		public String yesno(boolean b) {
			if (b)
				return "yes";
			return "no";
		}
	}
}
