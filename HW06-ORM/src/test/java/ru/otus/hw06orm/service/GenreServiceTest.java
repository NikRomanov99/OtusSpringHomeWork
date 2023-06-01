package ru.otus.hw06orm.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import ru.otus.hw06orm.model.Genre;
import ru.otus.hw06orm.repository.GenreRepository;
import ru.otus.hw06orm.service.impl.GenreServiceImpl;

@DisplayName("GenreService")
@SpringBootTest
public class GenreServiceTest {
  private static final int GENRE_DEFAULT_LIST_SIZE = 1;
  private static final long TEST_GENRE_ID = 1;

  @Autowired
  private GenreServiceImpl genreService;
  @MockBean
  private GenreRepository genreRepository;

  @DisplayName("добавлять жанр в БД")
  @Test
  void shouldInsertGenre() {
    Genre expectedGenre = getTestGenre();
    genreService.addGenre(expectedGenre);

    verify(genreRepository, times(1)).saveGenre(expectedGenre);
  }

  @DisplayName("возвращать жанр по id")
  @Test
  void shouldReturnExpectedGenreById() {
    Genre expectedGenre = getTestGenre();
    Mockito.when(genreRepository.findGenreById(TEST_GENRE_ID)).thenReturn(expectedGenre);


    Genre genre = genreService.getGenreById(TEST_GENRE_ID);
    assertThat(genre).isNotNull();
    assertThat(genre).isEqualTo(expectedGenre);
  }

  @DisplayName("возвращать все жанры")
  @Test
  void shouldReturnExpectedBookList() {
    Genre expectedGenre = getTestGenre();
    Mockito.when(genreRepository.findGenres()).thenReturn(List.of(expectedGenre));

    List<Genre> genreList = genreService.getAllGeneres();

    assertThat(genreList).isNotNull();
    assertThat(genreList.size()).isEqualTo(GENRE_DEFAULT_LIST_SIZE);
    assertThat(genreList.get(0)).isEqualTo(expectedGenre);
  }

  @DisplayName("удаляем жанр по id")
  @Test
  void shouldCorrectDeleteGenre() {
    genreService.deleteGenreById(TEST_GENRE_ID);

    verify(genreRepository, times(1)).deleteGenreById(TEST_GENRE_ID);
  }

  @DisplayName("удаляем жанр по id")
  @Test
  void shouldCorrectUpdateGenre() {
    Genre testGenre = getTestGenre();
    genreService.updateGenre(testGenre);

    verify(genreRepository, times(1)).saveGenre(testGenre);
  }

  private Genre getTestGenre() {
    return new Genre(TEST_GENRE_ID, "Mysticism", "some text about mysticism");
  }
}
