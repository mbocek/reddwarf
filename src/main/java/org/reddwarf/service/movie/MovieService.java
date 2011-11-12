package org.reddwarf.service.movie;

import java.util.List;

import org.reddwarf.dto.movie.MovieDTO;
import org.reddwarf.dto.movie.MovieSearchDTO;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
public interface MovieService {

	void addMovie(MovieSearchDTO movie);

	Boolean checkIfAlreadyAdded(Integer movieId);
	
	List<MovieDTO> getMovies();
}