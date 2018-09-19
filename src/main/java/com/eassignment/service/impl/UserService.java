package com.eassignment.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.eassignment.mapper.UserMapper;
import com.eassignment.persistence.dao.PasswordResetTokenRepository;
import com.eassignment.persistence.dao.RoleRepository;
import com.eassignment.persistence.dao.UserRepository;
import com.eassignment.persistence.dao.VerificationTokenRepository;
import com.eassignment.persistence.model.PasswordResetToken;
import com.eassignment.persistence.model.QUser;
import com.eassignment.persistence.model.Role;
import com.eassignment.persistence.model.User;
import com.eassignment.persistence.model.VerificationToken;
import com.eassignment.predicates.UserPredicates;
import com.eassignment.service.IUserService;
import com.eassignment.web.dto.UserDTO;
import com.eassignment.web.dto.UserSearchDTO;
import com.eassignment.web.dto.UserStatusDto;
import com.eassignment.web.dto.UsersDTO;
import com.eassignment.web.error.UserAlreadyExistException;
import com.querydsl.core.types.Predicate;



@Service
@Transactional
public class UserService implements IUserService {

	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private UserRepository repository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    private static final String TOKEN_VALID = "valid";
    
    
   
    // API
    @Override
    public User registerNewUserAccount(final UserDTO accountDto) {
    	
        if (emailExist(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
        }
        
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        
        Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByName("ROLE_STUDENT"));
        
        user.setRoles(roles);
        
        return repository.save(user);
    }

    @Override
    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(final User user) {
        repository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
        }

        repository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public UserDTO findUserDTOByEmail(final String email) {
    	
    	User user = repository.findByEmail(email);
    	
        return UserMapper.mapEntityIntoDTO(user);
    }
    
    @Override
    public User findUserByEmail(final String email) {
        return repository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserByID(final long id) {
        return repository.findOne(id);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public String validateVerificationToken(String token) {
    	
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        
        
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            /*tokenRepository.delete(verificationToken);*/
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        repository.save(user);
        return TOKEN_VALID;
    }
    
    private boolean emailExist(final String email) {
        final User user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
	

	@Override
	public List<User> findByRoles(String name) {
		return repository.findByRolesName(name);
	}

	@Override
	public List<User> findByRolesNameAndEmailIgnoreCaseContainingOrFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(
			String roleName, String email, String firstName, String lastName) {
		return repository.findByRolesNameAndEmailIgnoreCaseContainingOrFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(roleName, email, firstName, lastName);
	}

	@Override
	public long countAllUser() {
		// TODO Count all user
		return repository.count();
	}

	@Override
	public List<User> getAllUsers() {
		return repository.findAll();
	}

	@Override
	public List<UserStatusDto> registerNewUserAccounts(UsersDTO usersDto) {
		List<UserStatusDto> users = null;
		if (!StringUtils.isEmpty(usersDto.getEmails())) {
			users = new ArrayList<>();
			List<String> emailList = Arrays.asList(usersDto.getEmails().split("\\s*,\\s*"));
			
			List<User> usersInBatch = new ArrayList<User>();
			
			for (String email : emailList) {
				
				UserStatusDto usersDtoStatus = new UserStatusDto();
				usersDtoStatus.setEmail(email);
				
				if (!emailExist(email)) {
					
					User user = new User();
					user.setEmail(email);
					for (Role role : usersDto.getRoles()) {
						
						Set<Role> roles = new HashSet<>();
						roles.add(roleRepository.findByName(role.getName()));
						
						user.setRoles(roles);
						usersDtoStatus.setRoleName(role.getName());
					}
					user.setPassword(passwordEncoder.encode(email));
					user.setEnabled(true);
					
					usersInBatch.add(user);
					
					usersDtoStatus.setUserCreateOrNot(true);
		        }else {
		        	usersDtoStatus.setRoleName("");
					usersDtoStatus.setUserCreateOrNot(false);
				}
				users.add(usersDtoStatus);
			}
			
			if(usersInBatch.size()>0){
				long start = System.nanoTime();
				repository.save(usersInBatch);
				LOGGER.info("INSERTED {} USERS IN: {} MILISECONDS.",usersInBatch.size(),TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));     
			}
			
			
		}
		return users;
	}

	@Override
	public Iterable<User> searchEmailByRoleAndUserId(String filter,long userId) {
		QUser qUser = QUser.user;
		Predicate predicate = qUser.email.containsIgnoreCase(filter)
				   .and(qUser.roles.any().name.eq("ROLE_STUDENT"))
				   .and(qUser.createdBy.eq(userId));
		
		LOGGER.info("assignment's email predicate :"+predicate);
		
		
		return repository.findAll(predicate);
	}

	@Override
	public Page<User> getAllUser(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Page<User> searchUser(UserSearchDTO searchDTO, Pageable pageable) {
		return null;
	}

	@Override
	public Page<UserDTO> getUsers(String searchTerm,Pageable pageable) {
		
		Predicate userSearchPredicate = UserPredicates.emailOrFirstNameOrLastNameContainsIgnoreCase(searchTerm);
		
		Page<User> allUser = repository.findAll(userSearchPredicate, pageable);
		
		return UserMapper.mapEntityPageIntoDTOPage(pageable, allUser);
	}

	@Override
	public long countByRoleName(String roleName) {
		return repository.countByRolesName(roleName);
	}

	@Override
	public boolean checkUserNameModifiable(String currentUsername, String changedUserName) {
		
		boolean isUserNameModifiable = false;
		
		if(changedUserName.equalsIgnoreCase(currentUsername)){
			isUserNameModifiable = true;
		}else if(!emailExist(changedUserName)){
			isUserNameModifiable = true;
		}
		
		return isUserNameModifiable;
	}

}