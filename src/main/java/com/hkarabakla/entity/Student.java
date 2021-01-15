package com.hkarabakla.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 100, unique = false)
    private String name;

    private LocalDate birthDate;

    @Transient
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
    private Set<Lesson> registeredLessons;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", age=" + age +
                ", gender=" + gender +
                ", registeredLessons=" + registeredLessons +
                ", deleted=" + deleted +
                '}';
    }

    private boolean deleted = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Lesson> getRegisteredLessons() {
        return registeredLessons;
    }

    public void setRegisteredLessons(Set<Lesson> registeredLessons) {
        this.registeredLessons = registeredLessons;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}