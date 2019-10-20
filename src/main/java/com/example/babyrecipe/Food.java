package com.example.babyrecipe;

import java.math.BigInteger;
import java.util.*;

public class Food implements Comparable<Food> {
	String _name;
//	List<Integer> _month;
	BigInteger _month;
	float _price;
	public Map<String, Integer> _with = new TreeMap<String, Integer>();

	public Food(String name, float price, BigInteger month) {
		_name = name;
		_price = price;
		_month = month;
	}

	public String toString() {
		String result = null;
//		result = _name;		
		result = "食材的名称 ：" + _name +" "+ "食材的时令" + _month.toString(2);
		return result;
	}

	@Override
	public int compareTo(Food o) {
		// TODO Auto-generated method stub
		if(!o._name.equals(this._name)) {
			return -1;
		}else if(o._name.equals(this._name)) {
			return 0;
		}else {
			return 1;
		}
		
	}
}
