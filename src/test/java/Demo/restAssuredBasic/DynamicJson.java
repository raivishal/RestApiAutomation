package Demo.restAssuredBasic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DynamicJson {

	
	
	@Test(dataProvider = "bookDetails", enabled = false)
	public void addBookToLibraryUsingParametrization(String ISBN, String AISLE)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response =given().log().all().header("Content-Type","application/json")
		.body(payLoads.addBook(ISBN,AISLE))
		.when()
		.post("/Library/Addbook.php")
		.then().log().all().extract().asString();
		
		JsonPath js=jsonParsing.rawToJson(response);
		System.out.println(js.getString("ID"));
	}
	
	
	@Test
	public void addBookToLibraryUsingExternalJsonFile() throws IOException
	{
		String path =System.getProperty("user.dir")+"/bookData.json";
		RestAssured.baseURI="http://216.10.245.166";
		String response =given().log().all().header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get(path))))
		.when()
		.post("/Library/Addbook.php")
		.then().log().all().extract().asString();
		
		JsonPath js=jsonParsing.rawToJson(response);
		System.out.println(js.getString("ID"));
	}
	
	
	@DataProvider(name = "bookDetails")
	public Object[][] provideData()
	{
		Object[][] a=new Object[][] {{"qaz","wsx"},{"edc","rfv"},{"tgb","yhn"},{"ujm","ikm"}};
		
		return a;
	}
	
}
