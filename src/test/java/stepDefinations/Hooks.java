package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeSenario() throws IOException {
		
	//execute this code only when place id null
	//write the code that will give place id
		
		if(AddPlaceAPI.placeid==null) {
		AddPlaceAPI addPlaceAPI=new AddPlaceAPI();
			addPlaceAPI.add_place_payload("tony", "English", "United State");
			addPlaceAPI.user_call_with_https_request("AddPlaceAPI", "post");
			addPlaceAPI.verify_place_id_created_maps_to_using("tony", "getPlaceAPI");
		}
		
	}

}
