package com.techsolutions.sportcoach

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator

/**
 *
 * @author Abaev Evgeniy
 */
@SpringBootApplication(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator::class)
@ConfigurationPropertiesScan("com.techsolutions.sportcoach")
class SportCoachApplication

fun main(args: Array<String>) {
  runApplication<SportCoachApplication>(*args)
}