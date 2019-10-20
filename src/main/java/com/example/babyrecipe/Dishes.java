package com.example.babyrecipe;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Dishes {
	String _name;
	Map<Food, Integer> _dishesfoodlist = new TreeMap<Food, Integer>();
	Map<String, Integer> _with = new TreeMap<String, Integer>();
	BigInteger _month;
	float _price=0.0f;
    List<String>_foodname;
	public Dishes(String name) {
		_name = name;
	}

	public void cal() {
		_foodname=_dishesfoodlist.keySet().stream().map(v->v._name).collect(Collectors.toList());
		// 计算菜品的时令
		BigInteger re = new BigInteger("0");
		for (Food f : _dishesfoodlist.keySet()) {
			if (re.intValue() == 0) {
				re = f._month;
				continue;
			}
			re = re.and(f._month);
		}
		_month = re;
		// 计算菜品的营养成分和价格
		for (Entry<Food, Integer> entry : _dishesfoodlist.entrySet()) {
			float c = (float) entry.getValue() / 100;
			_price+=c*entry.getKey()._price;
			for (Map.Entry<String, Integer> e : entry.getKey()._with.entrySet()) {
				int val = (int) (e.getValue() * c);
				if (_with.containsKey(e.getKey())) {
					_with.put(e.getKey(), _with.get(e.getKey()) + val);
				} else {
					_with.put(e.getKey(), val);
				}
			}
		}
	}
	
	public String toString() {
		String result="";
		result+="菜品名称："+_name;
		result+="\r\n";
		result+="组成："+_dishesfoodlist;
		result+="\r\n";
		result+="营养成分："+_with;
		result+="\r\n";
		result+="时令："+_month.toString(2);
		result+="\r\n";
		return result;
	}
}
