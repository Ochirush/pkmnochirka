package ru.mirea.ulemdzhievob;

public class Student {
    protected String firstname;
    protected String surname;
    protected String familyname;
    protected String group;

    public Student(String firstname, String surname, String familyname, String group) {
        this.firstname = firstname;
        this.surname = surname;
        this.familyname = familyname;
        this.group = group;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getFamilyname() {
        return familyname;
    }

    public String getGroup() {
        return group;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    public String getFullName() {
        return surname + "/" + firstname + "/" + familyname+ "/" + group;
    }
}

