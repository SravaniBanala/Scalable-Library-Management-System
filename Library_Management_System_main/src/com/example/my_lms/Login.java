package com.example.my_lms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
// Represents Login class for both student and Admin
public class Login {


    public Button btnAdmin;
    public Button btnStudent;
    
    // StudentLoginAction() method for student login
    public void StudentLoginAction(ActionEvent actionEvent) throws IOException {
        HelloApplication.SceneSwitch( "Studentlogin.fxml", "Student Login",(Stage)btnStudent.getScene().getWindow() );
    }
    
    // StudentLoginAction() method for Admin user login
    public void AdminLoginAction(ActionEvent actionEvent) throws IOException {
        HelloApplication.SceneSwitch( "Adminlogin.fxml", "Admin Login",(Stage)btnAdmin.getScene().getWindow() );

    }

}
