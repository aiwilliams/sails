/**
 * 
 */
package org.opensails.prevayler.acceptance;

import java.io.Serializable;

import org.opensails.sails.persist.IIdentifiable;

public class IdentifiableString implements IIdentifiable, Serializable {
	final String content;

	final Long id;

	public IdentifiableString(Long id, String content) {
		this.id = id;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return id + "=" + content;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof IdentifiableString)
			return id.equals(((IdentifiableString) object).getId());
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}