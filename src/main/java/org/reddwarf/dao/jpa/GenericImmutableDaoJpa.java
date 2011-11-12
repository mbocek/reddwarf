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
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.reddwarf.dao.GenericImmutableDao;
import org.reddwarf.model.ImmutableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michal Bocek
 */
public abstract class GenericImmutableDaoJpa<T extends ImmutableEntity, Id extends Serializable> implements
		GenericImmutableDao<T, Id> {

	private static final Logger logger = LoggerFactory.getLogger(GenericImmutableDaoJpa.class);

	protected Class<T> persistentClass;

	@PersistenceContext(name = "entityManagerFactory")
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public GenericImmutableDaoJpa() {
		if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
			this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
		} else {
			logger.warn("Insufficient implementation on DAO layer for class: " + getClass().getGenericSuperclass());
		}
	}

	public T read(Id id) throws EntityNotFoundException {
		if (logger.isTraceEnabled()) {
			logger.trace("Reading entity for id: " + id);
		}
		T entry = this.entityManager.find(this.persistentClass, id);
		if (entry == null) {
			throw new EntityNotFoundException("Entity for class " + this.persistentClass + " with id " + id
					+ " can not be found!");
		}
		return entry;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		if (logger.isTraceEnabled()) {
			logger.trace("Reading all entities");
		}
		Query query = this.entityManager.createQuery("from " + this.persistentClass.getName());
		return query.getResultList();
	}

	public T findById(Id id) {
		if (logger.isTraceEnabled()) {
			logger.trace("Finding entity for id: " + id);
		}
		T entry = this.entityManager.find(this.persistentClass, id);
		return entry;
	}

}
