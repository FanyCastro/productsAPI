package com.capitole.productsapi.bdd.runners

import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.*

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters(
    value = [
        ConfigurationParameter(
            key = Constants.GLUE_PROPERTY_NAME,
            value = "com.capitole.productsapi.bdd.steps"
        ),
        ConfigurationParameter(
            key = Constants.PLUGIN_PROPERTY_NAME,
            value = "html:target/cucumber-reports/cucumber.html,pretty"
        ),
        ConfigurationParameter(
            key = Constants.FILTER_TAGS_PROPERTY_NAME,
            value = "not @ignored"
        )
    ]
)
class CucumberTestSuite {
}