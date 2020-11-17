package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = {"src/test/java/features"},
        glue = {"stepDefinition"},
        plugin = { "pretty", "html:target/htmlreports"},
        monochrome = true
)

public class MainRunner
{

}
