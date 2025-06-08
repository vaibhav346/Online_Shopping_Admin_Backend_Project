package com.Controller;

import com.Entity.Admin;
import com.Entity.Product;
import com.Service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	AdminService aser;

	// 1.save admin
	@PostMapping("/save")
	public String save(@RequestBody Admin admin) {
		return aser.save(admin);
	}

	// 2.get all data admin
	@GetMapping("/findall")
	public List<Admin> findall() {
		return aser.findalldata();
	}

	// 3.login admin
	@PostMapping("/login")
	public Admin login(@RequestBody Admin admin) {
		return aser.loginAdmin(admin.getAdminusername(), admin.getAdminpassword());
	}

	// 4.delete admin
	@DeleteMapping("/deletebyid/{pid}")
	public String deletebyid(@PathVariable Long pid) {
		return aser.deleteById(pid);
	}

	// 5.findbyid admin
	@GetMapping("/findbyid/{aid}")
	public Admin findbyid(@PathVariable long aid) {
		return aser.findbyid(aid);
	}

	// 6. update byid admin and update byid product and new product add
	@PutMapping("/updatebyid/{aid}")
	public String updatebyid(@PathVariable long aid, @RequestBody Admin newdata) {
		return aser.updatebyid(aid, newdata);
	}

	// product methods

	// 1.findall products
	@GetMapping("/productsget")
	public List<Product> getall() {
		return aser.getall();
	}

	// 2. findbyid product
	@GetMapping("/getbyidproduct/{pid}")
	public Product findbyproduct(@PathVariable long pid) {
		return aser.findbyidproduct(pid);
	}

	// 3. deletebyid product
	@DeleteMapping("/deletebyidproduct/{pid}")
	public String deltebyidproduct(@PathVariable long pid) {
		return aser.deletebyidproduct(pid);
	}

	// 4.updatebyid product
	@PutMapping("updatebyidproduct/{pid}")
	public String updatebyidproduct(@PathVariable long pid,@RequestBody Product newdata) {
		return aser.updatebyidproduct(pid, newdata);
	}

}
