package ru.mirea.ulemdzhievob;

import java.util.List;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    protected PokemonStage stage;
    protected String name;
    protected int hp;
    protected EnergyType pokemonType;
    protected Card evolvesFrom;
    protected List<AttackSkill> skills;
    protected EnergyType weaknessType;
    protected EnergyType resistanceType;
    protected String retreatCost;
    protected String gameset;
    protected char regulationMark;
    protected Student pokemonOwner;
    protected String number;

    public Card(PokemonStage stage, String name, int hp, EnergyType pokemonType, Card evolvesFrom, List<AttackSkill> skills,
                EnergyType weaknessType, EnergyType resistanceType, String retreatCost, String gameset, char regulationMark,
                Student pokemonOwner,String number ) {
        this.stage = stage;
        this.name = name;
        this.hp = hp;
        this.pokemonType = pokemonType;
        this.evolvesFrom = evolvesFrom;
        this.skills = skills;
        this.weaknessType = weaknessType;
        this.resistanceType = resistanceType;
        this.retreatCost = retreatCost;
        this.gameset = gameset;
        this.regulationMark = regulationMark;
        this.pokemonOwner = pokemonOwner;
        this.number = number;
    }


    public PokemonStage getStage() {
        return stage;
    }

    public void setStage(PokemonStage stage) {
        this.stage = stage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public EnergyType getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(EnergyType pokemonType) {
        this.pokemonType = pokemonType;
    }

    public Card getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(Card evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public List<AttackSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<AttackSkill> skills) {
        this.skills = skills;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) { // Метод для установки номера карты
        this.number = number;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Имя: ").append(name).append("\n");
        sb.append("Этап: ").append(stage).append("\n");
        sb.append("HP: ").append(hp).append("\n");
        sb.append("Тип покемона: ").append(pokemonType).append("\n");
        sb.append("Номер карты: ").append(number).append("\n");
        sb.append("Эволюция из: ").append(evolvesFrom != null ? evolvesFrom.getName() : "нет").append("\n");
        sb.append("Навыки: ").append(skills != null ? skills.toString() : "нет").append("\n");
        sb.append("Слабость к: ").append(weaknessType).append("\n");
        sb.append("Сопротивляемость к: ").append(resistanceType).append("\n");
        sb.append("Стоимость отступления: ").append(retreatCost).append("\n");
        sb.append("Игровая серия: ").append(gameset).append("\n");
        sb.append("Регуляционный знак: ").append(regulationMark).append("\n");
        sb.append("Владелец покемона: ").append(pokemonOwner.getFullName()).append("\n");
        return sb.toString();
    }

    public String getEvolvesFromName() {
        return evolvesFrom != null ? evolvesFrom.getName() : "Нет эволюции";
    }
}