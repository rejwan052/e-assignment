package com.eassignment.security;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.stereotype.Component;

import com.eassignment.persistence.model.Role;

@Component
public class LoggedUser implements HttpSessionBindingListener {

    private String username;
    private String firstName;
    private String lastName;
    private Collection<Role> roles; 
    
    private ActiveUserStore activeUserStore;

    public LoggedUser(String username,String firstName,String lastName,Collection<Role> roles,ActiveUserStore activeUserStore) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.activeUserStore = activeUserStore;
    }

    public LoggedUser() {
    	
    }

    public void valueBound(HttpSessionBindingEvent event) {
        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (!users.contains(user.getUsername())) {
            users.add(user.getUsername());
            users.add(user.getFirstName());
            users.add(user.getLastName());
            for (Role r : user.getRoles()) {
            	users.add(r.getName());
			}
            
        }
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (users.contains(user.getUsername())) {
            users.remove(user.getUsername());
            users.remove(user.getFirstName());
            users.remove(user.getLastName());
            
            for (Role r : user.getRoles()) {
            	users.remove(r.getName());
			}
            
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
}
