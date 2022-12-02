package com.example.my_lms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
//Represents class StudentLoginController
public class StudentLoginController {
    @FXML
    public Button btnLogin;
    public PasswordField pfPass;
    public TextField tfStudentId;
    public Button btnSignUp;
    @FXML
    private ImageView ivBack;
    
    // Set Connection, Statement to null 
    Connection con = null;
    Statement stnt = null;
    
    // Implemented Login Method which helps student to login to the system
    public void LOGIN(ActionEvent actionEvent) {

        if(tfStudentId.getText().length()==0 || pfPass.getText().length()==0 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            // Alert shown to user if any login fields are blank
            alert.setContentText("All fields are not filled!...");
            alert.show();
            return;
        }
        HelloApplication.studID=Integer.parseInt(tfStudentId.getText());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String sql = "SELECT id, password " +" FROM student";
            ResultSet rs = stnt.executeQuery(sql);
            boolean present = false;
            while(rs.next()) {
                if (rs.getInt("id")==Integer.parseInt(tfStudentId.getText()) && (rs.getString("password").equals(pfPass.getText()))) {
                    present = true;
                    break;
                }
            }
            if(present){

                tfStudentId.setText("");
                pfPass.setText("");

                HelloApplication.SceneSwitch( "Student.fxml", "Student",(Stage)btnLogin.getScene().getWindow() );

            }
            else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Incorrect Name or Password");
                a.show();
            }
            stnt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Implemented SignUp method which shows signup page on click of signup button
    public void SignUp(ActionEvent actionEvent) throws IOException {
        HelloApplication.SceneSwitch( "StudentSignUp.fxml", "SignUp", (Stage)btnSignUp.getScene().getWindow() );
    }
    
    // Implemented Reset method to reset fields to blank
    public void Reset(ActionEvent actionEvent) {
        tfStudentId.setText("");
        pfPass.setText("");
    }
    
    // Implemented ivBackAction method to switch to login page on a click
    public void ivBackAction(MouseEvent mouseEvent) throws IOException {

        HelloApplication.SceneSwitch( "login.fxml", "Library Management System",(Stage)ivBack.getScene().getWindow() );
    }
}
