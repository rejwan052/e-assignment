package com.eassignment.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.bouncycastle.crypto.tls.UserMappingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eassignment.Predicates.UserPredicates;
import com.eassignment.mapper.UserMapper;
import com.eassignment.persistence.dao.PasswordResetTokenRepository;
import com.eassignment.persistence.dao.RoleRepository;
import com.eassignment.persistence.dao.UserRepository;
import com.eassignment.persistence.dao.VerificationTokenRepository;
import com.eassignment.persistence.model.PasswordResetToken;
import com.eassignment.persistence.model.User;
import com.eassignment.persistence.model.VerificationToken;
import com.eassignment.service.IUserService;
import com.eassignment.web.dto.UserDTO;
import com.eassignment.web.dto.UserSearchDTO;
import com.eassignment.web.dto.UserStatusDto;
import com.eassignment.web.dto.UsersDto;
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
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_STUDENT")));
        
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
	public List<UserStatusDto> registerNewUserAccounts(UsersDto usersDto) {
		return null;
	}

	@Override
	public Iterable<User> searchEmail(String filter) {
		return null;
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

}