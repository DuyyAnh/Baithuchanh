package com.example.Lab05.controller;


import com.example.Lab05.dto.BookDto;
import com.example.Lab05.entity.Book;
import com.example.Lab05.services.BookService;
import com.example.Lab05.services.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/books")
public class ApiController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    private BookDto convertToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        bookDto.setCategoryName(categoryService.getCategoryById(book.getCategory().getId()).getName());
        return bookDto;
    }
    @GetMapping
    @ResponseBody
    public List<BookDto> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books){
            bookDtos.add(convertToBookDto(book));
        }
        return bookDtos;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BookDto getBookById(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        return convertToBookDto(book);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteBook(@PathVariable Long id){
        if (bookService.getBookById(id) != null)
            bookService.deleteBook(id);
    }
}
