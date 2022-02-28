package com.minicommerce.orderservice.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.minicommerce.orderservice.entity.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId>{

}
