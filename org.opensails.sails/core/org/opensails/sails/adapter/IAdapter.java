package org.opensails.sails.adapter;

import org.opensails.sails.adapter.oem.PrimitiveAdapter;

/**
 * Converts Strings into Objects and back again.
 * 
 * These are used by various clients to deal the way of the web: everything is a
 * String, except when it isn't. These do not generate HTML.
 * 
 * @author aiwilliams
 */
public interface IAdapter<M, W> {
	IAdapter PRIMITIVE_ADAPTER = new PrimitiveAdapter();

	/**
	 * @param adaptationTarget
	 * @param fromWeb value from web to be adapted
	 * @return value for model
	 * @throws AdaptationException
	 */
	M forModel(AdaptationTarget adaptationTarget, W fromWeb) throws AdaptationException;

	/**
	 * @param fromWeb value from web to be adapted
	 * @return value for model
	 * @throws AdaptationException
	 */
	M forModel(Class<? extends M> modelType, W fromWeb) throws AdaptationException;

	/**
	 * @param adaptationTarget
	 * @param fromModel value from model to be adapted
	 * @return value for web
	 * @throws AdaptationException
	 */
	W forWeb(AdaptationTarget adaptationTarget, M fromModel) throws AdaptationException;

	/**
	 * @param fromModel value from model to be adapted
	 * @return value for web
	 * @throws AdaptationException
	 */
	W forWeb(Class<? extends M> modelType, M fromModel) throws AdaptationException;

	/**
	 * @return the 'web type' the adapter converts to and from
	 */
	FieldType getFieldType();
}
