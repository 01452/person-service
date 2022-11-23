package telran.java2022.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.Return;
import telran.java2022.person.dao.PersonRepository;
import telran.java2022.person.dto.AddressDto;
import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.dto.PersonDto;
import telran.java2022.person.dto.PersonNotFoundException;
import telran.java2022.person.model.Address;
import telran.java2022.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	public Boolean addPerson(PersonDto personDto) {
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
		return modelMapper.map(personRepository.save(person), PersonDto.class);
	}

	@Override
	public PersonDto updatePersonAddress(Integer id, AddressDto adressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(adressDto, Address.class));
		return modelMapper.map(personRepository.save(person), PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findByNameIgnoreCase(name).map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
//		return null;
	}

	@Override
	public Iterable<PersonDto> findPersonsBetweenAges(Integer minAge, Integer maxAge) {
//		LocalDate from = LocalDate.now().minusYears(maxAge);
//		LocalDate to = LocalDate.now().minusYears(minAge);
//		return personRepository.findByBirthDateBetween(to, from).map(p -> modelMapper.map(p, PersonDto.class))
//				.collect(Collectors.toList());
		return null;
	}

	@Override
	public Iterable<PersonDto> findPersonsByCity(String city) {
//		return personRepository.findByCityIgnoreCase(city).map(p -> modelMapper.map(p, PersonDto.class))
//				.collect(Collectors.toList());
		return null;
	}

	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		// TODO Auto-generated method stub
		return null;
	}

}
