import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;
public class SpecBuilderTest {

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
		
		//Specification builder 
		RequestSpecification reqspec  = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification response = given().spec(reqspec).body(p);
		
		String r = response.when().post("/maps/api/place/add/json")
		.then().spec(resspec).extract().response().asString();
		
		System.out.println(r);
		
	}

}
