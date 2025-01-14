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

import com.prs.db.VendorRepo;
import com.prs.model.Vendor;

@RestController
@RequestMapping("/api/Vendors")
public class VendorController {
	@Autowired
	private VendorRepo vendorRepo;
	//Get all Vendors
		@GetMapping("/")
		public List<Vendor> getAllVendor() {
			return vendorRepo.findAll();
		}

	//GetById   -"/api/vendors/{id}
//	          - return :User
		@GetMapping("/{id}")
		public Optional<Vendor> getVendorById(@PathVariable int id) {
			// Check if vendor exists for id
			// if yes,return vendor
			// if no, return NotFound
			Optional<Vendor> v = vendorRepo.findById(id);
			if (v.isPresent())
				return v;
			else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found for id: " + id);

		}
		//post -"/api/vendors/" (vendor will be in the RequestBody)
		//     - return Vendor
			@PostMapping("")
			public Vendor addVendor(@RequestBody Vendor vendor) {
				return vendorRepo.save(vendor);
			}
			
			//put             -"/api/vendors/{id}(vendor passed in RB)
			//                -return :No content
			@PutMapping("/{id}")
			@ResponseStatus(HttpStatus.NO_CONTENT)
			public void putVendor(@PathVariable int id,@RequestBody Vendor vendor) {
				//check id passed vs id in instance
				//-BadRequest if not exist
				
				if (id != vendor.getId()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vendor id mismatch vs url");
				}
				//If user exists ,update,else not found
					
					else if( vendorRepo.existsById(vendor.getId())) {
				vendorRepo.save(vendor);
					}
					else {
					
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vendor id mismatch vs URL");
					
				}
			}
				
				//delete           - "/api/vendors/{id}
				//-return          -NoContent
				@DeleteMapping("/{id}")
				@ResponseStatus(HttpStatus.NO_CONTENT)
				public void deleteVendor(@PathVariable int id) {
					if (vendorRepo.existsById(id)){
						vendorRepo.deleteById(id);
					}
					else {
						throw new ResponseStatusException(
								HttpStatus.NOT_FOUND,"Vendor not found for id: "+id);
					}
				

			}



}
