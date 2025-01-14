package com.prs.controller;

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

import com.prs.db.UserRepo;
import com.prs.model.User;

@RestController
@RequestMapping("/api/Users")
public class UserController {
	@Autowired
	private UserRepo userRepo;

//Get all Users
	@GetMapping("/")
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

//GetById   -"/api/users/{id}
//          - return :User
	@GetMapping("/{id}")
	public Optional<User> getUserById(@PathVariable int id) {
		// Check if user exists for id
		// if yes,return movie
		// if no, return NotFound
		Optional<User> u = userRepo.findById(id);
		if (u.isPresent())
			return u;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id: " + id);

	}
	//post -"/api/users/" (user will be in the RequestBody)
	//     - return User
		@PostMapping("")
		public User addUser(@RequestBody User user) {
			return userRepo.save(user);
		}
		
		//put             -"/api/users/{id}(user passed in RB)
		//                -return :No content
		@PutMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void putUser(@PathVariable int id,@RequestBody User user) {
			//check id passed vs id in instance
			//-BadRequest if not exist
			
			if (id != user.getId()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User id mismatch vs url");
			}
			//If user exists ,update,else not found
				
				else if( userRepo.existsById(user.getId())) {
			userRepo.save(user);
				}
				else {
				
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User id mismatch vs URL");
				
			}
		}
			
			//delete           - "/api/users/{id}
			//-return          -NoContent
			@DeleteMapping("/{id}")
			@ResponseStatus(HttpStatus.NO_CONTENT)
			public void deleteUser(@PathVariable int id) {
				if (userRepo.existsById(id)){
					userRepo.deleteById(id);
				}
				else {
					throw new ResponseStatusException(
							HttpStatus.NOT_FOUND,"User not found for id: "+id);
				}
			

		}

}
