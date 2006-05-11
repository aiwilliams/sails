package org.opensails.sails.tester;

import org.opensails.rigging.IScopedContainer;

public interface ITesterScopedContainer extends ITesterContainer, IScopedContainer {
    ITesterScopedContainer getContainerInHierarchy(Enum scope);
}
