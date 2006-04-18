package org.opensails.viento;

public class TargetedMethodKey extends TopLevelMethodKey {
	public Class targetClass;
	
	public TargetedMethodKey(Class targetClass, String methodName, Class[] argClasses) {
		super(methodName, argClasses);
		this.targetClass = targetClass;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != TargetedMethodKey.class)
			return false;
		
		TargetedMethodKey other = (TargetedMethodKey) obj;
		return targetClass == other.targetClass
			&& methodName.equals(other.methodName)
			&& argClasses.equals(other.argClasses);
	}
	
	@Override
	public int hashCode() {
		return methodName.hashCode() + argClasses.hashCode() + targetClass.hashCode();
	}
}
