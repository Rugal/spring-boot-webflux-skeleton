package ga.rugal.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import ga.rugal.PackageInfo as RootPackage
import ga.rugal.reactor.core.dao.PackageInfo as RepositoryPackage

@ComponentScan(basePackageClasses = [RootPackage::class])
@Configuration
@EnableR2dbcRepositories(basePackageClasses = [RepositoryPackage::class])
@EnableTransactionManagement
open class ApplicationContext {
}

