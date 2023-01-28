package dk.bringlarsen.testcontainersexploration.person;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Person {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }
}
