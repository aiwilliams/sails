package org.opensails.sails.model;

public interface IPropertyPath {
    /**
     * The target identifier is used to identify which Object this should be
     * used on. This is mostly a hint, and may be ignored to some degree.
     * {@link #getAllNodes()} will include this in position 0.
     * 
     * @return the identifier of the target for which this exists
     */
    String getTargetIdentifier();

    /**
     * @return the path nodes, including the target identifier
     */
    String[] getAllNodes();

    /**
     * @return the path nodes relative to the target
     */
    String[] getNodes();

    /**
     * @return the name of the property on the target
     */
    String getProperty();
}
