package com.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.dto.Food;

public interface FoodRepository extends JpaRepository<Food, Integer> {
	boolean existsByFoodName(String foodName);
}
