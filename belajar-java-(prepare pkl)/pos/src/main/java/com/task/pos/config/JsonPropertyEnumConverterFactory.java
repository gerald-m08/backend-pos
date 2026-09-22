package com.task.pos.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.reflect.Field;

public class JsonPropertyEnumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter<>(targetType);
    }

    private static class StringToEnumConverter<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T convert(String source) {

            String trimmed = source.trim();

            for (T constant : enumType.getEnumConstants()) {


                try {
                    Field field = enumType.getField(constant.name());
                    JsonProperty annotation = field.getAnnotation(JsonProperty.class);

                    if (annotation != null && annotation.value().equalsIgnoreCase(trimmed)) {
                        return constant;
                    }
                } catch (NoSuchFieldException e) {

                }

                if (constant.name().equalsIgnoreCase(trimmed)) {
                    return constant;
                }
            }

            throw new IllegalArgumentException(
                    "No enum constant " + enumType.getSimpleName() + " matching '" + source + "'"
            );
        }
    }
}