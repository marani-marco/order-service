package com.minicommerce.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minicommerce.orderservice.entity.Order;
import com.minicommerce.orderservice.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@PostMapping("/save")
	public ResponseEntity<Object> saveOrder(@RequestBody Order order) {
		try {
			return ResponseEntity.ok(orderService.saveOrder(order));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/get/{id}")
	public Order getOrderById(@PathVariable("id") String id) {
		return orderService.getOrderById(id);
	}
	
	@GetMapping("/get-by-account/{id}")
	public List<Order> getOrdersByAccountId(@PathVariable("id") String id) {
		return orderService.getOrdersByAccountId(id);
	}
	
}
