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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reddwarf.dao.MovieDao;
import org.reddwarf.model.movie.Movie;
import org.reddwarf.test.SpringSecurityLoginExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { SpringSecurityLoginExecutionListener.class, DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:/spring/persistence-config.xml", "classpath:/spring/service-config.xml",
		"classpath:/spring/security-config.xml", "classpath:/spring/transaction-config.xml", "classpath:/spring/utils-config.xml" })
public class MovieDaoJpaTest {

	@Autowired
	private MovieDao movieDao;
	
	/**
	 * Test method for {@link org.reddwarf.dao.jpa.GenericDaoJpa#create(org.reddwarf.model.MutableEntity)}.
	 */
	@Test
	public void testCreate() {
		Movie movie = new Movie();
	}

	/**
	 * Test method for {@link org.reddwarf.dao.jpa.GenericDaoJpa#delete(org.reddwarf.model.MutableEntity)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.reddwarf.dao.jpa.GenericDaoJpa#update(org.reddwarf.model.MutableEntity)}.
	 */
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.reddwarf.dao.jpa.GenericImmutableDaoJpa#read(java.io.Serializable)}.
	 */
	@Test
	public void testRead() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.reddwarf.dao.jpa.GenericImmutableDaoJpa#findAll()}.
	 */
	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

}
