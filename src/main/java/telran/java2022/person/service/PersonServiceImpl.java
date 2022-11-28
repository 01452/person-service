package telran.java2022.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java2022.person.dao.PersonRepository;
import telran.java2022.person.dto.AddressDto;
import telran.java2022.person.dto.ChildDto;
import telran.java2022.person.dto.CityPopulationDto;
import telran.java2022.person.dto.EmployeeDto;
import telran.java2022.person.dto.PersonDto;
import telran.java2022.person.dto.PersonNotFoundException;
import telran.java2022.person.model.Address;
import telran.java2022.person.model.Child;
import telran.java2022.person.model.Employee;
import telran.java2022.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	public void run(String... args) throws Exception {
		Person person = new Person(1000, "John", LocalDate.of(1985, 4, 11), new Address("Tel-Aviv", "Ben Gvirol", 87));
		Child child = new Child(2000, "Mosche", LocalDate.of(2018, 7, 5), new Address("Sshkelon", "Bar Kohva", 21),"Shalom");
		Employee employee = new Employee(3000, "Sara", LocalDate.of(1995, 11, 23), new Address("Rehovot", "Herzl", 7),"Motorola",20000);
		personRepository.save(person);
		personRepository.save(child);
		personRepository.save(employee);
	}
	
	@Override
	@Transactional
	public Boolean addPerson(PersonDto personDto) {
		if(personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, getModelClass(personDto)));
		return true;
	}

	private Class<? extends Person> getModelClass(PersonDto personDto) {
		if(personDto instanceof EmployeeDto) {
			return Employee.class;
		}
		if(personDto instanceof ChildDto) {
			return Child.class;
		}
		return Person.class;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto updatePersonAddress(Integer id, AddressDto adressDto) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(adressDto, Address.class));
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findAllByName(name).map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsBetweenAges(Integer minAge, Integer maxAge) {
		LocalDate from = LocalDate.now().minusYears(maxAge);
		LocalDate to = LocalDate.now().minusYears(minAge);
		return personRepository.findAllByBirthDateBetween(from, to).map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findAllByAddressCity(city).map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		return personRepository.getCitiesPopulation().map(p -> modelMapper.map(p, CityPopulationDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findEmployeeBySalary(int min, int max) {
		return personRepository.findEmployeeBySalary(min,max).map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> getChildren() {
		return personRepository.getChildren().map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}


}
