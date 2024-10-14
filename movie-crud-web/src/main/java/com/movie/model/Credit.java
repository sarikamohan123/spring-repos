package com.movie.model;

public class Credit {
	
	private int id;
	private int actorId;
	private int movieId;
	private String role;
	
	//Default Constructor
	
	public Credit() {
		super();
	}



	//Fully loaded Constructor
	public Credit(int id, int actorId, int movieId, String role) {
		super();
		this.id = id;
		this.actorId = actorId;
		this.movieId = movieId;
		this.role = role;
		
		
		//Getter and setters
		
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getActorId() {
		return actorId;
	}



	public void setActorId(int actorId) {
		this.actorId = actorId;
	}



	public int getMovieId() {
		return movieId;
	}



	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}

//tostring

	@Override
	public String toString() {
		return "Credit [id=" + id + ", actorId=" + actorId + ", movieId=" + movieId + ", role=" + role + "]";
	}


	
}
