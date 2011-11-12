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
package org.reddwarf.service.movie;

import java.util.List;

import javax.inject.Inject;

import org.reddwarf.dao.MovieDao;
import org.reddwarf.dao.MovieQualityDao;
import org.reddwarf.dto.movie.MovieDTO;
import org.reddwarf.dto.movie.MovieSearchDTO;
import org.reddwarf.model.movie.Movie;
import org.reddwarf.model.movie.MovieInfo;
import org.reddwarf.model.movie.MovieQuality;
import org.reddwarf.util.DTOConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class MovieServiceImpl implements MovieService {

	private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

	@Inject
	private MovieDao movieDao;
	
	@Inject
	private MovieQualityDao movieQualityDao;

	/* (non-Javadoc)
	 * @see org.reddwarf.service.movie.MovieService#addMovie(org.reddwarf.dto.movie.MovieDTO)
	 */
	@Override
	@Transactional(readOnly = false)
	public void addMovie(MovieSearchDTO movie) {
		logger.info("addMovie({})", movie.getSelectedMovie());
		MovieInfo movieInfo = getMovie(movie.getSearchResult(), movie.getSelectedMovie());
		Movie movieToStore = DTOConverter.convert(movieInfo, Movie.class);
		MovieQuality movieQuality = movieQualityDao.read(movie.getMovieQuality());
		movieToStore.setMovieQuality(movieQuality);
		movieDao.create(movieToStore);
	}

	@Override
	public Boolean checkIfAlreadyAdded(Integer movieId) {
		logger.info("(checkIfAlreadyAdded({})", movieId);
		Movie movie = movieDao.findById(movieId);
		return movie != null;
	}

	@Override
	public List<MovieDTO> getMovies() {
		logger.info("getMovies()");
		List<Movie> findAll = movieDao.findAll();
		logger.info("movie count: {}", findAll.size());
		return DTOConverter.convertList(findAll, MovieDTO.class);
	}

	private MovieInfo getMovie(List<MovieInfo> movies, Integer selectedMovie) {
		MovieInfo result = null;
		for (MovieInfo movieInfo : movies) {
			if (selectedMovie.equals(movieInfo.getId())) {
				result = movieInfo;
				break;
			}
		}
		return result; 
	}
}