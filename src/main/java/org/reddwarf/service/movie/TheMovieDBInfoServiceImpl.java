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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.sf.jtmdb.Genre;

import org.json.JSONException;
import org.reddwarf.model.movie.MovieInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@Service("theMovieDBService")
@Transactional(readOnly = true)
public class TheMovieDBInfoServiceImpl implements MovieInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(TheMovieDBInfoServiceImpl.class);

	@Inject
	private TheMovieDBWrapper theMovieDBWrapper;

	@Override
	public List<MovieInfo> search(String movieName) {
		logger.info("Searching TheMovieDB for: {}", movieName);
		List<MovieInfo> result = new ArrayList<MovieInfo>();
		try {
			List<net.sf.jtmdb.Movie> movies = theMovieDBWrapper.search(movieName);
			if (movies != null) {
				for (net.sf.jtmdb.Movie movie : movies) {
					MovieInfo myMovieInfo = new MovieInfo();
					myMovieInfo.setTitle(movie.getName());
					myMovieInfo.setId(movie.getID());
					myMovieInfo.setImdbId(movie.getImdbID());
					myMovieInfo.setImdbId(movie.getImdbID());
					myMovieInfo.setPlot(movie.getOverview());
					myMovieInfo.setRelease(movie.getReleasedDate());
					for(Genre genre: movie.getGenres()) {
						myMovieInfo.addGenre(genre.getName());
					}
					result.add(myMovieInfo);
				}
			}
		} catch (IOException e) {
			logger.error("Cannot connect server!", e);
		} catch (JSONException e) {
			logger.error("Wrong communitation!", e);
		}
		return result;
	}	
}
