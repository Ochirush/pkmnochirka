package ru.mirea.ulemdzhievob;

import javax.sound.sampled.Port;

public enum PokemonStage {
    BASIC("Базовый"),
    STAGE1("Стадия 1"),
    STAGE2("Стадия 2"),
    VSTAR("Звездная стадия"),
    a012345678912("da"),
    VMAX("Максимальная стадия");

    protected final String explanation;
    PokemonStage(String explanation) {
        this.explanation = explanation;
    }
    public String getExplanation() {
        return explanation;
    }


}
