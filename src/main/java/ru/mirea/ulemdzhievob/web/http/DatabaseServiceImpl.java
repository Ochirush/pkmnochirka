package ru.mirea.ulemdzhievob.web.http;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.mirea.ulemdzhievob.models.*;
import ru.mirea.ulemdzhievob.web.http.jdbc.DatabaseService;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class DatabaseServiceImpl implements DatabaseService {

    private final Connection connection;

    private final Properties databaseProperties;

    public DatabaseServiceImpl() throws SQLException, IOException {



        databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream("src/main/resources/database.properties"));


        connection = DriverManager.getConnection(
                databaseProperties.getProperty("database.url"),
                databaseProperties.getProperty("database.user"),
                databaseProperties.getProperty("database.password")
        );
        System.out.println("Connection is "+(connection.isValid(0) ? "up" : "down"));
    }


    public Card getCardFromDatabase(String cardName, UUID... id) throws SQLException, JsonProcessingException {
        // Реализовать получение данных о карте из БД
        String selectCardSQL;
        if (id.length != 0)
            selectCardSQL = "SELECT * FROM card WHERE id = ?";
        else
            selectCardSQL = "SELECT * FROM card WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCardSQL)) {
            if (id.length != 0)
                preparedStatement.setObject(1, id[0]);
            else
                preparedStatement.setString(1, cardName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PokemonStage pokemonStage = PokemonStage.valueOf(resultSet.getString("stage"));
                String name = resultSet.getString("name");
                int hp = Integer.parseInt(resultSet.getString("hp"));
                EnergyType pokemonType = EnergyType.valueOf(resultSet.getString("pokemon_type"));
                Card evolvesFrom = null;
                if (resultSet.getObject("evolves_from") != null) {
                    evolvesFrom = getCardFromDatabase(null, (UUID) resultSet.getObject("evolves_from"));
                }
                ObjectMapper objectMapper = new ObjectMapper();

                PGobject jsonObject = (PGobject) resultSet.getObject("attack_skills");
                String json = jsonObject.getValue();
                List<AttackSkill> skills = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, AttackSkill.class));


                for (AttackSkill skill : skills) {
                    System.out.println(skill);
                }

                String line = resultSet.getString("weakness_type");
                EnergyType weaknessType = null;
                if (line != null) {
                    weaknessType = EnergyType.valueOf(line);
                }
                line = resultSet.getString("resistance_type");
                EnergyType resistanceType = null;
                if (line != null) {
                    resistanceType = EnergyType.valueOf(line);
                }
                String retreatCost = resultSet.getString("retreat_cost");
                String gameSet = resultSet.getString("game_set");
                char regulationMark = resultSet.getString("regulation_mark").charAt(0);
                UUID studentID = (UUID) resultSet.getObject("pokemon_owner");
                Student pokemonOwner;
                if (studentID != null)
                    pokemonOwner = getStudentFromDatabase(studentID);
                else
                    pokemonOwner = null;
                String number = resultSet.getString("card_number");
                return new Card(pokemonStage, name, hp, pokemonType,  evolvesFrom, skills, weaknessType, resistanceType, retreatCost, gameSet, regulationMark, pokemonOwner, number);
            }
        }
        return null; 
    }

    @Override
    public Student getStudentFromDatabase(String studentName) throws SQLException {

        String[] nameParts = studentName.split(" ");
        if (nameParts.length != 3) {
            throw new IllegalArgumentException("Полное имя должно содержать Фамилию, Имя и Отчество.");
        }

        String lastName = nameParts[0];
        String firstName = nameParts[1];
        String patronymic = nameParts[2];

        String selectStudentSQL = "SELECT * FROM student WHERE familyName = ? AND firstName = ? AND patronicName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStudentSQL)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, patronymic);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               
                UUID id = (UUID) resultSet.getObject("id");
                String group = resultSet.getString("group");

                
                return new Student(id, firstName, lastName, patronymic, group);
            }
        }
        return null; // Возвращаем null, если студент не найден
    }


    public Student getStudentFromDatabase(UUID id) throws SQLException {
        String selectStudentSQL = "SELECT * FROM student WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStudentSQL)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                
                UUID studentId = (UUID) resultSet.getObject("id");
                String group = resultSet.getString("group");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("familyName");
                String patronymic = resultSet.getString("patronicName");


                return new Student(studentId, firstName, lastName, patronymic, group);
            }
        }
        return null;
    }


    @Override
    public void saveCardToDatabase(Card card) {
        // Реализовать отправку данных карты в БД
        String insertCardSQL = "INSERT INTO card (name, hp, evolves_from, game_set, pokemon_owner, stage, retreat_cost," +
                " weakness_type, resistance_type, attack_skills, pokemon_type, regulation_mark, card_number, id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCardSQL)) {
            preparedStatement.setString(1, card.getName());
            preparedStatement.setInt(2, card.getHp());
            if (card.getEvolvesFrom()!=null)
            {
                saveCardToDatabase(card.getEvolvesFrom());
                preparedStatement.setObject(3, getUUID(card.getEvolvesFrom().getNumber()));
            }
            else
                preparedStatement.setObject(3, (UUID)null);
            preparedStatement.setString(4, card.getGameset());
            if (card.getPokemonOwner() != null)
            {
                createPokemonOwner(card.getPokemonOwner());
                preparedStatement.setObject(5, getStudentUUID(card.getPokemonOwner()));
            }
            else
                preparedStatement.setObject(5, (UUID)null);
            preparedStatement.setString(6, String.valueOf(card.getStage()));
            if (card.getRetreatCost() != null)
                preparedStatement.setString(7, card.getRetreatCost());
            else
                preparedStatement.setString(7, null);
            if (card.getWeaknessType() != null)
                preparedStatement.setString(8, String.valueOf(card.getWeaknessType()));
            else
                preparedStatement.setString(8, null);
            if (card.getResistanceType() != null)
                preparedStatement.setString(9, String.valueOf(card.getResistanceType()));
            else
                preparedStatement.setString(9, null);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(card.getSkills());
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            jsonObject.setValue(json);
            preparedStatement.setObject(10, jsonObject);
            preparedStatement.setString(11, String.valueOf(card.getPokemonType()));
            preparedStatement.setString(12, String.valueOf(card.getRegulationMark()));
            preparedStatement.setString(13, card.getNumber());
            preparedStatement.setObject(14, UUID.randomUUID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createPokemonOwner(Student owner) {
        // Реализовать добавление студента - владельца карты в БД
        String insertStudentSQL = "INSERT INTO student (\"familyName\", \"firstName\", \"patronicName\", \"group\", \"id\") VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStudentSQL)) {
            preparedStatement.setString(1, owner.getSurName());
            preparedStatement.setString(2, owner.getFirstName());
            preparedStatement.setString(3, owner.getFamilyName());
            preparedStatement.setString(4, owner.getGroup());
            preparedStatement.setObject(5, UUID.randomUUID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Object getUUID(String cardNumber) throws SQLException {
        String selectStudentSQL = "SELECT * FROM card WHERE card_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStudentSQL)) {
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getObject("id");
            }
        }
        return null;
    }

    public Object getStudentUUID(Student student) throws SQLException {
        String selectStudentSQL = "SELECT * FROM student WHERE \"familyName\" = ? AND \"firstName\" = ? AND \"patronicName\" = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStudentSQL)) {
            preparedStatement.setString(1, student.getSurName());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getFamilyName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getObject("id");
            }
        }
        return null;
    }
}
