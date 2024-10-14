package com.prs.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name ="UserId", nullable =false)//Foreign key relation to User
	private User user; //Reference to the User object
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	private String status ;
	private Double total;
	private LocalDateTime submittedDate;
	private String reasonForRejection;
	private String requestNumber;
	
	@OneToMany(mappedBy ="request",cascade =CascadeType.ALL,fetch =FetchType.LAZY)
	
	private List<LineItem> lineItems;
	
	//Getters and Setters
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getJustification() {
		return justification;
	}



	public void setJustification(String justification) {
		this.justification = justification;
	}



	public LocalDate getDateNeeded() {
		return dateNeeded;
	}



	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}



	public String getDeliveryMode() {
		return deliveryMode;
	}



	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Double getTotal() {
		return total;
	}



	public void setTotal(Double total) {
		this.total = total;
	}



	public LocalDateTime getSubmittedDate() {
		return submittedDate;
	}



	public void setSubmittedDate(LocalDateTime submittedDate) {
		this.submittedDate = submittedDate;
	}



	public String getReasonForRejection() {
		return reasonForRejection;
	}



	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}



	public String getRequestNumber() {
		return requestNumber;
	}



	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	
	
	
	

}
