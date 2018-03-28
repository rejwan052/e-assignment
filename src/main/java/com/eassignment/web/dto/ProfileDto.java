package com.eassignment.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfileDto {
	
    @NotNull(message="{NotNull.profileDto.firstName}")
    @Size(min = 1,max=30,message="{Size.profileDto.firstName}")
    private String firstName;

    @NotNull(message="{NotNull.profileDto.lastName}")
    @Size(min = 1,max=30,message="{Size.profileDto.lastName}")
    private String lastName;
    
    private Character gender;
    
    private String bloodGroup;
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	@Override
	public String toString() {
		return "ProfileDto [firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender + ", bloodGroup="
				+ bloodGroup + "]";
	}

}
