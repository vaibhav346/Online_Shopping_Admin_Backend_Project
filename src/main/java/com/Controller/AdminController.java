package com.Controller;

import com.Entity.Admin;
import com.Service.AdminService;
//import com.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	AdminService aser;

	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> registerAdmin(@RequestParam("adminusername") String username,
			@RequestParam("adminpassword") String password, @RequestParam("email") String email,
			@RequestParam("image") MultipartFile image) {

		try {
			String message = aser.registerAdminWithImage(username, password, email, image);
			return ResponseEntity.ok("Admin Registered sucessfully. Image: " + message);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	@PostMapping("/save")
	public String save(@RequestBody Admin admin) {
		return aser.save(admin);
	}

	@GetMapping("/findall")
	public List<Admin> findall() {
		return aser.findalldata();
	}

	@PostMapping("/login")
	public Admin login(@RequestBody Admin admin) {
		return aser.loginAdmin(admin.getAdminusername(), admin.getAdminpassword());
	}

	@DeleteMapping("/{pid}")
	public String deletebyid(@PathVariable Long pid) {
		return aser.deleteById(pid);
	}

	@PutMapping("/updatebyid")
	public String updatebyid(@RequestParam Long aid, @RequestParam Admin newdata) {
		return aser.updatebyadminid(aid, newdata);
	}

	@PostMapping("/add/{adminId}")
	public ResponseEntity<?> addProductWithImage(@PathVariable Long adminId, @RequestParam String name,
			@RequestParam float rating, @RequestParam float price, @RequestParam String off,
			@RequestParam String delivery, @RequestParam String bank,
			@RequestParam(value = "img", required = false) MultipartFile image) {

		try {
			aser.addProductWithImage(adminId, name, rating, price, off, delivery, bank, image);
			return ResponseEntity.ok("Product added successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product");
		}
	}

	// Update product info (without image upload)
	@PutMapping("/updatebyid/{adminId}")
	public ResponseEntity<String> updateProductByAdminId(@PathVariable Long adminId, @RequestBody Admin adminData) {
		try {
			String message = aser.updateByAdminId(adminId, adminData);
			return ResponseEntity.ok(message);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed");
		}
	}

	// Image upload endpoint (optional use during update)
	@PostMapping("/upload-product-image")
	public ResponseEntity<String> uploadProductImage(@RequestParam("img") MultipartFile file) {
		try {
			String imageUrl = aser.uploadProductImage(file);
			return ResponseEntity.ok(imageUrl);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
		}
	}
}
