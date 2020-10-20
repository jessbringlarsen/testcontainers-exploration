package dk.bringlarsen.testcontainersexploration.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonJpaRepository extends JpaRepository<Person, UUID> {

    List<Person> findAllByName(String name);
}
