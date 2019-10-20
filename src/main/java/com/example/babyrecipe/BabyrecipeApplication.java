package com.example.babyrecipe;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BabyrecipeApplication {
     public Map<String,Food> _foodlist=new TreeMap<String,Food>();
	public Map<String,Dishes> _disheslist=new TreeMap<String,Dishes>();
     public static void main(String[] args) {
		SpringApplication.run(BabyrecipeApplication.class, args);
	}
}
