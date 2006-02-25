package org.opensails.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.opensails.sails.persist.IIdentifiable;

@Entity
public class Harbor implements IIdentifiable {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(targetEntity = Sailboat.class, mappedBy = "harbor")
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
