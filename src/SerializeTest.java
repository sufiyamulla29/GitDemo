import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;
public class SerializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Set values using POJO class setter methods
		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side new, cohen 09");
		p.setLanguage("French-IN");
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		
		List<String> types = new ArrayList<String>();
		
		types.add("shoe park");
		types.add("shop");
		p.setTypes(types);
		
		//Location
		Location l1 = new Location();
		l1.setLat(-38.383494);
		l1.setLng(33.427362);
		
		p.setLocation(l1);
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type", "application/json").queryParam("key", "qaclick123")
		.body(p)
		.when().post("/maps/api/place/add/json").asString();
		
		System.out.println(response);
		
	}

}
