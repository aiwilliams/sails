package org.opensails.sails.oem;

import junit.framework.TestCase;

import org.opensails.sails.model.AccessorException;
import org.opensails.sails.tester.util.CollectionAssert;
import org.opensails.sails.util.ClassInstanceAccessor;

public class ClassInstanceAccessorTest extends TestCase {
    protected ClassInstanceAccessor accessor;
    protected Bean bean;

    public ClassInstanceAccessorTest(String name) {
        super(name);
    }

    public void testGetFloatValue() {
        assertEquals(new Float(100.25), accessor.getProperty(bean, "floatProperty"));
    }

    public void testGetIntValue() {
        assertEquals(new Integer(100), accessor.getProperty(bean, "intProperty"));
    }

    public void testGetPropertyNames() {
        CollectionAssert.containsOnly(new String[] { "methodOnlyProperty", "publicFieldProperty", "protectedFieldProperty", "getterOnlyProperty", "class",
            "setterGetterProperty", "intProperty", "floatProperty", "doubleProperty" }, accessor.getPropertyNames());
    }

    public void testGetPropertyType() {
        assertEquals(String.class, accessor.getPropertyType("setterGetterProperty"));
        assertEquals(String.class, accessor.getPropertyType("getterOnlyProperty"));
        assertEquals(String.class, accessor.getPropertyType("methodOnlyProperty"));
        try {
            accessor.getPropertyType("nonExistentProperty");
            fail("need to let the developer know there is no property by this name");
        } catch (AccessorException expected) {}
    }

    public void testGetPropertyValue() {
        assertEquals("originalSetterGetterPropertyValue", accessor.getProperty(bean, "setterGetterProperty"));
        try {
            accessor.getProperty(bean, "nonExistentProperty");
            fail("need to let the developer know there is no property by this name");
        } catch (AccessorException expected) {}
    }

    public void testIsProperty() {
        accessor = new ClassInstanceAccessor(Klass.class);
        assertTrue("klassProperty should be a property of the Klass.", accessor.isProperty("klassProperty"));
        assertTrue("nonExistentProperty should NOT be a property of the Klass.", !accessor.isProperty("nonExistentProperty"));

        accessor = new ClassInstanceAccessor(SubKlass.class);
        assertTrue("klassProperty should be a property of the SubKlass.", accessor.isProperty("klassProperty"));

        accessor = new ClassInstanceAccessor(Interface.class);
        assertTrue("interfaceProperty should be a property of the Interface.", accessor.isProperty("interfaceProperty"));
        assertTrue("nonExistentProperty should NOT be a property of the Interface.", !accessor.isProperty("nonExistentProperty"));

        accessor = new ClassInstanceAccessor(SubInterface.class);
        assertTrue("interfaceProperty should be a property of the SubInterface.", accessor.isProperty("interfaceProperty"));
    }

    public void testIsPropertyReadable() {
        assertTrue(accessor.isPropertyReadable("setterGetterProperty"));
        assertTrue(accessor.isPropertyReadable("protectedFieldProperty"));
        assertFalse(accessor.isPropertyReadable("nonExistentProperty"));
        assertFalse("accessPrivates is false", accessor.isPropertyReadable("privateFieldProperty"));
        assertFalse("there is no field and no getter", accessor.isPropertyReadable("methodOnlyProperty"));
    }

    public void testSetProperty() {
        checkSetProperty("doubleProperty", new Double(44.5), "nonDoubleValue");
        checkSetProperty("intProperty", new Integer(44), "nonIntValue");
        checkSetProperty("floatProperty", new Float(88.3), "nonFloatValue");
        checkSetProperty("setterGetterProperty", "newValue", new Integer(32));
        // make sure we can set when there is a field
        checkSetProperty("getterOnlyProperty", "newValue", new Integer(21));
    }

    protected void checkSetProperty(String propertyName, Object validValue, Object invalidValue) {
        accessor.setProperty(bean, propertyName, validValue);
        assertEquals(validValue, accessor.getProperty(bean, propertyName));

        try {
            accessor.setProperty(bean, propertyName, invalidValue);
            fail("Should throw an exception if given the wrong type. PropertyName: " + propertyName);
        } catch (RuntimeException e) {
            assertEquals(validValue, accessor.getProperty(bean, propertyName));
        }
    }

    protected void setUp() {
        accessor = new ClassInstanceAccessor(Bean.class);
        bean = new Bean();
    }

    public class AnotherBean {
        protected double doubleProperty = 55.6;

        protected float floatProperty = 100.25f;
        protected int intProperty = 100;
        protected String propertyBeanDoesntHave = "originalPropertyBeanDoesntHave";

        public double getDoubleProperty() {
            return doubleProperty;
        }

        public float getFloatProperty() {
            return floatProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public String getPropertyBeanDoesntHave() {
            return propertyBeanDoesntHave;
        }

        public void setDoubleProperty(double aDoubleProperty) {
            doubleProperty = aDoubleProperty;
        }

        public void setFloatProperty(float existentFloat) {
            this.floatProperty = existentFloat;
        }

        public void setIntProperty(int existentInt) {
            this.intProperty = existentInt;
        }

        public void setPropertyBeanDoesntHave(String propertyBeanDoesntHave) {
            this.propertyBeanDoesntHave = propertyBeanDoesntHave;
        }
    }

    public class Bean {
        public String publicFieldProperty = "originalPublicFieldPropertyValue";
        private String privateFieldProperty = "originalPrivateFieldPropertyValue";
        protected double doubleProperty = 55.6;
        protected float floatProperty = 100.25f;
        protected String getterOnlyProperty = "originalGetterOnlyPropertyValue";
        protected int intProperty = 100;
        protected String protectedFieldProperty = "originalProtectedFieldPropertyValue";
        protected String setterGetterProperty = "originalSetterGetterPropertyValue";

        public double getDoubleProperty() {
            return doubleProperty;
        }

        public float getFloatProperty() {
            return floatProperty;
        }

        public String getGetterOnlyProperty() {
            return getterOnlyProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public String getSetterGetterProperty() {
            return setterGetterProperty;
        }

        public void setDoubleProperty(double aDoubleProperty) {
            doubleProperty = aDoubleProperty;
        }

        public void setFloatProperty(float existentFloat) {
            this.floatProperty = existentFloat;
        }

        public void setIntProperty(int existentInt) {
            this.intProperty = existentInt;
        }

        public void setMethodOnlyProperty(String value) {}

        public void setSetterGetterProperty(String existentsetterGetterProperty) {
            this.setterGetterProperty = existentsetterGetterProperty;
        }
    }

    public interface Interface {
        int getInterfaceProperty();

        void setInterfaceProperty(int newInt);
    }

    public class Klass {
        public int getKlassProperty() {
            return 4;
        }

        public void setKlassProperty(int newKlassValue) {}
    }

    public interface SubInterface extends Interface {}

    public class SubKlass extends Klass {}
}
