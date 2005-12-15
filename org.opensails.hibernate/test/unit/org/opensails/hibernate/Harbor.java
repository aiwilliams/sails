package org.opensails.hibernate;

import java.util.*;

import javax.persistence.*;

import org.opensails.sails.persist.*;

@Entity(access = AccessType.FIELD)
public class Harbor implements IIdentifiable {
	@Id(generate = GeneratorType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "harbor")
	private Collection<Sailboat> sailboats = new ArrayList<Sailboat>();

	public void addSailboat(Sailboat sailboat) {
		sailboats.add(sailboat);
		sailboat.setHarbor(this);
	}

	@Override
	public boolean equals(Object obj) {
		return id == ((Harbor) obj).id;
	}

	public Long getId() {
		return id;
	}

	public Collection<Sailboat> getSailboats() {
		return sailboats;
	}

	@Override
	public int hashCode() {
		return new Long(id).hashCode();
	}

}
