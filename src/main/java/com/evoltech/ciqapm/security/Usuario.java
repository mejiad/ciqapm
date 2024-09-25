package com.evoltech.ciqapm.security;

import com.evoltech.ciqapm.model.jpa.BaseClass;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Usuario extends BaseClass implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String username;

    private String password;

    private String email;

    private Boolean locked = false;

    private Boolean accountExpired = false;

    private Boolean credentialsExpired = false;

    private Boolean enable = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMIN");
        GrantedAuthority userAuthority = new SimpleGrantedAuthority("USER");
        GrantedAuthority investigadorAuthority = new SimpleGrantedAuthority("INVESTIGADOR");
        GrantedAuthority tecnicoAuthority = new SimpleGrantedAuthority("TECNICO");
        GrantedAuthority coordinadorAuthority = new SimpleGrantedAuthority("COORDINADOR");

        return List.of(userAuthority, investigadorAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

}
