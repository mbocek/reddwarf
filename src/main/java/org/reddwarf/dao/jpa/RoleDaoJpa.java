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

import javax.persistence.Query;

import org.reddwarf.dao.RoleDao;
import org.reddwarf.model.user.Role;
import org.springframework.stereotype.Repository;

/**
 * @author Michal Bocek
 */
@Repository("roleDao")
public class RoleDaoJpa extends GenericImmutableDaoJpa<Role, Long> implements RoleDao {

	/* (non-Javadoc)
	 * @see org.reddwarf.dao.RoleDao#getAdminRole()
	 */
	@Override
	public Role getAdminRole() {
		Query query = this.entityManager.createNamedQuery("findByRoleName");
		query.setParameter("roleName", Role.ROLE_ADMINISTRATOR);
		return (Role)query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see org.reddwarf.dao.RoleDao#getUserRole()
	 */
	@Override
	public Role getUserRole() {
		Query query = this.entityManager.createNamedQuery("findByRoleName");
		query.setParameter("roleName", Role.ROLE_USER);
		return (Role)query.getSingleResult();
	}

}
