package com.minicommerce.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.minicommerce.orderservice.entity.Order;
import com.minicommerce.orderservice.repository.OrderRepository;
import com.minicommerce.productservice.entity.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	RestTemplate restTemplate;
	
	public Order saveOrder(@RequestBody Order order) throws Exception {
		
		log.info("OrderService.saveOrder() - Chiamata a microservizio product-service per verificare disponibilità");
		
		Product product = restTemplate.getForObject("http://PRODUCT-SERVICE/products/get/" + order.getProduct().getId(), Product.class);
		
		if(order.getQuantity() > product.getQuantity()){
			log.error("OrderService.saveOrder() - Prodotto non disponibile");
			throw new Exception("Prodotto non disponibile");
		}
		
		Order orderSaved = orderRepository.save(order);
		
		log.info("OrderService.saveOrder() - ordine salvato");
		
		orderSaved.getProduct().setQuantity(orderSaved.getProduct().getQuantity() - orderSaved.getQuantity());
		
		restTemplate.postForObject("http://PRODUCT-SERVICE/products/save", orderSaved.getProduct(), Product.class);
		
		log.info("OrderService.saveOrder() - Chiamata a microservizio product-service per aggiornare disponibilità prodotto");
		
		return orderSaved;
	}
	
	public Order getOrderById(String id) {
		return mongoTemplate.findById(id, Order.class);
	}
	
	public List<Order> getOrdersByAccountId(String id){
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where("account.id").is("620fd73352464e128612d02a"));
		
		return mongoTemplate.find(query, Order.class);
		
	}
}
