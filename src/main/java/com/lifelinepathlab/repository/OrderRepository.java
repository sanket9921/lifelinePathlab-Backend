package com.lifelinepathlab.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.lifelinepathlab.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer>{
	
	@Query("SELECT o FROM Orders o WHERE o.user.userId = :userId AND o.status=:status")
	Orders findByUserId(@Param("userId") int userId, String status);
	
	List<Orders> findByStatus(String status);
	
}
