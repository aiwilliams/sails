package org.opensails.sails.controller.oem;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.ISailsEvent;
import org.opensails.sails.SailsException;

public class ParameterMismatchException extends SailsException {
    private static final long serialVersionUID = 3329615758011603024L;

    protected final Method actionMethod;
    protected final ISailsEvent event;
    protected final Object[] values;

    public ParameterMismatchException(ISailsEvent event, Method actionMethod, Object[] values) {
        super("Parameters types did not match");
        this.event = event;
        this.actionMethod = actionMethod;
        this.values = values;
    }

    public String getAction() {
        return event.getActionName();
    }

    public Class[] getActionParameterTypes() {
        return actionMethod.getParameterTypes();
    }

    public Object[] getActionParameterValues() {
        return values;
    }

    public String getController() {
        return event.getProcessorName();
    }

    public String getMessage() {
        StringBuffer message = new StringBuffer();
        message.append(event);
        message.append(" expected parameter types &lt;");
        message.append(getTypesString(getActionParameterTypes()));
        message.append("&gt; but was &lt;");
        message.append(getValuesString(getActionParameterValues()));
        message.append("&gt;");
        return message.toString();
    }

    protected String getValuesString(Object[] values) {
        Class[] valueTypes = new Class[values.length];
        for (int i = 0; i < values.length; i++)
            valueTypes[i] = values[i].getClass();
        return getTypesString(valueTypes);
    }

    protected String getTypesString(Class[] types) {
        String[] typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++)
            typeNames[i] = types[i].getName();
        return StringUtils.join(typeNames, ", ");
    }
}
