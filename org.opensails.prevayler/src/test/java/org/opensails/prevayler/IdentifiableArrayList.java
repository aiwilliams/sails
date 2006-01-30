package org.opensails.prevayler;

import java.io.Serializable;
import java.util.ArrayList;

import org.opensails.sails.persist.IIdentifiable;

public class IdentifiableArrayList implements IIdentifiable, Serializable {

	private final Long id;

	private final ArrayList arrayList;

	public IdentifiableArrayList(Long id, ArrayList arrayList) {
		this.id = id;
		this.arrayList = arrayList;
	}

	public Long getId() {
		return id;
	}

	public ArrayList getArrayList() {
		return arrayList;
	}

	@Override
	public String toString() {
		return id + ":" + arrayList.toString();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof IdentifiableArrayList)
			return id.equals(((IdentifiableArrayList) object).getId());
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
