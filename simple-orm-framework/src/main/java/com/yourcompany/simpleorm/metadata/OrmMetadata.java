package com.yourcompany.simpleorm.metadata;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public record OrmMetadata<T> 
(
		Class<T> clazz,
        String tableName,
        Field primaryKeyField,
        String primaryKeyColumnName,
        List<Field> mappedFields,
        Map<Field, String> columnNames
)
{
	public OrmMetadata 
	{
		mappedFields = Collections.unmodifiableList(mappedFields);
		columnNames = Collections.unmodifiableMap(columnNames);
	}
}
