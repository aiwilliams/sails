package org.opensails.sails.adapter.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.adapter.AbstractAdapter;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.IAdapter;

/**
 * Supports adapting primitive and wrapper objects forModel and forWeb.
 * 
 * @author Adam 'Programmer' Williams
 */
public class PrimitiveAdapter extends AbstractAdapter<Object, Object> {
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
        // TODO: Add remaining adapters (like arrays)
    }

	@SuppressWarnings("unchecked")
	public Object forModel(Class<? extends Object> modelType, Object fromWeb) throws AdaptationException {
        return ADAPTERS.get(modelType).forModel(modelType, fromWeb);
    }

    @SuppressWarnings("unchecked")
	public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
        return ADAPTERS.get(modelType).forWeb(modelType, fromModel);
    }

    public static class BooleanAdapter extends AbstractAdapter<Boolean, String> {
    	final static String CHECKBOX_VALUE = "checked";
        public Boolean forModel(Class<? extends Boolean> modelType, String fromWeb) throws AdaptationException {
        	if (CHECKBOX_VALUE.equals(fromWeb)) return true;
            return Boolean.valueOf(fromWeb);
        }

        public String forWeb(Class<? extends Boolean> modelType, Boolean fromModel) throws AdaptationException {
            return fromModel == null ? null : fromModel.toString();
        }
    }
    
    public static class ByteAdapter extends AbstractAdapter<Byte, String> {
    	public Byte forModel(Class<? extends Byte> modelType, String fromWeb) throws AdaptationException {
    		return Byte.valueOf(fromWeb);
    	}
    	
    	public String forWeb(Class<? extends Byte> modelType, Byte fromModel) throws AdaptationException {
    		return fromModel == null ? null : fromModel.toString();
    	}
    }
    
    public static class CharAdapter extends AbstractAdapter<Character, String> {
    	public Character forModel(Class<? extends Character> modelType, String fromWeb) throws AdaptationException {
    		return fromWeb.charAt(0);
    	}
    	
    	public String forWeb(Class<? extends Character> modelType, Character fromModel) throws AdaptationException {
    		return fromModel == null ? null : fromModel.toString();
    	}
    }
    
    public static class DoubleAdapter extends AbstractAdapter<Double, String> {
    	public Double forModel(Class<? extends Double> modelType, String fromWeb) throws AdaptationException {
    		return Double.valueOf(fromWeb);
    	}
    	
    	public String forWeb(Class<? extends Double> modelType, Double fromModel) throws AdaptationException {
    		return fromModel == null ? null : fromModel.toString();
    	}
    }
    public static class FloatAdapter extends AbstractAdapter<Float, String> {
        public Float forModel(Class<? extends Float> modelType, String fromWeb) throws AdaptationException {
            return Float.valueOf(fromWeb);
        }

        public String forWeb(Class<? extends Float> modelType, Float fromModel) throws AdaptationException {
            return fromModel == null ? null : fromModel.toString();
        }
    }
    
    public static class IntAdapter extends AbstractAdapter<Integer, String> {
        public Integer forModel(Class<? extends Integer> modelType, String fromWeb) throws AdaptationException {
            return Integer.valueOf(fromWeb);
        }
        public String forWeb(Class<? extends Integer> modelType, Integer fromModel) throws AdaptationException {
            return fromModel == null ? null : fromModel.toString();
        }
    }
    
    public static class LongAdapter extends AbstractAdapter<Long, String> {
        public Long forModel(Class<? extends Long> modelType, String fromWeb) throws AdaptationException {
            return Long.valueOf(fromWeb);
        }

        public String forWeb(Class<? extends Long> modelType, Long fromModel) throws AdaptationException {
            return fromModel == null ? null : fromModel.toString();
        }
    }
    
    public static class ShortAdapter extends AbstractAdapter<Short, String> {
    	public Short forModel(Class<? extends Short> modelType, String fromWeb) throws AdaptationException {
    		return Short.valueOf(fromWeb);
    	}
    	
    	public String forWeb(Class<? extends Short> modelType, Short fromModel) throws AdaptationException {
    		return fromModel == null ? null : fromModel.toString();
    	}
    }

    public static class StringAdapter extends AbstractAdapter<String, String> {
        public String forModel(Class<? extends String> modelType, String fromWeb) throws AdaptationException {
            return fromWeb;
        }

        public String forWeb(Class<? extends String> modelType, String fromModel) throws AdaptationException {
            return fromModel == null ? "" : fromModel.toString();
        }
    }
}
