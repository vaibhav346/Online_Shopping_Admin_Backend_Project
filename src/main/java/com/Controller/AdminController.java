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
}
