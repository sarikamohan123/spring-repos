package com.prs.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.User;

public interface LoginRepo extends JpaRepository<User,Integer>{

	User findByUserNameAndPassWord(String userName, String passWord);
	

}
