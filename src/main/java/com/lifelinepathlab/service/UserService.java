package com.lifelinepathlab.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lifelinepathlab.exception.ResourceNotFoundException;
import com.lifelinepathlab.model.ClientFeedback;
import com.lifelinepathlab.model.User;
import com.lifelinepathlab.repository.UserRepository;
import com.lifelinepathlab.util.EmailUtil;
import com.lifelinepathlab.util.OtpUtil;
import java.time.Duration;

import jakarta.mail.MessagingException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepositoryRef;
	
	@Autowired
	private OtpUtil otpUtil;
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void addUser(User user) {
		String otp = otpUtil.generateOtp();
	    try {
	      emailUtil.sendOtpEmail(user.getEmailId(), otp);
	    } catch (MessagingException e) {
	      throw new RuntimeException("Unable to send otp please try again");
	    }
		user.setUuid(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setOtp(otp);
	    user.setOtpGeneratedTime(LocalDateTime.now());
		userRepositoryRef.save(user);
	} 
	public String verifyAccount(String email, String otp) {
	    User user = userRepositoryRef.findByEmailId(email);
	    if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
	        LocalDateTime.now()).getSeconds() < (5 * 60)) {
	      user.setActive(true);
	      userRepositoryRef.save(user);
	      return "OTPVERIFIED";
	    }
	    return "Please regenerate otp and try again";
	  }
	
	
	public String regenerateOtp(String email) {
	    User user = userRepositoryRef.findByEmailId(email);
	    String otp = otpUtil.generateOtp();
	    try {
	      emailUtil.sendOtpEmail(email, otp);
	    } catch (MessagingException e) {
	      return "Unable to send otp please try again";
	    }
	    user.setOtp(otp);
	    user.setOtpGeneratedTime(LocalDateTime.now());
	    userRepositoryRef.save(user);
	    return "Email sent... please verify account within 5 minute";
	  }
	
	public List<User> getUsers() {
		List<User> users = userRepositoryRef.findAll();
		return users;
	}
	
	public User getUser(int userId) {
		User user = userRepositoryRef.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User does not exits with User Id: ", userId));
		return user;	
	}
		
	public User updateUser(User newUser, int userId) {
		User oldUser = userRepositoryRef.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User does not exits with User Id: ", userId));
			oldUser.setFirstName(newUser.getFirstName());
			oldUser.setLastName(newUser.getLastName());
			oldUser.setEmailId(newUser.getEmailId());
			oldUser.setContactNo(newUser.getContactNo());
			oldUser.setDateOfBirth(newUser.getDateOfBirth());
			oldUser.setGender(newUser.getGender());
			oldUser.setBloodGroup(newUser.getBloodGroup());
			oldUser.setAddress(newUser.getAddress());
			oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));			
			userRepositoryRef.save(oldUser);
			
			return oldUser;
	}
	
    public List<User> getAdminByRole() {
    	List<User> admins = userRepositoryRef.findByRole("ADMIN");
        return admins;
    }
    
    public List<User> getUsersByRole() {
    	List<User> users = userRepositoryRef.findByRole("USER");
        return users;
    }
	
	public void updateUserRole(int userId) {
		User user=userRepositoryRef.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User does not exits with User Id: ", userId));;
		user.setRole("ADMIN");
		userRepositoryRef.save(user);
	}
	
	public void deleteUser(int userId) {
		User user = userRepositoryRef.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User does not exits with User Id: ", userId));
		userRepositoryRef.delete(user);
	}
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		// load user from 
				User user = userRepositoryRef.findByEmailId(username);
				return user;
			}
	
	public String resetPassword(String email, String otp, String password) {
		
		
		 User user = userRepositoryRef.findByEmailId(email);
		 
		 System.out.println(otp);
		    if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
		        LocalDateTime.now()).getSeconds() < (5 * 60)) {
				user.setPassword(passwordEncoder.encode(password));

		      userRepositoryRef.save(user);
		      return "PASSWORDRESETED";
		    }
		    return "Please regenerate otp and try again";
	}

	
}
