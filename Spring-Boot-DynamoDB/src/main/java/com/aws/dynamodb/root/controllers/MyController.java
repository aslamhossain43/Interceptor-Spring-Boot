package com.aws.dynamodb.root.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.aws.dynamodb.root.entities.ProductInfo;
import com.aws.dynamodb.root.repositories.ProductInfoRepository;

@RestController
public class MyController {

	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Autowired
	ProductInfoRepository repository;

	private static final String EXPECTED_COST = "20";
	private static final String EXPECTED_PRICE = "50";

	@RequestMapping(value = "/get")
	public ResponseEntity<List<ProductInfo>> getProductInfo() {
		System.out.println("=====================Enter getProductInfo()====================");
		List<ProductInfo> result = (List<ProductInfo>) repository.findAll();
		return ResponseEntity.ok(result);
	}
	@RequestMapping(value = "/get/{id}")
	public ResponseEntity<ProductInfo> getProductInfoById(@PathVariable String id) {
		System.out.println("=====================Enter getProductInfoById()====================");
		return ResponseEntity.ok(repository.findById(id).get());
	}
	@RequestMapping(value = "/delete/{id}")
	public ResponseEntity<String> deleteProductInfoById(@PathVariable String id) {
		System.out.println("=====================Enter deleteProductInfoById()====================");
		repository.deleteById(id);
		return ResponseEntity.ok("Deleted id: "+id);
	}

	@RequestMapping(value = "/add")
	public ResponseEntity<ProductInfo> addProductInfo() {
		System.out.println("=====================Enter addProductInfo()====================");
		/*
		 * dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB); CreateTableRequest
		 * tableRequest = dynamoDBMapper.generateCreateTableRequest(ProductInfo.class);
		 * tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		 * amazonDynamoDB.createTable(tableRequest);
		 * dynamoDBMapper.batchDelete((List<ProductInfo>) repository.findAll());
		 */
		ProductInfo productInfo = new ProductInfo();
		productInfo.setMsrp(EXPECTED_COST);
		productInfo.setCost(EXPECTED_PRICE);
		repository.save(productInfo);
		System.out.println("=====================Save complete in  addProductInfo()====================");
		return ResponseEntity.ok(productInfo);
	}

}
