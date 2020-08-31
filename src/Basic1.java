import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class Basic1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(Payload.addPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.extract().response().asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String PlaceID = js.getString("place_id");
		System.out.println(PlaceID);
		
		
		//update place
		String newaddress = "70 Summer walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+PlaceID+"\",\r\n" + 
				"\"address\":\""+newaddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}\r\n" + 
				"")
		.when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//get place
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("pace_id", PlaceID)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = ReusableMethods.rawTojson(getPlaceResponse);
		String actualAddress = js.getString("address");
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newaddress);
	}

}
