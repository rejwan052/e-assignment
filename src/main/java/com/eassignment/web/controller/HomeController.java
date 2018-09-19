package com.eassignment.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eassignment.authentication.IAuthenticationFacade;
import com.eassignment.persistence.model.User;
import com.eassignment.security.service.SecurityService;
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
	
	@Autowired
	private SecurityService securityService;
	
	/*home page controller*/
	
    @RequestMapping(value="/home",method=RequestMethod.GET)
    public String homePage(Model model) {
    	
    	if(authenticationFacade.isAuthenticated()){
    		
    		User currentUser = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());
    		
    		if(securityService.hasAdminPrivilege()){
        		model.addAttribute("dashboardInfo", dashboardService.getEassignmentUserInformation());
        	}else if(securityService.hasTeacherPrivilege()){
        		model.addAttribute("dashboardInfo", dashboardService.getTeacherAssignmentInfo(currentUser.getId()));
        	}else if(securityService.hasStudentPrivilege()){
        		model.addAttribute("dashboardInfo", null);
        	}
    		
    	}

		return "index";
    }
	
}
