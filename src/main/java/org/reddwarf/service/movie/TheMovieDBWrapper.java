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
import java.util.List;

import net.sf.jtmdb.GeneralSettings;
import net.sf.jtmdb.Movie;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
public class TheMovieDBWrapper {
	
	private static final Logger logger = LoggerFactory.getLogger(TheMovieDBWrapper.class);
	
	public TheMovieDBWrapper(String theMovieDBApiKey) {
		GeneralSettings.setLogEnabled(true);
		logger.debug("The movie db api key: {}", theMovieDBApiKey);
		GeneralSettings.setApiKey(theMovieDBApiKey);
		
	}

	public List<Movie> search(String movieName) throws IOException, JSONException {
		return Movie.search(movieName);
	}
	
}
