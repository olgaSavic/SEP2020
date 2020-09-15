package com.ftn.nc.security.services;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.nc.model.Korisnik;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String email;

	@JsonIgnore
	private String lozinka;


	public UserDetailsImpl(Long id, String email, String lozinka) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;

	}

	public static UserDetailsImpl build(Korisnik user) {

		return new UserDetailsImpl(
				user.getId(), 
				user.getEmail(),
				user.getLozinka());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return lozinka;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}



	
}