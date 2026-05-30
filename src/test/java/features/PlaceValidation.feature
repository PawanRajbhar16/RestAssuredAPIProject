Feature: Validate Place API's

@AddPlace @Regression @Smok
Scenario Outline: Verify if Place is being successfull added using AddPlaceAPI
Given add place payload "<name>" "<language>" "<address>"
When user call "AddPlaceAPI" with "post" https request
Then the API call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify place_Id created maps to "<name>" using "getPlaceAPI"

Examples:	
		|name |language|address|
		|test |eng	   |mumbai |
#		|pawan|hindi   |mumbai |
#		|mark |eng     |us     |

@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working
Given Delete PlacePayload
When user call "deletePlaceAPI" with "post" https request
Then the API call got success with status code 200
And "status" in response body is "OK"

Scenario: Verify inquiry and deletion of place details
Given Valid Place id is available
When user call "getPlaceAPI" with "get" http request
Then the API call got success with status code 404
And "msg" in response body is "Get operation failed, looks like place_id  doesn't exists"
When user call "getPlaceAPI" with "get" http request
Then the API call got success with status code 404
And "msg" in response body is "Get operation failed, looks like place_id  doesn't exists"
