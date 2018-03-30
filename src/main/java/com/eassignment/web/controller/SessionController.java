package com.eassignment.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SessionController {
	
	@RequestMapping(value="/invalidSession",method=RequestMethod.GET)
	public String imvalidSession(){
		
		
		return "/session/invalidSession";
	}
	
	@RequestMapping(value="/expiredSession",method=RequestMethod.GET)
	public String expiredSession(){
		
		
		return "/session/expiredSession";
	}
}
