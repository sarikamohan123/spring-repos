package com.movie.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.model.Movie;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

	private static List<Movie> movies = new ArrayList<>();

	// Get all movie
	@GetMapping("/")
	public List<Movie> getAll() {
		return movies;
	}
//Get a movie by id
	@GetMapping("/{id}")
	public String getMovie(@PathVariable int id) {
		for (Movie movie : movies) {
			if (movie.getId() == id) {
				return movie.toString();
			}
		}
		return "Movie not found.";

	}
	// Add a movie
	

	@PostMapping("/")

	public String add(@RequestBody Movie movie) {
		for (Movie m : movies) {
			if (m.getId() == movie.getId()) {
				return "Movie already exists";

			} 
				
		}
            movies.add(movie); 
		return movie.toString();
	}

	@PutMapping("")
	public Movie update(@RequestBody Movie movie) {
		int idx =-1;
		for (int i = 0 ; i< movies.size();i++ ) {
			if (movies.get(i).getId() == movie.getId()) {
				idx =i;
				movies.set(i, movie);
				break;
			}
		}
        if (idx <0) {
        	System.err.println("Error updating movie-id not found");
        }
        return movie;
	}
	
	@DeleteMapping("/{id}")
	public String delete (@PathVariable int id) {
		for (Movie m :movies) {
			if (m.getId() ==id) {
				movies.remove(m);
				break;
			}
		}
		return "Movie removed";
	}
	
	
	
}
