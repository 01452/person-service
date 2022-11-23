package telran.java2022.person.service;

import telran.java2022.person.dto.AddressDto;
import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);

	PersonDto removePerson(Integer id);

	PersonDto updatePersonName(Integer id, String name);

	PersonDto updatePersonAddress(Integer id, AddressDto adressDto);

	Iterable<PersonDto> findPersonsByName(String name);

	Iterable<PersonDto> findPersonsBetweenAges(Integer minAge, Integer maxAge);

	Iterable<PersonDto> findPersonsByCity(String city);

	Iterable<CityPopulationDto> getCitiesPopulation();

}
