package dk.bringlarsen.testcontainersexploration;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class ManualFlywayMigrationTest {

	@Container
	private final MSSQLServerContainer container = new MSSQLServerContainer();

	@BeforeEach
	void migrate() {
		Flyway.configure()
				.dataSource(container.getJdbcUrl(), container.getUsername(), container.getPassword())
				.load()
				.migrate();
	}

	@Test
	void expectDatabaseIsRunning() {
		Assertions.assertTrue(container.isRunning());
	}
}
