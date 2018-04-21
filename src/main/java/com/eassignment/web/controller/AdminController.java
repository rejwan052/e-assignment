package com.eassignment.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.passay.CharacterCharacteristicsRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eassignment.authentication.IAuthenticationFacade;
import com.eassignment.persistence.model.ImageEntity;
import com.eassignment.persistence.model.Role;
import com.eassignment.persistence.model.User;
import com.eassignment.service.IImageService;
import com.eassignment.service.IOrganizationService;
import com.eassignment.service.IRoleService;
import com.eassignment.service.IUserService;
import com.eassignment.web.dto.UserStatusDto;
import com.eassignment.web.dto.UsersDTO;
import com.eassignment.web.util.GenericResponse;
import com.eassignment.web.util.PageConstantUtils;



@Controller
@PreAuthorize("@securityService.hasAdminPrivilege()")
public class AdminController extends EAssignmentBaseController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private IOrganizationService organizationService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IImageService imageService;
	
	@Autowired
	private MessageSource messages;
	
	@Autowired
	private IRoleService roleService;
	
	/*organizations page controller*/
	
	@RequestMapping(value="/organizations",method=RequestMethod.GET)
	public String organizationsPage(Model model, Pageable pageable){
		
		model.addAttribute("page", organizationService.getAllOrganization(pageable));
		
		return PageConstantUtils.ORGANIZATIONS;
	}
	
	/*e-learning all user page controller*/
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String usersPage(Model model,@RequestParam(value = "searchTerm", required=false) 
							String searchTerm,@SortDefault("email") Pageable pageable){
		
		model.addAttribute("page", userService.getUsers(searchTerm,pageable));
		
		return PageConstantUtils.USERS;
	}
	
	@RequestMapping(value="/createUser",method=RequestMethod.GET)
	public String createUserPage(Model model){
		
		UsersDTO usersDto = new UsersDTO();
		usersDto.setRoles(roleService.getAllRoles());
		
		model.addAttribute("usersDto",usersDto);
		
		return PageConstantUtils.CREATE_USER;
	}
	
	@RequestMapping(value="/createUser",method=RequestMethod.POST)
	public @ResponseBody List<UserStatusDto> createUser(@Valid UsersDTO usersDto){
		
		List<UserStatusDto> users = userService.registerNewUserAccounts(usersDto);
    	
		return users;
	}
	
	
	//User account update modal
	@RequestMapping(value="/updateUser/{userId}",method=RequestMethod.GET)
	public String updateUserModal(Model model,@PathVariable Long userId){
		
		User updateUser = userService.getUserByID(userId);
		
		model.addAttribute("updateUser", updateUser);
		model.addAttribute("roles", roleService.getAllRoles());
		
		
		return PageConstantUtils.UPDATE_USER;
	}
	
	@RequestMapping(value="/updateUser/{id}",method=RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateUser(Model model,final Locale locale,
									  @PathVariable Long id,@RequestParam Map<String, String> reqPar,
									  @RequestParam("roles") Collection<Role> roles){
		
		User updateUser = userService.getUserByID(id);
		
		String firstName = reqPar.get("firstName");
		String lastName = reqPar.get("lastName");	
		String email = reqPar.get("email");
		
		String gender = reqPar.get("gender") ;
		LOGGER.info("gender "+gender);
		
		updateUser.setFirstName(firstName);
		updateUser.setLastName(lastName);
		updateUser.setGender(gender.charAt(0));
		updateUser.setEmail(email);
		updateUser.setRoles(roles);
		
		userService.saveRegisteredUser(updateUser);
		
		return new GenericResponse(messages.getMessage("message.userUpdateSucc", null,locale));
	}
	
	@RequestMapping(value="/activeOrInactiveUser/{userId}",method=RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateUser(final Locale locale,@PathVariable Long userId,@RequestParam Map<String, String> reqPar){
		
		User updateUser = userService.getUserByID(userId);
		
		Boolean active = Boolean.valueOf(reqPar.get("enabled"));
		boolean accountIsEnabledOrNot = active.booleanValue();
		updateUser.setEnabled(accountIsEnabledOrNot);
		
		userService.saveRegisteredUser(updateUser);
		
		String msg = "";
		if(accountIsEnabledOrNot){
			msg = messages.getMessage("message.userActiveSucc", null,locale);
		}else{
			msg = messages.getMessage("message.userInactiveSucc", null,locale);
		}
		
		return new GenericResponse(msg);
	}
	
	@RequestMapping(value = "/avatar/{userId}", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<byte[]> getUserAvatarImage(@PathVariable("userId") Long userId) {
    	
    	ImageEntity imageEntity = null;
    	byte[] avatarImage = null;
    	
    	if (authenticationFacade.isAuthenticated()) {
    		imageEntity = imageService.findByUserId(userId);
            if (imageEntity!=null) {
            	avatarImage = imageEntity.getImage();
			}else {
				try {
					File file = new ClassPathResource("static/assets/images/avatar-big.png").getFile();
					byte[] defaultAvatarImage = Files.readAllBytes(file.toPath());
					avatarImage = defaultAvatarImage;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}
    	
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]> (avatarImage, headers, HttpStatus.CREATED);
        
    }


}
