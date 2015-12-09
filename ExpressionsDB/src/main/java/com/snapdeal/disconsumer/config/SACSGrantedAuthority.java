package com.snapdeal.disconsumer.config;

import org.springframework.security.core.GrantedAuthority;


public class SACSGrantedAuthority implements GrantedAuthority{
	public SACSGrantedAuthority(String name) {
		
		
	}

	@Override
	public String getAuthority() {
		return null;
	}
	
}
