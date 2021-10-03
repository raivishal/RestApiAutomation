package OAuth2;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Demo.restAssuredBasic.jsonParsing;
import io.restassured.path.json.JsonPath;

public class AuthorizationCodeDemo 
{
	String code,access_token;
	WebDriver driver;
	
	
	@Test
	public void getCode() throws InterruptedException
	{
		String path="D:\\ExternalLibrary\\chromedriver_win32\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", path);
		
		//--open google chrome in incognito mode --
//		ChromeOptions options=new ChromeOptions();
//		options.addArguments("incognito");
//		DesiredCapabilities desCap=DesiredCapabilities.chrome();
//		desCap.setCapability(ChromeOptions.CAPABILITY,options);
		
		driver=new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.id("identifierId")).sendKeys("email");
		driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		driver.findElement(By.cssSelector("input[name='password']")).sendKeys("pass");
		driver.findElement(By.cssSelector("input[name='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		String codeURL=driver.getCurrentUrl();
		String data=codeURL.split("code=")[1];
		code=data.split("&")[0];
		
	}
	
	@Test(dependsOnMethods = "getCode")
	public void getAccessToken()
	{
		String accessTokenResponse=given().urlEncodingEnabled(false)
											.queryParam("code", code)
											.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
											.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
											.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
											.queryParam("grant_type", "authorization_code")
											.when().post("https://www.googleapis.com/oauth2/v4/token")
											.then().extract().asString();

		JsonPath js=jsonParsing.rawToJson(accessTokenResponse);
		access_token=js.get("access_token");
	}
	
	@Test(dependsOnMethods = "getAccessToken")
	public void getCourse()
	{
		String value=given().queryParam("access_token", access_token)
							.when()
							.get("https://rahulshettyacademy.com/getCourse.php")
							.then().extract().asString();
		
		System.out.println(value);
	}
}
