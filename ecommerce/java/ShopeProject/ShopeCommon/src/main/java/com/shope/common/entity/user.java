package com.shope.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.lang.NonNull;

@Entity
@Table(name="users")
public class user {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	
	@NonNull
	@ColumnDefault("0")
	private boolean enabled;
	
	@Column(length = 45, nullable = false)
	private String first_name;
	
	@Column(length = 45, nullable = false)
	private String last_name;
	
	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(length = 64)
	private String photos;
	
	public user() {
		
	}
	
	
	public user(String email, String first_name, String last_name, String password) {
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
	}
	
	
	public user(String email, boolean enabled, String first_name, String last_name, String password, String photos) {
		super();
		this.email = email;
		this.enabled = enabled;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
		this.photos = photos;
	}


	public user(String email, boolean enabled, String first_name, String last_name, String password) {
		this.email = email;
		this.enabled = enabled;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
	}
	


	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", enabled=" + enabled + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", password=" + password + ", photos=" + photos + "]";
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
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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

}
