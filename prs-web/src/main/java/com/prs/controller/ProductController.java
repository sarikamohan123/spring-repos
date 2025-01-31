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

import com.prs.db.ProductRepo;
import com.prs.model.Product;

@RestController
@RequestMapping("/api/Products")
public class ProductController {
	@Autowired
	private ProductRepo productRepo;

	// Get all Products
	@GetMapping("/")
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}

	// GetById -"/api/products/{id}
//      - return :Product
	@GetMapping("/{id}")
	public Optional<Product> getProductById(@PathVariable int id) {
		// System.out.println("Fetching product with ID: " + id);
		// Check if Product exists for id
		// if yes,return product
		// if no, return NotFound
		Optional<Product> p = productRepo.findById(id);
		if (p.isPresent())
			return p;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: " + id);

	}
	

	// post -"/api/products/" (vendor will be in the RequestBody)
	// - return product
	@PostMapping("")
	public Product addProduct(@RequestBody Product product) {
		return productRepo.save(product);
	}
	
	

	// put -"/api/products/{id}(product passed in RB)
	// -return :No content
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putProduct(@PathVariable int id, @RequestBody Product product) {
		// check id passed vs id in instance
		// -BadRequest if not exist

		if (id != product.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id mismatch vs url");
		}
		// If product exists ,update,else not found

		else if (productRepo.existsById(product.getId())) {
			productRepo.save(product);
		} else {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id mismatch vs URL");

		}
	}

	// delete - "/api/products/{id}
	// -return -NoContent
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable int id) {
		if (productRepo.existsById(id)) {
			productRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: " + id);
		}

	}

}
