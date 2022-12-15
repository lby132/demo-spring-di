package me.whiteship.demospringdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService2 {

    @Autowired
    BookRepository bookRepository;

    public BookService2(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookService2() {
    }
}
