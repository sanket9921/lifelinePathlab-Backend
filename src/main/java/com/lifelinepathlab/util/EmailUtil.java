package com.lifelinepathlab.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendOtpEmail(String email, String otp) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		mimeMessageHelper.setTo(email);
		mimeMessageHelper.setSubject("Verify Your Account with LifelinePathLab - One-Time Passcode (OTP)");
		mimeMessageHelper.setText(
//          <a href="http://localhost:8080/verify-account?email=%s&otp=%s" target="_blank">click link to verify</a>

				"""

					<!DOCTYPE html>
						<html lang="en">
						<head>
						<meta charset="UTF-8">
						<meta name="viewport" content="width=device-width, initial-scale=1.0">
						<title>Verify Your Account</title>
						<style>
						  body {
						    font-family: Arial, sans-serif;
						    line-height: 1.6;
						    background-color: #f4f4f4;
						    margin: 0;
						    padding: 0;
						  }
						  .container {
						    max-width: 600px;
						    margin: 20px auto;
						    padding: 20px;
						    background-color: #fff;
						    border-radius: 8px;
						    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
						  }
						  .header {
						    text-align: center;
						    margin-bottom: 20px;
						  }
						  .otp {
						    background-color: #007bff;
						    color: #fff;
						    padding: 10px 20px;
						    border-radius: 5px;
						    display: inline-block;
						  }
						</style>
						</head>
						<body>
						  <div class="container">
						    <div class="header">
						      <h2>Verify Your Account with LifelinePathlab - One-Time Passcode (OTP)</h2>
						    </div>
						    <p>Dear User,</p>
						    <p>Thank you for choosing our LifelinePathlab to manage your medical records and appointments. To ensure the security of your account, we require you to verify your email address.</p>
						    <p>Please use the following One-Time Passcode (OTP) to complete the verification process:</p>
						    <p class="otp">OTP: %s</p>
						    <p>Please enter this code within the LifelinePathlab interface to validate your email address. If you did not request this verification or have any concerns, please contact our support team immediately.</p>
						    <p>Thank you for your cooperation.</p>
						    <p>Best regards,<br>Lifelinepaht Team</p>
						  </div>
						</body>
						</html>


						        """
						.formatted(otp),
				true);

		javaMailSender.send(mimeMessage);
	}
}