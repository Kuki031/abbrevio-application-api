package com.abbrevio.abbrevio.utils;

import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthRetrieval {

    private final UserRepository userRepository;

    public AuthRetrieval(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public <T> Object retrieveUsername(boolean wholeUser) {

        if (wholeUser) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new CustomNotFoundException(User.class, "username", authentication.getName()));
        }

        else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication.getName();
        }
    }
}
