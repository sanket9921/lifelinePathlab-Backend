package com.lifelinepathlab.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifelinepathlab.model.Doctor;
import com.lifelinepathlab.model.Orders;
import com.lifelinepathlab.model.Test;
import com.lifelinepathlab.model.User;
import com.lifelinepathlab.service.OrderService;
import com.razorpay.*;




@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	 
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/all/")
	public ResponseEntity<List<Orders>> getAllorders(){
		List<Orders> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/all/{status}")
	public ResponseEntity<List<Orders>> getOrdersByStatus(@PathVariable String status){
		List<Orders> orders = orderService.getOrdersByStatus(status);
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Orders> getOrders(@PathVariable int id) {
		Orders orders = orderService.getOrdersByid(id);
		return ResponseEntity.ok(orders);
	}
	
	
	@GetMapping("/cartOrders/{userid}")
	public ResponseEntity<Orders> getAllTestsByUserId(@PathVariable int userid) {
		Orders orders = orderService.getOrderByUserId(userid);
		return ResponseEntity.ok(orders);
	}
	
	
	
	@PostMapping("/addOrder")
	public ResponseEntity<String> addOrder(@RequestBody Orders orders) {
		orderService.addOrder(orders);
		return ResponseEntity.ok("Orders added successfully...!!!");
	}
	
//	@PutMapping("/{id}")
//	public ResponseEntity<String> UpdateOrder(@RequestBody Orders orders, @PathVariable int id){
//		orderService.updateOrder(orders, id);
//		return ResponseEntity.ok("Order update successfully");
//	}
	
	@PutMapping("/{id}/{testid}")
	public ResponseEntity<String> deleteOrder(@PathVariable int id, @PathVariable("testid") int testid){
		orderService.deleteTestFromOrder(id, testid);
		return ResponseEntity.ok("Order Deleted Successfully");
	}
	
	@PostMapping("/create-order")
	public String create_order(@RequestBody Map<String, Object> data)  {
		int orderid = (int) data.get("order");
		return orderService.create_order(orderid);	
	}
	
	@PostMapping("/create_order_by_test")
	public List<Object> createOrderByTest(@RequestBody Map<String, Object> data) {
		
//		System.out.println(data.get("testId") +" "+ data.get("userId"));
		String testid = data.get("testId").toString();
			
		String userid = data.get("userId").toString();
		
		System.out.println(testid+ " "+ userid);
		
		return orderService.create_order_by_Test(Integer.parseInt(testid), Integer.parseInt( userid));
	}
	
	@GetMapping("/update-satus/{id}")
	public void update_status(@PathVariable int id){
		System.out.println(id);
		 orderService.UpdateStatus(id);
	}
}
	
	

