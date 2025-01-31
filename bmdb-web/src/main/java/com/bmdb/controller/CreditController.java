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

import com.bmdb.db.CreditRepo;
import com.bmdb.model.Credit;
import com.bmdb.model.Movie;

@RestController
@RequestMapping("/api/credits")
public class CreditController {
	@Autowired
	private CreditRepo creditRepo;
	
	@GetMapping("/")
	public List<Credit> getAllCredit(){
		return creditRepo.findAll();
	}
	
	
	@GetMapping("/{id}")
	public Optional<Credit> getCreditById(@PathVariable int id) {
		//Check if credit exists for id
		//if yes,return credit
		//if no, return NotFound
		Optional<Credit> c = creditRepo.findById(id);
		if (c.isPresent())
			return c;
		else throw new ResponseStatusException(
				HttpStatus.NOT_FOUND,"Credit not found for id: "+id);
		
	}
	
	@PostMapping("")
	public Credit addCredit(@RequestBody Credit credit) {
		return creditRepo.save(credit);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putCredit(@PathVariable int id,@RequestBody Credit credit) {
		//check id passed vs id in instance
		//-BadRequest if not exist
		
		if (id != credit.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Credit id mismatch vs url");
		}
		//If credit exists ,update,else not found
			
			else if( creditRepo.existsById(credit.getId())) {
		creditRepo.save(credit);
			}
			else {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Credit id mismatch vs URL");
			
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCredit(@PathVariable int id) {
		if (creditRepo.existsById(id)){
			creditRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND,"credit not found for id: "+id);
		}
	}

//new requirement :Movie-Credits return all credits for a movie
	@GetMapping("/movie-credits/{movieId}")
	public List<Credit>getCreditsForMovie(@PathVariable int movieId){
		return creditRepo.findByMovieId(movieId);
	}
	
	
	//new requirement :Actor -Filmography return all credits for a actor
	@GetMapping("/actor-credits/{actorId}")
     public List<Credit> getCreditsForActor(@PathVariable int actorId){
    	 return creditRepo.findByActorId(actorId);
     }
	
}
