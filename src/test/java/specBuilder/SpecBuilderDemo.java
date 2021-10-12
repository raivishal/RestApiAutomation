package specBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

public class SpecBuilderDemo {
	/*
	 * Given - input Data of API
	 * When - Submit the API
	 * Then - Response section
	 */
	
	RequestSpecification  req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();
	
	String placeId;
	@Test(enabled = true)
	public void postData()
	{
		
		String response=given().log().all().spec(req).body
				("{\r\n"
						+ "    \"location\": \r\n"
						+ "    {\r\n"
						+ "        \"lat\": -38.383494,\r\n"
						+ "        \"lng\": 33.427362\r\n"
						+ "    },\r\n"
						+ "    \"accuracy\": 50,\r\n"
						+ "    \"name\": \"Chennai house\",\r\n"
						+ "    \"phone_number\": \"(+91) 983 893 3937\",\r\n"
						+ "    \"address\": \"29, side layout, cohen 09\",\r\n"
						+ "    \"types\": \r\n"
						+ "    [\r\n"
						+ "        \"shoe park\",\r\n"
						+ "        \"shop\"\r\n"
						+ "    ],\r\n"
						+ "    \"website\": \"http://dumyurl.com\",\r\n"
						+ "    \"language\": \"English-EN\"\r\n"
						+ "}"
						).when().post("/maps/api/place/add/json").then().log().all()
				.assertThat()
				.statusCode(200)
				.body("scope",equalTo("APP"))
				.header("Server", equalTo("Apache/2.4.18 (Ubuntu)"))
				.extract().asString();

		JsonPath js=new JsonPath(response);
		placeId=js.get("place_id");
		
		
		
	}

	@Test(dependsOnMethods = "postData")
	public void updateData()
	{
		
		given().log().all().spec(req)
		.body("{\r\n"
				+ "    \"place_id\": \""+placeId+"\",\r\n"
				+ "    \"address\": \"Lane new 1\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json").then().log().all()
		.assertThat()
		.body("msg", equalTo("Address successfully updated"));

	}
	
	@Test(dependsOnMethods = "updateData")
	public void getData()
	{
		
		String getDataValue=given().log().all().spec(req)
		.when().get("maps/api/place/get/json").then().log().all().extract().asString();
		
		JsonPath js=new JsonPath(getDataValue);
		String address=js.get("address");
		System.out.println("UPDATED ADDRESS =========== "+address);
		
		
	}


}
