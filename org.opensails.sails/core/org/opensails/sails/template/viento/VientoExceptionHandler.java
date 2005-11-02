package org.opensails.sails.template.viento;

import org.opensails.sails.template.IExceptionHandler;
import org.opensails.viento.DefaultExceptionHandler;

/*
 * TODO The org.opensails.viento.ExceptionHandler interface now depends on Viento classes 
 * (TopLevelMethodKey and TargetedMethodKey). If we change IExceptionHandler to take those, 
 * it defeats the purpose of the separation. If we don't, then we need to do something 
 * different here (provide the default Viento exception behavior some other way). Or maybe 
 * just change Viento to not use the MethodKeys when handling exceptions. Since no-one 
 * actually uses this thing, it'll work as is. Come to think of it, why aren't we using 
 * this thing? Is that why our error handling has been less than stellar?
 */
public class VientoExceptionHandler extends DefaultExceptionHandler implements IExceptionHandler {

	public Object resolutionFailed(String methodName, Object[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object resolutionFailed(Object target, String methodName, Object[] args) {
		// TODO Auto-generated method stub
		return null;
	}
}
