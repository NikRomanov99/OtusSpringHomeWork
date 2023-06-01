package ru.otus.hw13acl.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw13acl.exception.BookReferenceException;
import ru.otus.hw13acl.model.Genre;
import ru.otus.hw13acl.repository.BookRepository;
import ru.otus.hw13acl.repository.GenreRepository;
import ru.otus.hw13acl.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
  private final GenreRepository genreRepository;
  private final BookRepository bookRepository;

  public GenreServiceImpl(GenreRepository genreRepository,
      BookRepository bookRepository) {
    this.genreRepository = genreRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Genre getGenreById(long id) {
    return genreRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Genre> getAllGeneres() {
    return genreRepository.findAll();
  }

  @Override
  @Transactional
  public Genre addGenre(Genre genre) {
    return genreRepository.save(genre);
  }

  @Override
  @Transactional
  public void updateGenre(Genre genre) {
    genreRepository.save(genre);
  }

  @Override
  @Transactional
  public void deleteGenreById(long id) {
    if (!bookRepository.existsBookByGenreId(id)) {
      genreRepository.deleteById(id);
    } else {
      throw new BookReferenceException("Genre has books, please delete book firstly!");
    }
  }

  @Override
  @Transactional
  public void deleteGenreByIdWithBooks(long id) {
    bookRepository.deleteBookByGenreId(id);
    genreRepository.deleteById(id);
  }
}
