package config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@ComponentScan(basePackages = ["ga.rugal"])
@Configuration
@EnableR2dbcRepositories(basePackages = ["ga.rugal.reactor.core.dao"])
open class ApplicationContext {
}

