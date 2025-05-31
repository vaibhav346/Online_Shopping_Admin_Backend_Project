package com.Service;

import com.Entity.Admin;
import com.Entity.Product;
import com.Repository.AdminRepository;
import com.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

	@Autowired
	ProductRepository prepo;

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

	public String deleteById(Long pid) {
		prepo.deleteById(pid);
		return "Product deleted successfully";
	}

	public String updatebyadminid(Long aid, Admin newdata) {
		Admin existingadmin = arepo.findById(aid).orElse(null);
		if (existingadmin == null) {
			return "Admin is not found";

		}
		List<Product> product = newdata.getPlist();
		for (Product p : product) {
			if (p.getPid() != 0) {
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
				} else {
					p.setProduct(existingadmin);
					existingadmin.getPlist().add(p);
				}

			}
		}
		arepo.save(existingadmin);
		return "Record updated sucessfully";
	}

	public void addProductWithImage(Long adminId, String name, float rating, float price, String off, String delivery,
			String bank, MultipartFile image) throws Exception {

// 1. Find Admin
		Admin admin = arepo.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));

// 2. Handle Image Upload
		String imageUrl = null;
		if (image != null && !image.isEmpty()) {
			String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

			Path uploadPath = Paths.get(UPLOAD_DIR);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path imagePath = uploadPath.resolve(imageName);
			Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
			imageUrl = "/product-images/" + imageName;
		}

// 3. Create and Save Product
		Product product = new Product();
		product.setName(name);
		product.setRating(rating);
		product.setPrice(price);
		product.setOff(off);
		product.setDelivery(delivery);
		product.setBank(bank);
		product.setImg(imageUrl);
		product.setProduct(admin); // admin reference

		prepo.save(product);
	}

	public String updateByAdminId(Long aid, Admin newdata) throws IOException {
		Admin existingAdmin = arepo.findById(aid).orElse(null);
		if (existingAdmin == null) {
			return "Admin not found";
		}

		List<Product> newProductList = newdata.getPlist();
		for (Product p : newProductList) {
			if (p.getPid() != 0) {
				// Existing product - update fields
				Product existingProduct = prepo.findById(p.getPid()).orElse(null);
				if (existingProduct != null) {
					if (p.getName() != null)
						existingProduct.setName(p.getName());
					if (p.getRating() != 0)
						existingProduct.setRating(p.getRating());
					if (p.getPrice() != 0)
						existingProduct.setPrice(p.getPrice());
					if (p.getOff() != null)
						existingProduct.setOff(p.getOff());
					if (p.getDelivery() != null)
						existingProduct.setDelivery(p.getDelivery());
					if (p.getBank() != null)
						existingProduct.setBank(p.getBank());
					if (p.getImg() != null)
						existingProduct.setImg(p.getImg());
					prepo.save(existingProduct);
				}
			} else {
				// New product - set admin and save
				p.setProduct(existingAdmin);
				prepo.save(p);
			}
		}

		return "Record updated successfully";
	}

	// Separate method to handle image update
	public String uploadProductImage(MultipartFile image) throws IOException {
		if (image == null || image.isEmpty()) {
			throw new IllegalArgumentException("Image file is required");
		}

		String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
		Path uploadPath = Paths.get(UPLOAD_DIR);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		Path imagePath = uploadPath.resolve(imageName);
		Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

		return "/product-images/" + imageName;
	}
}
