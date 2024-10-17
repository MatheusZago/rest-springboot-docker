package br.com.matheus.repositories;

import br.com.matheus.model.Book;
import br.com.matheus.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
