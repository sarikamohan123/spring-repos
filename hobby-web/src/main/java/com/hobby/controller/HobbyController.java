package com.hobby.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/hobbies")

public class HobbyController {
	
	/*This is the first controller in a Java
	 * for the bootcamp.We'ii create a list of
	 * hobbies then perform
	 * 
	 */

	private static List<String> hobbies = new ArrayList<>();
	@GetMapping("/")
	public List<String> getAll(){
		return hobbies;
	}
	@GetMapping("/{idx}")
	public String get(@PathVariable int idx){
		return hobbies.get(idx);
	}
	@PostMapping("")
	
	public String add (String hobby) {
		if(hobbies.indexOf(hobby) >=0) {
			return "Hobby already exists";
		}
		else {
			hobbies.add(hobby);
			return "Hobby added";
		}
	}
	
	@PutMapping("/{idx}/{hobby}")
	public String update(@PathVariable String hobby ,@PathVariable int idx) {
		hobbies.set(idx,hobby);
		return "Hobby updated";
	}
	@DeleteMapping("/{idx}")
	public String delete(@PathVariable int idx) {
		hobbies.remove(idx);
		return "Hobby removed";
	}
}
