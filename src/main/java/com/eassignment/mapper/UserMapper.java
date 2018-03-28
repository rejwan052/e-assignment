package com.eassignment.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.eassignment.persistence.model.Role;
import com.eassignment.persistence.model.User;
import com.eassignment.web.dto.UserDTO;
import com.eassignment.web.util.ImageUtil;

public class UserMapper {

	static List<UserDTO> mapEntitiesIntoDTOs(List<User> entities) {
		return entities.stream().map(UserMapper::mapEntityIntoDTO).collect(Collectors.toList());
	}

	
	public static UserDTO mapEntityIntoDTO(User entity) {
		UserDTO dto = new UserDTO();

		dto.setId(entity.getId());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setEmail(entity.getEmail());
		dto.setPassword(entity.getPassword());
		dto.setBloodGroup(entity.getBloodGroup() != null ? entity.getBloodGroup() : " ");
		dto.setGender(entity.getGender() != null ? entity.getGender() : Character.MIN_VALUE);

		List<Role> roles = new ArrayList<>(entity.getRoles());
		dto.setRoles(roles);
		
		if(entity.getOrganization() != null){
			dto.setOrganizationName(entity.getOrganization().getName());
			dto.setOrganizationDescription(entity.getOrganization().getDescription());
		}
		
		if(entity.getImageEntity()!=null){
			dto.setImage(Base64.encodeBase64String(entity.getImageEntity().getImage()));
		}else{
			dto.setImage(Base64.encodeBase64String(ImageUtil.getDefaultImage()));
		}
		
		dto.setEnabled(entity.isEnabled());

		return dto;
	}

	public static Page<UserDTO> mapEntityPageIntoDTOPage(Pageable page, Page<User> source) {
		List<UserDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
		return new PageImpl<>(dtos, page, source.getTotalElements());
	}

}
