package br.com.matheus.services;

import br.com.matheus.controllers.BookController;
import br.com.matheus.controllers.PersonController;
import br.com.matheus.data.vo.v1.BookVO;
import br.com.matheus.data.vo.v1.PersonVO;
import br.com.matheus.exceptions.RequiredObjectIsNullException;
import br.com.matheus.exceptions.ResourceNotFoundException;
import br.com.matheus.mapper.DozerMapper;
import br.com.matheus.model.Book;
import br.com.matheus.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

	private Logger logger = Logger.getLogger(BookServices.class.getName());

	@Autowired 
	private BookRepository bookRepository;

	@Autowired
	private PagedResourcesAssembler<BookVO> assembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) throws Exception {
		logger.info("Finding all books");

		var bookPage = bookRepository.findAll(pageable);
		var bookVOPage = bookPage.map(b ->  DozerMapper.parseObject(b, BookVO.class));

		//Passando linkhateos
		bookVOPage.map(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));

		//Para adicionar o link hateas da PÁGINA em si
		Link link = linkTo(methodOn(BookController.class).
				findByAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

		return assembler.toModel(bookVOPage, link);
	}

	public BookVO findById(Long id) {
		logger.info("Finding one book!");

		var entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

		var vo = DozerMapper.parseObject(entity, BookVO.class);
		//Usando hateo para criar um link de auto relacionamento
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel()); //Adicionando um endereço para si mesmo
		return vo;

	}

	public BookVO create(BookVO bookVO) {
		if(bookVO == null) throw new RequiredObjectIsNullException();

		logger.info("Creating one book");
		var entity = DozerMapper.parseObject(bookVO, Book.class);

		//Primeiro ele vai salvar, e ai o objeto salvo vai ser covertido para VO PARA A APLICAÇÃO APENAS
		var vo = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel()); //Adicionando um endereço para si mesmo
		return vo;
	}  

	  public BookVO update(BookVO bookVO) {
		  if(bookVO == null) throw new RequiredObjectIsNullException();

		  logger.info("Updating one book");

		var entity = bookRepository.findById(bookVO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

		entity.setAuthor(bookVO.getAuthor());
		entity.setLaunchdate(bookVO.getLaunchdate());
		entity.setPrice(bookVO.getPrice());
		entity.setTitle(bookVO.getTitle());

		//Primeiro ele vai salvar, e ai o objeto salvo vai ser covertido para VO PARA A APLICAÇÃO APENAS
		var vo = DozerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel()); //Adicionando um endereço para si mesmo
		return vo;
	}

	public void delete(Long id)  {
		logger.info("Deleting one book");

		var entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

		bookRepository.delete(entity);
	}

}
