package org.opensails.sails.util;

import org.mortbay.jetty.Server;

/**
 * You must create a main method on your subclass that creates an instance of
 * your subclass and calls {@link #startServer()}. Sometimes I really dislike
 * Java.
 * 
 * @author aiwilliams
 */
public abstract class DevelopmentBoot {
	protected Server server;

	public void startServer() {
		try {
			server = new Server();
			server.addListener(Quick.string(":", port()));
			server.addWebApplication("/", contextDirectory());
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopServer() {
		try {
			server.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Override to define context directory. The default implementation assumes
	 * that the project directory is the context directory. That is, the project
	 * has a WEB-INF subdirectory, etc.
	 * 
	 * @return context directory path, relative to working directory (project
	 *         unless you have changed that)
	 */
	protected String contextDirectory() {
		return ".";
	}

	/**
	 * @return port to run server on
	 */
	protected String port() {
		return "1111";
	}
}