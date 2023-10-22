package ca.mcgill.ecse.assetplus.features;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

//To run ONLY OUR TESTS, use
//"src/test/resources/AddAndUpdateMaintenanceNoteToTicket.feature" in "features"

//To run ALL TESTS use
//"src/test/resources"

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/resources/AddTicketImage.feature",
    glue = "ca.mcgill.ecse.assetplus.features")
public class CucumberFeaturesTestRunner {

}
