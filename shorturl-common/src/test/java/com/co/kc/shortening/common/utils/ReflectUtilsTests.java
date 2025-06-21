package com.co.kc.shortening.common.utils;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Date;

class ReflectUtilsTests {

    @Test
    void testGetterMethodName() {
        Assertions.assertEquals("getId", ReflectUtils.getterMethodName("id"));
        Assertions.assertEquals("getName", ReflectUtils.getterMethodName("name"));
        Assertions.assertEquals("getAge", ReflectUtils.getterMethodName("age"));
        Assertions.assertEquals("getScore", ReflectUtils.getterMethodName("score"));
        Assertions.assertEquals("getCreatedTime", ReflectUtils.getterMethodName("createdTime"));
    }

    @Test
    void testSetterMethodName() {
        Assertions.assertEquals("setId", ReflectUtils.setterMethodName("id"));
        Assertions.assertEquals("setName", ReflectUtils.setterMethodName("name"));
        Assertions.assertEquals("setAge", ReflectUtils.setterMethodName("age"));
        Assertions.assertEquals("setScore", ReflectUtils.setterMethodName("score"));
        Assertions.assertEquals("setCreatedTime", ReflectUtils.setterMethodName("createdTime"));
    }

    @Test
    void testGetPropertyName() {
        Assertions.assertEquals("id", ReflectUtils.getPropertyName(TestObject::getId));
        Assertions.assertEquals("name", ReflectUtils.getPropertyName(TestObject::getName));
        Assertions.assertEquals("age", ReflectUtils.getPropertyName(TestObject::getAge));
        Assertions.assertEquals("score", ReflectUtils.getPropertyName(TestObject::getScore));
        Assertions.assertEquals("createdTime", ReflectUtils.getPropertyName(TestObject::getCreatedTime));
    }

    @Data
    private static class TestObject implements Serializable {
        private Long id;
        private String name;
        private Integer age;
        private Double score;
        private Date createdTime;
    }
}
