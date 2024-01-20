package org.alenapech;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    static BookRepository bookRepository;
    static BookService bookService;
    static List<Book> bookList;

    @BeforeAll
    static void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
        bookList = List.of(new Book("1", "title1", "author1")
                , new Book("2", "title2", "author2")
                , new Book("3", "title3", "author3"));
        when(bookRepository.findById(bookList.get(0).getId())).thenReturn(bookList.get(0));
        when(bookRepository.findById(bookList.get(1).getId())).thenReturn(bookList.get(1));
        when(bookRepository.findById(bookList.get(2).getId())).thenReturn(bookList.get(2));
        when(bookRepository.findAll()).thenReturn(bookList);
    }

    private static Stream<Arguments> findBookByIdTestData() {
        return Stream.of(
                Arguments.of("1", bookList.get(0)),
                Arguments.of("2", bookList.get(1)),
                Arguments.of("3", bookList.get(2))
        );
    }

    @ParameterizedTest
    @MethodSource("findBookByIdTestData")
    void findBookById(String id, Book expectedResult) {
        Book actualResult = bookService.findBookById(id);
        verify(bookRepository, times(1)).findById(id);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findAllBooks() {
        List<Book> actualResult = bookService.findAllBooks();
        verify(bookRepository, times(1)).findAll();
        assertEquals(bookList, actualResult);
    }
}