package telran.java2022.person.dao;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java2022.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

//	@Query(value ="select * from persons where product_table.name = 'Peter'",
//			nativeQuery = true)
	Stream<Person> findAllByName(String name);

	Stream<Person> findAllByAddressCity(String city);

	Stream<Person> findAllByBirthDateBetween(LocalDate from, LocalDate to);

//	@Query(value = "SELECT product.city, COUNT( product.id ) AS product[*].population FROM product.persons GROUP BY product.city", nativeQuery = true)
	@Query(value = "SELECT city, COUNT( id ) AS population FROM persons GROUP BY city", nativeQuery = true)
	Stream<Map<String, Long>> getCitiesPopulation();
}
