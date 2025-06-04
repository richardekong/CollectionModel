package com.richard.collectionModel;

import com.richard.collectionModel.model.Person;
import com.richard.collectionModel.repository.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CollectionModelApplication {

    @Autowired
    People peopleDB;

    public static void main(String[] args) {
        SpringApplication.run(CollectionModelApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

            System.out.println("\n\n\nListing the people from database\n");

            Iterable<Person> myPeople = peopleDB.findAll();

            printPeopleFromDB(myPeople, "=============================\n\n\n");

            System.out.println("Saving more people to the database\n");

            System.out.println("Creating the people ...\n");

            List<Person> people = List.of(
                    new Person("Benson", 50),
                    new Person("John", 40),
                    new Person("Mary", 35),
                    new Person("Peter", 20),
                    new Person("Victor", 23)
            );

            peopleDB.saveAll(people);

            System.out.println("Listing the people from database again\n");

            myPeople = peopleDB.findAll();

            printPeopleFromDB(myPeople, "=============================\n");

            // calculate the average age
            printAverageAge(myPeople);

            // Get the youngest person
            printYoungestPersonFromDB(myPeople);

            // Get the oldest person
            printOldestPersonFromDB(myPeople);
        };
    }

    private void printOldestPersonFromDB(Iterable<Person> myPeople) {
        System.out.println("The oldest Person is:\n");
        Person oldest = peopleDB.findOldest(myPeople);
        System.out.printf("\t\tID: %d\n\t\tName: %s\n\t\tAge: %d\n\n", oldest.getId(), oldest.getName(), oldest.getAge());
    }

    private void printYoungestPersonFromDB(Iterable<Person> myPeople) {
        System.out.println("The Youngest Person is:\n");
        Person youngest = peopleDB.findYoungest(myPeople);
        System.out.printf("\t\tID: %d\n\t\tName: %s\n\t\tAge: %d\n\n", youngest.getId(), youngest.getName(), youngest.getAge());
    }

    private void printAverageAge(Iterable<Person> myPeople) {
        System.out.println("\n\nCalculating the average age\n");
        System.out.printf("Average Age: %f\n\n", peopleDB.findAverageAge(myPeople));
    }

    private void printPeopleFromDB(Iterable<Person> myPeople, String s) {
        System.out.print("ID\t\tName\t\tAge\n");
        System.out.print("=============================\n");
        for (Person person : myPeople) {
            System.out.printf("%d\t\t%s\t\t%d\n", person.getId(), person.getName(), person.getAge());
        }
        System.out.print(s);
    }

}
