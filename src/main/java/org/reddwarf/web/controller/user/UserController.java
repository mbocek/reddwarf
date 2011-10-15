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
package org.reddwarf.web.controller.user;

import org.reddwarf.dao.RoleDao;
import org.reddwarf.model.user.Role;
import org.reddwarf.model.user.User;
import org.reddwarf.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application welcome page.
 * @author Michal Bocek
 */
@Controller
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;
	
    @Autowired
    private Validator validator;
	
	@RequestMapping(value="new", method = RequestMethod.GET)
	public String getNewUser() {
		return "register";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String handleRegister(@RequestParam("confirmationPassword") String confirmationPassword, 
			@ModelAttribute("users") User user, BindingResult results) {
		
		Role role = roleDao.getUserRole();
		user.setRole(role); 
		validator.validate(user, results);
		
		if (user.getPassword().compareTo(confirmationPassword) != 0) {
			results.rejectValue("password", "user.password.notsame", "Password and confirmation password are not same");
		}
		
		if (!results.hasErrors()) {
			userService.createUser(user);
			return "redirect:/user/signin";
		} else {
			return "register";
		}
	}
}
