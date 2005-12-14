package org.opensails.sails.adapter;

public abstract class AbstractAdapter<M, W> implements IAdapter<M, W> {
	/**
	 * Defaults to the most used field type of STRING
	 */
	public FieldType getFieldType() {
		return FieldType.STRING;
	}
}
