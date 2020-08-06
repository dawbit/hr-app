package com.hr.app.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hr.app.repositories.IUsersRepository;
import com.hr.app.models.UsersModel;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private IUsersRepository userRepository;

    public UserPrincipalDetailsService(IUsersRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UsersModel user = this.userRepository.findByLogin(s);
        UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
