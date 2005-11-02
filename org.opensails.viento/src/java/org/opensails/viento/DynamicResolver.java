package org.opensails.viento;

public interface DynamicResolver {
	CallableMethod find(TopLevelMethodKey key);
}
