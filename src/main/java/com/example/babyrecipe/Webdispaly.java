package com.example.babyrecipe;

import java.math.BigInteger;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Webdispaly {
	@Autowired
	BabyrecipeApplication _app;

	MyTool mytool = new MyTool();

	@RequestMapping(value = "/searchbymonth", method = RequestMethod.POST)
	public String searchbymonth(@RequestBody(required = false) String params) {
		String result = "查找到的食材：";
		String name = mytool.readFirstMsg(params); // 营养成分的名字
		String month = mytool.readSecondMsg(params);
		BigInteger test = mytool.dealMonth(month);
		for (Map.Entry<String, Food> entry : _app._foodlist.entrySet()) {
			if (entry.getValue()._with.containsKey(name)) {
				for (int i = 0; i < month.length(); i++) {
					if (test.testBit(i)) {
						if (!entry.getValue()._month.testBit(i)) {
							break;
						}
					}
					if (i == month.length() - 1) {
						result += entry.getKey();
					}
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/searchmaterial", method = RequestMethod.POST)
	public String searchmaterial(@RequestBody(required = false) String params) {
		String result = "";

		List<Food> needwithlist = new ArrayList<Food>();
		List<String> res = new ArrayList<String>();
		String name = mytool.readFirstMsg(params); // 营养成分的名字
		String quality = mytool.readSecondMsg(params);
		if (name.contains(" ")) {
			needwithlist.addAll(_app._foodlist.values());
			String[] fname = name.split(" ");
			String[] quality1 = quality.split(" ");
			List<String> searchmsg = new ArrayList<String>();
			for (int i = 0; i < fname.length; i++) {
				searchmsg.add(fname[i] + "&" + quality1[i]);
			}
			for (Map.Entry<String, Food> e : _app._foodlist.entrySet()) {
				if (searchmsg.stream().map(v -> mytool.getInFoodWith(v, e.getValue())).reduce(true, (a, b) -> a & b)) {
					res.add(e.getKey());
				}
			}
			// searchmsg.stream().map(value ->
			// _app._foodlist.containsKey(value)).reduce(false, (a, b) -> a & b);
			//
			// for (Food f : _app._foodlist.values()) {
			// (f._with.containsKey(key)&&f._with.get(key)>num)
			// }
		} else {
			int quality1 = Integer.parseInt(quality);
			for (Map.Entry<String, Food> entry : _app._foodlist.entrySet()) {
				if (entry.getValue()._with.keySet().contains(name)) {
					if (entry.getValue()._with.get(name) > quality1) {
						res.add(entry.getKey());
					}
				}
			}
		}
		// 查询的结果从List转换String
		for (String s : res) {
			result += s;
			result += "    ";
		}
		System.out.println(res);
		return result;
	}

	@RequestMapping(value = "/searchdishesshort", method = RequestMethod.POST)
	public String searchdishesshort(@RequestBody(required = false) String params) {
		String result="";
		
		return result;
	}
	
	@RequestMapping(value = "/searchfoodcombo", method = RequestMethod.POST)
	public String searchwithcombo(@RequestBody(required = false) String params) {
		String result="查找到的菜品：";
		String[] fname = mytool.readFirstMsg(params).split(" ");
		List<String> withnamelist = new ArrayList<String>();
		for (int i = 0; i < fname.length; i++) {
			withnamelist.add(fname[i] );
		}
		for(Map.Entry<String, Dishes> e : _app._disheslist.entrySet()) {
			if(withnamelist.stream().map(v->e.getValue()._foodname.contains(v)).reduce(true,(a,b)->a&b)){
				result+=e.getValue()._name;
				result+="   ,";
			}
		}
		return result;
	}
	@RequestMapping(value = "/searchdishesbymonthandwith", method = RequestMethod.POST)
	public String searchdishesbymonthandwith(@RequestBody(required = false) String params) {
		String result = "符合要求的菜品有：";
		String withname = mytool.readFirstMsg(params);
		BigInteger month = mytool.dealMonth(mytool.readSecondMsg(params));
		String[] fname = withname.split(" ");
		List<String> withnamelist = new ArrayList<String>();
		for (int i = 0; i < fname.length; i++) {
			withnamelist.add(fname[i]);
		}
		System.out.println(month.toString(2));
		for (Map.Entry<String, Dishes> e : _app._disheslist.entrySet()) {
			if (withnamelist.stream().map(v -> e.getValue()._with.containsKey(v)).reduce(true, (a, b) -> a & b)) {
				if (mytool.testMonth(month, e.getValue()._month)) {
					result += e.getKey();
					result+=" ,";
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/adddishes", method = RequestMethod.POST)
	public String adddishes(@RequestBody(required = false) String params) {
		String result = null;
		String dishesname = mytool.readFirstMsg(params);
		String foodlistname = mytool.readSecondMsg(params);
		String quality = mytool.readThirdMsg(params);
		Dishes ds = new Dishes(dishesname);
		if (foodlistname.contains(" ")) {
			String[] fname = foodlistname.split(" ");
			String[] quality1 = quality.split(" ");
			for (int i = 0; i < fname.length; i++) {
				ds._dishesfoodlist.put(_app._foodlist.get(fname[i]), Integer.parseInt(quality1[i]));
			}
		} else {
			ds._dishesfoodlist.put(_app._foodlist.get(foodlistname), Integer.parseInt(quality));
		}
		ds.cal();
		_app._disheslist.put(ds._name, ds);
		result = dishesname + "创建成功";
		return result;
	}

	@RequestMapping(value = "/addfoodwith", method = RequestMethod.POST)
	public String addfoodwith(@RequestBody(required = false) String params) {
		String result = "";
		String name = mytool.readFirstMsg(params);
		String withname = mytool.readSecondMsg(params);
		int num = Integer.parseInt(mytool.readThirdMsg(params));
		mytool.addWith(_app._foodlist.get(name), withname, num);
		result += name + "添加" + withname + "成功";
		return result;
	}

	@RequestMapping(value = "/addfood", method = RequestMethod.POST)
	public String addfood(@RequestBody(required = false) String params) {
		String result = "";
		String name = mytool.readFirstMsg(params);
		float price = Float.parseFloat(mytool.readSecondMsg(params));
		String month = mytool.readThirdMsg(params);
		Food f = new Food(name, price, mytool.dealMonth(month));
		_app._foodlist.put(name, f);
		result += name + "添加" + name + "成功";
		return result;
	}

	@RequestMapping(value = "/updatafoodwith", method = RequestMethod.POST)
	public String updatafoodwith(@RequestBody(required = false) String params) {
		Food beff = new Food("乌拉圭冷冻鲜牛肉", 33.1f, new BigInteger("4095"));
		Food carrot = new Food("山东泥胡萝卜", 1.78f, new BigInteger("63"));
		Food cabbage = new Food("卷心菜", 5.76f, new BigInteger("4032"));
		if (_app._foodlist.size() == 0) {
		
			mytool.addWith(beff, "蛋白质", 20000);
			mytool.addWith(beff, "碳水化合物", 1200);
			mytool.addWith(beff, "脂肪", 1000);
			mytool.addWith(beff, "钙", 9);
			mytool.addWith(beff, "铁", 3);
			_app._foodlist.put(beff._name, beff);
			
			mytool.addWith(carrot, "蛋白质", 600);
			mytool.addWith(carrot, "脂肪", 300);
			mytool.addWith(carrot, "碳水化合物", 180);
			mytool.addWith(carrot, "维生素A", 18);
			mytool.addWith(carrot, "维生素c", 23);
			_app._foodlist.put(carrot._name, carrot);
		
			mytool.addWith(cabbage, "蛋白质", 2200);
			mytool.addWith(cabbage, "脂肪", 150);
			mytool.addWith(cabbage, "维生素A", 12);
			mytool.addWith(cabbage, "膳食维生素", 100);
			mytool.addWith(cabbage, "碳水化合物", 200);
			_app._foodlist.put(cabbage._name, cabbage);
		}
		String result = "食材表：";
		for (Map.Entry<String, Food> entry : _app._foodlist.entrySet()) {
			result += "\r\n";
			result += entry.getValue() + "营养价值：";
			result += "\r\n";
			if (entry.getValue()._with.size() != 0) {
				for (Map.Entry<String, Integer> e : entry.getValue()._with.entrySet()) {
					result += e.getKey() + "  :  " + e.getValue();
					result += "   ,   ";
				}
			} else {
				result += "该食材的营养价值还未添加";
			}
		}
		result+="\r\n";
		result+="菜品表：";
		if(_app._disheslist.size()==0) {
			Dishes ds = new Dishes("萝卜炒牛肉");
			ds._dishesfoodlist.put(beff, 200);
			ds._dishesfoodlist.put(carrot, 100);
			ds.cal();
			_app._disheslist.put(ds._name, ds);
			Dishes ds1 = new Dishes("包菜炒牛肉");
			ds1._dishesfoodlist.put(beff, 200);
			ds1._dishesfoodlist.put(cabbage, 100);
			ds1.cal();
			_app._disheslist.put(ds1._name, ds1);
			Dishes ds2 = new Dishes("凉拌包菜");
			ds2._dishesfoodlist.put(cabbage, 300);
			ds2.cal();
			_app._disheslist.put(ds2._name, ds2);
			Dishes ds3 = new Dishes("水煮牛肉");
			ds3._dishesfoodlist.put(beff, 400);
			ds3.cal();
			_app._disheslist.put(ds3._name, ds3);
		}
			for(Map.Entry<String, Dishes> entry : _app._disheslist.entrySet()) {
				result+="\r\n";
				result+=entry.getValue().toString();
		}
		
//		List<Food> foodlist = new ArrayList<Food>();
//		foodlist.addAll(_app._foodlist.values());
//		System.out.println(mytool.foodCombo(foodlist));
		return result;
	}

}
