package com.eassignment.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
