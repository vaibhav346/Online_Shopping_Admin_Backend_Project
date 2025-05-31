package com.Service;

import com.Entity.Admin;
import com.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

	@Autowired
	AdminRepository arepo;

	private static final String UPLOAD_DIR = "admin-images";

	public String registerAdminWithImage(String adminusername, String adminpassword, String email, MultipartFile image)
			throws Exception {
		// Generate unique filename
		String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

		// Ensure directory exists
		Path uploadPath = Paths.get(UPLOAD_DIR);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		// Save the image to disk
		Path imagePath = uploadPath.resolve(imageName);
		Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

		// Create image URL (accessible path, like "/admin-images//filename.jpg")
		String imageUrl = "/admin-images/" + imageName;

		// Save student data
		Admin adm = new Admin();
		adm.setAdminusername(adminusername);
		adm.setAdminpassword(adminpassword);
		adm.setEmail(email);
		adm.setImgurl(imageUrl);
		arepo.save(adm);

		return imageUrl;
	}

	public String save(Admin admin) {
		arepo.save(admin);
		return "Admin added sucessfully";
	}

	public List<Admin> findalldata() {
		return arepo.findAll();
	}

	public Admin loginAdmin(String username, String password) {
		return arepo.findByAdminusernameAndAdminpassword(username, password)
				.orElseThrow(() -> new RuntimeException("Invalid Credentials"));
	}
}
