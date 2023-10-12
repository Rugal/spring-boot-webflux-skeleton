package ga.rugal.reactor.core.dao

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.test.context.junit.jupiter.SpringExtension

// https://medium.com/@BPandey/writing-unit-test-in-reactive-spring-boot-application-32b8878e2f57
@DataR2dbcTest
@ExtendWith(SpringExtension::class)
abstract class DaoIntegrationTestBase {
}
