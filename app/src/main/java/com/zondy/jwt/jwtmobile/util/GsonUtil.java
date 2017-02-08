package com.zondy.jwt.jwtmobile.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * GsonUtil,用于将json转化为Entity 或者 List<Entity>
 */
public class GsonUtil {


	public static String bean2Json(Object obj) {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(obj);
	}

	public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonStr, objClass);
	}

	/**
	 * 此方法转化为list之后,返回值遍历时强转为T类型会报错,强制转化异常.
	 * @param jsonStr
	 * @param objClass
	 * @return
	 */
	public static <T> List<T> json2BeanList2(String jsonStr, Class<T> objClass){
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonStr, new TypeToken<List<T>>(){}.getType());

	}

	/**
	 * 此方法可以解决返回值遍历时强转为T类型时出现的强制转化异常.
	 * @param jsonStr
	 * @param objClass
	 * @return
	 */
	public static <T> ArrayList<T> json2BeanList(String jsonStr, Class<T> objClass) {
		Gson gson = new GsonBuilder().create();
		ArrayList<T> mList = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(jsonStr).getAsJsonArray();
		for(final JsonElement elem : array){
			mList.add(gson.fromJson(elem, objClass));
		}
		return mList;
	}

	public static <T> String beanList2Json(T obj){
		Gson gson = new GsonBuilder().create();
		return gson.toJson(obj);

	}

	/**
	 * 把混乱的json字符串整理成缩进的json字符串
	 *
	 * @param uglyJsonStr
	 * @return
	 */
	public static String jsonFormatter(String uglyJsonStr) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJsonStr);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}
}
