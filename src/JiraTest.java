import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import com.sun.mail.handlers.message_rfc822;

public class JiraTest {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://localhost:8080/";

		// login Scenario
		SessionFilter session = new SessionFilter();
		String Response = given().log().all().header("Content-Type", "application/json")
				.body("{ \"username\": \"sufiya.mulla\", \"password\": \"alimatrix\" }").filter(session).when()
				.post("rest/auth/1/session").then().log().all().extract().response().asString();

		// Add comment to issue
		String expectedMessage = "Now this is new comment";
		String commentResponse = given().log().all().pathParam("id", "10002").header("Content-Type", "application/json")
				.body("{ \r\n" + " \"body\": \""+expectedMessage+"\",\r\n" + " \"visibility\": {\r\n"
						+ "    \"type\": \"role\",\r\n" + "    \"value\": \"Administrators\"\r\n" + "  }\r\n" + "}")
				.filter(session).when().post("rest/api/2/issue/{id}/comment").then().log().all().assertThat()
				.statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(commentResponse);
		String commentId = js.get("id");

		// Add attachment to issue
		given().log().all().header("X-Atlassian-Token", "no-check").header("Content-Type", "multipart/form-data")
				.filter(session).pathParam("id", "10002").multiPart("file", new File("jira.txt")).when()
				.post("rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);

		// get issue details
		String issueDetails = given().log().all().filter(session).pathParam("id", "10002")
				.queryParam("fields", "comment").when().get("rest/api/2/issue/{id}").then().log().all().extract()
				.response().asString();

		// verify comment
		JsonPath js1 = new JsonPath(issueDetails);
		int commentCount = js1.get("fields.comment.comments.size()");
		System.out.println(commentCount);
		for (int i = 0; i < commentCount; i++) {
			String id = js1.get("fields.comment.comments["+i+"].id");
			if(id.equalsIgnoreCase(commentId)) {
				String message1 = js1.get("fields.comment.comments["+i+"].body");
				Assert.assertEquals(message1, expectedMessage);
			}
		}
		
	}
}
