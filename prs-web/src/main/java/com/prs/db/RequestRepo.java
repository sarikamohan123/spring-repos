package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prs.model.Request;

public interface RequestRepo extends JpaRepository<Request,Integer> {

	//Query to fetch the maximum request number
	@Query("SELECT MAX(r.requestNumber)FROM Request r")
	String findMaxRequestNumber();

	List<Request> findByStatusAndUserIdNot(String status, int userId);

	

}
