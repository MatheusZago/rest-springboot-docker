package br.com.matheus.unittests.mockito.services;

import br.com.matheus.data.vo.v1.PersonVO;
import br.com.matheus.exceptions.RequiredObjectIsNullException;
import br.com.matheus.model.Person;
import br.com.matheus.repositories.PersonRepository;
import br.com.matheus.services.PersonServices;
import br.com.matheus.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@TestInstance(Lifecycle.PER_CLASS) //Uma instancia por classe
@ExtendWith(MockitoExtension.class) //apara poder mockar
public class PersonServicesTest {

    MockPerson input;

    @InjectMocks //Realmente está puxando o service
    private PersonServices service;

    @Mock
    private PersonRepository repository;

    @BeforeEach
    void setupMocks(){
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);

    }

   /* @Test
    void testFindAll(){
        List<Person> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var people = service.findAll(pageable);

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);
        assertNotNull(personOne);
        assertThat(personOne.getKey()).isNotNull();
        assertThat(personOne.getLinks()).isNotNull();

        assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

        assertEquals("Addres Test1", personOne.getAddress());
        assertEquals("First Name Test1", personOne.getFirstName());
        assertEquals("Last Name Test1", personOne.getLastName());
        assertEquals("Female", personOne.getGender());

        var personFour = people.get(4);
        assertNotNull(personFour);
        assertThat(personFour.getKey()).isNotNull();
        assertThat(personFour.getLinks()).isNotNull();

        assertTrue(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));

        assertEquals("Addres Test4", personFour.getAddress());
        assertEquals("First Name Test4", personFour.getFirstName());
        assertEquals("Last Name Test4", personFour.getLastName());
        assertEquals("Male", personFour.getGender());

        var personSeven = people.get(7);
        assertNotNull(personSeven);
        assertThat(personSeven.getKey()).isNotNull();
        assertThat(personSeven.getLinks()).isNotNull();

        assertTrue(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));

        assertEquals("Addres Test7", personSeven.getAddress());
        assertEquals("First Name Test7", personSeven.getFirstName());
        assertEquals("Last Name Test7", personSeven.getLastName());
        assertEquals("Female", personSeven.getGender());

    } */

    @Test
    void testFindById(){
        Person entity = input.mockEntity(1); //Retorna um new perosn, só que sem Id
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertThat(result.getKey()).isNotNull();
        assertThat(result.getLinks()).isNotNull();

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreate(){
        Person entity = input.mockEntity(1); //Retorna um new perosn, só que sem Id
        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);
        assertThat(result).isNotNull();
        assertThat(result.getKey()).isNotNull();
        assertThat(result.getLinks()).isNotNull();

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
           service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdate(){
        Person entity = input.mockEntity(1); //Retorna um new perosn, só que sem Id
        entity.setId(1L);

        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        assertThat(result).isNotNull();
        assertThat(result.getKey()).isNotNull();
        assertThat(result.getLinks()).isNotNull();

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

        assertEquals("Addres Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson(){
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });

        String expectedMessage = "It is not allowed to persist a null object.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDelete(){
        Person entity = input.mockEntity(1); //Retorna um new perosn, só que sem Id
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }
}
