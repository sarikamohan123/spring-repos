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

import com.movie.model.Actor;
@RestController
@RequestMapping("/api/actors")

public class ActorController {

	private static List<Actor> actors = new ArrayList<>();

	// Get all Actors
	@GetMapping("/")
	public List<Actor> getAll() {
		return actors;
	}

	// Get Actors by id
	@GetMapping("/{id}")
	public String getActor(@PathVariable int id) {
		for (Actor actor : actors) {
			if (actor.getId() == id) {
				return actor.toString();
			}
		}
		return "Actor not found";
	}

	// Add an actor
	@PostMapping("/")
	public String add(@RequestBody Actor actor) {
		for (Actor a : actors) {
			if (a.getId() == actor.getId()) {
				return "Movie already exists";

			}

		}
		actors.add(actor);
		return actor.toString();
	}

	// Update an actor
	@PutMapping("")
	public Actor update(@RequestBody Actor actor) {
		int idx = -1;
		for (int i = 0; i < actors.size(); i++) {
			if (actors.get(i).getId() == actor.getId()) {
				idx = i;
				actors.set(i, actor);
			}

		}
		if (idx < 0) {
			System.err.println("Error updating - actor id not found");
		}
		return actor;

	}

	// Delete an actor
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		for (Actor a:actors) {
			if(a.getId()== id) {
				actors.remove(a);
			}
		}
		return "actor removed";
		
	}

}
