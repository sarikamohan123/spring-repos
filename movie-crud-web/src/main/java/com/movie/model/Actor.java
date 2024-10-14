package com.movie.model;

import java.sql.Date;

public class Actor {

	private int id;
	private String firstName;
	private String lastName;
	private String gender;
	private Date birthdate;
	
	//Default Constructor
		public Actor() {
			super();
			
			
		}

	
	//Fully Loaded Constructor
	
	public Actor(int id, String firstName, String lastName, String gender, Date birthdate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthdate = birthdate;
	}

	//Getters/Setters



public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}


	//ToString method
	@Override
	public String toString() {
		return "Actor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", birthdate=" + birthdate + "]";
	}
	
	
	
	
}
