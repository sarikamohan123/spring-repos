package com.prs.controller;

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

import com.prs.db.LineItemRepo;
import com.prs.db.ProductRepo;
import com.prs.db.RequestRepo;
import com.prs.model.LineItem;
import com.prs.model.Product;
import com.prs.model.Request;

@RestController
@RequestMapping("/api/LineItems")
public class LineItemController {
	@Autowired
	private LineItemRepo lineItemRepo;

	@Autowired
	private RequestRepo requestRepo;
	
	@Autowired
	private ProductRepo productRepo;

	// Get all LineItems
	@GetMapping("/")
	public List<LineItem> getAllLineItem() {
		return lineItemRepo.findAll();
	}

	// GetById
	@GetMapping("/{id}")
	public Optional<LineItem> getLineItemById(@PathVariable int id) {

		Optional<LineItem> l = lineItemRepo.findById(id);
		if (l.isPresent())
			return l;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lineitem not found for id: " + id);

	}

	
	@PostMapping("")
	public ResponseEntity<LineItem> addLineItem(@RequestBody LineItem lineItem){
		//Save the new LineItem
		LineItem savedLineItem = lineItemRepo.save(lineItem);
		
		//Recalculate the total for the associated request
		recalculateRequestTotal(savedLineItem.getRequest().getId());
		
		return new ResponseEntity<>(savedLineItem, HttpStatus.CREATED);
	}
	  
	// Method to recalculate the total cost for the associated request
	private void recalculateRequestTotal(int requestId) {
		Optional<Request> requestOptional  =requestRepo.findById(requestId);
		// check if the request exists
		if(!requestOptional.isPresent()) {
			return; //exit if the request doesn't exist
		}
		//get the request from the optional
		Request request = requestOptional.get();
		//Fetch the line items associated with the request
		List<LineItem> lineItems = lineItemRepo.findByRequestId(requestId);
		//Calculate the total by summing the product of quantity and price
		double total =0.0;
		for(LineItem item :lineItems) {
			// Check product details
	        Product product = item.getProduct();
			double itemTotal = item.getQuantity() * item.getProduct().getPrice();
			total +=itemTotal;// Add to the overall total
			}
		request.setTotal(total);
		System.out.println("New Total for Request Id "+ requestId +":" +total);
		//Save the updated request with the new total
		requestRepo.save(request);
	}
	
		
	

	
	  
	// put -
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putLineItem(@PathVariable int id, @RequestBody LineItem lineItem) {

		if (id != lineItem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs url");
		}
		// If LineItem exists ,update,else not found

		 if (lineItemRepo.existsById(lineItem.getId())) {
			 //save and get the saved entity
			LineItem savedLineItem = lineItemRepo.save(lineItem);
			//Recalculate the total for the associated request
			recalculateRequestTotal(savedLineItem.getRequest().getId());
			
		} else {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL");

		}
	}

		// delete
		@DeleteMapping("/{id}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void deleteLineItem(@PathVariable int id) {
			// Check if the LineItem exists
			Optional<LineItem> optionalLineItem = lineItemRepo.findById(id);
			//If the LineItem is found
			if  (optionalLineItem.isPresent()) {
				LineItem lineItem = optionalLineItem.get(); //Get the LieItem object
				int requestId =lineItem.getRequest().getId();// Retrieve requestId from the LineItem
				// delete the LineItem by its id
				lineItemRepo.deleteById(id);
				//Recalculate the request total using the request id
				recalculateRequestTotal(requestId);
			} else {
				//if the LineItem is not found,throw an exception
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id: " + id);
			}
	
		}

	// Get LineItems by requestId
	@GetMapping("/lines-for-req/{requestId}")
	public List<LineItem> getLineItemsForRequestId(@PathVariable int requestId) {
		return lineItemRepo.findByRequestId(requestId);
	}
}
