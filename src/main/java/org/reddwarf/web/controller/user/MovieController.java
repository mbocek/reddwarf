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

import java.util.List;

import javax.inject.Inject;

import org.reddwarf.model.movie.Movie;
import org.reddwarf.model.movie.MovieDTO;
import org.reddwarf.service.movie.MovieInfoService;
import org.reddwarf.service.movie.TheMovieDBInfoServiceImpl;
import org.reddwarf.util.DTOConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application welcome page.
 * @author Michal Bocek
 */
@Controller
@RequestMapping(value="/movie")
public class MovieController {

	private static final Logger logger = LoggerFactory.getLogger(TheMovieDBInfoServiceImpl.class);

	@Inject
	private MovieInfoService theMovieDBInfoService;

	@Inject
	private MovieDTO movieDTO;
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String getMovies(ModelMap model) {
		movieDTO.clear();
		model.addAttribute("movies", new MovieDTO());
		return "movie";
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public String findMovie(@ModelAttribute("movies") MovieDTO movies, BindingResult results) {
		logger.info("findMovie({})", movies.getSearchName());
		String searchName = movies.getSearchName();
		
		if (searchName != null && !searchName.isEmpty() && !searchName.equals(movieDTO.getSearchName())) {
			List<Movie> search = theMovieDBInfoService.search(searchName);
			movies.setSearchResult(search);
			movieDTO.setSearchName(searchName);
			movieDTO.setSearchResult(DTOConverter.convertList(search, Movie.class));
		} else {
			movies.setSearchResult(movieDTO.getSearchResult());
		}
		return "movie";
	}
}
