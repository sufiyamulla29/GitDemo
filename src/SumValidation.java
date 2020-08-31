import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumOfCourse() {
		// verify sum of all course prices matches with purches amount
			JsonPath js = new JsonPath(Payload.CoursePrice());
				int sum = 0;
				int count = js.getInt("courses.size()");
				int amount = js.getInt("dashboard.purchaseAmount");
				
				for (int i = 0; i < count; i++) {

					int price = js.getInt("courses[" + i + "].price");
					int copies = js.getInt("courses[" + i + "].copies");
					int n = price * copies;
					sum = sum + n;
					
				}
				System.out.println(sum +" "+ amount);
				if(sum==amount) {
					System.out.println("both are same");
				}else {
					System.out.println("both are different");
				}

			}
	

}
