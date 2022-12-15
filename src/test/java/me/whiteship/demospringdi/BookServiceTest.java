package me.whiteship.demospringdi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class BookServiceTest {

    //BookService bookService = new BookServiceProxy(new DefaultBookService());
    BookService bookService = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class},
            new InvocationHandler() {
                BookService bookService = new DefaultBookService();

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("aaaa");
                    final Object invoke = method.invoke(bookService, args);
                    System.out.println("bbbb");
                    return invoke;
                }
            });
    @Test
    void di() {
        final Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
    }

}