package com.lifelinepathlab.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "report_details")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    
    @ManyToOne
    private ScheduleAppointment appointment;
    
    private String reportFileName;
    private String comment;
    private Date uploadDate;

    public Report() {
    }

    public Report(User user, Doctor doctor,ScheduleAppointment appointment, String reportFileName, String comment, Date uploadDate) {
        this.user = user;
        this.doctor = doctor;
        this.appointment = appointment;
        this.reportFileName = reportFileName;
        this.comment = comment;
        this.uploadDate = uploadDate;
    }

    public Report(User user,  String reportFileName, String comment, Date uploadDate) {
        this.user = user;
        this.reportFileName = reportFileName;
        this.comment = comment;
        this.uploadDate = uploadDate;
    }
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

	public ScheduleAppointment getAppointment() {
		return appointment;
	}

	public void setAppointment(ScheduleAppointment appointment) {
		this.appointment = appointment;
	}
    
    
}