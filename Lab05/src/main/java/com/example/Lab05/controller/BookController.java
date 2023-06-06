package com.example.Lab05.controller;


import com.example.Lab05.entity.Book;
import com.example.Lab05.entity.Category;
import com.example.Lab05.services.BookService;
import com.example.Lab05.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/books")
    public String showAllBooks(Model model){
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/books/add")
    public String addBookForm(Model model){
        model.addAttribute("book",new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }
    @PostMapping("/books/add")
    public String addBook(@ModelAttribute("book") Book book){
        bookService.addBook(book);
        return "redirect:/books";
    }
    @GetMapping("books/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        Book editbook = bookService.getBookById(id);
        if (editbook != null) {
            model.addAttribute("book", editbook);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/edit";
        } else {
            return "redirect:/books";
        }
    }
    @PostMapping("books/edit/{id}")
    public String editBook(@PathVariable("id") Long id, @ModelAttribute("book") @Valid Book editBook, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/edit";
        } else {
            Book existingBook = bookService.getBookById(id);
            if (existingBook != null) {
                existingBook.setTitle(editBook.getTitle());
                existingBook.setPrice(editBook.getPrice());
                existingBook.setCategory(editBook.getCategory());
                bookService.updateBook(existingBook); // Lưu thay đổi vào cơ sở dữ liệu
            }
            return "redirect:/books";
        }
    }
    @GetMapping("books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            bookService.deleteBook(id);
        }
        return "redirect:/books";
    }
    @GetMapping("books/search")
    public String search(@RequestParam("searchText") String searchText,Model model) {
        List<Book> books = bookService.getAllBooks();
        List<Book> filteredBooks = new ArrayList<>();

        if (searchText != null && !searchText.isEmpty()) {
            filteredBooks = books.stream()
                    .filter(book -> book.getTitle().contains(searchText))
                    .collect(Collectors.toList());
        }
        model.addAttribute("books", filteredBooks);
        return "book/list";
    }
}
