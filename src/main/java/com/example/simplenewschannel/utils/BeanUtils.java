package com.example.simplenewschannel.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Collection;

@UtilityClass
public class BeanUtils {
    //    @SneakyThrows
//    public void notNullCopyProperties(Object source, Object destination) {
//        Class<?> clazz = source.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            Object value = field.get(source);
//            if (value != null) {
//                field.set(destination, value);
//            }
//        }
//    }
    @SneakyThrows
    public static void notNullCopyProperties(Object source, Object target) {
        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(source);
            if (value != null && !hasFieldEmptyCollection(field, source)) {
                field.set(target, value);
            }
        }
    }

    @SneakyThrows
    private static boolean hasFieldEmptyCollection(Field field, Object source) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            Collection<?> collection = (Collection<?>) field.get(source);
            return collection.isEmpty();
        }
        return false;
    }
}
