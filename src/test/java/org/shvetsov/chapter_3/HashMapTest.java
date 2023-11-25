package org.shvetsov.chapter_3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @Test
    void hashMapPutGetTest() {

    }

    @Test
    void hashMapSecondObjectTest() {

    }

    @Test
    void hashMapClearTest() {

    }

    @Test
    void hashMapNullKeyTest(String key) {

    }
}
