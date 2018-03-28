package com.eassignment.web.controller;

import java.util.Iterator;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eassignment.authentication.IAuthenticationFacade;
import com.eassignment.persistence.model.ImageEntity;
import com.eassignment.persistence.model.User;
import com.eassignment.service.IImageService;
import com.eassignment.service.IUserService;
import com.eassignment.web.dto.PasswordDto;
import com.eassignment.web.dto.ProfileDto;
import com.eassignment.web.error.InvalidOldPasswordException;
import com.eassignment.web.util.GenericResponse;
import com.eassignment.web.util.PageConstantUtils;

@Controller
public class UserController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private IUserService userService;
	
	@Autowired
	private IImageService imageService;
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;
	
	@Autowired
    private MessageSource messages;
	
	/*Logged in user own profile page*/
	@RequestMapping(value="/profile",method=RequestMethod.GET)
	public String userProfile(Model model){
		
		if(authenticationFacade.isAuthenticated()){
			model.addAttribute("userProfile",userService.findUserByEmail(authenticationFacade.getAuthentication().getName()));
		}
		
		return PageConstantUtils.USER_PROFILE;
	}
	
	
	// Update User Personal Info
    @RequestMapping(value = "/user/updatePersonalInfo", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse updateUserPersonalInformation(final Locale locale, @Valid ProfileDto profileDto) {
    	
        final User user = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());
        
        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setGender(profileDto.getGender());
        user.setBloodGroup(profileDto.getBloodGroup());
        
        userService.saveRegisteredUser(user);
        
        return new GenericResponse(messages.getMessage("message.updateUserSuc", null, locale));
    }
    
    
    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST
    )
    public ResponseEntity<String> uploadFile(MultipartHttpServletRequest request) {

    	if (authenticationFacade.isAuthenticated()) {
    		User user = userService.findUserByEmail(authenticationFacade.getAuthentication().getName());
    		try {
    			
                Iterator<String> itr = request.getFileNames();

                while (itr.hasNext()) {
                	
                    String uploadedFile = itr.next();
                    MultipartFile file = request.getFile(uploadedFile);
                    String mimeType = file.getContentType();
                    String filename = file.getOriginalFilename();
                    byte[] bytes = file.getBytes();
                    
                    ImageEntity imageEntity = imageService.findByUser(user);
                    if (imageEntity!=null) {
                    	imageEntity.setFilename(filename);
                    	imageEntity.setMimeType(mimeType);
                    	imageEntity.setImage(bytes);
                    	imageEntity.setUser(user);
					}else {
						imageEntity = new ImageEntity(filename,mimeType,bytes,user);
					}
                    imageService.uploadImage(imageEntity);
                }
            }
            catch (Exception e) {
                return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
            }
    	}
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
    
    
    // Change Password
    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('CHANGE_PASSWORD_PRIVILEGE')")
    @ResponseBody
    public GenericResponse changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto) {
        final User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

}
