package org.opensails.viento;

public class TopLevelMethodKey implements MethodKey {
	public String methodName;
	public Class[] argClasses;

	public TopLevelMethodKey(String methodName, Class[] argClasses) {
		this.methodName = methodName;
		this.argClasses = argClasses;
	}

	@Override public boolean equals(Object obj) {
		if (obj.getClass() != TopLevelMethodKey.class)
			return false;

		TopLevelMethodKey other = (TopLevelMethodKey) obj;
		return methodName.equals(other.methodName) && argClasses.equals(other.argClasses);
	}

	@Override public int hashCode() {
		return methodName.hashCode() + argClasses.hashCode();
	}

	@Override public String toString() {
		StringBuilder argumentTypes = new StringBuilder();
		for (int i = 0; i < argClasses.length; i++) {
			argumentTypes.append(argClasses[i]);
			if (argClasses.length > i)
				argumentTypes.append(", ");
		}
		return String.format("%s(%s)", methodName, argumentTypes);
	}
}
