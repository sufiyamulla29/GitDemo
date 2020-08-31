import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@Test(dataProvider="BookData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json").body(Payload.Addbook(isbn, aisle))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawTojson(response);
		String id = js.getString("ID");
		System.out.println(id);
		
	}
	
	@Test(dataProvider="BookData")
	public void deleteBook(String id) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "applicatoin/json").body(Payload.DeleteBook(id))
		.when().post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
	}
	
	@DataProvider(name="BookData")
	public Object[][] getData() {
		return new Object[][] {{"abbd", "3455"}, {"atgb", "29495"}, {"arbbfc", "00255"}};
	}
	
}
