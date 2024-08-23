package com.lifelinepathlab.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lifelinepathlab.model.Report;
import com.lifelinepathlab.service.ReportService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadReport(
    											@RequestParam("file") MultipartFile reportFile,
    											@RequestParam("appoitmentid") int id,
    										   @RequestParam("userId") int userId,
                                               @RequestParam("doctorId") String doctorId,
                                               @RequestParam("comment") String comment) 
   
    {
        try {
            reportService.uploadReport(id,userId, doctorId, reportFile, comment);
            return ResponseEntity.ok("Report uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to upload report: " + e.getMessage());
        }
    }
    
    @PostMapping("/uploadOrderReport")
    public ResponseEntity<String> uploadOrderReport(
    											@RequestParam("file") MultipartFile reportFile,
    											@RequestParam("orderid") int id,
    										   @RequestParam("userId") int userId,
                                               @RequestParam("comment") String comment) 
   
    {
        try {
            reportService.uploadOrderReport(id,userId, reportFile, comment);
            return ResponseEntity.ok("Report uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to upload report: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/email/{emailId}")
    public ResponseEntity<List<Report>> getAllReportsByDoctorEmailId(@PathVariable("emailId") String mail) {
        List<Report> reports = reportService.getAllReportsByDoctorEmailId(mail);
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Report>> getAllReportByUserId(@PathVariable("id") int id){
    	List<Report> reports = reportService.getAllReportByUserid(id);
    	return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable int id) {
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable int id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok("Report deleted successfully!");
    }
}