package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.sql.*;

//Represents AdminLoginController class
public class AdminLoginController {


    public PasswordField pfPass;
    public TextField tfAdminName;
    public Button btnLogin;
    public ImageView ivBack;

    Connection con = null;
    Statement stnt = null;

    //Reset() action used to set text of admin name and password
    public void Reset(ActionEvent actionEvent) {

        pfPass.setText("");
        tfAdminName.setText("");

    }
    
    //LOGIN() method used by admin user for  login
    public void LOGIN(ActionEvent actionEvent) {

        Alert a = new Alert(Alert.AlertType.ERROR);


        if(tfAdminName.getText().length()==0 || pfPass.getText().length()==0)
        {
        	
            //alert to notify user if any field is blank
            a.setContentText("Please fill all fields");
            a.show();
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String sql = "SELECT *  FROM admin";
            ResultSet rs = stnt.executeQuery(sql);
            boolean present = false;
            while(rs.next()) {
                if (rs.getString("username").equalsIgnoreCase(tfAdminName.getText()) && (rs.getString("password").equalsIgnoreCase(pfPass.getText()))) {
                    present = true;
                    break;
                }
            }
            if(present){
                HelloApplication.SceneSwitch( "Admin.fxml", "Admin",(Stage)btnLogin.getScene().getWindow() );

            }
            else {
            	
            	// alert to notify user if login details are mismatched
                a.setContentText("Incorrect Name or Password");
                a.show();
            }
            stnt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    
    // Implemented method ivBackAction to switch to login page.
    public void ivBack(MouseEvent mouseEvent) throws IOException {
        HelloApplication.SceneSwitch( "login.fxml", "Library Management System",(Stage)ivBack.getScene().getWindow() );

    }
}
