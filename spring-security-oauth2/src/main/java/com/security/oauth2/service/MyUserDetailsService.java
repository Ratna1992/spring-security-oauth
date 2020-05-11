package com.security.oauth2.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.oauth2.configuration.UserConfig;
import com.security.oauth2.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Query createQuery = entityManager.createQuery("From User where email=:email");
		createQuery.setParameter("email", username);
		User res = (User) createQuery.getSingleResult();
		if (res == null) {
			throw new UsernameNotFoundException("No user found with email ::" + username);
		}
		return new UserConfig(res);
	}

}
