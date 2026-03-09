package com.yourcompany.simpleorm.simple_orm_framework;

import com.yourcompany.simpleorm.annotation.Column;
import com.yourcompany.simpleorm.annotation.Entity;
import com.yourcompany.simpleorm.annotation.Id;
import com.yourcompany.simpleorm.annotation.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "roll_number")
    private Integer rollNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "course")
    private String course;

    public Student(){}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Integer getRollNumber() { return rollNumber; }

    public void setRollNumber(Integer rollNumber) { this.rollNumber = rollNumber; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public String getCourse() { return course; }

    public void setCourse(String course) { this.course = course; }
}