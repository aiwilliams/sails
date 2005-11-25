package org.opensails.sails.oem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.ISailsEventListener;

/**
 * A Flash exposes values for use by the next ActionEvent.
 */
public class Flash implements Map<Object, Object>, ISailsEventListener {
	/**
	 * The key used to store the Flash in any scope that is accessable to a
	 * TemplateContext.
	 */
	public static final String KEY = "flash";

	/**
	 * The flash key used to store a notification message.
	 */
	public static final String NOTIFICATION = "notification";

	/**
	 * The flash key used to store a problem message.
	 */
	public static final String PROBLEM = "problem";

	/**
	 * @param session
	 * @return true if there is a Flash in the session
	 */
	public static boolean exists(HttpSession session) {
		return session != null && session.getAttribute(KEY) != null;
	}

	public static Flash load(HttpServletRequest request, HttpSession session) {
		return session != null ? Flash.load(session) : Flash.load(request);
	}

	private static void install(Flash flash, HttpServletRequest request) {
		request.setAttribute(Flash.KEY, flash);
	}

	private static void install(Flash flash, HttpSession session) {
		session.setAttribute(Flash.KEY, flash);
	}

	private static Flash load(HttpServletRequest request) {
		Flash flash = (Flash) request.getAttribute(Flash.KEY);
		if (flash == null) {
			flash = new Flash();
			install(flash, request);
		}
		return flash;
	}

	private static Flash load(HttpSession session) {
		Flash flash = (Flash) session.getAttribute(Flash.KEY);
		if (flash == null) {
			flash = new Flash();
			install(flash, session);
		}
		return flash;
	}

	protected Map<Object, Object> availableInNextAction = new HashMap<Object, Object>(2);

	protected Map<Object, Object> expiring = new HashMap<Object, Object>(2);

	public void beginDispatch(ISailsEvent event) {
		expiring.clear();
		expiring.putAll(availableInNextAction);
		availableInNextAction.clear();
	}

	public void clear() {
		expiring.clear();
		availableInNextAction.clear();
	}

	public boolean containsKey(Object key) {
		return keySet().contains(key);
	}

	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	public void endDispatch(ISailsEvent event) {
		expiring.clear();
	}

	public Set<Map.Entry<Object, Object>> entrySet() {
		Set<Map.Entry<Object, Object>> entrySet = new HashSet<Map.Entry<Object, Object>>(availableInNextAction.entrySet());
		for (Map.Entry<Object, Object> entry : expiring.entrySet())
			if (!availableInNextAction.containsKey(entry.getKey())) entrySet.add(entry);
		return entrySet;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public Object get(Object key) {
		Object value = availableInNextAction.get(key);
		return value == null ? expiring.get(key) : value;
	}

	@Override
	public int hashCode() {
		return expiring.hashCode() ^ availableInNextAction.hashCode();
	}

	public boolean isEmpty() {
		return keySet().isEmpty();
	}

	public Set<Object> keySet() {
		Set<Object> keySet = new HashSet<Object>(availableInNextAction.keySet());
		keySet.addAll(expiring.keySet());
		return keySet;
	}

	public void notification(String message) {
		put(NOTIFICATION, message);
	}

	public void problem(String message) {
		put(PROBLEM, message);
	}

	public Object put(Object key, Object value) {
		availableInNextAction.put(key, value);
		return expiring.put(key, value);
	}

	public void putAll(Map<? extends Object, ? extends Object> t) {
		availableInNextAction.putAll(t);
	}

	public Object remove(Object key) {
		availableInNextAction.remove(key);
		return expiring.remove(key);
	}

	public void sessionCreated(ISailsEvent event, HttpSession session) {
		if (event.getRequest().getAttribute(KEY) == this) session.setAttribute(KEY, this);
	}

	public int size() {
		return keySet().size();
	}

	public Collection<Object> values() {
		Set<Object> keys = new HashSet<Object>(availableInNextAction.keySet());
		keys.addAll(expiring.keySet());
		List<Object> values = new ArrayList<Object>(keys.size());
		for (Object key : keys)
			values.add(get(key));
		return values;
	}
}
