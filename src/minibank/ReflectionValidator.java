package minibank;

import java.lang.reflect.Field;

public class ReflectionValidator {
    public static void validate(Object obj) throws IllegalArgumentException {
        Class<?> clazz = obj.getClass();
        
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                
                if (field.isAnnotationPresent(NotBlank.class)) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(obj);
                        if (value == null || value.toString().trim().isEmpty()) {
                            throw new IllegalArgumentException(field.getAnnotation(NotBlank.class).message());
                        }
                    } catch (IllegalAccessException e) { e.printStackTrace(); }
                }
                
                if (field.isAnnotationPresent(MaxLength.class)) {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(obj);
                        if (value != null && value.toString().length() > field.getAnnotation(MaxLength.class).value()) {
                            throw new IllegalArgumentException(field.getAnnotation(MaxLength.class).message());
                        }
                    } catch (IllegalAccessException e) { e.printStackTrace(); }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}