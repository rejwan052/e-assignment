package com.eassignment.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eassignment.authentication.IAuthenticationFacade;
import com.eassignment.service.IDashboardService;
import com.eassignment.service.IUserService;

@Controller
public class HomeController extends EAssignmentBaseController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IDashboardService dashboardService;
	
	/*home page controller*/
	
    @RequestMapping(value="/home",method=RequestMethod.GET)
    public String homePage(Model model) {
		
    	model.addAttribute("dashboardInfo", dashboardService.getEassignmentUserInformation());

		return "index";
    }
	
}
