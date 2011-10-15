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
package org.reddwarf.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.context.SecurityContextHolder;

/**
 * Immutable domain object. Standard used for immutable objects.
 * @author Michal Bocek
 */
@MappedSuperclass
public abstract class ImmutableEntity implements Serializable {

	private static final long serialVersionUID = 278370948029307004L;
	@Column(name = "CREATED_BY", nullable = false, length = 20)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", nullable = false)
	private Date createDate;

	public ImmutableEntity() {
	}

	/**
	 * Get information about when was object created.
	 * @return creation date
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * Get information about who create object.
	 * @return created by
	 */
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * Prepersist event method. Method will be called prior to the EntityManager persisting bean.
	 */
	@SuppressWarnings("unused")
	@PrePersist
	private void prePersistImmutableDomainObject() {
		createDate = new Date();
		createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
