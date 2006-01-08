package org.opensails.sails.action.oem;

import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.annotate.oem.BehaviorHandlerAdapter;

public class ActionFilterHandler extends BehaviorHandlerAdapter {
	public boolean add(BehaviorInstance instance) {
		return true;
	}
}
