package com.isylph.basis.jwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BaseJwtUser extends BaseUser implements UserDetails {

    public BaseJwtUser() {
        super();
    }


    public BaseJwtUser(Long id, String user, String role) {
        super(id, user, role);
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (CollectionUtils.isEmpty(getRoles())){
            return null;
        }
        List<SimpleGrantedAuthority> aus = new ArrayList<>();
        for(String s: getRoles()){
            aus.add(new SimpleGrantedAuthority(s));
        }

        return aus;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
