package com.shope.common.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	
//	@NonNull
//	@ColumnDefault("0")
	private boolean enabled;
	
	@Column(name = "first_name", length = 45, nullable = false)
	private String firstname;
	
	@Column(name = "last_name", length = 45, nullable = false)
	private String lastname;
	
	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(length = 64)
	private String photos;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles",joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles = new HashSet<>();
	
	public User() {	
	}

	public User(String email, String firstname, String lastname, String password) {
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}
	
	 @Transient
	   public String getPhotosImagePath() {
	      if(id==null || photos ==null) return "/images/default-user.png";
	      return "/user-photos/" + this.id +"/"+this.photos;
	   }

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", enabled=" + enabled + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", password=" + password + ", photos=" + photos + ", roles=" + roles + "]";
	}
	
	public void addRole(Role... roles) {
//		for(Role role : roles) {
//			this.roles.add(role);
//		}
		Arrays.stream(roles).forEach(role -> this.roles.add(role));	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
