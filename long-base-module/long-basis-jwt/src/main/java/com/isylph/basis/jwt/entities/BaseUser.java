package com.isylph.basis.jwt.entities;


import java.util.Arrays;
import java.util.List;

public class BaseUser {

    private Long id;

    private String name;

    private String username;

    private String type;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    // Token的有效时长
    private Long expiredTime;

    private List<String> roles;


    //private Collection<? extends GrantedAuthority> authorities;

    public BaseUser() {
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = true;
    }


    public BaseUser(Long id, String user, String role) {
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = true;

        this.id = id;
        this.username = user;
        this.roles = Arrays.asList(role);
    }

    public Long getId() {
        return id;
    }

    public BaseUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BaseUser setName(String name) {
        this.name = name;
        return this;
    }

    public BaseUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getType() {
        return type;
    }

    public BaseUser setType(String type) {
        this.type = type;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public BaseUser setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public void setPassword(String password) {
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return "";
    }


    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }


    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }


    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

}
