package ru.mirea.ulemdzhievob;

public class AttackSkill {
    protected String name;
    protected String description;
    protected String cost;
    protected int damage;

    public AttackSkill(String name, String description, String cost, int damage) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    public String toString() {
        return name + "/" + description + "/" + cost + "/" + damage;
    }
}
