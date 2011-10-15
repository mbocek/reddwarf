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
package org.reddwarf.dao.jpa;

import java.io.Serializable;

import org.reddwarf.dao.GenericDao;
import org.reddwarf.model.MutableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michal Bocek
 */
public class GenericDaoJpa<T extends MutableEntity, Id extends Serializable> extends GenericImmutableDaoJpa<T, Id> implements
		GenericDao<T, Id> {

	private static final Logger logger = LoggerFactory.getLogger(GenericDaoJpa.class);

	public GenericDaoJpa() {
	}

	public void create(T entity) {
		if (logger.isTraceEnabled()) {
			logger.trace("Creating entity: " + entity.getClass());
			logger.trace("  With contents: " + String.valueOf(entity));
		}
		this.entityManager.persist(entity);
	}

	public void delete(T entry) {
		if (logger.isTraceEnabled()) {
			logger.trace("Deleting entity: " + entry.getClass());
			logger.trace("  With contents: " + String.valueOf(entry));
		}
		this.entityManager.remove(entry);
	}

	public T update(T entry) {
		if (logger.isTraceEnabled()) {
			logger.trace("Updating entity: " + entry.getClass());
			logger.trace("  With contents: " + String.valueOf(entry));
		}
		return this.entityManager.merge(entry);
	}
}
