package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.learning.payload.response.MessageResponse;
import com.learning.service.FoodService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	@Autowired
	FoodService foodService;

//							ADDING FOOD TO THE DATABASE

	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addFood(@Valid @RequestBody Food food) throws AlreadyExistsException {
		Food food1 = foodService.addFood(food);
		return ResponseEntity.status(201).body(food1);
	}

//                           GETTING FOOD BY ID FROM THE DATABASE	

	@GetMapping("/admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getFoodById(@PathVariable("id") Integer id) throws IdNotFoundException {
		Food food = foodService.getFoodById(id);
		return ResponseEntity.status(200).body(food);
	}

//                          UPDATING THE RECORD IN THE DATABASE

	@PutMapping("/admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateFoodById(@PathVariable("id") Integer id, Food food) throws IdNotFoundException {
		Food food1 = foodService.getFoodById(id);
		return ResponseEntity.status(200).body(food1);
	}

//                           GETTING ALL THE FOOD AVAILABLE	

	@GetMapping("/admin/")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllFood() {
		Optional<List<Food>> optional = foodService.getAllFood();
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("no food record found"));
		}
		return ResponseEntity.ok(optional);
	}

//                         DELETING FOOD FROM THE DATABASE

	@DeleteMapping("/admin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteFoodById(@PathVariable("id") Integer id) throws IdNotFoundException {
		String result = foodService.deleteFoodById(id);		
		if (result.equals("successfully deleted food")) {
			return ResponseEntity.status(200).body(new MessageResponse("record deleted successfully"));
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Could not find food"));
	}

	// GET FOOD BY TYPE
	@GetMapping("/admin/type/{type}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getFoodByType(@PathVariable("type") TYPE type) {
		Optional<List<Food>> optional = foodService.getFoodByType(type);
		if (optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("string", "no records found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional);
	}
}
