package com.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	Long pid;
	String name;
	float rating;
	float price;
	String off;
	String delivery;
	String bank;
	String img;

	@ManyToOne
	@JoinColumn(name = "admin_id")
	@JsonBackReference
	Admin admin;

	public Product() {
		super();
	}

	public Product(Long pid, String name, float rating, float price, String off, String delivery, String bank,
			String img, Admin admin) {
		super();
		this.pid = pid;
		this.name = name;
		this.rating = rating;
		this.price = price;
		this.off = off;
		this.delivery = delivery;
		this.bank = bank;
		this.img = img;
		this.admin = admin;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getOff() {
		return off;
	}

	public void setOff(String off) {
		this.off = off;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
