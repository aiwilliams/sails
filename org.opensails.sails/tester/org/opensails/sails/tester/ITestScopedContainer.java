package org.opensails.sails.tester;

import org.opensails.rigging.IScopedContainer;

public interface ITestScopedContainer extends ITestContainer, IScopedContainer {
    ITestScopedContainer getContainerInHierarchy(Enum scope);
}
