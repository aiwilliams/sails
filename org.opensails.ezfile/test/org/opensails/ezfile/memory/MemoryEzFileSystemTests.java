package org.opensails.ezfile.memory;

import junit.framework.TestCase;

import org.opensails.ezfile.EzDir;
import org.opensails.ezfile.EzFile;

public class MemoryEzFileSystemTests extends TestCase {
	public void testFile() throws Exception {
		MemoryEzFileSystem fs = new MemoryEzFileSystem();
		EzFile ezFile = fs.file("/a/b");
		assertFalse(ezFile.exists());
		assertFalse(ezFile.isDirectory());

		EzDir ezDir = fs.mkdir("/a/b");
		assertTrue(ezDir.exists());
		assertTrue(fs.file("/a").exists());
		assertTrue(fs.file("/a").isDirectory());
		try {
			ezFile.save("sometext");
			fail("Cannot save text to a directory!");
		} catch (IllegalStateException e) {
		}

		ezDir.mkdir("c");
		EzFile newDir = fs.file("/a/b/c");
		assertTrue(newDir.exists());
		assertTrue(newDir.isDirectory());

		EzFile file = fs.file("x");
		assertFalse(file.exists());
		file.save("sometext");
		assertTrue(file.exists());
		assertFalse(file.isDirectory());
		try {
			file.asDir();
			fail("Can't become a dir once written to");
		} catch (IllegalStateException e) {
		}

		file = fs.file("y");
		assertEquals("/y", file.getPath());
		EzDir converted = file.asDir();
		assertFalse(converted.exists());
		assertTrue(fs.fileImpl("y").isDirectory);
	}

	public void testFileImpls() throws Exception {
		MemoryEzFileSystem fs = new MemoryEzFileSystem();
		MemoryEzFileImpl root = fs.rootImpl();
		assertTrue(root.exists);
		assertTrue(root.isDirectory);

		MemoryEzFileImpl a = fs.fileImpl("/a");
		assertFalse(a.exists);
		assertFalse(a.isDirectory);

		MemoryEzFileImpl b = fs.fileImpl("/a/b");
		assertFalse(a.exists);
		assertFalse(a.isDirectory);
		assertSame(a, b.parent());

		b.mkdirs();
		assertTrue(a.exists);
		assertTrue(a.isDirectory);
		assertFalse(b.exists);
		assertFalse(b.isDirectory);

		assertSame(a, fs.fileImpl("a"));

		MemoryEzFileImpl nb = fs.fileImpl("b");
		assertNotSame(b, nb);
		assertEquals("/b", nb.path);

		nb.mkdirs();
		assertFalse(nb.exists);
		assertFalse(nb.isDirectory);

		nb.save("text");
		assertTrue(nb.exists);
		assertFalse(nb.isDirectory);
		assertEquals("text", nb.text);

		fs.workingDirectory("/a");
		assertSame(b, fs.fileImpl("/a/b"));
		assertSame(b, fs.fileImpl("b"));
		assertSame(root, fs.fileImpl("/"));
		assertSame(nb, fs.fileImpl("/b"));
		assertSame(a, fs.fileImpl("/a"));
		assertNotSame(a, fs.fileImpl("a"));
	}
}
