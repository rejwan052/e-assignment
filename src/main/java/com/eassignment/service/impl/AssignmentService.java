package com.eassignment.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.eassignment.authentication.IAuthenticationFacade;
import com.eassignment.mapper.AssignmentMapper;
import com.eassignment.persistence.dao.AssignmentRepository;
import com.eassignment.persistence.dao.RoleRepository;
import com.eassignment.persistence.dao.UserRepository;
import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.AssignmentStudent;
import com.eassignment.persistence.model.Role;
import com.eassignment.persistence.model.User;
import com.eassignment.predicates.AssignmentPredicates;
import com.eassignment.service.IAssignmentService;
import com.eassignment.service.IAssignmentStudentService;
import com.eassignment.service.IUserService;
import com.eassignment.web.dto.AssignmentDTO;
import com.eassignment.web.dto.AssignmentInfoDTO;
import com.eassignment.web.error.AssignmentTitleAlreadyExistsByUserException;
import com.querydsl.core.types.Predicate;

@Service
@Transactional
public class AssignmentService implements IAssignmentService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserService userService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private IAssignmentStudentService  assignmentStudentService;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	
	
	@Override
	public Page<AssignmentInfoDTO> getUserAssignmentsByTitleOrStatus(User user, String searchTerm,Boolean status,Pageable pageable) {
		
		Predicate userAssignmentsPredicate = AssignmentPredicates.getAssignmentsByUserOrTitleOrStatus(user, searchTerm, status);
		LOGGER.info("assignment search predicate : "+userAssignmentsPredicate);
		
		Page<Assignment> assignments = assignmentRepository.findAll(userAssignmentsPredicate,pageable);
		
		return AssignmentMapper.mapEntityPageIntoDTOPage(pageable, assignments);
	}

	@Override
	public void saveAssignment(AssignmentDTO assignmentDTO) {
		
    	if (authenticationFacade.isAuthenticated()) {
    		
    		User assignmentUser = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());
    		
    		if (assignmentDTO.getId()!=0) {
    			
    			Assignment assignment = assignmentRepository.findOne(assignmentDTO.getId());
    			
    			/*LOGGER.info("assignment status :"+assignment.getStatus());*/
    			
    			List<User> sendEmailUserList = new ArrayList<User>();
    			
    			List<User> newEmailUserList = new ArrayList<User>();
    			
    			assignment.setTitle(assignmentDTO.getTitle());
    			assignment.setSession(assignmentDTO.getSession());
    			assignment.setInstructions(assignmentDTO.getInstructions());
    			assignment.setUser(assignmentUser);
    			assignment.setSubmitStartDate(assignmentDTO.getSubmitStartDate());
    			assignment.setSubmitEndDate(assignmentDTO.getSubmitEndDate());
    			
    			Set<AssignmentStudent>  assignmentStudents= new HashSet<AssignmentStudent>();
    			
    			if (!StringUtils.isEmpty(assignmentDTO.getEmailsTo())) {
    				
    				List<String> emailList = Arrays.asList(assignmentDTO.getEmailsTo().split("\\s*,\\s*"));
        			
        			for (String email : emailList) {
        				
        				AssignmentStudent existsAssignmentStudent = assignmentStudentService.findByEmailAndAssignmentId(email,assignmentDTO.getId());
        				
        				if (existsAssignmentStudent!=null) {
        					
        					User existsAssignmentUser = userService.findUserByEmail(existsAssignmentStudent.getEmail());
        					sendEmailUserList.add(existsAssignmentUser);
        					
        					if (!existsAssignmentStudent.isDateChange()) {
        						existsAssignmentStudent.setSubmitStartDate(assignmentDTO.getSubmitStartDate());
        						existsAssignmentStudent.setSubmitEndDate(assignmentDTO.getSubmitEndDate());
							}
        					assignmentStudents.add(existsAssignmentStudent);
						}else {
							
							AssignmentStudent assignmentStudent = new AssignmentStudent();
	        				
	        				User user = userService.findUserByEmail(email);
							if (user!=null) {
								sendEmailUserList.add(user);
								assignmentStudent.setEmail(user.getEmail());
							}else {
								User createUser = createUserIfNotFound(email);
								sendEmailUserList.add(createUser);
								assignmentStudent.setEmail(createUser.getEmail());
							}
							
							assignmentStudent.setAssignment(assignment);
	        				assignmentStudent.setSubmitStartDate(assignmentDTO.getSubmitStartDate());
	        				assignmentStudent.setSubmitEndDate(assignmentDTO.getSubmitEndDate());
	        				
	        				AssignmentStudent student = assignmentStudentService.findByEmailAndAssignment(email, assignment);
	        				if (student!=null) {
	        					assignmentStudent.setId(student.getId());
	    					}
	        				
	        				assignmentStudents.add(assignmentStudent);
						}
        				
    				}
    			}
    			
    			Set<AssignmentStudent>  deletedAssignmentStudents= new HashSet<AssignmentStudent>();
    			
    			for (AssignmentStudent existsAssignmentStudent : assignment.getAssignmentStudents()) {
    				
    				boolean result = isObjectInSet(existsAssignmentStudent,assignmentStudents);
    				/*LOGGER.info("Contains :"+existsAssignmentStudent.getEmail()+"--->"+result);*/
    				if (!result) {
    					AssignmentStudent deletedAssignmentStudent = assignmentStudentService.findByEmailAndAssignment(existsAssignmentStudent.getEmail(),assignment);
    					/*LOGGER.info("deletedAssignmentStudent :"+deletedAssignmentStudent.getId()+"--->"+deletedAssignmentStudent.getEmail());*/
    					deletedAssignmentStudents.add(deletedAssignmentStudent);
					}
				}
    			
    			/*LOGGER.info("Deleted AssignmentStudents Size :"+deletedAssignmentStudents.size());*/
    			for (AssignmentStudent deletedAssignmentStudent : deletedAssignmentStudents) {
    				assignmentStudentService.deleteAssignmentStudent(deletedAssignmentStudent);
				}
    			
    			
    			assignment.setStatus(assignmentDTO.getStatus());
    			assignment.setAssignmentStudents(assignmentStudents);
    			assignmentRepository.save(Arrays.asList(assignment));
    			LOGGER.info(" Update assignment "+assignment.getTitle());
    			
    			// Sent email
    			if (assignment.getStatus()==false && assignmentDTO.getStatus()==true) {
    				
    				/*LOGGER.info("Assignment Status in 1st: "+assignmentDto.getStatus()+"---->"+assignment.getStatus());*/
					/*try {
						emailService.sendAssignmentNotification(sendEmailUserList,assignmentDto,assignmentUser);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
    				/*eventPublisher.publishEvent(new AssignmentEmailNotificationEvent(sendEmailUserList, assignmentDto, assignmentUser));*/
					
				}else if (assignment.getStatus()==true) {
					
					LOGGER.info("Assignment Status in 2nd:"+assignmentDTO.getStatus());
					
					for (AssignmentStudent assignmentStudent : assignmentStudents) {
						
						/*LOGGER.info("Assignment Status: "+assignmentStudent.getEmail());*/
						
	    				boolean result = isObjectInSet(assignmentStudent,assignment.getAssignmentStudents());
	    				
	    				if (!result) {
							User newEmailuser = userService.findUserByEmail(assignmentStudent.getEmail());
							LOGGER.info("Newly sent email user :"+newEmailuser.getEmail()+"--->"+result);
							if (newEmailuser!=null) {
								newEmailUserList.add(newEmailuser);
							}
						}
					}
					
					if (newEmailUserList.size()>0) {
						LOGGER.info("New Entry Email size :"+newEmailUserList.size());
						/*try {
							emailService.sendAssignmentNotification(newEmailUserList,assignmentDto,assignmentUser);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}*/
						/*eventPublisher.publishEvent(new AssignmentEmailNotificationEvent(newEmailUserList, assignmentDto, assignmentUser));*/
					}
				}
    			
			}else {
				
				if (assignmentTitleByUserExist(assignmentUser, assignmentDTO.getTitle())) {
					throw new AssignmentTitleAlreadyExistsByUserException("Assignment title already exists with "+assignmentUser.getEmail());
				}
				
				
				Assignment assignment = new Assignment();
				
				assignment.setTitle(assignmentDTO.getTitle());
				assignment.setSession(assignmentDTO.getSession());
				assignment.setSubmitStartDate(assignmentDTO.getSubmitStartDate());
				assignment.setSubmitEndDate(assignmentDTO.getSubmitEndDate());
				assignment.setInstructions(assignmentDTO.getInstructions());
				assignment.setCreateDate(new Date());
				assignment.setUser(assignmentUser);
				assignment.setStatus(assignmentDTO.getStatus());
				
				List<User> sendEmailUserList = new ArrayList<User>();
				
				
				Set<AssignmentStudent> assignmentEmails = new HashSet<AssignmentStudent>();
				
				if (!StringUtils.isEmpty(assignmentDTO.getEmailsTo())) {
					
					List<String> emailList = Arrays.asList(assignmentDTO.getEmailsTo().split("\\s*,\\s*"));
					
					for (String email : emailList) {
						
						AssignmentStudent assignmentStudent = new AssignmentStudent();
						
						User user = userService.findUserByEmail(email);
						
						if (user!=null) {
							sendEmailUserList.add(user);
							assignmentStudent.setEmail(user.getEmail());
						}else {
							
							User createUser = createUserIfNotFound(email);
							sendEmailUserList.add(createUser);
							assignmentStudent.setEmail(createUser.getEmail());
							
						}
						
						assignmentStudent.setAssignment(assignment);
						assignmentStudent.setSubmitStartDate(assignmentDTO.getSubmitStartDate());
						assignmentStudent.setSubmitEndDate(assignmentDTO.getSubmitEndDate());
						
						assignmentEmails.add(assignmentStudent);
						
					}
					
				}
				assignment.setAssignmentStudents(assignmentEmails);
				assignmentRepository.save(Arrays.asList(assignment));
				LOGGER.info(" Add assignment "+assignment.getTitle());
				
				// Sent email
				if (assignmentDTO.getStatus()==true) {
					/*try {
						emailService.sendAssignmentNotification(sendEmailUserList,assignmentDto,assignmentUser);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
					/*eventPublisher.publishEvent(new AssignmentEmailNotificationEvent(sendEmailUserList,assignmentDto,assignmentUser));*/
				}
			}
    	}
	}
	
	
	private User createUserIfNotFound(String email){
		
		final Role studentRole = roleRepository.findByName("ROLE_STUDENT");
        final User user = new User();
        /*user.setFirstName("Student");*/
        /*user.setLastName("Test");*/
        user.setPassword(passwordEncoder.encode(email));
        user.setEmail(email);
        user.setRoles(Arrays.asList(studentRole));
        user.setEnabled(true);
        userRepository.save(user);
		
		return user;
	}
	
	private boolean isObjectInSet(AssignmentStudent object, Set<AssignmentStudent> set) {
		
		   boolean result = false;

		   for(AssignmentStudent o : set) {
		     if(o.getEmail().equalsIgnoreCase(object.getEmail())) {
		       result = true;
		       break;
		     }
		   }

		   return result;
	}
	
	private boolean assignmentTitleByUserExist(User user,final String title){
    	final Assignment assignment = assignmentRepository.findByUserAndTitleIgnoreCase(user, title);
    	if (assignment!=null) {
			return true;
		}
    	return false;
    }

	@Override
	public Assignment getAssignmentByIdAndUser(long id, long userId) {
		return assignmentRepository.findByIdAndUserId(id, userId);
	}

}
