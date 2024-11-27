package com.isylph.basis.jwt.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BaseJwtUser implements UserDetails {

    private Long id;

    private String name;

    private String username;

    private Integer type;

    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    // Token的有效时长
    private Long expiredTime;

    private List<String> roles;


    //private Collection<? extends GrantedAuthority> authorities;

    public BaseJwtUser() {
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = true;
    }


    public BaseJwtUser(Long id, String user, String role) {
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

    public BaseJwtUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BaseJwtUser setName(String name) {
        this.name = name;
        return this;
    }

    public BaseJwtUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public BaseJwtUser setType(Integer type) {
        this.type = type;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public BaseJwtUser setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (CollectionUtils.isEmpty(roles)){
            return null;
        }
        List<SimpleGrantedAuthority> aus = new ArrayList<>();
        for(String s: roles){
            aus.add(new SimpleGrantedAuthority(s));
        }

        return aus;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "BaseJwtUserVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", type=" + type +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                '}';
    }
}
