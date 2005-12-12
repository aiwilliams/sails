package org.opensails.sails.adapter;

import org.opensails.sails.adapter.oem.PrimitiveAdapter;

/**
 * Converts Strings into Objects and back again.
 * 
 * These are used by various clients to deal the way of the web: everything is a
 * String. These do not generate HTML.
 * 
 * @author Adam 'Programmer' Williams
 */
public interface IAdapter {
    IAdapter PRIMITIVE_ADAPTER = new PrimitiveAdapter();

    Class[] getSupportedTypes();

    /**
     * @param fromWeb String or String[] from web to be adapted
     * @return value for model
     * @throws AdaptationException
     */
    Object forModel(Class modelType, Object fromWeb) throws AdaptationException;

    /**
     * @param fromModel value from model to be adapted
     * @return either a String or String[]
     * @throws AdaptationException
     */
    Object forWeb(Class modelType, Object fromModel) throws AdaptationException;
}
