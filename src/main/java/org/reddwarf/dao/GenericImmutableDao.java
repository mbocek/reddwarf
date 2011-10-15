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
import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.reddwarf.model.ImmutableEntity;

/**
 * Generic immutable dao interface. Interface is designed as generic class.
 * @author Michal Bocek
 */
public interface GenericImmutableDao<T extends ImmutableEntity, Id extends Serializable> {

	/**
	 * Read entity identified with id. When entity doesn't exist the throw runtime exception.
	 * @param id identifier
	 * @return
	 * @throws EntityNotFoundException
	 */
	T read(Id id) throws EntityNotFoundException;

	/**
	 * Get all entities. If mapped table id empty return empty list.
	 * @return
	 */
	Collection<T> findAll();
}
