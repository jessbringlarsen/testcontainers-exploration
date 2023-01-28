package dk.bringlarsen.testcontainersexploration;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class ManualFlywayMigrationTest {

	@Container
	PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.1"));

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
