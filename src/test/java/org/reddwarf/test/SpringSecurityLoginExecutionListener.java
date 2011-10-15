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
package org.reddwarf.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * @author Michal Bocek
 */
public class SpringSecurityLoginExecutionListener extends AbstractTestExecutionListener {

	private static final Logger logger = LoggerFactory.getLogger(SpringSecurityLoginExecutionListener.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.test.context.support.AbstractTestExecutionListener#beforeTestMethod(org.springframework.test.context.TestContext)
	 */
	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		logger.info("Injecting authentication user to test context...");
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("testUser", "testPassword");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		super.beforeTestMethod(testContext);
	}

	/* (non-Javadoc)
	 * @see org.springframework.test.context.support.AbstractTestExecutionListener#afterTestMethod(org.springframework.test.context.TestContext)
	 */
	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		SecurityContextHolder.getContext().setAuthentication(null);
		super.afterTestMethod(testContext);
	}
}
