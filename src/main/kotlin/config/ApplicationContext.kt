package config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@ComponentScan(basePackages = ["ga.rugal"])
@Configuration
@EnableR2dbcRepositories(basePackages = ["ga.rugal.reactor.core.dao"])
@EnableTransactionManagement
open class ApplicationContext {
}

