package testCases;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;


public class TC_VideoGameApi {
	
	@Test(priority = 1)
	public void test_getAllVideoGames() {
		given().contentType("application/json").log().all()
		.when()
			.get("http://localhost:8080/app/videogames")
		.then().log().all()
				.statusCode(200);
	}
	
	@Test(priority = 2)
	public void test_AddNewVideoGame() {
		
		Map <String, Object> dataHashMap = new HashMap<String, Object>();
		dataHashMap.put("id", "105");
		dataHashMap.put("name", "Spider-Man");
		dataHashMap.put("releaseDate", "2019-09-20T08:55:58.510Z");
		dataHashMap.put("reviewScore", "5");
		dataHashMap.put("category", "Adventure");
		dataHashMap.put("rating", "Universal");
		
		Response response =
		given()
			.contentType("application/json")
			.body(dataHashMap)
			.when()
			.post("http://localhost:8080/app/videogames")
			.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		String jsonString = response.asString();
		
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
	}
	
	@Test(priority = 3)
	public void get_VideoGame() {
		
		given()
		.when()
		.get("http://localhost:8080/app/videogames/105")
		.then().statusCode(200)
		.body("videoGame.id", Matchers.equalTo("105"))
		.body("videoGame.name", Matchers.equalTo("Spider-Man"));
		
	}
	
	@Test(priority = 4)
	public void test_UpdateVideoGame() {
		
		Map <String, Object> dataHashMap = new HashMap<String, Object>();
		dataHashMap.put("id", "105");
		dataHashMap.put("name", "Super Spider-Man");
		dataHashMap.put("releaseDate", "2019-09-20T08:55:58.510Z");
		dataHashMap.put("reviewScore", "4");
		dataHashMap.put("category", "Adventure");
		dataHashMap.put("rating", "Universal");
		
		given()
			.contentType("application/json")
			.body(dataHashMap)
			.when()
			.put("http://localhost:8080/app/videogames/105")
			.then().statusCode(200)
			.log().body()
			.body("videoGame.id", Matchers.equalTo("105"))
			.body("videoGame.name", Matchers.equalTo("Super Spider-Man"));
	}
	
	@Test(priority = 5)
	public void deleteGame() throws InterruptedException {
		
		Response response =
		given()
		.when()
		.delete("http://localhost:8080/app/videogames/105")
		.then()
		.statusCode(200)
		.log().body()
		.extract().response();
		Thread.sleep(3000);
		String jsonString = response.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
		
	}
	
			
	
}
