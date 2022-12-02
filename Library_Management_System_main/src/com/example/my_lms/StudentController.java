package com.example.my_lms;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


import javafx.scene.input.MouseEvent;
//Represents class StudentController which implements method Initializable
public class StudentController implements Initializable {
    @FXML
    public MenuItem mbShowProfile;
    public MenuItem mbEditProfile;
    public MenuItem mbChangePass;
    public MenuItem mbLogout;
    public ImageView ivReturnBook;
    public ImageView ivIssueBook;
    public Label lblStudentName;
    public Button btnLogOut;

    Connection con = null;
    Statement stnt = null;
    
    // Implemented method initialize  to retrive student id and name.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "SELECT name, id FROM student";
            ResultSet rs = stnt.executeQuery(query);
            while (rs.next()) {
                if (HelloApplication.studID == rs.getInt("id")) {
                    lblStudentName.setText(rs.getString("name"));
                    break;
                }
            }
        } catch (ClassNotFoundException | SQLException e ) {
            throw new RuntimeException(e);
        }
    }
    
    // Implemented ShowProfileAction method to view student profile 
    public void ShowProfileAction(Event actionEvent) throws IOException {

        HelloApplication.SceneSwitch( "Profile.fxml", "Student Profile",new Stage() );
    }
    
   //Implemented EditProfileAction method to 	Edit student profile 
    public void EditProfileAction(Event actionEvent) throws IOException {
        HelloApplication.SceneSwitch( "EditProfile.fxml", "Edit Student Profile",new Stage() );
    }
    
    //Implemented ChangePasswordAction method to change password 
    public void ChangePasswordAction(Event actionEvent) throws IOException {
        HelloApplication.SceneSwitch( "StudentPassword.fxml", "Student Password",new Stage() );
   } 
    
    //Implemented LogOutAction method to help student logout
    public void LogOutAction(Event actionEvent) throws IOException {
        Stage stage = (Stage)btnLogOut.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "StudentLogin.fxml", "Student Login",stage );
    }
    
    //Implemented IssueBook method to help student to issue book
    public void IssueBook(MouseEvent mouseEvent) throws IOException {
        HelloApplication.SceneSwitch( "IssueBook.fxml", "Issue Book",(Stage)ivIssueBook.getScene().getWindow() );
    }
    
    //Implemented ReturnBook method to help student to Return book
    public void ReturnBook(MouseEvent mouseEvent) throws IOException {
        HelloApplication.SceneSwitch( "ReturnBook.fxml", "Return Book",(Stage)ivReturnBook.getScene().getWindow() );
    }
}
