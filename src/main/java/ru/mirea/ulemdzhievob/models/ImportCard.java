    package ru.mirea.ulemdzhievob.models;
    import ru.mirea.ulemdzhievob.web.http.PkmnHttpClient;
    import com.fasterxml.jackson.databind.JsonNode;
    import java.io.BufferedReader;
    import java.io.FileReader;
    import java.io.IOException;
    import java.io.FileInputStream;
    import java.io.ObjectInputStream;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.UUID;


    public class ImportCard {
        public Card loadCardFromFile(String fileName) {
            Card card = null;
            try (FileInputStream fileIn = new FileInputStream(fileName);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                card = (Card) in.readObject();
                System.out.println("Card loaded from " + fileName);
            } catch (IOException e) {
                System.err.println("Error reading card from file: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println("Card class not found: " + e.getMessage());
            }
            return card;
        }

        private static Card loadEvolvesFromCard(String evolvesFromName) {

            if (evolvesFromName.equals("-")) {
                return null;
            }

            String filePath = "src\\main\\resources\\" + evolvesFromName + ".txt";
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
                    String numberLine = br.readLine();


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
                    String number = numberLine.trim();


                    String[] studentData = studentLine.split("/");
                    UUID studentId = UUID.randomUUID();
                    Student pokemonOwner = new Student(
                            studentId, 
                            studentData[0].trim(), 
                            studentData[1].trim(), 
                            studentData[2].trim(), 
                            studentData[3].trim()  
                    );
                    PkmnHttpClient pkmnHttpClient = new PkmnHttpClient();
                    JsonNode cardData = pkmnHttpClient.getPokemonCard(name, number);
                    JsonNode attacksArray = cardData.path("data").get(0).path("attacks");
                    int i = 0;
                    for (AttackSkill attackSkill : skills) {
                        if (i < attacksArray.size()) {
                            String apiDescription = attacksArray.get(i).path("text").asText(); 
                            String apiName = attacksArray.get(i).path("name").asText(); 
                            attackSkill.setDescription(apiDescription); 
                            attackSkill.setName(apiName); 
                        }
                        i++;
                    }

                    return new Card(stage, name, hp, energyType, evolvesFrom, skills, weaknessType, resistanceType, retreatCost, gameset, regulationMark, pokemonOwner, number);
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
                    String name = attributes[1].trim();
                    String description = attributes[2].trim();
                    String cost = attributes[0].trim();
                    int damage = Integer.parseInt(attributes[3].trim());


                    skills.add(new AttackSkill( description, name, cost   , damage));
                }
            }
            return skills;
        }

    }
