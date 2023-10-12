package config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@ComponentScan(basePackages = ["ga.rugal"])
@Configuration
@EnableR2dbcRepositories(basePackages = ["ga.rugal.reactor.core.dao"])
open class R2dbcContext : AbstractR2dbcConfiguration() {

  @Bean
  override fun connectionFactory(): ConnectionFactory = PostgresqlConnectionFactory(
    PostgresqlConnectionConfiguration.builder()
      .host("127.0.0.1")
      .username("postgres")
      .password("123")
      .schema(SystemDefaultProperty.SCHEMA)
      .build()
  )
}

