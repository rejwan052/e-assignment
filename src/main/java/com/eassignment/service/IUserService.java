package com.eassignment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eassignment.persistence.model.PasswordResetToken;
import com.eassignment.persistence.model.User;
import com.eassignment.persistence.model.VerificationToken;
import com.eassignment.web.dto.UserDTO;
import com.eassignment.web.dto.UserSearchDTO;
import com.eassignment.web.dto.UserStatusDto;
import com.eassignment.web.dto.UsersDTO;
import com.eassignment.web.error.UserAlreadyExistException;

public interface IUserService {
	
	UserDTO findUserDTOByEmail(String email);
	
	User findUserByEmail(String email);
	
    User registerNewUserAccount(UserDTO accountDto) throws UserAlreadyExistException;
    
    List<UserStatusDto> registerNewUserAccounts(UsersDTO usersDto);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);


    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    List<User> findByRoles(String name);
    
    List<User> findByRolesNameAndEmailIgnoreCaseContainingOrFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String roleName,String email,String firstName,String lastName);
    
    Iterable<User> searchEmail(String filter);
    
    Page<User> getAllUser(Pageable pageable);
    
    Page<User> searchUser(UserSearchDTO searchDTO,Pageable pageable);
    
    //Count all user
    long countAllUser();
    
    long countByRoleName(String roleName);
    
    //get all users
    List<User> getAllUsers();
    
    Page<UserDTO> getUsers(String searchTerm,Pageable pageable);
}

