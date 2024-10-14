package com.prs.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.prs.db.RequestRepo;
import com.prs.db.UserRepo;
import com.prs.model.Request;

@RestController
@RequestMapping("/api/Requests")
public class RequestController {
	@Autowired
	private final RequestRepo requestRepo;
	
	@Autowired
	private final UserRepo userRepo;
	
	public RequestController(RequestRepo requestRepo,UserRepo userRepo) {
		this.requestRepo =requestRepo;
		this.userRepo =userRepo;
	}

	// Get all Requests
	@GetMapping("/")
	public List<Request> getAllRequest() {
		return requestRepo.findAll();
	}

	// GetById -"/api/requests/{id}
	// - return :Request
	@GetMapping("/{id}")
	public Optional<Request> getRequestById(@PathVariable int id) {
		// System.out.println("Fetching product with ID: " + id);
		// Check if Product exists for id
		// if yes,return product
		// if no, return NotFound
		Optional<Request> r = requestRepo.findById(id);
		if (r.isPresent())
			return r;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);

	}
	
	
	
	
	@PostMapping("")
	public Request addRequest(@RequestBody Request request) {
		// Fetch the maximum request number from the database
		String maxRequestNumber = requestRepo.findMaxRequestNumber();
		
		//Set the generated request number
		request.setRequestNumber(incrementRequestNumber(maxRequestNumber));
		request.setStatus("New");
		request.setSubmittedDate(LocalDateTime.now());
		request.setTotal(0.0);
		return requestRepo.save(request);
	}
	//Method for generating incremental request number
	private String incrementRequestNumber(String maxReqNbr) {
		StringBuilder nextReqNbr = new StringBuilder();
		//Extract the numeric part from the 7th character onward
		int nbr = Integer.parseInt(maxReqNbr.substring(7));
		//Increment the numeric part
		nbr++;
		nextReqNbr.append(maxReqNbr.substring(0, 1));
		//Generate the date part in the format"yyMMdd"
		String dateStr =LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		//Format the incremented number to be 4 digit
		String nbrStr =String.format("%04d", nbr);
		
		//Append the date and the new incremented number
		nextReqNbr.append(dateStr).append(nbrStr);
		return nextReqNbr.toString();
			
	}

	
	
	
	
	
	
	
	// put -"/api/requests/{id}(request passed in RB)
		// -return :No content
		@PutMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void putRequest(@PathVariable int id, @RequestBody Request request) {
			// check id passed vs id in instance
			// -BadRequest if not exist

			if (id != request.getId()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request id mismatch vs url");
			}
			// If request exists ,update,else not found

			else if (requestRepo.existsById(request.getId())) {
				requestRepo.save(request);
			} else {

				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request id mismatch vs URL");

			}
		}
		
		//PUT :api/Requests/submit-review/{id}
		@PutMapping("/submit-review/{id}")
		
		public ResponseEntity<Request> submitForReview(@PathVariable int id){
			
			//Fetch the request from the database using the id
			Optional<Request>optionalRequest =requestRepo.findById(id);
			
			//If the request is not found,return 404 Not Found
			if(!optionalRequest.isPresent()) {
				return ResponseEntity.notFound().build();
				
			}
			//Retrieve the request
			Request request =optionalRequest.get();
			//Update the status based on the total value
			if(request.getTotal().compareTo(50.0) <=0) {
				request.setStatus("APPROVED");
			}else {
				request.setStatus("REVIEW");
			}
			//Save the changes to the database
			try {
				requestRepo.save(request);
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			// Return 200 OK with the updated request
			return ResponseEntity.ok(request);
			
		}
		
		
		//Fetching requests that are under review and belong to other users.
		//GET: api/Requests/list-review/{userId}
		
		@GetMapping("/list-review/{userId}")
		public ResponseEntity<List<Request>> getRequestForReview(@PathVariable int userId){
			// check if the user exists
			boolean userExists =userRepo.existsById(userId);
			if(!userExists) {
				return ResponseEntity.notFound().build();
			}
			//Fetch requests with Status ="REVIEW" and UserId != id as path variable
			List<Request> requests = requestRepo.findByStatusAndUserIdNot("REVIEW",userId);
			//If no requests are found, return a 404 response
			if(requests.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(requests);
		}
		
		//Put  the request status to approve and save request
		@PutMapping("/approve/{id}")
		 
		public ResponseEntity<String>  approveRequest(@PathVariable("id") int id){
			Optional<Request> optionalRequest = requestRepo.findById(id);
			
			if(optionalRequest.isEmpty()) {
				return new ResponseEntity<>("Request with ID" + id + "not found",HttpStatus.NOT_FOUND);		
			}
			Request request =optionalRequest.get();
			// Update the status to "Approved"
			request.setStatus("APPROVED");
			
			try {
				//Save changes to the database
				requestRepo.save(request);
			} catch (Exception e ) {
				//Handle potential concurrency exception
				if(! requestRepo.existsById(id)) {
					return new ResponseEntity<>("Request with Id" + id + "not found",HttpStatus.NOT_FOUND);
				}
				throw new RuntimeException(e);
			
			} 
			//Return a success response
			return new ResponseEntity<>("Request approved successfully",HttpStatus.NO_CONTENT);
			
		}
				
		
		//Put the request status to reject,set reason for rejection and save the request
				@PutMapping("/reject/{id}")
				 
				public ResponseEntity<String>  rejectRequest(@PathVariable("id") int id){
					Optional<Request> optionalRequest = requestRepo.findById(id);
					
					if(optionalRequest.isEmpty()) {
						return new ResponseEntity<>("Request with ID" + id + "not found",HttpStatus.NOT_FOUND);		
					}
					Request request =optionalRequest.get();
					// Update the status to "REJECTED"
					request.setStatus("REJECTED");
					request.setReasonForRejection("Not on the approved List");
					
					try {
						//Save changes to the database
						requestRepo.save(request);
					} catch (Exception e ) {
						//Handle potential concurrency exception
						if(! requestRepo.existsById(id)) {
							return new ResponseEntity<>("Request with Id" + id + "not found",HttpStatus.NOT_FOUND);
						}
						throw new RuntimeException(e);
					
					} 
					//Return a success response
					return new ResponseEntity<>("Request approved successfully",HttpStatus.NO_CONTENT);
					
				}
						
				
		
		
		
		// delete - "/api/requests/{id}
		// -return -NoContent
		@DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deleteRequest(@PathVariable int id) {
			if (requestRepo.existsById(id)) {
				requestRepo.deleteById(id);
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
			}

		}


}
