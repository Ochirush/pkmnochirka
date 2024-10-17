package ru.mirea.ulemdzhievob;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "src\\main\\resources\\my_card.txt"; // Укажите здесь имя файла покемона

        try {

            Card card = ImportCard.loadCard(filePath);


            if (card != null) {

                System.out.println("Имя покемона: " + card.getName());
                System.out.println("Здоровье покемона: " + card.getHp());
                System.out.println("Этап: " + card.getStage());
                System.out.println("Тип энергии: " + card.getPokemonType());
                System.out.println("Слабость: " + card.getWeaknessType());
                System.out.println("Сопротивляемость: " + card.getResistanceType());
                System.out.println("Владелец: " + card.getPokemonOwner().getFirstname() + "/" + card.getPokemonOwner().getSurname()+"/"+card.getPokemonOwner().getGroup());
                System.out.println("Способность: ");
                for (AttackSkill skill : card.getSkills()) {
                    System.out.println("" + skill);
                }
                System.out.println("Цена побега: "+card.getRetreatCost() );
                System.out.println("Набор: "+card.getGameset() );
                System.out.println("Марка: "+card.getRegulationMark() );
                System.out.println("Эволюционирует из: "+card.getEvolvesFromName() );
                System.out.println("-------------------");
                System.out.println("Имя покемона: " + card.evolvesFrom.getName());
                System.out.println("Здоровье покемона: " + card.evolvesFrom.getHp());
                System.out.println("Этап: " + card.evolvesFrom.getStage());
                System.out.println("Тип энергии: " + card.evolvesFrom.getPokemonType());
                System.out.println("Слабость: " + card.evolvesFrom.getWeaknessType());
                System.out.println("Сопротивляемость: " + card.evolvesFrom.getResistanceType());
                System.out.println("Владелец: " + card.evolvesFrom.getPokemonOwner().getFirstname() + "/" + card.evolvesFrom.getPokemonOwner().getSurname()+"/"+card.evolvesFrom.getPokemonOwner().getGroup());
                System.out.println("Способность: ");
                for (AttackSkill skill : card.evolvesFrom.getSkills()) {
                    System.out.println("" + skill);
                }
                System.out.println("Цена побега: "+card.evolvesFrom.getRetreatCost() );
                System.out.println("Набор: "+card.evolvesFrom.getGameset() );
                System.out.println("Марка: "+card.evolvesFrom.getRegulationMark() );
                System.out.println("Эволюционирует из: "+card.evolvesFrom.getEvolvesFromName() );

            }

            else {
                System.out.println("не удалось загрузить карту");
            }
        }
        catch (IOException e) {
            System.err.println("ошибка загрузки покемона: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }


}

