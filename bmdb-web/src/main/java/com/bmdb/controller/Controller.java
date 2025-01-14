package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.MovieRepo;
import com.bmdb.model.Movie;

@RestController
@RequestMapping("/api/movies")
public class Controller {
	@Autowired
    private MovieRepo movieRepo;
	@GetMapping("/")
	public List<Movie> getAllMovie(){
		return movieRepo.findAll();
	}
	
	//GetById     -"/api/movies/{id}
	//             -return :Movie
	@GetMapping("/{id}")
	public Optional<Movie> getMovieById(@PathVariable int id) {
		//Check if movie exists for id
		//if yes,return movie
		//if no, return NotFound
		Optional<Movie> m = movieRepo.findById(id);
		if (m.isPresent())
			return m;
		else throw new ResponseStatusException(
				HttpStatus.NOT_FOUND,"Movie not found for id: "+id);
		
	}
	
	
	
	//post           -"/api/movies/" (movie will be in the RequestBody)
	//                - return Movie
	@PostMapping("")
	public Movie addMovie(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}
	
	//put             -"/api/movies/{id}(movie passed inRB)
	//                -return :No content
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putMovie(@PathVariable int id,@RequestBody Movie movie) {
		//check id passed vs id in instance
		//-BadRequest if not exist
		
		if (id != movie.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Movie id mismatch vs url");
		}
		//If movie exists ,update,else not found
			
			else if( movieRepo.existsById(movie.getId())) {
		movieRepo.save(movie);
			}
			else {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Movie id mismatch vs URL");
			
		}
	}

	
	//delete           - "/api/movies/{id}
	//-return          -NoContent
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMovie(@PathVariable int id) {
		if (movieRepo.existsById(id)){
			movieRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND,"Movie not found for id: "+id);
		}
	}
	
	// Find movie by rating R and year 2024
	@GetMapping("/by-rating-year/{rating}/{year}")
		public List<Movie> findMoviesByRatingAndYear(@PathVariable String rating, @PathVariable int year){
			return movieRepo.findByRatingAndYear(rating,year);
		}
	
	//Find movies by rating =PG and year>=1999
	@GetMapping("/by-rating-year-after/{rating}/{year}")
	public List<Movie> findMoviesByRatingAndYearAfter(@PathVariable String rating, @PathVariable int year){
		return movieRepo.findByRatingAndYearGreaterThanEqual(rating,year);
		
	}
	
	//Find Movies where rating is not R
	@GetMapping("/by-rating-not/{rating}")
	public List<Movie> findMoviesNotByRating(@PathVariable String rating){
		return movieRepo.findByRatingNot(rating);
	}
  
}
