package com.Service;

import com.Entity.Admin;
import com.Entity.Product;
//import com.Entity.Product;
import com.Repository.AdminRepository;
import com.Repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	@Autowired
	AdminRepository arepo;

	@Autowired
	ProductRepository prepo;

	// 1.add admin and student but admin id is go to student table
	public String save(Admin a) {
		for (Product p : a.getPlist()) {
			p.setAdmin(a);
		}
		arepo.save(a);
		return "Record added in Admin and Product table";
	}

	// 2. findall admin data
	public List<Admin> findalldata() {
		return arepo.findAll();
	}

	// 3.login admin password and username
	public Admin loginAdmin(String username, String password) {
		return arepo.findByAdminusernameAndAdminpassword(username, password)
				.orElseThrow(() -> new RuntimeException("Invalid Credentials"));
	}

	// 4.findbyid admin
	public Admin findbyid(long aid) {
		return arepo.findById(aid).orElse(null);
	}

	// 5.deletebyid admin
	public String deleteById(Long pid) {
		prepo.deleteById(pid);
		return "Product deleted successfully";
	}

	// 6.updatebyid and new record added product
	public String updatebyid(long aid, Admin newdata) {
		Admin existingeadmin = arepo.findById(aid).orElse(null);
		if (existingeadmin == null) {
			return "Employee is not found";
		}
		if (newdata.getAdminusername() == null && newdata.getAdminpassword() == null && newdata.getEmail() == null
				&& newdata.getImgurl() == null && newdata.getPlist() == null) {
			return "No New Data provided for updataion";
		}

		if (newdata.getAdminusername() != null) {
			existingeadmin.setAdminusername(newdata.getAdminusername());
		}
		if (newdata.getAdminpassword() != null) {
			existingeadmin.setAdminpassword(newdata.getAdminpassword());
		}

		if (newdata.getEmail() != null) {
			existingeadmin.setEmail(newdata.getEmail());
		}

		if (newdata.getImgurl() != null) {
			existingeadmin.setImgurl(newdata.getImgurl());
		}

		List<Product> product = newdata.getPlist();
		for (Product p : product) {
			if (p.getPid() != null && p.getPid() != 0) // old record of product for updation.
			{

				Product existingproduct = prepo.findById(p.getPid()).orElse(null);
				if (p.getName() != null) {
					existingproduct.setName(p.getName());
				}
				if (p.getRating() != 0) {
					existingproduct.setRating(p.getRating());
				}
				if (p.getPrice() != 0) {
					existingproduct.setPrice(p.getPrice());
				}

				if (p.getOff() != null) {
					existingproduct.setOff(p.getOff());
				}
				if (p.getDelivery() != null) {
					existingproduct.setDelivery(p.getDelivery());
				}

				if (p.getBank() != null) {
					existingproduct.setBank(p.getBank());
				}
				if (p.getImg() != null) {
					existingproduct.setImg(p.getImg());
				}
			} else {
				// if pid is not given then it is new student record
				p.setAdmin(existingeadmin);
				existingeadmin.getPlist().add(p);
			}
		}
		arepo.save(existingeadmin);

		return "Admin updated sucessfully";
	}

	// 7.updatebyid and new record added product
	public String adminupdatebyid(long aid, Admin newdata) {
		Admin existingeadmin = arepo.findById(aid).orElse(null);
		if (existingeadmin == null) {
			return "Employee is not found";
		}
		if (newdata.getAdminusername() == null && newdata.getAdminpassword() == null && newdata.getEmail() == null
				&& newdata.getImgurl() == null) {
			return "No New Data provided for updataion";
		}

		if (newdata.getAdminusername() != null) {
			existingeadmin.setAdminusername(newdata.getAdminusername());
		}
		if (newdata.getAdminpassword() != null) {
			existingeadmin.setAdminpassword(newdata.getAdminpassword());
		}

		if (newdata.getEmail() != null) {
			existingeadmin.setEmail(newdata.getEmail());
		}

		if (newdata.getImgurl() != null) {
			existingeadmin.setImgurl(newdata.getImgurl());
		}
		arepo.save(existingeadmin);
		return "Admin updated sucessfully";

	}

	// product methods

	// 1. findall products
	public List<Product> getall() {
		return prepo.findAll();
	}

	// 2.findbyid product
	public Product findbyidproduct(long pid) {
		return prepo.findById(pid).orElse(null);
	}

	// 3. deletebyid

	public String deletebyidproduct(long pid) {
		prepo.deleteById(pid);
		return "product deleted sucessfully";
	}

	// 4.update byid
	public String updatebyidproduct(long pid, Product newdata) {
		Product existingproduct = prepo.findById(pid).orElse(null);
		if (existingproduct == null) {
			return "Product is not found";

		}

		if (newdata.getName() == null && newdata.getRating() == 0 && newdata.getPrice() == 0 && newdata.getOff() == null
				&& newdata.getDelivery() == null && newdata.getBank() == null && newdata.getImg() == null) {
			return "No New data provided for updataion";
		}
		if (newdata.getName() != null) {
			existingproduct.setName(newdata.getName());
		}

		if (newdata.getRating() != 0) {
			existingproduct.setRating(newdata.getRating());
		}

		if (newdata.getPrice() != 0) {
			existingproduct.setPrice(newdata.getPrice());
		}

		if (newdata.getOff() != null) {
			existingproduct.setOff(newdata.getOff());
		}

		if (newdata.getDelivery() != null) {
			existingproduct.setDelivery(newdata.getDelivery());
		}

		if (newdata.getBank() != null) {
			existingproduct.setBank(newdata.getBank());
		}

		if (newdata.getImg() != null) {
			existingproduct.setImg(newdata.getImg());
		}
		prepo.save(existingproduct);
		return "Record updated sucessfully";

	}

}
