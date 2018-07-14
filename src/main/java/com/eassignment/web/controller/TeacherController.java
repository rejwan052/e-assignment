package com.eassignment.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eassignment.authentication.IAuthenticationFacade;
import com.eassignment.persistence.model.User;
import com.eassignment.service.IAssignmentService;
import com.eassignment.service.IUserService;
import com.eassignment.web.util.PageConstantUtils;

@Controller
@PreAuthorize("@securityService.hasTeacherPrivilege()")
@RequestMapping("/teacher")
public class TeacherController extends EAssignmentBaseController{
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAssignmentService assignmentService;
	
	
	
	@RequestMapping("/assignments")
	public String assignments(Model model,
			@RequestParam(value = "searchTerm", required=false) String searchTerm,
			@RequestParam(value = "status", required = false) Boolean status,
			@SortDefault("submitStartDate") @PageableDefault(size=10) Pageable pageable){
		
		LOGGER.info("authenticated user email :"+authenticationFacade.getAuthentication().getName());
		User user = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());
		
		model.addAttribute("page", assignmentService.getUserAssignmentsByTitleOrStatus(user, searchTerm, status, pageable));
		
		return PageConstantUtils.ASSIGNMENTS;
	}
	

}
