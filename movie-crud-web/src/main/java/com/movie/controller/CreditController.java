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
import com.movie.model.Credit;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

	private static List<Credit> credits = new ArrayList<>();
	
	//Get all Credits
	@GetMapping("/")
	public List<Credit> getAll(){
		return credits;
			}
	
	//Get a credit by id
	@GetMapping("/{id}")
	public String getCredit(@PathVariable int id) {
		for (Credit credit :credits ) {
			if (credit.getId()== id) {
				return credit.toString();
			}
			
		}
		return "Credit not found";
	}
	
	//Add a credit
	@PostMapping("/")
	public String  add(@RequestBody Credit credit) {
		for(Credit c: credits) {
			if(c.getId() == credit.getId()) {
				return "Credit already exist";}
			}
		
		credits.add(credit);
		return credit.toString();
		}
		
	
	
		//update a credit;
	@PutMapping("")
	public Credit update(@RequestBody  Credit credit) {
		int idx = -1;
		for(int  i=0; i< credits.size(); i++) {
			if(credits.get(i).getId()  == credit.getId()) {
				idx =i;
				credits.set(i, credit);	
				break;}
				
			}
			if(idx<0) {
				System.err.println("Error -updating credit id not found");}
			return credit;
		}
		
	
	//delete a credit
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		for(Credit c :credits) {
			if(c.getId() == id) {
				credits.remove(c);
				break;
			}
		}
		return "Credit removed";
		
		
		
	}
	
		
	}
	
	


