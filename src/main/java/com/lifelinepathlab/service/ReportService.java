package com.lifelinepathlab.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lifelinepathlab.exception.ResourceNotFoundException;
import com.lifelinepathlab.model.Doctor;
import com.lifelinepathlab.model.Orders;
import com.lifelinepathlab.model.Report;
import com.lifelinepathlab.model.ScheduleAppointment;
import com.lifelinepathlab.model.User;
import com.lifelinepathlab.repository.ReportRepository;
import com.razorpay.Order;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private OrderService orderService;

	private String storeFile(MultipartFile reportFile) throws IOException {
		// Generate a UUID for the file
		String fileId = UUID.randomUUID().toString();
		// Save the file to the file storage location with the UUID as the filename
		return fileStorageService.storeFile(reportFile, fileId, "Reports");
	}

	public void uploadReport(int id, int userId, String doctorId, MultipartFile reportFile, String comment)
			throws IOException {

		Report report;
		String fileName = storeFile(reportFile);
		User user = new User();
		user.setUserId(userId);
		ScheduleAppointment appointment = appointmentService.getAppointmentById(id);
		appointment.setStatus("COMPLETED");
		
		if (doctorId.equals("undefined")) {
			report = new Report(user, fileName, comment, new Date());

		} else {
			Doctor doctor = new Doctor();			
			doctor.setDoctorId(Integer.parseInt(doctorId));
			ScheduleAppointment appointment2 = new ScheduleAppointment();
			appointment2.setAppointmentId(id);
			
			report = new Report(user, doctor, appointment2, fileName, comment, new Date());

		}
		reportRepository.save(report);

	}

	public void uploadOrderReport(int id, int userId, MultipartFile reportFile, String comment) throws IOException {

		Report report;
		String fileName = storeFile(reportFile);
		User user = new User();
		user.setUserId(userId);
		Orders order = orderService.getOrdersByid(id);
		order.setStatus("COM");
		report = new Report(user, fileName, comment, new Date());
		reportRepository.save(report);

	}

	public List<Report> getAllReports() {
		return reportRepository.findAll();
	}

	public List<Report> getAllReportsByDoctorEmailId(String email) {
		return reportRepository.getByDoctorByEmailId(email);
	}

	public List<Report> getAllReportByUserid(int userid) {
		return reportRepository.getByUserid(userid);
	}

	public Report getReportById(int id) {
		return reportRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Report not found with id: ", id));
	}

	public void deleteReport(int id) {
		reportRepository.deleteById(id);
	}
}