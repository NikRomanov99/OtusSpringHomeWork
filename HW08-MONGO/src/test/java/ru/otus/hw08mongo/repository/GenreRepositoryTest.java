package ru.otus.hw08mongo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import ru.otus.hw08mongo.model.Genre;
import ru.otus.hw08mongo.testchangelog.TestData;

@DisplayName("Genre Repository")
@DataMongoTest
public class GenreRepositoryTest {

  @Autowired
  private GenreRepository genreRepository;
  @Autowired
  private MongoOperations mongoOperations;

  @DisplayName("должен загружать информацию о нужном жанре по его id")
  @Test
  void shouldFindExpectedGenreById() {
    final var actualGenre = genreRepository.findById(getGenreIdForTest());
    assertThat(actualGenre).get().usingRecursiveComparison().isEqualTo(getGenreForTest());
  }

  @DisplayName("должен загружать информацию о нужном жанре по его имени")
  @Test
  void shouldFindExpectedGenreByName() {
    final var actualGenre = genreRepository.findByName(getGenreForTest().getName());
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(getGenreForTest());
  }

  @DisplayName("должен загружать список всех жанров")
  @Test
  void shouldReturnCorrectGenreList() {
    final int expectedNumberOfGeneres = 3;
    final var genreList = genreRepository.findAll();
    assertThat(genreList).isNotNull().hasSize(expectedNumberOfGeneres)
                         .allMatch(g -> !g.getName().isEmpty());
  }

  @Test
  @DisplayName("должен корректно удалять жанр")
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  void shouldCorrectlyDeleteGenreById() {

    genreRepository.deleteById(getGenreIdForTest());

    Genre genre = mongoOperations.findById(getGenreIdForTest(), Genre.class);
    assertThat(genre).isNull();
  }

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("должен корректно добавлять жанр")
  void shouldCorrectlyInsertGenre() {
    Genre genreForAdding = new Genre("some New Genre",
                                     "some dsc for genre");
    String id = genreRepository.save(genreForAdding).getId();
    Genre genre = mongoOperations.findById(id, Genre.class);

    assertThat(genre).isNotNull();
    assertThat(genre.getName()).isEqualTo(genreForAdding.getName());
    assertThat(genre.getDescription()).isEqualTo(genreForAdding.getDescription());
  }

  @DisplayName("должен корректно обнавлять жанр")
  @Test
  void shouldCorrectlyUpdateGenreById() {
    String nameForUpdate = "some name for update";

    Genre genreForUpdate = getGenreForTest();
    genreForUpdate.setName(nameForUpdate);
    String id = genreRepository.save(genreForUpdate).getId();
    Genre genre = mongoOperations.findById(id, Genre.class);

    assertThat(genre).isNotNull();
    assertThat(genre.getName()).isEqualTo(nameForUpdate);
  }

  private Genre getGenreForTest() {
    return TestData.GENRE_FOR_TEST;
  }

  private String getGenreIdForTest() {
    return TestData.GENRE_FOR_TEST.getId();
  }
}