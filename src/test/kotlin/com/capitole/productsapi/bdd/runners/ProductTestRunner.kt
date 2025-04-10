package com.capitole.productsapi.bdd.runners

import org.junit.platform.suite.api.ConfigurationParameters
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite
import io.cucumber.junit.platform.engine.Constants

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters(
    value = [
        ConfigurationParameter(
            key = Constants.GLUE_PROPERTY_NAME,
            value = "com.capitole.productsapi.bdd.steps, com.capitole.productsapi.bdd.config"
        ),
        ConfigurationParameter(
            key = Constants.PLUGIN_PROPERTY_NAME,
            value = "pretty, html:build/reports/cucumber/report.html, json:build/reports/cucumber/report.json"
        ),
        ConfigurationParameter(
            key = Constants.FILTER_TAGS_PROPERTY_NAME,
            value = "not @ignored"
        )
    ]
)
class ProductTestRunner {
}