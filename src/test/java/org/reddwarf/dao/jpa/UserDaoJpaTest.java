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

import static org.junit.Assert.fail;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reddwarf.dao.RoleDao;
import org.reddwarf.dao.UserDao;
import org.reddwarf.model.user.User;
import org.reddwarf.test.SpringSecurityLoginExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michal Bocek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { SpringSecurityLoginExecutionListener.class, DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:/spring/persistence-config.xml", "classpath:/spring/service-config.xml",
		"classpath:/spring/security-config.xml", "classpath:/spring/transaction-config.xml", "classpath:/spring/utils-config.xml" })
@Transactional
public class UserDaoJpaTest {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;

	/**
	 * Test method for
	 * {@link org.reddwarf.dao.jpa.GenericDaoJpa#create(org.reddwarf.model.MutableEntity)}.
	 */
	@Test
	public void testCreate() {
		User user = new User("test", "test", roleDao.getAdminRole());
		Assert.assertNull(user.getId());
		userDao.create(user);
		Assert.assertNotNull(user.getId());
	}

	/**
	 * Test method for
	 * {@link org.reddwarf.dao.jpa.GenericDaoJpa#delete(org.reddwarf.model.MutableEntity)}.
	 */
	@Test
	public void testDelete() {
		User user = new User("deleteUser", "test", roleDao.getAdminRole());
		Assert.assertNull(user.getId());
		userDao.create(user);
		Assert.assertNotNull(user.getId());
		userDao.delete(user);
		boolean fail = false;
		try {
			userDao.read(user.getId());
			fail = true;
		} catch (EntityNotFoundException e) {
			fail = false;
		}
		if (fail) {
			fail("User with id: " + user.getId() + "haven't deleted");
		}
	}

	/**
	 * Test method for
	 * {@link org.reddwarf.dao.jpa.GenericDaoJpa#update(org.reddwarf.model.MutableEntity)}.
	 */
	@Test
	public void testUpdate() {
		User user = new User("updateUser", "test", roleDao.getAdminRole());
		Assert.assertNull(user.getId());
		userDao.create(user);
		Assert.assertNotNull(user.getId());
		user.setPassword("updatePwd");
		userDao.update(user);
	}

	/**
	 * Test method for {@link org.reddwarf.dao.jpa.GenericImmutableDaoJpa#read(java.io.Serializable)}.
	 */
	@Test
	public void testRead() {
		User user = new User("deleteUser", "test", roleDao.getAdminRole());
		Assert.assertNull(user.getId());
		userDao.create(user);
		Assert.assertNotNull(user.getId());
		try {
			userDao.read(user.getId());
		} catch (EntityNotFoundException e) {
			fail("Can not read user with id: " + user.getId());
		}
	}

	/**
	 * Test method for {@link org.reddwarf.dao.jpa.GenericImmutableDaoJpa#findAll()}.
	 */
	@Test
	public void testFindAll() {
		User user = new User("deleteUser", "test", roleDao.getAdminRole());
		Assert.assertNull(user.getId());
		userDao.create(user);
		Assert.assertNotNull(user.getId());
		Collection<User> findAll = userDao.findAll();
		Assert.assertTrue(findAll.size() > 0);
	}

}
