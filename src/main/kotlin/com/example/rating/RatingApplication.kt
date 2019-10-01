package com.example.rating

import com.example.rating.model.RateFile
import com.example.rating.repository.RateRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.util.ResourceUtils
import java.io.File

@SpringBootApplication
class RatingApplication(
	private val objectMapper: ObjectMapper
) {

	@Bean
	fun init(repository: RateRepository) = ApplicationRunner {

		val file: File = ResourceUtils.getFile("classpath:rates.json")
		val rateFile = objectMapper.readValue<RateFile>(file)
        rateFile.rates.map {
			repository.save(it.toEntity())
		}
	}
}

fun main(args: Array<String>) {
	runApplication<RatingApplication>(*args)
}
