import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;
public class oAuth {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//To get authorization code
		 /*System.setProperty("webdriver.chrome.driver", "E:\\APIAutomation\\Drivers\\chromedriver.exe");
		 WebDriver driver = new ChromeDriver();
		 driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://www.googleapis.com/auth/userinfo.email&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
		 driver.findElement(By.xpath("//div[@data-email='sufiyamulla29@gmail.com']")).click();
		 String getCodeUrl = driver.getCurrentUrl();*/
		
		 String[] CourseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		 String getCodeUrl = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F3gHKnpo7mCEO2uzihNCt5HwRmjNEITM8Zu_DVjqepvQoaEIy0p7IBenaOrsP3UDRV0DqtcPehc7y7n1wRgzjNm0&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
		 
		 //get the code by splliting the url string
		 String partialCode = getCodeUrl.split("code=")[1];
		 String finalCode = partialCode.split("&scope")[0];
		 
		//to generate access token
		String accessTokenResponse = given().urlEncodingEnabled(false)
		.queryParams("code", finalCode)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token")
		.asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.get("access_token");
		
//		String response = given().queryParam("access_token", accessToken)
//				.when()
//				.get("https://rahulshettyacademy.com/getCourse.php")
//				.asString();
//		
//		System.out.println(response);
				
		
		//API to get the course
		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php")
		.as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		
		//to get the price of specific API Course
		List<Api> ApiCourses = gc.getCourses().getApi();
		
		for(int i=0;i<ApiCourses.size();i++) {
			String CourseTitle = ApiCourses.get(i).getCourseTitle();
			if(CourseTitle.equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(ApiCourses.get(i).getPrice());
			}
		}
		
		//Print all course title in web automation
		ArrayList<String> a = new ArrayList<String>();
		List<WebAutomation> WebAutomationCourses = gc.getCourses().getWebAutomation();
		for(int i=0;i<WebAutomationCourses.size();i++) {
			a.add(WebAutomationCourses.get(i).getCourseTitle());
		}
		
		List<String>ExpectedList  = Arrays.asList(CourseTitles);
		
		Assert.assertTrue(a.equals(ExpectedList));
		
		

		
	}

}
