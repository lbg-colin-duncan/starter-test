package __PACKAGE_PREFIX__.bdd.stepdefs;

import __PACKAGE_PREFIX__.bdd.IntegrationBase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.http.ResponseEntity;
import io.restassured.path.json.JsonPath;


public class RestEndpointAccessibleStepDefs extends IntegrationBase {

    public static String scenario;
    public static String jsonResponse;
    private ResponseEntity response;


    @Given("^the '(.*)' needs to be validated$")
    public void theScenarioNeedsToBeValidated(String testScenario) {
        scenario = testScenario;
        System.out.println("Test Scenario : "+testScenario);
    }

    @When("^called '(.*)' endpoint$")
    public void callEndpoint(String endPoint) {
        response = template.getForEntity(endPoint, String.class);
    }

    @Then("^should return '(\\d+)'$")
    public void shouldMatchResult(int statusCode) {
        System.out.println(response.getBody());

        Assert.assertTrue(statusCode == response.getStatusCodeValue());
        jsonResponse = (String) response.getBody();

        if(scenario.equals("HealthCheck")){
            Assert.assertEquals("UP",JsonPath.from(jsonResponse).get("status"));
        }
        else if(scenario.equals("GetCustomerDetails")){
            Assert.assertNotNull(JsonPath.from(jsonResponse).get("id"));
            Assert.assertNotNull(JsonPath.from(jsonResponse).get("name"));
        }
    }


}
