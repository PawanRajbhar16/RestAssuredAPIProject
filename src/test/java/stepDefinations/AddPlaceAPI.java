package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class AddPlaceAPI extends Utils {

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	 static String placeid;
	 
	@Given("add place payload {string} {string} {string}")
	public void add_place_payload(String name, String language, String address) throws IOException {
		// Write code here that turns the phrase above into concrete actions
		res = given().spec(requestSpecification()).body(data.addPlacePayLoad(name, language, address));
	}

	@When("user call {string} with {string} https request")
	public void user_call_with_https_request(String resource, String method) {
		// Write code here that turns the phrase above into concrete actions

		APIResources resourceAPI = APIResources.valueOf(resource);
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("post"))
			response = res.when().post(resourceAPI.getResource());

		else if (method.equalsIgnoreCase("get"))
			response = res.when().get(resourceAPI.getResource());

	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(getJsonPath(response, keyValue), Expectedvalue);
	}

	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String name, String resources) throws IOException {
		// Write code here that turns the phrase above into concrete actions
		 placeid = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", placeid);
		user_call_with_https_request(resources, "get");
			String Actualname=getJsonPath(response, "name");
			assertEquals(name, Actualname);
			
	}
	
	@Given("Delete PlacePayload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		res = given().spec(requestSpecification()).body(data.DeletePlacePayload(placeid));
	}

}
