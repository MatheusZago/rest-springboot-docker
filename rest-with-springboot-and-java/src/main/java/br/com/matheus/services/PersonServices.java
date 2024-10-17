package br.com.matheus.services;

import java.util.logging.Logger;

import br.com.matheus.controllers.PersonController;
import br.com.matheus.exceptions.RequiredObjectIsNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.matheus.data.vo.v1.PersonVO;
import br.com.matheus.exceptions.ResourceNotFoundException;
import br.com.matheus.mapper.DozerMapper;
import br.com.matheus.model.Person;
import br.com.matheus.repositories.PersonRepository;

@Service // Para que o Springboot encare esse objeto como um que vai ser injetado em
			// runtime na app
//Ou seja, ele não precisa fazer o PersonVO Service = new Service(), isso já faz ele ficar instanciado em todos os lugares que precisam dele
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PagedResourcesAssembler<PersonVO> assembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) throws Exception {
		logger.info("Finding all people");

		var personPage = personRepository.findAll(pageable);
		var personVOPage = personPage.map(p ->  DozerMapper.parseObject(p, PersonVO.class));

		//Passando linkhateos
		personVOPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		//Para adicionar o link hateas da PÁGINA em si
		Link link = linkTo(methodOn(PersonController.class).
				findByAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

		return assembler.toModel(personVOPage, link);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<EntityModel<PersonVO>> findPeopleByName(String firstName, Pageable pageable) throws Exception {
		logger.info("Finding all people");

		var personPage = personRepository.findPeopleByName(firstName ,pageable);
		var personVOPage = personPage.map(p ->  DozerMapper.parseObject(p, PersonVO.class));

		//Passando linkhateos
		personVOPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		//Para adicionar o link hateas da PÁGINA em si
		Link link = linkTo(methodOn(PersonController.class).
				findByAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

		return assembler.toModel(personVOPage, link);
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person!");

		var entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		//Usando hateo para criar um link de auto relacionamento
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel()); //Adicionando um endereço para si mesmo
		return vo;
	}

	public PersonVO create(PersonVO personVO) {
		if(personVO == null) throw new RequiredObjectIsNullException();

		logger.info("Creating one person");
		var entity = DozerMapper.parseObject(personVO, Person.class);  

		//Primeiro ele vai salvar, e ai o objeto salvo vai ser covertido para VO PARA A APLICAÇÃO APENAS
		var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel()); //Adicionando um endereço para si mesmo
		return vo;
	}  

	  public PersonVO update(PersonVO personVO) {
		  if(personVO == null) throw new RequiredObjectIsNullException();

		  logger.info("Updating one person");

		var entity = personRepository.findById(personVO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());

		//Primeiro ele vai salvar, e ai o objeto salvo vai ser covertido para VO PARA A APLICAÇÃO APENAS
		var vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel()); //Adicionando um endereço para si mesmo
		return vo;
	}

	@Transactional
	public PersonVO disablePerson(Long id) {
		logger.info("Disabling one person!");

		personRepository.disablePerson(id);

		var entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		//Usando hateo para criar um link de auto relacionamento
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel()); //Adicionando um endereço para si mesmo
		return vo;
	}


	public void delete(Long id)  {
		logger.info("Deleting one person");

		var entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

		personRepository.delete(entity);
	}

}
