package com.eassignment.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eassignment.authentication.IAuthenticationFacade;
import com.eassignment.persistence.model.Assignment;
import com.eassignment.persistence.model.AssignmentStudent;
import com.eassignment.persistence.model.Document;
import com.eassignment.persistence.model.User;
import com.eassignment.service.IAssignmentService;
import com.eassignment.service.IAssignmentStudentService;
import com.eassignment.service.IDocumentService;
import com.eassignment.service.IUserService;
import com.eassignment.web.dto.AssignmentDTO;
import com.eassignment.web.dto.AssignmentStudentSearchDTO;
import com.eassignment.web.dto.DocumentDTO;
import com.eassignment.web.dto.EmailsDto;
import com.eassignment.web.util.GenericResponse;
import com.eassignment.web.util.PageConstantUtils;



@Controller
@PreAuthorize("@securityService.hasTeacherPrivilege()")
@RequestMapping("/teacher")
public class TeacherController extends EAssignmentBaseController{
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	// The Environment object will be used to read parameters from the 
	// application.properties configuration file
	@Autowired
	private Environment env;
		
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAssignmentService assignmentService;
	
	@Autowired
	private IAssignmentStudentService assignmentStudentService;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private IDocumentService documentService;
	
	
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
	
	@RequestMapping(value="/viewAssignmentStudents",method=RequestMethod.GET)
	public String viewAssignmentStudent(Model model,@RequestParam(value = "assignmentId", required = true) Long assignmentId,
										@SortDefault.SortDefaults({
											@SortDefault(sort = "status",direction = Sort.Direction.ASC)
										}) Pageable pageable,@ModelAttribute("searchDTO") AssignmentStudentSearchDTO searchDTO){
		
		if (authenticationFacade.isAuthenticated()) {
			
			User currentUser = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());
    		
    		Assignment assignment = assignmentService.getAssignmentByIdAndUser(assignmentId,currentUser.getId());
    		if (assignment!=null) {
    			model.addAttribute("assignmentStudents", assignmentStudentService.getAssignmentStudentsByAssignment(assignment,null,pageable));
    			model.addAttribute("assignment", assignment);
    		}
		}
		
		
		return PageConstantUtils.ASSIGNMENT_STUDENTS;
	}
	
	@RequestMapping(value="/searchOrFilterAssignmentStudents",method = RequestMethod.GET)
	public String searchOrFilterAssignmentStudents(Model model,@ModelAttribute("searchDTO") AssignmentStudentSearchDTO searchDTO,
												   @SortDefault.SortDefaults({
														@SortDefault(sort = "email",direction = Sort.Direction.ASC)
												   })Pageable pageable){
		
		if (authenticationFacade.isAuthenticated()) {
			model.addAttribute("assignmentStudents", assignmentStudentService.getAssignmentStudentsByAssignment(null,searchDTO,pageable));
		}
	
		return PageConstantUtils.ASSIGNMENT_STUDENTS_SEARCH_OR_FILTER.toString();
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
	
	
	
	//Assignment Reference Documents
	@RequestMapping(value="/assignment/{assignmentId}/documents",method=RequestMethod.GET)
	public String assignmentReferenceDocuments(Model model,@PathVariable Long assignmentId,@SortDefault.SortDefaults({
													@SortDefault(sort = "createOn",direction = Sort.Direction.DESC)
											  }) Pageable pageable){
		
		if (authenticationFacade.isAuthenticated()) {
			User currentUser = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());
			Assignment assignment = assignmentService.getAssignmentByIdAndUser(assignmentId, currentUser.getId());
			if(null != assignment){
				model.addAttribute("assignmentsDocuments", documentService.getAllDocumentsByAssignmenmt(assignment, pageable));
				model.addAttribute("assignment", assignment);
			}
			
		}
		
		return "/teacher/referenceDocuments";
	}
	
	@RequestMapping(value = "/uploadAssignmentDocuments", method = RequestMethod.POST)
	public @ResponseBody List<DocumentDTO> uploadAssignmentDocuments(MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "assignmentId") Long assignmentId) throws IOException {

		List<DocumentDTO> uploadedFiles = null;
		if (authenticationFacade.isAuthenticated()) {

			User user = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());

			// Getting uploaded files from the request object
			Map<String, MultipartFile> fileMap = request.getFileMap();

			// Maintain a list to send back the files info. to the client side
			uploadedFiles = new ArrayList<DocumentDTO>();

			// Iterate through the map
			for (MultipartFile multipartFile : fileMap.values()) {

				// Save the file to local disk
				saveFileToLocalDisk(multipartFile, user);

				LOGGER.info("Assignment Id :" + assignmentId);

				DocumentDTO fileInfo = getUploadedFileInfo(multipartFile, user, assignmentId);

				// Save the file info to database
				Document document = saveFileToDatabase(fileInfo);

				// adding the file info to the list
				uploadedFiles.add(fileInfo);
			}

		}
		return uploadedFiles;
	}
	
	
	
	private void saveFileToLocalDisk(MultipartFile multipartFile,User user) throws IOException, FileNotFoundException {

		String outputFileName = getOutputFilename(multipartFile,user);

		// Get the filename and build the local file path
		String filename = multipartFile.getOriginalFilename();
		String filepath = Paths.get(outputFileName, filename).toString();

		// Save the file locally
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
		stream.write(multipartFile.getBytes());
		stream.close();
	}
	
	private String getOutputFilename(MultipartFile multipartFile,User user) {

		return getDestinationLocation(user);
	}
	
	private String getDestinationLocation(User user) {

		/*String parrentDirectory = "D:/file_to_save/";*/
		String parrentDirectory = env.getProperty("e-assignment.paths.uploadedFiles");
		boolean flag = false;

		File file = new File(parrentDirectory + user.getEmail());
		if (!file.exists()) {
			flag = file.mkdirs();
		}

		return file.getAbsolutePath();
	}
	
	private DocumentDTO getUploadedFileInfo(MultipartFile multipartFile,User user,Long assignmentId) throws IOException {

		DocumentDTO fileInfo = new DocumentDTO();
	 	
	    fileInfo.setName(multipartFile.getOriginalFilename());
	    fileInfo.setSize(multipartFile.getSize());
	    fileInfo.setType(multipartFile.getContentType());
	    fileInfo.setLocation(getDestinationLocation(user));
	    fileInfo.setUserId(user.getId());
	    fileInfo.setAssignmentId(assignmentId);
	    fileInfo.setStatus(1);  // Status = 1, teacher assignment related document
	    
	    return fileInfo;
	}
	
	private Document saveFileToDatabase(DocumentDTO uploadedFile) {

	    return documentService.saveOrUpdate(uploadedFile);

	}

}
