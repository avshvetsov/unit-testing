package org.shvetsov.Chapter3_UnitTestsWithNoCollaborators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Write unit tests which will verify the following properties of the java.util.HashMap class:
 * <p>• an object stored with the put() method can be retrieved with the get() method,
 * <p>• adding a second object with the same key results in the old value being replaced ,
 * <p>• the clear() method removes all its content,
 * <p>• the null value can be used as a key,
 * <p>Additional requirement:
 * <p>• use the appropariate JUnit annotations to create a fresh, empty map before each test method is
 * called (see Section 3.8)
 */
@DisplayName("3.11.4. HashMap")
public class HashMapTest {

    public static final String KEY = "key";
    public static final String VALUE = "some value";
    private Map<String, String> map;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
    }

    @Test
    void hashMapPutGetTest() {
        String value = "some object";

        map.put(KEY, value);

        assertThat(map.get(KEY)).isEqualTo(value);
    }

    @Test
    void hashMapSecondObjectTest() {
        String value = VALUE;
        String newValue = "new value";
        map.put(KEY, value);
        assertThat(map.containsValue(value)).isTrue();

        map.put(KEY, newValue);

        assertThat(map.containsValue(value)).isFalse();
        assertThat(map.containsValue(newValue)).isTrue();
    }

    @Test
    void hashMapClearTest() {
        String newValue = "new value";
        map.put(KEY, VALUE);
        map.put("key2", newValue);
        assertThat(map).isNotEmpty();

        map.clear();

        assertThat(map).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"1", "asdfas", KEY})
    void hashMapNullKeyTest(String key) {
        map.put(key, VALUE);

        assertThat(map).containsKey(key);
    }
}
