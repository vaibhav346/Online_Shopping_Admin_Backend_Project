package com.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Admin_Login")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	Long aid;
	@Column(unique = true, name = "Username")
	String adminusername;
	@Column(name = "Password")
	String adminpassword;
	String imgurl;
	@Column(unique = true, name = "Email-id")
	String email;

//    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
//    @JsonManagedReference(value="products")
//    List<Product> plist;

	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	List<Product> plist;

	public Admin() {
		super();
	}

	public Admin(Long aid, String adminusername, String adminpassword, String imgurl, String email,
			List<Product> plist) {
		super();
		this.aid = aid;
		this.adminusername = adminusername;
		this.adminpassword = adminpassword;
		this.imgurl = imgurl;
		this.email = email;
		this.plist = plist;
	}

	public Long getAid() {
		return aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	public String getAdminusername() {
		return adminusername;
	}

	public void setAdminusername(String adminusername) {
		this.adminusername = adminusername;
	}

	public String getAdminpassword() {
		return adminpassword;
	}

	public void setAdminpassword(String adminpassword) {
		this.adminpassword = adminpassword;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Product> getPlist() {
		return plist;
	}

	public void setPlist(List<Product> plist) {
		this.plist = plist;
	}

}
