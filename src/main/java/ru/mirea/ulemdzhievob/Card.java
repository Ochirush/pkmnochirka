package ru.mirea.ulemdzhievob;
import java.util.List;


public class Card {
    protected PokemonStage Stage;
    protected String Name;
    protected int Hp;
    protected EnergyType PokemonType;
    protected Card evolvesFrom;
    protected List<AttackSkill> Skills;
    protected EnergyType weaknessType;
    protected EnergyType resistanceType;
    protected String retreatCost;
    protected String gameset;
    protected char regulationMark;
    protected Student pokemonOwner;
    public Card(PokemonStage Stage, String Name, int Hp, EnergyType PokemonType, Card evolvesFrom, List<AttackSkill> Skills,
                EnergyType weaknessType, EnergyType resistanceType, String retreatCost, String gameset, char regulationMark,
                Student pokemonOwner) {
        this.Stage = Stage;
        this.Name = Name;
        this.Hp = Hp;
        this.PokemonType = PokemonType;
        this.evolvesFrom = evolvesFrom;
        this.Skills = Skills;
        this.weaknessType = weaknessType;
        this.resistanceType = resistanceType;
        this.retreatCost = retreatCost;
        this.gameset = gameset;
        this.regulationMark = regulationMark;
        this.pokemonOwner = pokemonOwner;
    }
    public PokemonStage getStage() {
        return Stage;
    }

    public void setStage(PokemonStage Stage) {
        this.Stage = Stage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int Hp) {
        this.Hp = Hp;
    }

    public EnergyType getPokemonType() {
        return PokemonType;
    }

    public void setPokemonType(EnergyType PokemonType) {
        this.PokemonType = PokemonType;
    }

    public Card getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(Card evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public List<AttackSkill> getSkills() {
        return Skills;
    }

    public void setSkills(List<AttackSkill> Skills) {
        this.Skills = Skills;
    }

    public EnergyType getWeaknessType() {
        return weaknessType;
    }

    public void setWeaknessType(EnergyType weaknessType) {
        this.weaknessType = weaknessType;
    }

    public EnergyType getResistanceType() {
        return resistanceType;
    }

    public void setResistanceType(EnergyType resistanceType) {
        this.resistanceType = resistanceType;
    }

    public String getRetreatCost() {
        return retreatCost;
    }

    public void setRetreatCost(String retreatCost) {
        this.retreatCost = retreatCost;
    }

    public String getGameset() {
        return gameset;
    }

    public void setGameset(String gameset) {
        this.gameset = gameset;
    }

    public char getRegulationMark() {
        return regulationMark;
    }

    public void setRegulationMark(char regulationMark) {
        this.regulationMark = regulationMark;
    }

    public Student getPokemonOwner() {
        return pokemonOwner;
    }

    public void setPokemonOwner(Student pokemonOwner) {
        this.pokemonOwner = pokemonOwner;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Имя: ").append(Name).append("\n");
        sb.append("Этап: ").append(Stage).append("\n");
        sb.append("HP: ").append(Hp).append("\n");
        sb.append("Тип покемона: ").append(PokemonType).append("\n");
        sb.append("Эволюция из: ").append(evolvesFrom != null ? evolvesFrom.getName() : "нет").append("\n");
        sb.append("Навыки: ").append(Skills != null ? Skills.toString() : "нет").append("\n");
        sb.append("Слабость к: ").append(weaknessType).append("\n");
        sb.append("Сопротивляемость к: ").append(resistanceType).append("\n");
        sb.append("Стоимость отступления: ").append(retreatCost).append("\n");
        sb.append("Игровая серия: ").append(gameset).append("\n");
        sb.append("Регуляционный знак: ").append(regulationMark).append("\n");
        sb.append("Владелец покемона: ").append(pokemonOwner.getFullName()).append("\n");
        return sb.toString();

    }
    public String getEvolvesFromName() {
        return evolvesFrom != null ? evolvesFrom.getName() : "Нет эволюции"; // Возвращает имя или сообщение
    }

}
