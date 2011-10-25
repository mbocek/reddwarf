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
package org.reddwarf.service.startup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.reddwarf.model.movie.MovieQuality;
import org.reddwarf.model.user.Role;
import org.reddwarf.model.user.User;
import org.reddwarf.util.CSVLoader;
import org.reddwarf.util.reflection.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Startup service is declared as spring bean in services.xml. Because there doesn't exists annotation for
 * initialization of bean. Other annotations for auto wiring are declared.
 * 
 * @author Michal Bocek
 */
public class StartupServiceImpl {
	private static final Logger logger = LoggerFactory.getLogger(StartupServiceImpl.class);

	// single TransactionTemplate shared amongst all methods in this instance
	private final TransactionTemplate transactionTemplate;

	@PersistenceContext(name = "entityManagerFactory")
	protected EntityManager entityManager;

	@Autowired
	private CSVLoader csvLoader;
	
	private Collection<String> roleInfo;
	private Collection<String> userInfo;
	private Collection<String> movieQualityInfo;

	public StartupServiceImpl(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
		this.roleInfo = new ArrayList<String>();
		this.roleInfo.add("id");
		this.roleInfo.add("roleName");
		this.roleInfo.add("description");

		this.userInfo = new ArrayList<String>();
		this.userInfo.add("id");
		this.userInfo.add("userName");
		this.userInfo.add("password");
		this.userInfo.add("role");

		this.movieQualityInfo = new ArrayList<String>();
		this.movieQualityInfo.add("code");
		this.movieQualityInfo.add("description");
		this.movieQualityInfo.add("type");
	}

	protected void initialize() {
		logger.info("Initializing database...");
		transactionTemplate.execute(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				populateRoles();
				return null;
			}
		});
		transactionTemplate.execute(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				populateTestUsers();
				return null;
			}
		});
		transactionTemplate.execute(new TransactionCallback<Object>() {
			@Override
			public Object doInTransaction(TransactionStatus arg0) {
				populateMovieQuality();
				return null;
			}
		});
		logger.info("Finished initialization of database...");
	}

	protected void populateTestUsers() {
		// login fake user
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("preload", "preload"));
		
		// role data
		Collection<Collection<String>> userDatas = csvLoader.load("startup-data/users.csv");
		for (Collection<String> userData : userDatas) {
			User user = buildUser(userData);
			user.setEnabled(true);
			Query query = entityManager.createQuery("from User where userName = :userName").setParameter("userName", user.getUserName());
			User persistentUser = null;
			try {
				persistentUser = (User) query.getSingleResult();
			} catch (NoResultException e) {
				logger.debug("User with name: " + user.getUserName() + " doesn't exist's and will be created!");
			}
			if (persistentUser == null) {
				entityManager.persist(user);
			} else {
				logger.info("User with id: " + user.getId() + " already exists!");
			}
		}
		
		// logout fake user
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	/* (non-Javadoc)
	 * @see org.reddwarf.service.startup.Startup#populateData()
	 */
	public void populateRoles() {
		// login fake user
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("preload", "preload"));
		
		// role data
		Collection<Collection<String>> roleDatas = csvLoader.load("startup-data/roles.csv");
		for (Collection<String> roleData : roleDatas) {
			Role role = buildRole(roleData);
			Query query = entityManager.createQuery("from Role where roleName = :roleName").setParameter("roleName", role.getRoleName());
			Role persistentRole = null;
			try {
				persistentRole = (Role) query.getSingleResult();
			} catch (NoResultException e) {
				logger.debug("Role with id: " + role.getRoleName() + " doesn't exist's and will be created!");
			}
			if (persistentRole == null) {	
				entityManager.persist(role);
			} else {
				logger.info("Role with id: " + role.getId() + " already exists!");
			}
		}
		
		// logout fake user
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	/* (non-Javadoc)
	 * @see org.reddwarf.service.startup.Startup#populateData()
	 */
	public void populateMovieQuality() {
		// login fake user
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("preload", "preload"));
		
		// role data
		Collection<Collection<String>> movieQualityDatas = csvLoader.load("startup-data/movieQuality.csv");
		for (Collection<String> movieQualityData : movieQualityDatas) {
			MovieQuality movieQuality = buildMovieQuality(movieQualityData);
			Query query = entityManager.createQuery("from MovieQuality where code = :code").setParameter("code", movieQuality.getCode());
			MovieQuality persistentMovieQuality = null;
			try {
				persistentMovieQuality = (MovieQuality) query.getSingleResult();
			} catch (NoResultException e) {
				logger.debug("Movie quality with id: " + movieQuality.getCode() + " doesn't exist's and will be created!");
			}
			if (persistentMovieQuality == null) {	
				entityManager.persist(movieQuality);
			} else {
				logger.info("Movie quality with code: " + movieQuality.getCode() + " already exists!");
			}
		}
		
		// logout fake user
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	private MovieQuality buildMovieQuality(Collection<String> movieQualityData) {
		assert movieQualityData.size() == movieQualityInfo.size() : "Role datas are not complete... " + movieQualityData;
		MovieQuality movieQuality = new MovieQuality();
		Iterator<String> movieQualityDataIterator = movieQualityData.iterator();
		Iterator<String> movieQualityInfoIterator = movieQualityInfo.iterator();
		while (movieQualityInfoIterator.hasNext()) {
			String fieldName = movieQualityInfoIterator.next(); 
			String data = movieQualityDataIterator.next();
			try {
				if (fieldName.equals("code")) {
					FieldUtils.setField(movieQuality, fieldName, String.valueOf(data));
				} else {
					FieldUtils.setField(movieQuality, fieldName, data);
				}
			} catch (Exception e) {
				logger.warn("Can not set field: " + fieldName + "for class: " + movieQuality.getClass().getName(), e);
			}

		}
		return movieQuality;
	}

	private Role buildRole(Collection<String> roleData) {
		assert roleData.size() == roleInfo.size() : "Role datas are not complete... " + roleData;
		Role role = new Role();
		Iterator<String> roleDataIterator = roleData.iterator();
		Iterator<String> roleInfoIterator = roleInfo.iterator();
		while (roleInfoIterator.hasNext()) {
			String fieldName = roleInfoIterator.next(); 
			String data = roleDataIterator.next();
			try {
				if (fieldName.equals("id")) {
					FieldUtils.setField(role, fieldName, Long.valueOf(data));
				} else {
					FieldUtils.setField(role, fieldName, data);
				}
			} catch (Exception e) {
				logger.warn("Can not set field: " + fieldName + "for class: " + role.getClass().getName(), e);
			}

		}
		return role;
	}
	
	private User buildUser(Collection<String> userData) {
		assert userData.size() == userInfo.size() : "User datas are not complete... " + userData;
		User user = new User();
		Iterator<String> userDataIterator = userData.iterator();
		Iterator<String> userInfoIterator = userInfo.iterator();
		while (userInfoIterator.hasNext()) {
			String fieldName = userInfoIterator.next(); 
			String data = userDataIterator.next();
			try {
				if (fieldName.equals("id")) {
					FieldUtils.setField(user, fieldName, Long.valueOf(data));
				} else if (fieldName.equals("role")) {
					Role role = entityManager.find(Role.class, Long.valueOf(data));
					FieldUtils.setField(user, fieldName, role);
				} else {
					FieldUtils.setField(user, fieldName, data);
				}
			} catch (Exception e) {
				logger.warn("Can not set field: " + fieldName + "for class: " + user.getClass().getName(), e);
			}

		}
		return user;
	}
}
