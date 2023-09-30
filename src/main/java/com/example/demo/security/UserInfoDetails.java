package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.model.enums.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

//this is a replacement for the user details
//to be able to modify the saving isue (to be able to save the stuff in the bata base)
//we still need the userDetails (so we implement it ) to have the rest methods that we donot wont to change them
@Getter
public class UserInfoDetails implements UserDetails {
    private UUID id;
    private String username;
    private String password;
    @Getter
    private String email;
    private List<Role> roles;
    private boolean enabled;

    public UserInfoDetails(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.roles = user.getRoles();
    }
    //the rest methods that are implemented from user details
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    public UUID getUserId(){ return this.id;}

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
        return true;
    }
}
