package com.onjung.onjung.common.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import javax.naming.AuthenticationException;

public class DummyAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) {
        // Dummy implementation. We don't check anything here.
        return authentication;
    }

    public DummyAuthenticationManager() {
    }
}
