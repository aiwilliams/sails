package org.opensails.sails.util;

import junit.framework.TestCase;

public class DoubleKeyedMapTest extends TestCase {
	protected DoubleKeyedMap<String, String, String> map = new DoubleKeyedMap<String, String, String>();

	public void testGetPutAndRemove() {
		map.put("level1Key", "level2Key", "value");

		assertEquals("value", map.get("level1Key", "level2Key"));
		assertNull(map.get("level1Key", "nonExistentKey"));
		assertNull(map.get("nonExistentKey", "level2Key"));

		map.remove("level1Key", "level2Key");
		assertNull("Map should not have value after remove.", map.get("level1Key", "level2Key"));
	}

	public void testPutAll_MultiLevelMap() throws Exception {
		map.put("level1Key", "level2Key", "value");
		DoubleKeyedMap<String, String, String> anotherMap = new DoubleKeyedMap<String, String, String>();
		anotherMap.put("level1Key", "differentLevel2Key", "differentValue");
		anotherMap.put("differentLevel1Key", "differentLevel2Key", "anotherValue");
		map.putAll(anotherMap);
		assertEquals("value", map.get("level1Key", "level2Key"));
		assertEquals("differentValue", map.get("level1Key", "differentLevel2Key"));
		assertEquals("anotherValue", map.get("differentLevel1Key", "differentLevel2Key"));

		anotherMap.put("differentLevel1Key", "anotherLevel2Key", "anotherValue");
		assertNull(map.get("differentLevel1Key", "anotherLevel2Key"));
	}
}
