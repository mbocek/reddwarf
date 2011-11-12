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
package org.reddwarf.converter;

import org.dozer.CustomConverter;
import org.dozer.MappingException;
import org.reddwarf.dto.movie.MovieDTO;
import org.reddwarf.model.movie.Movie;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
public class MovieConverter implements CustomConverter {
	/**
	 * Method converts between CaseEntity and MortgageCaseDTO bi-directionally.
	 */
	@SuppressWarnings("rawtypes")
	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
		if(source == null) return null;
		if (source instanceof Movie) {
			MovieDTO movieDTO = convert((Movie) source);
			return movieDTO;
		} else if (source instanceof MovieDTO) {
			throw new MappingException("Only converting Movie entity to MovieDTO is supported");
		} else {
			throw new MappingException("Converter MovieConverter used incorrectly. Arguments passed were:"
					+ destination + " and " + source);
		}
	}
	
	private MovieDTO convert(Movie movie) {
		MovieDTO result = new MovieDTO();
		result.setTitle(movie.getTitle());
		result.setRelease(movie.getRelease());
		result.setPlot(movie.getPlot());
		return result;
	}
}
