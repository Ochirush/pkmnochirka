package ru.mirea.ulemdzhievob;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.stream.Collectors;

import ru.mirea.ulemdzhievob.web.http.ExportCard;
import ru.mirea.ulemdzhievob.web.http.PokemonTcgAPI;
import ru.mirea.ulemdzhievob.web.http.PkmnHttpClient;
import ru.mirea.ulemdzhievob.Card;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.mirea.ulemdzhievob.web.http.DatabaseServiceImpl;
import ru.mirea.ulemdzhievob.AttackSkill;

public class Main {
    public static void main(String[] args) {
        String filePath = "src\\main\\resources\\my_card.txt";
        try {

            Card card = ImportCard.loadCard(filePath);


            if (card != null) {

                PkmnHttpClient pkmnHttpClient = new PkmnHttpClient();
                JsonNode apiCard = pkmnHttpClient.getPokemonCard(card.getName(), card.getNumber());

                System.out.println(apiCard.toPrettyString());
                System.out.println(apiCard.findValues("attacks")
                        .stream()
                        .map(JsonNode::toPrettyString)
                        .collect(Collectors.toSet()));


                printCardInfo(card);
                updateSkills(card, apiCard);
                ExportCard exportCard = new ExportCard();
                exportCard.saveCardToFile(card);
                DatabaseServiceImpl databaseService = new DatabaseServiceImpl();
                databaseService.saveCardToDatabase(card);
            } else {
                System.out.println("Не удалось загрузить карту");
            }
        } catch (IOException e) {
            System.err.println("Ошибка загрузки покемона: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static void printCardInfo(Card card) {
        System.out.println("Имя покемона: " + card.getName());
        System.out.println("Здоровье покемона: " + card.getHp());
        System.out.println("Этап: " + card.getStage());
        System.out.println("Тип энергии: " + card.getPokemonType());
        System.out.println("Слабость: " + card.getWeaknessType());
        System.out.println("Сопротивляемость: " + card.getResistanceType());
        System.out.println("Владелец: " + card.getPokemonOwner().getFirstName() + "/" + card.getPokemonOwner().getSurName() + "/" + card.getPokemonOwner().getGroup());
        System.out.println("Способность: ");
        for (AttackSkill skill : card.getSkills()) {
            System.out.println("" + skill);
        }
        System.out.println("Цена побега: " + card.getRetreatCost());
        System.out.println("Набор: " + card.getGameset());
        System.out.println("Марка: " + card.getRegulationMark());
        System.out.println("Эволюционирует из: " + card.getEvolvesFromName());
        System.out.println(card.getEvolvesFrom());
    }


    private static void updateSkills(Card card, JsonNode apiCard) {
        if (apiCard.has("data") && apiCard.get("data").isArray()) {
            for (JsonNode crd : apiCard.get("data")) {
                if (crd.has("attacks") && crd.get("attacks").isArray()) {
                    for (JsonNode attack : crd.get("attacks")) {
                        String newDescription = attack.get("text").asText();
                        String targetName = attack.get("name").asText();
                        for (AttackSkill skill : card.getSkills()) {
                            if (skill.getName().equals(targetName)) {
                                skill.setDescription(newDescription);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}