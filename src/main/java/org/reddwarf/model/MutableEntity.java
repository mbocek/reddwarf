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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.security.context.SecurityContextHolder;

/**
 * Domain object. Standard used for mutable domain objetcs.
 * @author Michal Bocek
 */
@MappedSuperclass
public abstract class MutableEntity extends ImmutableEntity {

	private static final long serialVersionUID = 5147016653535034887L;

	@Column(name = "UPDATED_BY", nullable = false, length = 20)
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE", nullable = false)
	private Date updateDate;

	@SuppressWarnings("unused")
	@Version
	@Column(nullable = false)
	private Integer version;

	public MutableEntity() {
	}

	/**
	 * Get information about when was object last time change.
	 * @return
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * Get information about who last time change object.
	 * @return
	 */
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	/**
	 * Preupdate event method. Method will be called prior to the EntityManager
	 * persisting bean. Method change updateDate and updatedBy. UpdateDate id
	 * actual date and time and updatedBy is identifier of user who change the
	 * object. This method is merge with call supper method marked as preupdate.
	 */
	@SuppressWarnings("unused")
	@PreUpdate
	private void preUpdateDomainObject() {
		updateDate = new Date();
		updatedBy = SecurityContextHolder.getContext().getAuthentication().getName();
	}

	/**
	 * Prepersist event method. Method will be called prior to the EntityManager
	 * persisting bean. Method change updateDate and updatedBy. UpdateDate is
	 * actual date and time and updatedBy is identifier of user who change the
	 * object. This method is merge with call supper method marked as
	 * prepersist.
	 */
	@SuppressWarnings("unused")
	@PrePersist
	private void prePersistDomainObject() {
		updateDate = new Date();
		updatedBy = SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
