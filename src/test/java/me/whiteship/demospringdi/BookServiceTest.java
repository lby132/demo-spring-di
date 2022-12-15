package me.whiteship.demospringdi;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    void di2() {
        MethodInterceptor handler = new MethodInterceptor() {
            BookService2 bookService = new BookService2();
            @Override
            public Object intercept(Object o, Method method, Object[] objects,
                                    MethodProxy methodProxy) throws Throwable {
                return method.invoke(bookService, objects);
            } };

        final Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
    }

    @Test
    void di3() throws Exception {
        final Class<? extends BookService2> proxyClass = new ByteBuddy().subclass(BookService2.class)
                .method(named("rent")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    BookService2 bookService = new BookService2();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(bookService, args);
                    }
                }))
                .make().load(BookService2.class.getClassLoader()).getLoaded();
        final BookService2 bookService2 = proxyClass.getConstructor(null).newInstance();

        final Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
    }

    @Test
    void mockTest() {
        final BookRepository bookRepositoryMock = mock(BookRepository.class);
        final Book hibernateBook = new Book();
        hibernateBook.setTitle("Hibernate");
        when(bookRepositoryMock.save(any())).thenReturn(hibernateBook);

        final BookService2 bookService2 = new BookService2(bookRepositoryMock);

        final Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
    }
}