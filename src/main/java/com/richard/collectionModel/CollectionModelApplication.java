package com.richard.collectionModel;

import com.richard.collectionModel.model.Person;
import com.richard.collectionModel.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CollectionModelApplication {

    @Autowired
    PeopleService peopleService;

    public static void main(String[] args) {
        SpringApplication.run(CollectionModelApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

            System.out.println("\n\n\nListing the people from database\n");

            Iterable<Person> myPeople = peopleService.findAll();

            printPeopleFromDB(myPeople);

            System.out.println("Saving more people to the database\n");

            System.out.println("Creating the people ...\n");

            List<Person> people = List.of(
                    new Person(null,"Benson", 50),
                    new Person(null, "John", 40),
                    new Person(null,"Mary", 35),
                    new Person(null,"Peter", 20),
                    new Person(null,"Victor", 23)
            );

            peopleService.saveAll(people);

            System.out.println("Listing the people from database again\n");

            myPeople = peopleService.findAll();

            printPeopleFromDB(myPeople);

            // calculate the average age
            printAverageAge();

            // Get the youngest person
            printYoungestPersonFromDB();

            // Get the oldest person
            printOldestPersonFromDB();
        };
    }

    private void printOldestPersonFromDB() {
        System.out.println("The oldest Person is:\n");
        Person oldest = peopleService.findOldest();
        System.out.printf("\t\tID: %d\n\t\tName: %s\n\t\tAge: %d\n\n", oldest.id(), oldest.name(), oldest.age());
    }

    private void printYoungestPersonFromDB() {
        System.out.println("The Youngest Person is:\n");
        Person youngest = peopleService.findYoungest();
        System.out.printf("\t\tID: %d\n\t\tName: %s\n\t\tAge: %d\n\n", youngest.id(), youngest.name(), youngest.age());
    }

    private void printAverageAge() {
        System.out.println("\n\nCalculating the average age\n");
        System.out.printf("Average Age: %f\n\n", peopleService.findAverageAge());
    }

    private void printPeopleFromDB(Iterable<Person> myPeople) {
        System.out.print("ID\t\tName\t\tAge\n");
        System.out.print("=============================\n");
        for (Person person : myPeople) {
            System.out.printf("%d\t\t%s\t\t%d\n", person.id(), person.name(), person.age());
        }
        System.out.print("=============================\n");
    }

}
