package org.opensails.sails.url;

import java.util.List;

import junit.framework.TestCase;

import org.opensails.sails.oem.SailsEventFixture;

public class ActionUrlTest extends TestCase {
	public void testSetParameters_List() {
		ActionUrl url = new ActionUrl(SailsEventFixture.sham());
		url.setParameters((List) null);
	}

	public void testSetParameters_ObjectArray() {
		ActionUrl url = new ActionUrl(SailsEventFixture.sham());
		url.setParameters((Object[]) null);
	}
}
