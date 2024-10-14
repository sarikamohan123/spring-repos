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

import com.bmdb.db.ActorRepo;

import com.bmdb.model.Actor;


@RestController
@RequestMapping("/api/actors")
public class ActorController {
		@Autowired
	    private ActorRepo actorRepo;
		
		@GetMapping("/")
		public List<Actor> getAllActor(){
			return actorRepo.findAll();
		}
		
	
	
	@GetMapping("/{id}")
	public Optional<Actor> getActorById(@PathVariable int id) {
		//Check if actor exists for id
		//if yes,return actor
		//if no, return NotFound
		Optional<Actor> a = actorRepo.findById(id);
		if (a.isPresent())
			return a;
		else throw new ResponseStatusException(
				HttpStatus.NOT_FOUND,"Actor not found for id: "+id);
		
	}
	
	//post           -"/api/actors/" (actor will be in the RequestBody)
		//                - return Movie
		@PostMapping("")
		public Actor addActor(@RequestBody Actor actor) {
			return actorRepo.save(actor);
		}
		
		@PutMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void putActor(@PathVariable int id,@RequestBody Actor actor) {
			//check id passed vs id in instance
			//-BadRequest if not exist
			
			if (id != actor.getId()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Actor id mismatch vs url");
			}
			//If actor exists ,update,else not found
				
				else if( actorRepo.existsById(actor.getId())) {
			actorRepo.save(actor);
				}
				else {
				
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Actor id mismatch vs URL");
				
			}
		}
		
		@DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deleteActor(@PathVariable int id) {
			if (actorRepo.existsById(id)){
				actorRepo.deleteById(id);
			}
			else {
				throw new ResponseStatusException(
						HttpStatus.NOT_FOUND,"Actor not found for id: "+id);
			}
		}

}