package telran.java2022.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java2022.person.dto.PersonDto;
import telran.java2022.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	@Query(value ="select * from persons where product_table.name = 'Peter'",
			nativeQuery = true)
	Stream<PersonDto> findByNameIgnoreCase(String name);

//	Stream<Person> findByCityIgnoreCase(String city);

//	Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);

}
