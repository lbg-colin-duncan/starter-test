package __PACKAGE_PREFIX__.bdd;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		glue = {"__PACKAGE_PREFIX__.bdd.stepdefs"},
		plugin = {
				"pretty",
				"html:target/cucumber-reports.html",
				"json:target/cucumber.json"
				//"json:target/surefire-reports/CucumberIntegrationTestReport.json",

		},
		tags ="@Regression",
		features = {"src/test/resources/features"})
public class IntegrationRunner {
}
