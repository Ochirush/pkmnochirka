package ru.mirea.ulemdzhievob;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ImportCard {

    private static Card loadEvolvesFromCard(String evolvesFromName) {

        if (evolvesFromName.equals("-")) {
            return null;
        }

        String filePath = "C:\\Users\\Ochir\\IdeaProjects\\pkmn\\src\\main\\resources\\" + evolvesFromName + ".txt";
        try {
            return loadCard(filePath);
        } catch (IOException e) {

            System.out.println("Error loading evolves from card: " + e.getMessage());
            return null;
        }
    }

        public static Card loadCard(String filePath) throws IOException {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String stageLine = br.readLine();
                String nameLine = br.readLine();
                String hpLine = br.readLine();
                String typeLine = br.readLine();
                String evolvesFromLine = br.readLine();
                String skillsLine = br.readLine();
                String weaknessLine = br.readLine();
                String resistanceLine = br.readLine();
                String retreatCostLine = br.readLine();
                String gamesetLine = br.readLine();
                String regulationMarkLine = br.readLine();
                String studentLine = br.readLine();


                PokemonStage stage = PokemonStage.valueOf(stageLine.trim().toUpperCase());
                String name = nameLine.trim();
                int hp = Integer.parseInt(hpLine.trim());
                EnergyType energyType = EnergyType.valueOf(typeLine.trim().toUpperCase());
                Card evolvesFrom = loadEvolvesFromCard(evolvesFromLine.trim());

                List<AttackSkill> skills = parseSkills(skillsLine);
                EnergyType weaknessType = weaknessLine.trim().equals("-") ? null : EnergyType.valueOf(weaknessLine.trim().toUpperCase());
                EnergyType resistanceType = resistanceLine.trim().equals("-") ? null : EnergyType.valueOf(resistanceLine.trim().toUpperCase());
                String retreatCost = retreatCostLine.trim();
                String gameset = gamesetLine.trim();
                char regulationMark = regulationMarkLine.trim().charAt(0);


                String[] studentData = studentLine.split("/");
                Student pokemonOwner = new Student(
                        studentData[0].trim(),
                        studentData[1].trim(),
                        studentData[2].trim(),
                        studentData[3].trim()
                );

                return new Card(stage, name, hp, energyType, evolvesFrom, skills, weaknessType, resistanceType, retreatCost, gameset, regulationMark, pokemonOwner);
            } catch (Exception e) {

                System.out.println("Error loading card data: " + e.getMessage());
                throw e;
            }
    }

    private static List<AttackSkill> parseSkills(String skillsLine) {
        List<AttackSkill> skills = new ArrayList<>();
        String[] skillsData = skillsLine.split(",");
        for (String skillData : skillsData) {
            String[] attributes = skillData.split("/");
            if (attributes.length == 4) {
                String name = attributes[0].trim();
                String description = attributes[1].trim();
                String cost = attributes[2].trim();
                int damage = Integer.parseInt(attributes[3].trim());

                skills.add(new AttackSkill(name, description, cost, damage));
            }
        }
        return skills;
    }
}
