package com.learning.dto;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="food")
public class Food {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int foodId;
	
	@NotBlank
	private String foodName;
	
	@NotNull
	private int foodCost;
	
	@NotBlank
	private String foodPic;
	
	private TYPE foodType;
	
}
