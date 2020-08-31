import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJson {

	@Test
	public void addBook() throws Exception {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(GenerateStringFromResource("E:\\APIAutomation\\DemoProjectt1\\Addbooks.json")).when().post("Library/Addbook.php").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath js = ReusableMethods.rawTojson(response);
		String id = js.getString("ID");
		System.out.println(id);

	}
	
	public static String GenerateStringFromResource(String path) throws Exception{
		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
