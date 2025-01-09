package ru.mirea.ulemdzhievob.models;

public enum EnergyType {
    FIRE("Огонь"),
    GRASS("Зелень"),
    WATER("Вода"),
    LIGHTNING("Электричество"),
    PSYCHIC("Психовоздействие"),
    FIGHTING("Бой"),
    DARKNESS("Тьма"),
    METAL("Металл"),
    FAIRY("Сказочность"),
    DRAGON("Дракон"),
    COLORLESS("Бесцветный");
    protected final String explanation;


     EnergyType(String explanation ) {
        this.explanation = explanation;
    }
    public String getExplanation() {
        return explanation;
    }
}
