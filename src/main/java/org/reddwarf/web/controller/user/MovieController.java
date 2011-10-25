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
import javax.validation.Valid;

import org.reddwarf.api.Constants;
import org.reddwarf.dao.MovieQualityDao;
import org.reddwarf.dto.movie.MovieDTO;
import org.reddwarf.model.movie.MovieInfo;
import org.reddwarf.service.movie.MovieInfoService;
import org.reddwarf.service.movie.TheMovieDBInfoServiceImpl;
import org.reddwarf.util.DTOConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application welcome page.
 * @author Michal Bocek
 */
@Controller
@RequestMapping(value="/movie")
@SessionAttributes("movieDTO")
public class MovieController {

	private static final Logger logger = LoggerFactory.getLogger(TheMovieDBInfoServiceImpl.class);

	@Inject
	private MovieInfoService theMovieDBInfoService;

	@Inject
	private MovieQualityDao movieQualityDao;
	
    @Autowired
    private Validator validator;
    
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView getMovies() {
		return new ModelAndView("movie", "movieDTO", new MovieDTO());
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public String findMovie(@ModelAttribute("movieDTO") MovieDTO movie) {
		logger.info("findMovie({})", movie.getSearchName());
		
		if (movie != null && movie.getSearchName() !=null && !movie.getSearchName().isEmpty()) {
			List<MovieInfo> search = theMovieDBInfoService.search(movie.getSearchName());
			movie.setSearchResult(DTOConverter.convertList(search, MovieInfo.class));
			movie.setMovieQualityList(movieQualityDao.findAll());
		} 
		return "movie";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addMovie(@ModelAttribute("movieDTO") @Valid MovieDTO movie, BindingResult result) {
		logger.info("addMovie({})", movie.getSelectedMovie());
		return "movie";
	}
	
}
