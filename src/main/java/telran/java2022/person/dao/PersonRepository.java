package telran.java2022.person.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.model.Child;
import telran.java2022.person.model.Employee;
import telran.java2022.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

	Stream<Person> findAllByName(String name);

	Stream<Person> findAllByAddressCity(String city);

	Stream<Person> findAllByBirthDateBetween(LocalDate from, LocalDate to);

	@Query("select new telran.java2022.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	Stream<CityPopulationDto> getCitiesPopulation();

	@Query(value ="SELECT * FROM EMPLOYEE e where e.salary>=? and e.salary<?", nativeQuery = true)
	Stream<Employee> findEmployeeBySalary(int min, int max);
	
	@Query(value ="SELECT * FROM Child", nativeQuery = true)
	Stream<Child> getChildren();
}
