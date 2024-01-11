package net.javaguides.springboot.web.dto;

public class UserRegistrationDto {
	private String fullName;
	private String role;
	private String email;
	private String password;
	private String subject;
	private String usertype;
	
	public UserRegistrationDto(){
		
	}
	
	public UserRegistrationDto(String fullName, String role, String email, String password, String subject, String usertype) {
		super();
		this.fullName = fullName;
		this.role = role;
		this.email = email;
		this.password = password;
		this.subject = subject;
		this.usertype=usertype;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
}
