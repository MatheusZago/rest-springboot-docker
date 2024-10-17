package br.com.matheus.services;

import br.com.matheus.controllers.PersonController;
import br.com.matheus.data.vo.v1.PersonVO;
import br.com.matheus.exceptions.ResourceNotFoundException;
import br.com.matheus.mapper.DozerMapper;
import br.com.matheus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service 
public class UserServices implements UserDetailsService {

	private Logger logger = Logger.getLogger(UserServices.class.getName());

	@Autowired // Tbm serve pra injetar p cpntroller
	private UserRepository repository;

	public UserServices(UserRepository repository) {
		this.repository = repository;
	}

//	public PersonVO findById(Long id) {
//
//		var entity = repository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
//
//		var vo = DozerMapper.parseObject(entity, PersonVO.class);
//		//Usando hateo para criar um link de auto relacionamento
//		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel()); //Adicionando um endereço para si mesmo
//		return vo;
//
//	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name " + username + "!");
		var user = repository.findByUsername(username);
		if(user != null){
			return user;
		}else {
			throw new UsernameNotFoundException("Username: " + username + " not found!");
		}
	}
}
