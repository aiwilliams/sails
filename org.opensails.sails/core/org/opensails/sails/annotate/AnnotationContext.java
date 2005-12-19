package org.opensails.sails.annotate;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.action.oem.Action;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.oem.IActionEventProcessor;

/**
 * Used by Sails to obtain information about the annotations on an
 * IEventProcessingContext (controller/component)
 * 
 * @author Adam 'Programmer' Williams
 */
public class AnnotationContext {
	protected final Class<? extends IEventProcessingContext<? extends IActionEventProcessor>> implementationClass;

	public AnnotationContext(Class<? extends IEventProcessingContext<? extends IActionEventProcessor>> implementationClass) {
		this.implementationClass = implementationClass;
	}

	public List<BehaviorInstance> behaviors(Action action) {
		ArrayList<BehaviorInstance> behaviors = new ArrayList<BehaviorInstance>();
		for (Annotation annotation : implementationClass.getAnnotations())
			if (annotation.annotationType().isAnnotationPresent(Behavior.class)) behaviors.add(new BehaviorInstance(annotation, ElementType.TYPE));
		for (Annotation annotation : action.getAnnotations())
			if (annotation.annotationType().isAnnotationPresent(Behavior.class)) behaviors.add(new BehaviorInstance(annotation, ElementType.METHOD));
		return behaviors;
	}

	public Annotation[] indicators() {
		return null;
	}
}
