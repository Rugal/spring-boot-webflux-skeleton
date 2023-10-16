package ga.rugal.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.transaction.annotation.EnableTransactionManagement
import ga.rugal.PackageInfo as RootPackage
import ga.rugal.reactor.core.dao.PackageInfo as RepositoryPackage

@ComponentScan(basePackageClasses = [RootPackage::class])
@Configuration
@EnableR2dbcRepositories(basePackageClasses = [RepositoryPackage::class])
@EnableTransactionManagement
open class ApplicationContext {

//  @Bean
//  open fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer =
//    ConnectionFactoryInitializer().also {
//      it.setConnectionFactory(connectionFactory)
//      it.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
//    }
}

