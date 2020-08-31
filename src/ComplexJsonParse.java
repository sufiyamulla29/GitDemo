import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(Payload.CoursePrice());

		// print number of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// print purchase amount
		int amount = js.getInt("dashboard.purchaseAmount");
		System.out.println(amount);

		// print title of first course
		String firstTitle = js.getString("courses[0].title");
		System.out.println(firstTitle);

		// print all courses titles and their respective prices
		for (int i = 0; i < count; i++) {
			System.out.println(js.getString("courses[" + i + "].title"));
			System.out.println(js.getInt("courses[" + i + "].price"));
		}

		// no of copies sold by PRA course
		for (int i = 0; i < count; i++) {
			String title = js.getString("courses[" + i + "].title");
			if (title.equalsIgnoreCase("RPA")) {
				System.out.println(js.getInt("courses[" + i + "].copies"));
				break;
			}
		}

	}

}
