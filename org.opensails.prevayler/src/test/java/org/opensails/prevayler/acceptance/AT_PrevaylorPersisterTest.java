package org.opensails.prevayler.acceptance;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;

public class AT_PrevaylorPersisterTest extends TestCase {

	protected String classpath;

	public void testLifecycle() throws Exception {
		assertEquals("nothing persisted", read());
		write("1=funny");
		assertEquals("1=funny", read());
	}

	public void testRead() throws Exception {
		assertEquals("nothing persisted", read());
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		classpath = "lib/prevayler-2.02.006.jar;bin;../org.opensails.sails/tools/eclipse/classes";
		destroyPrevalence();
	}

	@Override
	protected void tearDown() throws Exception {
		destroyPrevalence();
		super.tearDown();
	}

	protected boolean destroyPrevalence() {
		return deleteDir(new File("PrevalenceBase"));
	}

	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		return dir.delete();
	}

	private void write(String args) throws IOException, InterruptedException {
		createJavaProcess(classpath, Write.class, args);
	}

	protected String read() throws IOException, InterruptedException {
		Process process = createJavaProcess(classpath, Read.class);
		String output = inputStreamToString(process.getInputStream());
		return cleanupPrevaylorOutput(output);
	}

	private String cleanupPrevaylorOutput(String output) {
		return output.replaceAll("Reading .*\\.transactionLog\\.\\.\\.", "").trim();
	}

	protected Process createJavaProcess(String classpath, Class clazz) throws IOException, InterruptedException {
		return createJavaProcess(classpath, clazz, null);
	}

	protected Process createJavaProcess(String classpath, Class clazz, String args) throws IOException, InterruptedException {
		StringBuffer command = new StringBuffer();
		command.append("java -cp ");
		command.append(classpath);
		command.append(" ");
		command.append(clazz.getName());
		command.append(" ");
		if (args != null && args.length() != 0)
			command.append(args);

		Process process = Runtime.getRuntime().exec(command.toString());
		process.waitFor();
		return process;
	}

	private String inputStreamToString(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer buffer = new StringBuffer();
		int character = 0;

		while ((character = reader.read()) != -1) {
			buffer.append(Character.valueOf((char) character));
		}

		reader.close();

		return buffer.toString().trim();
	}

}
