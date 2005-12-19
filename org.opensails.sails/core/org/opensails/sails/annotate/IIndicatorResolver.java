package org.opensails.sails.annotate;

/**
 * Given an IndicatorContext, resolves the final Indicator for an action.
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IIndicatorResolver {
	/**
	 * @param context The result of collecting the annotations of a single type
	 *        on a given context
	 * @return the decided Indicator for an action
	 */
	Indicator resolve(IndicatorContext context);
}
