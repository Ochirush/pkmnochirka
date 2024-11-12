package ru.mirea.ulemdzhievob.web.http.jdbc;

import ru.mirea.ulemdzhievob.Card;
import ru.mirea.ulemdzhievob.Student;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;
import java.util.UUID;

public interface DatabaseService {

    Card getCardFromDatabase(String cardName, UUID... id) throws SQLException, JsonProcessingException;

    Student getStudentFromDatabase(String studentName) throws SQLException;

    Student getStudentFromDatabase(UUID id) throws SQLException;

    void saveCardToDatabase(Card card);

    void createPokemonOwner(Student owner);
}
