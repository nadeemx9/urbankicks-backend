package com.urbankicks.models;

import com.urbankicks.entities.UserRegister;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticatedUser implements UserDetails {

    private Integer userId;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String address;
    private String mobile;
    private String role;
    private Integer stateId;
    private String stateName;
    private Integer districtId;
    private String districtName;
    private String lastLoggedIn;

    public AuthenticatedUser(UserRegister user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.address = user.getAddress();
        this.mobile = user.getMobile();
        this.role = user.getRole().name();
        this.stateId = user.getState().getStateId();
        this.stateName = user.getState().getStateName();
        this.districtId = user.getDistrict().getDistrictId();
        this.districtName = user.getDistrict().getDistrictName();
        this.lastLoggedIn = user.getLastLoggedIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(role)
                .map(r -> (GrantedAuthority) () -> "ROLE_" + r) // Creating a new GrantedAuthority
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }
}
