package org.opensails.sails.tester;

import org.opensails.sails.url.IEventUrl;
import org.opensails.sails.util.RegexHelper;

public class TestUrl {
    protected final IEventUrl eventUrl;

    public TestUrl(IEventUrl eventUrl) {
        this.eventUrl = eventUrl;
    }

    public boolean matches(String regex) {
        return RegexHelper.containsMatch(eventUrl.getAbsoluteUrl(), regex);
    }
    
    @Override
    public String toString() {
        return eventUrl.getActionUrl();
    }
}
