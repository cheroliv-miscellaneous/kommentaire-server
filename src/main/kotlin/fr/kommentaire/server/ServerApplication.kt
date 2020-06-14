package fr.kommentaire.server

import com.expediagroup.graphql.directives.KotlinDirectiveWiringFactory
import fr.kommentaire.server.authentication.AuthenticationDirective
import fr.kommentaire.server.authentication.AuthenticationSchemaDirectiveWiring
import fr.kommentaire.server.directives.CustomDirectiveWiringFactory
import fr.kommentaire.server.directives.CustomSchemaGeneratorHooks
import graphql.schema.idl.RuntimeWiring
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@SpringBootApplication
class ServerApplication

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}

@Configuration
class Configuration {

	@Bean
	fun questionJdbcInsert(jdbcTemplate: JdbcTemplate) : SimpleJdbcInsert {
		return SimpleJdbcInsert(jdbcTemplate).withTableName("QUESTION").usingGeneratedKeyColumns("id")
	}

	@Bean
	fun userJdbcInsert(jdbcTemplate: JdbcTemplate) : SimpleJdbcInsert {
		return SimpleJdbcInsert(jdbcTemplate).withTableName("USER").usingGeneratedKeyColumns("id")
	}

	@Bean
	fun passwordEncoder() : PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun wiringFactory() = CustomDirectiveWiringFactory()

	@Bean
	fun hooks(wiringFactory: KotlinDirectiveWiringFactory) = CustomSchemaGeneratorHooks(wiringFactory)


}
