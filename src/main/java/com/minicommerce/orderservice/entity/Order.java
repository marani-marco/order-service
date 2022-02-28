package com.minicommerce.orderservice.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.minicommerce.accountservice.entity.Account;
import com.minicommerce.productservice.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	private String id;
	private Date dateOrder;
	private Integer quantity;
	private String status; // CARRELLO, ACQUISTATO, IN_PREPARAZIONE, SPEDITO, CONSEGNATO, RESTITUITO, ANNULLATO
	private String statusMessage;
	private String statusError;
	
	@DBRef(db = "account-service")
	private Account account;
	
	@DBRef(db = "product-service")
	private Product product;
	
}
