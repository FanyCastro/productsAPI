package com.capitole.productsapi.architecture

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.Architectures.layeredArchitecture

@AnalyzeClasses(
    packages = ["com.capitole.productsapi"],
    importOptions = [ImportOption.DoNotIncludeTests::class]
)
class HexagonalArchitectureTest {
    private val basePackage = "com.capitole.productsapi"

    @ArchTest
    val `hexagonal architecture layers are respected`: ArchRule = layeredArchitecture()
        .consideringAllDependencies()
        .layer("Domain").definedBy("..domain..")
        .layer("Application").definedBy("..application..")
        .layer("Infrastructure").definedBy("..infrastructure..")

    @ArchTest
    val `domain should not depend on application`: ArchRule = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat().resideInAPackage("..application..")

    @ArchTest
    val `domain should not depend on infrastructure`: ArchRule = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat().resideInAPackage("..infrastructure..")

    @ArchTest
    val `domain model should not have infrastructure annotations`: ArchRule = noClasses()
        .that()
        .resideInAPackage("$basePackage.domain.model..")
        .should()
        .beAnnotatedWith("jakarta.persistence.Entity")
        .orShould()
        .beAnnotatedWith("jakarta.persistence.Table")

    @ArchTest
    val `adapters should implement ports`: ArchRule = noClasses()
        .that()
        .resideInAPackage("$basePackage.infrastructure..")
        .and()
        .haveSimpleNameEndingWith("Adapter")
        .should()
        .implement("$basePackage.domain.port..")
}