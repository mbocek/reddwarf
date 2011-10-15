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
package org.reddwarf.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reddwarf.dao.RoleDao;
import org.reddwarf.model.movie.Movie;
import org.reddwarf.model.user.User;
import org.reddwarf.service.movie.MovieInfoService;
import org.reddwarf.service.movie.TheMovieDBInfoServiceImpl;
import org.reddwarf.service.user.UserService;
import org.reddwarf.test.SpringSecurityLoginExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * @author Michal Bocek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { SpringSecurityLoginExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = { "classpath:/spring/persistence-config.xml",
		"classpath:/spring/security-config.xml", "classpath:/spring/transaction-config.xml",
		"classpath:/spring/service-config.xml", "classpath:/spring/utils-config.xml" })
public class TheMovieDBServiceTest {

	@Autowired
	private MovieInfoService movieInfoService;

	@Test
	public void testTheMovieDbSearch() {
		List<Movie> search = movieInfoService.search("Lord of the ring");
		Assert.assertNotNull(search);
		Assert.assertTrue(search.size() > 0);
	}
}
