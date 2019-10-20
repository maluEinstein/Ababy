package com.example.babyrecipe;

import java.math.BigInteger;
import java.util.*;

public class MyTool {

	public String readFirstMsg(String params) {
		String msg[] = params.split("&");
		String FirstMsg = msg[0].substring(msg[0].indexOf('=') + 1);
		return FirstMsg;
	}

	public String readSecondMsg(String params) {
		String msg[] = params.split("&");
		String SecondMsg = msg[1].substring(msg[1].indexOf('=') + 1);
		return SecondMsg;
	}

	public String readThirdMsg(String params) {
		String msg[] = params.split("&");
		String ThirdMsg = msg[2].substring(msg[2].indexOf('=') + 1);
		return ThirdMsg;
	}

	public void addWith(Food f, String withname, int num) {
		f._with.put(withname, num);
	}

	public void addFood() {

	}

	public boolean getInFoodWith(String msg, Food f) {
		String name = msg.substring(0, msg.indexOf("&"));
		int num = Integer.parseInt(msg.substring(msg.indexOf("&") + 1));
		if (f._with.containsKey(name)) {
			if (f._with.get(name) > num) {
				return true;
			}
		}
		return false;
	}

	public BigInteger dealMonth(String month) {
		BigInteger bi = new BigInteger("0");
		for (int i = 0; i<month.length(); i++) {
			if (month.charAt(month.length()-1-i) == '1') {
				bi = bi.setBit(i);
			}
		}
		return bi;
	}

	public List<List<Food>> foodCombo(List<Food> foodlist) {
		List<List<Food>> combolist = new ArrayList<List<Food>>();
		List<Food> combo = new ArrayList<Food>();
		for (int i = 0; i < foodlist.size(); i++) {
			for (int j = 1; j < foodlist.size() - i; j++) {
				combo.clear();
				combo.add(foodlist.get(i));
				combo.add(foodlist.get(i + j));
				List<Food> tem = new ArrayList<Food>();
				tem.addAll(combo);
				combolist.add(tem);
			}
		}
		return combolist;
	}

	public void dealcomno(String msg, List<List<Food>> combolist) {
		// combolist.stream().map(v->v.stream().map(mapper))
	}

	public boolean testMonth(BigInteger test, BigInteger totest) {
		// test一般表示用户输入需要比较的条件，totest一般为存储的需要被比较的内容
		boolean result = false;
		int num = test.toString(2).length();
		for (int i = 0; i < num; i++) {
			if (test.testBit(i)) {
				if (!totest.testBit(i)) {
					break;
				}
			}
			if (i == num - 1) {
				result = true;
			}
		}
		return result;
	}

}
