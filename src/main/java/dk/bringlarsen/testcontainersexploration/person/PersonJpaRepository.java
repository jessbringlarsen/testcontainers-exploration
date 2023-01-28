package dk.bringlarsen.testcontainersexploration.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonJpaRepository extends JpaRepository<Person, String> {

    List<Person> findAllByName(String name);
}
