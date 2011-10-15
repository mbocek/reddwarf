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
package org.reddwarf.dao;

import java.io.Serializable;

import org.reddwarf.model.MutableEntity;

/**
 * Generic dao interface. Interface is designed as generic class.
 * @author Michal Bocek
 */
public interface GenericDao<T extends MutableEntity, Id extends Serializable> extends GenericImmutableDao<T, Id> {

	/**
	 * Create entity. After call of this method will bee object in persistent state.
	 * @param entry object which will bee persistent
	 */
	public void create(T entry);

	/**
	 * Update entity. After call of this method will be object in detached state merged to persistent and will be in
	 * persitent state.
	 * @param entry object which will bee updated
	 * @return
	 */
	public T update(T entry);

	/**
	 * Delete entity. After call of this method will be object in transient state and entity will be deleted from store.
	 * @param entry object which will bee deleted
	 */
	public void delete(T entry);
}
