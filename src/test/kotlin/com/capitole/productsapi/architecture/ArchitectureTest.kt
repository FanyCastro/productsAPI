package com.capitole.productsapi.architecture

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.Architectures.layeredArchitecture

@AnalyzeClasses(
    packages = ["com.capitole.productsapi"],
    importOptions = [ImportOption.DoNotIncludeTests::class]
)
class ArchitectureTest {

    @ArchTest
    val layeredArchitectureRule: ArchRule = layeredArchitecture()
        .consideringAllDependencies()
        .layer("Domain").definedBy("..domain..")
        .layer("Application").definedBy("..application..")
        .layer("Infrastructure").definedBy("..infrastructure..")
        .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application")
        .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure")
        .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()

    @ArchTest
    val domainShouldNotDependOnApplication: ArchRule = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat().resideInAPackage("..application..")

    @ArchTest
    val domainShouldNotDependOnInfrastructure: ArchRule = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat().resideInAPackage("..infrastructure..")

    @ArchTest
    val applicationShouldNotDependOnInfrastructure: ArchRule = noClasses()
        .that().resideInAPackage("..application..")
        .should().dependOnClassesThat().resideInAPackage("..infrastructure..")

    @ArchTest
    val domainModelsShouldBeInDomainPackage: ArchRule = classes()
        .that().haveSimpleNameEndingWith("Model")
        .should().resideInAPackage("..domain.model..")

    @ArchTest
    val dtosShouldBeInInfrastructurePackage: ArchRule = classes()
        .that().haveSimpleNameEndingWith("DTO")
        .should().resideInAPackage("..infrastructure.web.dto..")

    @ArchTest
    val repositoriesShouldBeInDomainPackage: ArchRule = classes()
        .that().haveSimpleNameEndingWith("Repository")
        .should().resideInAPackage("..domain.port.out..")

    @ArchTest
    val servicesShouldBeInApplicationPackage: ArchRule = classes()
        .that().haveSimpleNameEndingWith("Service")
        .should().resideInAPackage("..application..")
        .orShould().resideInAPackage("..domain.port.in..")
} 