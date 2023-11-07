package ga.rugal

import org.flywaydb.core.Flyway
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class ClearDatabaseExtension : BeforeAllCallback {
  @Throws(Exception::class)
  override fun beforeAll(context: ExtensionContext) {
    SpringExtension.getApplicationContext(context).getBean(Flyway::class.java).also {
      it.clean()
      it.migrate()
    }
  }
}

