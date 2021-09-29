package JiraRestAPIs;

import org.testng.annotations.Test;

import Demo.restAssuredBasic.jsonParsing;
import Demo.restAssuredBasic.payLoads;

import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Demo {

	String sessionId;
	String bugId;

	@Test
	public void authenticateJira()
	{
		RestAssured.baseURI="http://localhost:8080/";
		String response=given().log().all().relaxedHTTPSValidation().header("Content-Type","application/json")
						.body(payLoads.jiraLoginDetails())
						.when()
						.post("rest/auth/1/session").then().log().all().extract().asString();

		JsonPath js = jsonParsing.rawToJson(response);
		sessionId=js.get("session.value");
	}
	
	@Test(dependsOnMethods = "authenticateJira",enabled=true)
	public void createIssueInJira()
	{
		RestAssured.baseURI="http://localhost:8080/";
		String response=given().log().all().header("Content-Type","application/json").header("Cookie","JSESSIONID="+sessionId)
		.body(payLoads.createIssueInJira("CCRI","Bug11","this BUG has been created throug Automated Code"))
		.when().post("rest/api/2/issue/")
		.then().log().all().extract().asString();
		
		JsonPath js= jsonParsing.rawToJson(response);
		bugId=js.get("id");
	}
	
	@Test(dependsOnMethods = "createIssueInJira",enabled=true)
	public void addComment()
	{
		RestAssured.baseURI="http://localhost:8080/";
		given().pathParam("id", bugId).log().all().header("Content-Type","application/json").header("Cookie","JSESSIONID="+sessionId)
		.body(payLoads.addComment("this comment has been added by RestAPI automated code"))
		.when()
		.post("rest/api/2/issue/{id}/comment").then().log().all();
		
	}
	
	@Test(dependsOnMethods = "createIssueInJira")
	public void addAttachements()
	{
		RestAssured.baseURI="http://localhost:8080/";
		given().log().all().header("X-Atlassian-Token","no-check").pathParam("id", bugId).header("Content-Type","multipart/form-data").header("Cookie","JSESSIONID="+sessionId)
		.multiPart("file",new File("D:\\RestAPI\\restAssuredBasic\\bookData.json"))
		.when()
		.post("rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);
		System.out.println("");
	}

	@Test(dependsOnMethods = "addAttachements")
	public void getIssue()
	{
		RestAssured.baseURI="http://localhost:8080/";
		String issueDetails=given().log().all().header("Cookie","JSESSIONID="+sessionId).pathParam("id", bugId)
							.queryParam("fields", "comment")
							.when()
							.get("rest/api/2/issue/{id}")
							.then().extract().asString();
					
		System.out.println("*************************************************************************\n"+issueDetails);
		
	}


}
