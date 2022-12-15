package me.whiteship.demospringdi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void di() {
        final Book book = new Book();
        book.setTitle("spring");
        bookRepository.save(book);
        bookRepository.findAll();
    }
}
