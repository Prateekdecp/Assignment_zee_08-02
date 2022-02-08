package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.Food;
import com.learning.dto.TYPE;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.service.FoodService;

@RestController
@RequestMapping("/food")
public class FoodController {

	@Autowired
	FoodService foodService;
	
	
//							ADDING FOOD TO THE DATABASE
	
	@PostMapping("")
	public ResponseEntity<?> addFood(@Valid @RequestBody Food food) throws AlreadyExistsException
	{
		Food food1=foodService.addFood(food);
		return ResponseEntity.status(201).body(food1);
	}
	
//                           GETTING FOOD BY ID FROM THE DATABASE	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id")Integer id) throws IdNotFoundException
	{
		Food food=foodService.getFoodById(id);
		return ResponseEntity.status(200).body(food);
	}
	
//                          UPDATING THE RECORD IN THE DATABASE
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateFoodById(@PathVariable("id")Integer id,Food food) throws IdNotFoundException
	{
		Food food1=foodService.getFoodById(id);
		return ResponseEntity.status(200).body(food1);
	}
	
//                           GETTING ALL THE FOOD AVAILABLE	
	
	@GetMapping("/")
	public ResponseEntity<?> getAllFood()
	{
		Optional<List<Food>> optional=foodService.getAllFood();
		if (optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("string", "no records found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional);
	}
	
//                         DELETING FOOD FROM THE DATABASE
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?>deleteFoodById(@PathVariable("id")Integer id) throws IdNotFoundException
	{
		String result=foodService.deleteFoodById(id);
		Map<String, String> map = new HashMap<>();
		if (result.equals("successfully deleted food"))
			map.put("string", "record deleted successfully");
		return ResponseEntity.status(200).body(map);
	}
	
//                           GETTING FOOD TO TYPE
	
	@GetMapping("/{type}")
	public ResponseEntity<?> getFoodByType(@PathVariable("type") TYPE type)
	{
		Optional<List<Food>> optional=foodService.getFoodByType(type);
		if (optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("string", "no records found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional);
	}
 }
