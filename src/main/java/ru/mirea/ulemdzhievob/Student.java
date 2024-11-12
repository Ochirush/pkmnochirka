package ru.mirea.ulemdzhievob;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Версия сериализации

    private UUID id;
    private String firstName;
    private String surName;
    private String familyName;
    private String group;

    // Конструктор с id
    public Student(UUID id, String firstName, String surName, String familyName, String group) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.familyName = familyName;
        this.group = group;
    }

    // Конструктор без id
    public Student(String firstName, String surName, String familyName, String group) {

        this.firstName = firstName;
        this.surName = surName;
        this.familyName = familyName;
        this.group = group;
    }

    // Пустой конструктор
    public Student() {

    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


    public String getFullName() {
        return surName + "/" + firstName + "/" + familyName + "/" + group;
    }
}