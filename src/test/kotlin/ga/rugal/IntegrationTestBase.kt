package ga.rugal

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

@ExtendWith(ClearDatabaseExtension::class)
@SpringBootTest(
  classes = [MainEntrance::class],
  properties = ["spring.flyway.clean-disabled=false"],
)
abstract class IntegrationTestBase
