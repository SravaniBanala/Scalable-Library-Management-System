package com.example.my_lms;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
/* Represents class StudentSignUpController*/
public class StudentPasswordController {
    @FXML
    public ImageView ivBack;
    public PasswordField pfNewPassword;
    public PasswordField pfConfirmPassword;
    
    // Set Connection, Statement to null 
    Connection con = null;
    Statement stnt = null;
    
    // Implemented ChangeAction method to successfully change password of the user 
    public void ChangeAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(pfNewPassword.getText().length()==0 || pfConfirmPassword.getText().length()==0){
            
        	// alert shown to user if fields are blank
            alert.setContentText("All fields are not filled");
            alert.show();
            return;
        }
        if(pfNewPassword.getText().equals(pfConfirmPassword.getText())) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
                stnt = con.createStatement();

                String query = "UPDATE student SET password ='" + pfNewPassword.getText() + "' where id =   " + HelloApplication.studID;
                PreparedStatement ps = con.prepareStatement(query);
                ps.executeUpdate();

                stnt.close();
                con.close();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                
                // Alert shown to user on successfull password change
                a.setContentText("Password Changed Successfully!...");
                a.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
        	
            // Alert shown to user in case of password mismatch 
            alert.setContentText("Password in both fields are not same");
            alert.show();
        }


    }
    
    //  Implemented Back method to navigate to back window on MouseEvent
    public void Back(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();


    }
}
