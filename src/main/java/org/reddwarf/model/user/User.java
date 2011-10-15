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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.reddwarf.model.MutableEntity;

/**
 * @author Michal Bocek
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "findByUserName", query = "from User u where u.userName = :userName") 
})
public class User extends MutableEntity {

	private static final long serialVersionUID = -5998614176274746403L;
	
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 8, max = 20)
	@Column(length = 20, nullable = false, unique = true)
	private String userName;

	@NotNull
	@Min(8)
	@Column(length = 20, nullable = false)
	private String password;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private Role role;

	private boolean accountNotLocked;

	private boolean credentialNotExpired;

	private boolean accountNotExpired;

	private boolean enabled;
	
	public User() {
		this.accountNotLocked = true;
		this.credentialNotExpired = true;
		this.accountNotExpired = true;
		this.enabled = false;
	}
	
	public User(String userName, String password, Role role) {
		this();
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Role getRole() {
		return this.role;
	}

	/**
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return
	 */
	public boolean isAccountNotExpired() {
		return this.accountNotExpired;
	}

	/**
	 * @return
	 */
	public boolean isCredentialNotExpired() {
		return this.credentialNotExpired;
	}

	/**
	 * @return
	 */
	public boolean isAccountNotLocked() {
		return this.accountNotLocked;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", userName=" + userName + "]";
	}
}
