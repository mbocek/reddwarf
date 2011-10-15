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
package org.reddwarf.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.reddwarf.model.ImmutableEntity;

/**
 * @author Michal Bocek
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "findByRoleName", query = "from Role r where r.roleName = :roleName") 
})
public class Role extends ImmutableEntity {

	private static final long serialVersionUID = -9050034197490930655L;
	public static String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";
	public static String ROLE_USER = "ROLE_USER";

	@Id
	private Long id;

	@Column(name = "ROLE_NAME", length = 20, nullable = false)
	private String roleName;

	@Column(length = 200, nullable = false)
	private String description;

	public Role() {
	}

	/**
	 * @param roleName
	 * @param description
	 */
	public Role(String roleName, String description) {
		this();
		this.roleName = roleName;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the role
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + roleName + ", description=" + description + "]";
	}
}
