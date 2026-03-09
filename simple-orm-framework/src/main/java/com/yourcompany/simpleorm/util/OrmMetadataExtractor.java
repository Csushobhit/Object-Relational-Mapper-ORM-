package com.yourcompany.simpleorm.util;

import com.yourcompany.simpleorm.annotation.Column;
import com.yourcompany.simpleorm.annotation.Id;
import com.yourcompany.simpleorm.annotation.Table;
import com.yourcompany.simpleorm.annotation.Entity;
import com.yourcompany.simpleorm.metadata.OrmMetadata;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrmMetadataExtractor {

    private static final Map<Class<?>, OrmMetadata<?>> metadataCache =
            new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> OrmMetadata<T> getMetadata(Class<T> clazz) {

        if (clazz == null) {
            throw new NullPointerException("Input class cannot be null.");
        }

        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(
                    "Class " + clazz.getName() + " is not annotated with @Entity");
        }

        return (OrmMetadata<T>) metadataCache.computeIfAbsent(
                clazz,
                OrmMetadataExtractor::extractMetadataInternal
        );
    }

    private static <T> OrmMetadata<T> extractMetadataInternal(Class<T> clazz) {

        String tableName = getTableNameInternal(clazz);

        List<Field> mappedFields = getMappedColumnFieldsInternal(clazz);

        Field primaryKeyField = findPrimaryKeyFieldInternal(clazz);

        Map<Field, String> columnNames = getColumnNamesMapInternal(mappedFields);

        String primaryKeyColumnName = columnNames.get(primaryKeyField);

        if (primaryKeyColumnName == null) {
            throw new IllegalStateException(
                    "Could not determine column name for primary key field: "
                            + primaryKeyField.getName());
        }

        return new OrmMetadata<>(
                clazz,
                tableName,
                primaryKeyField,
                primaryKeyColumnName,
                mappedFields,
                columnNames
        );
    }

    private static String getTableNameInternal(Class<?> clazz) {

        if (clazz.isAnnotationPresent(Table.class)) {
            return clazz.getAnnotation(Table.class).name();
        }

        return clazz.getSimpleName().toLowerCase();
    }

    private static Field findPrimaryKeyFieldInternal(Class<?> clazz) {

        Field primaryKeyField = null;

        for (Field field : clazz.getDeclaredFields()) {

            if (field.isAnnotationPresent(Id.class)) {

                if (primaryKeyField != null) {
                    throw new IllegalStateException(
                            "Entity class '" + clazz.getName()
                                    + "' must have exactly one @Id annotation.");
                }

                primaryKeyField = field;
            }
        }

        if (primaryKeyField == null) {
            throw new IllegalStateException(
                    "Entity class '" + clazz.getName()
                            + "' must have one field annotated with @Id.");
        }

        primaryKeyField.setAccessible(true);

        return primaryKeyField;
    }

    private static List<Field> getMappedColumnFieldsInternal(Class<?> clazz) {

        List<Field> mappedFields = new ArrayList<>();

        Field[] allDeclaredFields = clazz.getDeclaredFields();

        for (Field field : allDeclaredFields) {

            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);

            mappedFields.add(field);
        }

        return mappedFields;
    }

    private static Map<Field, String> getColumnNamesMapInternal(List<Field> fields) {

        Map<Field, String> columnNamesMap = new HashMap<>();

        for (Field field : fields) {

            String columnName;

            if (field.isAnnotationPresent(Column.class)) {
                columnName = field.getAnnotation(Column.class).name();
            } else {
                columnName = field.getName().toLowerCase();
            }

            columnNamesMap.put(field, columnName);
        }

        return columnNamesMap;
    }
}