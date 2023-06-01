package ru.otus.hw05jdbc.shell;

import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.hw05jdbc.model.Author;
import ru.otus.hw05jdbc.model.Book;
import ru.otus.hw05jdbc.model.Genre;
import ru.otus.hw05jdbc.service.ApplicationMenu;
import ru.otus.hw05jdbc.service.AuthorService;
import ru.otus.hw05jdbc.service.BookService;
import ru.otus.hw05jdbc.service.GenreService;
import ru.otus.hw05jdbc.service.ui.AuthorUIService;
import ru.otus.hw05jdbc.service.ui.BookUIService;
import ru.otus.hw05jdbc.service.ui.GenreUIService;

@ShellComponent
public class ApplicationCommands {
  private final AuthorService authorService;
  private final GenreService genreService;
  private final BookService bookService;
  private final ApplicationMenu applicationMenu;
  private final AuthorUIService authorUIService;
  private final BookUIService bookUIService;
  private final GenreUIService genreUIService;

  public ApplicationCommands(AuthorService authorService,
      GenreService genreService, BookService bookService,
      ApplicationMenu applicationMenu, AuthorUIService authorUIService,
      BookUIService bookUIService, GenreUIService genreUIService) {
    this.authorService = authorService;
    this.genreService = genreService;
    this.bookService = bookService;
    this.applicationMenu = applicationMenu;
    this.authorUIService = authorUIService;
    this.bookUIService = bookUIService;
    this.genreUIService = genreUIService;
  }

  @ShellMethod(value = "start command", key = { "s", "start" })
  public void startApplication() {
    applicationMenu.showAllMenuItems();
  }

  @ShellMethod(value = "author", key = { "a", "author" })
  public void workWithAuthor() {
    applicationMenu.showMenuForAuthor();
  }

  @ShellMethod(value = "genre", key = { "g", "genre" })
  public void workWithGenre() {
    applicationMenu.showMenuForGenre();
  }

  @ShellMethod(value = "book", key = { "b", "book" })
  public void workWithBooks() {
    applicationMenu.showMenuForBooks();
  }

  @ShellMethod(value = "show all authors", key = "show a")
  public void showAuthors() {
    List<Author> authorList = authorService.getAllAuthors();

    authorUIService.showAuthor(authorList);
  }

  @ShellMethod(value = "show author by id", key = "showId a")
  public void showAuthorById(String id) {
    long authorId = Long.parseLong(id);
    Author author = authorService.getAuthorById(authorId);

    authorUIService.showAuthor(List.of(author));
  }

  @ShellMethod(value = "add new author", key = "add a")
  public void addAuthor() {
    Author author = authorUIService.getAuthorForCreate();
    authorService.addAuthor(author);
  }

  @ShellMethod(value = "update author", key = "update a")
  public void updateAuthor(String id) {
    Author author = authorUIService.getAuthorForUpdate(id);
    authorService.updateAuthor(author);
  }

  @ShellMethod(value = "delete author by id", key = "del a")
  public void deleteAuthorById(String id) {
    long authorId = Long.parseLong(id);
    authorService.deleteAuthorById(authorId);
  }

  @ShellMethod(value = "delete author with books by id", key = "del awb")
  public void deleteAuthorWithBooks(String id) {
    long authorId = Long.parseLong(id);
    authorService.deleteAuthorByIdWithBooks(authorId);
  }

  @ShellMethod(value = "show all genres", key = "show g")
  public void showAllGenres() {
    List<Genre> genreList = genreService.getAllGeneres();
    genreUIService.showGenre(genreList);
  }

  @ShellMethod(value = "show genre by Id", key = "showId g")
  public void showGenreById(String id) {
    long genreId = Long.parseLong(id);
    Genre genre = genreService.getGenreById(genreId);
    genreUIService.showGenre(List.of(genre));
  }

  @ShellMethod(value = "add new genre", key = "add g")
  public void addGenre() {
    Genre genre = genreUIService.getGenreForCreate();
    genreService.addGenre(genre);
  }

  @ShellMethod(value = "update author", key = "update g")
  public void updateGenre(String id) {
    Genre genre = genreUIService.getGenreForUpdate(id);
    genreService.updateGenre(genre);
  }

  @ShellMethod(value = "delete genre by id", key = "del g")
  public void deleteGenreById(String id) {
    long genreId = Long.parseLong(id);
    genreService.deleteGenreById(genreId);
  }

  @ShellMethod(value = "delete genre with books by id", key = "del gwb")
  public void deleteGenreWithBooksById(String id) {
    long genreId = Long.parseLong(id);
    genreService.deleteGenreByIdWithBooks(genreId);
  }

  @ShellMethod(value = "show all books", key = "show b")
  public void showAllBooks() {
    List<Book> bookList = bookService.getAllBooks();
    bookUIService.showBook(bookList);
  }

  @ShellMethod(value = "show book by id", key = "showId b")
  public void showBookById(String id) {
    long bookId = Long.parseLong(id);
    Book book = bookService.getBookById(bookId);
    bookUIService.showBook(List.of(book));
  }

  @ShellMethod(value = "add new book", key = "add b")
  public void addBook() {
    Book book = bookUIService.getBookForCreate();
    bookService.addBook(book);
  }

  @ShellMethod(value = "update book by id", key = "update b")
  public void updateBook(String id) {
    Book book = bookUIService.getBookForUpdate(id);
    bookService.updateBook(book);
  }

  @ShellMethod(value = "delete book by id", key = "del b")
  public void deleteBookById(String id) {
    long bookId = Long.parseLong(id);
    bookService.deleteBookById(bookId);
  }
}
