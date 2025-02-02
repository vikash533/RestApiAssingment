package com.TestCases;

import static io.restassured.RestAssured.*;

import java.util.Set;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class ValidateResponseData {
	public static String actualVlaueofGBPDescription = "British Pound Sterling";
	public static String baseUrl="https://api.coindesk.com/";
	public static String currentPriceEndpoint ="/v1/bpi/currentprice.json";
	public static String contentType="application/json";

	@Test
	public static void validateResponse() {

		Response response = getRequest(currentPriceEndpoint);
		JSONObject jsonobject = conversionResponseToJsonObject(response);
		JSONObject bpi = jsonobject.getJSONObject("bpi");
		int sizeOfBPI = bpi.keySet().size();
		Set<String> bpiKeys = bpi.keySet();
		//validating Size Of BPIs
		Assert.assertEquals(3, sizeOfBPI);
		//validating response contains in BPIs
		Assert.assertTrue(bpiKeys.contains("USD"), "USD is missing in BPI!");
		Assert.assertTrue(bpiKeys.contains("GBP"), "GBP is missing in BPI!");
		Assert.assertTrue(bpiKeys.contains("EUR"), "EUR is missing in BPI!");
		//validating response should contains GBP currency in description.
		for (String keys : bpiKeys) {
			if (keys.equalsIgnoreCase("GBP")) {
				Assert.assertEquals(actualVlaueofGBPDescription, bpi.getJSONObject("GBP").getString("description"));
			}
		}

	}
	
	public static JSONObject conversionResponseToJsonObject(Response response) {
		return new JSONObject(response.asString());
	}
	
	public static Response getRequest(String endpoint) {
		 return given().baseUri(baseUrl).contentType(contentType).when()
				.get(endpoint);	
	}
}
