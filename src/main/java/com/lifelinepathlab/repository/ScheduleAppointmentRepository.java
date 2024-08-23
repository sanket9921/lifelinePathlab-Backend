package com.lifelinepathlab.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lifelinepathlab.model.Doctor;
import com.lifelinepathlab.model.ScheduleAppointment;

@Repository
public interface ScheduleAppointmentRepository extends JpaRepository<ScheduleAppointment, Integer> {

	List<ScheduleAppointment> findByDoctor(Doctor doctor);
	
	List<ScheduleAppointment> findByStatus(String status);
	
	@Query("SELECT a FROM ScheduleAppointment a WHERE a.scheduledTime BETWEEN :startDate AND :endDate")
	List<ScheduleAppointment> findByScheduledTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
