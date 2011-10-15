/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.reddwarf.util.reflection;

import java.lang.reflect.Field;

/**
 * Utilities to access fields of object.
 * 
 * @author Michal Bocek
 */
public class FieldUtils {
	
	/**
	 * Get all fields (private, protected, public) included inherited fields.
	 * @param objectClass
	 * @param fields
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static Field[] getAllFields(Class objectClass, Field[] fields) throws ClassNotFoundException {
		Field[] totalFields = getFields(objectClass, fields);

		Class superClass = objectClass.getSuperclass();
		Field[] finalFieldsArray;
		Class clazz = null;
		
		clazz = Class.forName("java.lang.Object");
		
		if (superClass != null && !superClass.equals(clazz)) {
			finalFieldsArray = getAllFields(superClass, totalFields);
		} else {
			finalFieldsArray = totalFields;
		}

		return finalFieldsArray;
	}

	/**
	 * Get all fields (private, protected, public) for object except inherited fields.
	 * @param objectClass
	 * @param fields
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Field[] getFields(Class objectClass, Field[] fields) {
		Field[] newFields = objectClass.getDeclaredFields();

		int fieldsSize = 0;
		int newFieldsSize = 0;

		if (fields != null) {
			fieldsSize = fields.length;
		}

		if (newFields != null) {
			newFieldsSize = newFields.length;
		}

		Field[] totalFields = new Field[fieldsSize + newFieldsSize];

		if (fieldsSize > 0) {
			System.arraycopy(fields, 0, totalFields, 0, fieldsSize);
		}

		if (newFieldsSize > 0) {
			System.arraycopy(newFields, 0, totalFields, fieldsSize, newFieldsSize);
		}
		return totalFields;
	}
	
	public static void setField(Object object, String fieldName, Object value) throws Exception {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, value);
	}
}
