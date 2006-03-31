package org.opensails.sails.adapter;

public abstract class AbstractAdapter<M, W> implements IAdapter<M, W> {
	public M forModel(AdaptationTarget adaptationTarget, W fromWeb) throws AdaptationException {
		return forModel(adaptationTarget.getTargetClass(), fromWeb);
	}

	public W forWeb(AdaptationTarget adaptationTarget, M fromModel) throws AdaptationException {
		return forWeb(adaptationTarget.getTargetClass(), fromModel);
	}

	/**
	 * Defaults to the most used field type of STRING
	 */
	public FieldType getFieldType() {
		return FieldType.STRING;
	}

	/**
	 * @param fromWeb value from web to be adapted
	 * @return value for model
	 * @throws AdaptationException
	 */
	protected abstract M forModel(Class<? extends M> modelType, W fromWeb) throws AdaptationException;

	/**
	 * @param fromModel value from model to be adapted
	 * @return value for web
	 * @throws AdaptationException
	 */
	protected abstract W forWeb(Class<? extends M> modelType, M fromModel) throws AdaptationException;
}
