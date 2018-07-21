package com.eassignment.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eassignment.authentication.IAuthenticationFacade;
import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.AssignmentStudent;
import com.eassignment.persistence.model.User;
import com.eassignment.service.IAssignmentService;
import com.eassignment.service.IUserService;
import com.eassignment.web.dto.AssignmentDTO;
import com.eassignment.web.dto.EmailsDto;
import com.eassignment.web.util.GenericResponse;
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
	
	@Autowired
	private MessageSource messages;
	
	
	/*Assignments Page*/
	@RequestMapping(value="/assignments",method=RequestMethod.GET)
	public String assignments(Model model,
			@RequestParam(value = "searchTerm", required=false) String searchTerm,
			@RequestParam(value = "status", required = false) Boolean status,
			@PageableDefault(size=10) @SortDefault.SortDefaults({
                @SortDefault(sort = "submitStartDate", direction = Sort.Direction.DESC)
            })
            Pageable pageable){
		
		LOGGER.info("authenticated user email :"+authenticationFacade.getAuthentication().getName());
		User user = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());
		
		model.addAttribute("page", assignmentService.getUserAssignmentsByTitleOrStatus(user, searchTerm, status, pageable));
		
		return PageConstantUtils.ASSIGNMENTS;
	}
	
	/*Create Assignment Page*/
	@RequestMapping(value = "/createAssignment",method=RequestMethod.GET)
	public String createAssignment(Model model){
		
		model.addAttribute("assignmentDto",new AssignmentDTO());
		
		return PageConstantUtils.CREATE_ASSIGNMENT;
	}
	
	
	
	/*Start Assignment Save/Update Method*/
	@RequestMapping(value="/assignment",method=RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAssignment(final Locale locale,@ModelAttribute("assignmentDto") @Valid AssignmentDTO assignmentDTO,@RequestParam Map<String,String> reqPar){
		
 		String submitStartDate = reqPar.get("submitStartDate");
 		String submitEndDate = reqPar.get("submitEndDate");
 		
 		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
 		
			Date pStartDate = null;
			Date pEndDate = null;
			
			try {
				pStartDate = format.parse(submitStartDate);
				pEndDate = format.parse(submitEndDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Calendar c1 = Calendar.getInstance();
			c1.setTime(pStartDate);
			
			Calendar c2 = Calendar.getInstance();
			c2.setTime(pEndDate);
			
			assignmentDTO.setSubmitStartDate(c1.getTime());
			assignmentDTO.setSubmitEndDate(c2.getTime());
			
			assignmentService.saveAssignment(assignmentDTO);
		
		
    	return new GenericResponse(messages.getMessage("message.assignmentSaveSuc", null, locale));
	}
	/*End Assignment Save/Update Method*/
	
	@RequestMapping(value="/assignment/{id}",method=RequestMethod.GET)
	public String editAssignment(Model model,@PathVariable("id") long id){
		
		String viewPage = "";
		
		if (authenticationFacade.isAuthenticated()) {
			
    		User currentUser = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());
    		
    		Assignment assignment = assignmentService.getAssignmentByIdAndUser(id, currentUser.getId());
    		
    		/*LOGGER.info("Assignment Edit User:"+currentUser.getEmail()+"--->Assignment Id"+id+"---->Assignment :"+assignment);*/
    		
    		if (assignment!=null) {
    			
    			AssignmentDTO assignmentDto = new AssignmentDTO();
				
    			assignmentDto.setId(assignment.getId());
    			assignmentDto.setTitle(assignment.getTitle());
    			assignmentDto.setSession(assignment.getSession());
    			assignment.setCreateDate(assignment.getCreateDate());
    			assignmentDto.setSubmitStartDate(assignment.getSubmitStartDate());
    			assignmentDto.setSubmitEndDate(assignment.getSubmitEndDate());
    			assignmentDto.setInstructions(assignment.getInstructions());
    			assignmentDto.setStatus(assignment.getStatus());
    			StringJoiner joiner = new StringJoiner(",");
    			
    			for (AssignmentStudent assignmentStudent : assignment.getAssignmentStudents()) {
    				joiner.add(assignmentStudent.getEmail());
				}
    			assignmentDto.setEmailsTo(joiner.toString());
				
				model.addAttribute("assignmentDto", assignmentDto);
				
				viewPage = PageConstantUtils.CREATE_ASSIGNMENT;
				
			}else{
				
				viewPage = PageConstantUtils.PAGE_NOT_FOUND;
				
			}
    		
		}
		
		return viewPage;
	}
	
	@RequestMapping(value="/assignmentEmail.json/{query}",method=RequestMethod.GET)
	@ResponseBody
	public List<EmailsDto> getAssignmentEmails(@PathVariable("query") String query){
		
		List<EmailsDto> emails = new ArrayList<>();
		
		Iterable<User> iterable = userService.searchEmail(query);
		List<User> users = new ArrayList<>();
		iterable.iterator().forEachRemaining(users::add);
		
		for (User user : users) {
			EmailsDto dto = new EmailsDto();
			dto.setEmail(user.getEmail());
			
			String firstName = user.getFirstName() != null ? user.getFirstName():""; 
			String lastName = user.getLastName() !=null ?user.getLastName():"";
			
			String name = firstName+" "+lastName;
			
			dto.setName(name);
			emails.add(dto);
		}
		
		LOGGER.info("query String :"+query+"-->emails size :"+emails.size());
		
		return emails;
	}
	

}
