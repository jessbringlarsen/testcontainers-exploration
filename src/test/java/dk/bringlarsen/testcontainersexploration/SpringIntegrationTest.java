package dk.bringlarsen.testcontainersexploration;

import dk.bringlarsen.testcontainersexploration.person.Person;
import dk.bringlarsen.testcontainersexploration.person.PersonJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(initializers = {SpringIntegrationTest.Initializer.class})
class SpringIntegrationTest {

	@Autowired
	private PersonJpaRepository repository;

	@Container
	static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.1"));

	private String personId;

	@BeforeEach
	void setup() {
		Person person = new Person()
				.setName("John");
		personId = repository.save(person).getId();
	}

	@Test
	void testPersonIsCreated() {
		Optional<Person> person = repository.findById(personId);

		assertTrue(person.isPresent());
		assertEquals("John", person.get().getName());
	}

	@Test
	void testFindAll() {
		List<Person> result = repository.findAll();

		assertEquals(1, result.size());
	}

	@Test
	void testFindAllByName() {
		List<Person> result = repository.findAllByName("John");

		assertEquals(1, result.size());
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + CONTAINER.getJdbcUrl(),
					"spring.datasource.username=" + CONTAINER.getUsername(),
					"spring.datasource.password=" + CONTAINER.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}
