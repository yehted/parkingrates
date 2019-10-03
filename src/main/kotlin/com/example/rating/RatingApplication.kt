package com.example.rating

import com.example.rating.model.RateFile
import com.example.rating.repository.RateRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource

@SpringBootApplication
class RatingApplication(
	private val objectMapper: ObjectMapper
) {

	@Bean
	fun init(repository: RateRepository) = ApplicationRunner {

        val resource = ClassPathResource("rates.json").inputStream
		val rateFile = objectMapper.readValue<RateFile>(resource)
        rateFile.rates.map { dao ->
			repository.save(dao.toEntity().apply {
				this.weekDays?.forEach {
					it.parkingRate = this
				}
			})
		}
	}
}

// TODO(Use interval tree per day to check for overlap and find rate)
// TODO(Think about storing as interval tree per day)
fun main(args: Array<String>) {
	runApplication<RatingApplication>(*args)
}
