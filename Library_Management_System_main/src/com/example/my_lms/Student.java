package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.util.Date;
//Represents class Student
public class Student {

    int id;
    String name, fatherName, email, course, password;
    public Date dateOfBirth;

    // Implemented Student constructor which initiates below fields of student class.
    public Student(int id, String name, String fatherName,Date dateOfBirth, String email, String course) {
        this.id = id;
        this.name = name;
        this.fatherName = fatherName;
        this.email = email;
        this.course = course;
        //this.password = password;
        this.dateOfBirth = dateOfBirth;
    }
    
    // getName method to return name of student
    public String getName() {
        return name;
    }
    
    // getFatherName method to return Father name of student
    public String getFatherName() {
        return fatherName;
    }
    
    //getEmail method to return email of Student
    public String getEmail() {
        return email;
    }
    
    //getCourse method to return courses of Student
    public String getCourse() {
        return course;
    }
    
    //getDateofBirth method to return Student Date of Birth
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    //getPassword method to return Student Password
    public String getPassword() {
        return password;
    }
    
    // getId method to return Student Id
    public int getId() {
        return id;
    }

}
