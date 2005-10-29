package org.opensails.sails.adapter.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.IAdapter;

public class PrimitiveAdapter implements IAdapter {
    public static final Class[] SUPPORTED_TYPES;

    private static final Map<Class<?>, IAdapter> ADAPTERS;

    static {
        SUPPORTED_TYPES = new Class<?>[] { String.class, int.class, float.class, long.class, char.class, byte.class, short.class, double.class, boolean.class };
        ADAPTERS = new HashMap<Class<?>, IAdapter>();
        ADAPTERS.put(int.class, new IntAdapter());
        ADAPTERS.put(float.class, new FloatAdapter());
        ADAPTERS.put(long.class, new LongAdapter());
        ADAPTERS.put(boolean.class, new BooleanAdapter());
        ADAPTERS.put(String.class, new StringAdapter());
        // TODO: Add remaining adapters
    }

    public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
        return ADAPTERS.get(modelType).forModel(modelType, fromWeb);
    }

    public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
        return ADAPTERS.get(modelType).forWeb(modelType, fromModel);
    }

    public Class[] getSupportedTypes() {
        return SUPPORTED_TYPES;
    }
    public static class BooleanAdapter implements IAdapter {
        public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
            return Boolean.valueOf((String)fromWeb);
        }

        public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
            return fromModel == null ? null : fromModel.toString();
        }

        public Class[] getSupportedTypes() {
            return new Class[] {boolean.class};
        }

    }
    
    public static class FloatAdapter implements IAdapter {
        public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
            return Float.valueOf((String)fromWeb);
        }

        public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
            return fromModel == null ? null : fromModel.toString();
        }

        public Class[] getSupportedTypes() {
            return new Class[] {float.class};
        }
    }
    
    public static class IntAdapter implements IAdapter {
        public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
            return Integer.valueOf((String)fromWeb);
        }
        public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
            return fromModel == null ? null : fromModel.toString();
        }

        public Class[] getSupportedTypes() {
            return new Class[] {int.class};
        }
    }

    public static class LongAdapter implements IAdapter {
        public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
            return Long.valueOf((String)fromWeb);
        }

        public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
            return fromModel == null ? null : fromModel.toString();
        }

        public Class[] getSupportedTypes() {
            return new Class[] {long.class};
        }
    }

    public static class StringAdapter implements IAdapter {
        public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
            return fromWeb;
        }

        public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
            return fromModel == null ? "" : fromModel.toString();
        }

        public Class[] getSupportedTypes() {
            return new Class[] {String.class};
        }

    }
}
