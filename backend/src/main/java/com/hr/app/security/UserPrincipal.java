package com.hr.app.security;

import com.hr.app.models.AccountTypesModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.hr.app.models.UsersModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private UsersModel user;
    String userRoleString = "";

    public UserPrincipal(UsersModel user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        String userRole = this.user.getFK_userAccountTypes().getRoleName();
        if (userRole.equals("admin")) userRoleString = "ADMIN";
        else if (userRole.equals("hr_user")) userRoleString = "HR";
        else if (userRole.equals("ceo")) userRoleString = "CEO";
        else if (userRole.equals("user")) userRoleString = "USER";
        else userRoleString = "NOT_FOUND";

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+ userRoleString);
        authorities.add(authority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (this.user == null || this.user.getIsActive() == null) {
            throw new UsernameNotFoundException("username not found");
        } else {
            return this.user.getIsActive() == true;
        }
    }

    public String getRole() {
        return this.userRoleString;
    }
}
