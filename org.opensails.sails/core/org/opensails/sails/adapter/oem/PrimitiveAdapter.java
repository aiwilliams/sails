package org.opensails.sails.adapter.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.IAdapter;

/**
 * Supports adapting primitive and wrapper objects forModel and forWeb.
 * 
 * @author Adam 'Programmer' Williams
 */
public class PrimitiveAdapter implements IAdapter {
    public static final Class[] SUPPORTED_TYPES;

    private static final Map<Class<?>, IAdapter> ADAPTERS;

    static {
        SUPPORTED_TYPES = new Class<?>[] { String.class, int.class, Integer.class, float.class, Float.class, long.class, Long.class, char.class, Character.class, byte.class, Byte.class, short.class, Short.class, double.class, Double.class, boolean.class, Boolean.class };
        ADAPTERS = new HashMap<Class<?>, IAdapter>();
        ADAPTERS.put(int.class, new IntAdapter());
        ADAPTERS.put(Integer.class, new IntAdapter());
        ADAPTERS.put(float.class, new FloatAdapter());
        ADAPTERS.put(Float.class, new FloatAdapter());
        ADAPTERS.put(long.class, new LongAdapter());
        ADAPTERS.put(Long.class, new LongAdapter());
        ADAPTERS.put(char.class, new CharAdapter());
        ADAPTERS.put(Character.class, new CharAdapter());
        ADAPTERS.put(byte.class, new ByteAdapter());
        ADAPTERS.put(Byte.class, new ByteAdapter());
        ADAPTERS.put(short.class, new ShortAdapter());
        ADAPTERS.put(Short.class, new ShortAdapter());
        ADAPTERS.put(double.class, new DoubleAdapter());
        ADAPTERS.put(Double.class, new DoubleAdapter());
        ADAPTERS.put(boolean.class, new BooleanAdapter());
        ADAPTERS.put(Boolean.class, new BooleanAdapter());
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
            return new Class[] {boolean.class, Boolean.class};
        }
    }
    
    public static class ByteAdapter implements IAdapter {
    	public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
    		return Byte.valueOf((String)fromWeb);
    	}
    	
    	public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
    		return fromModel == null ? null : fromModel.toString();
    	}
    	
    	public Class[] getSupportedTypes() {
    		return new Class[] {byte.class, Byte.class};
    	}
    }
    
    public static class CharAdapter implements IAdapter {
    	public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
    		return ((String)fromWeb).charAt(0);
    	}
    	
    	public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
    		return fromModel == null ? null : fromModel.toString();
    	}
    	
    	public Class[] getSupportedTypes() {
    		return new Class[] {char.class, Character.class};
    	}
    	
    }
    
    public static class DoubleAdapter implements IAdapter {
    	public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
    		return Double.valueOf((String)fromWeb);
    	}
    	
    	public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
    		return fromModel == null ? null : fromModel.toString();
    	}
    	
    	public Class[] getSupportedTypes() {
    		return new Class[] {double.class, Double.class};
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
            return new Class[] {float.class, Float.class};
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
            return new Class[] {int.class, Integer.class};
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
            return new Class[] {long.class, Long.class};
        }
    }
    
    public static class ShortAdapter implements IAdapter {
    	public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
    		return Short.valueOf((String)fromWeb);
    	}
    	
    	public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
    		return fromModel == null ? null : fromModel.toString();
    	}
    	
    	public Class[] getSupportedTypes() {
    		return new Class[] {short.class, Short.class};
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
