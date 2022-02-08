package com.learning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Food;
import com.learning.dto.TYPE;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.FoodRepository;
import com.learning.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	FoodRepository foodRepository;

	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = AlreadyExistsException.class)
	public Food addFood(Food food) throws AlreadyExistsException {
		// TODO Auto-generated method stub
		if (foodRepository.existsByFoodName(food.getFoodName()))
			throw new AlreadyExistsException("food already exists");
		Food food1 = foodRepository.save(food);
		if (food1 != null)
			return food1;
		return null;
	}

	@Override
	public Food getFoodById(Integer id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<Food> optional = foodRepository.findById(id);
		if (optional.isEmpty())
			throw new IdNotFoundException("Sorry food with " + id + " not found");
		else
			return optional.get();
	}

	@Override
	public Optional<List<Food>> getAllFood() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(foodRepository.findAll());
	}

	@Override
	public String deleteFoodById(Integer id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		try {
			Food food=this.getFoodById(id);
			if(food==null)
				throw new IdNotFoundException("Sorry food with " + id + " not found");
			else
			{
				this.foodRepository.deleteById(id);
				return "successfully deleted food";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IdNotFoundException("Sorry food with " + id + " not found");

		}
		
	}

	@Override
	public Food updateFoodById(Integer id, Food food) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Food food1=getFoodById(id);
		if(food1==null)
			throw new IdNotFoundException("Sorry food with " + id + " not found");
		else
		{
			foodRepository.save(food);
			return food1;
		}
	}

	@Override
	public Optional<List<Food>> getFoodByType(TYPE type) {
		// TODO Auto-generated method stub
		List<Food> list=this.getAllFood().get();
		List<Food> result=new ArrayList<>();
		for (Food food : list) {
			if(food.getFoodType().equals(type))
				result.add(food);
		}
		return Optional.ofNullable(result);
	}

}
