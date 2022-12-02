package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

// Represents EditProfileController class implements Initializable
public class EditProfileController implements Initializable {
	
	//Data fields
    public TextField tfName;
    public TextField tfFatherName;
    public TextField tfEmail;
    public TextField tfCourse;
    public DatePicker dpDob;
    public ImageView ivBack;

    Connection con = null;
    Statement stnt = null;
    
    // Implemented method ivBackAction to close window.
    public void ivBackAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
    }
    
    // SaveAction() method used to save if any changes are made to user profile.
    public void SaveAction(ActionEvent actionEvent) {

        Alert a = new Alert(Alert.AlertType.ERROR);
        if(tfName.getText().length()==0 || tfFatherName.getText().length()==0|| dpDob.getValue()==null || tfEmail.getText().length()==0 || tfCourse.getText().length()==0 ){
            
        	// alert to notify student if any fields are not filled
            a.setContentText("All fields are not filled");
            a.show();
            return;
        }

        java.util.Date date = java.util.Date.from(dpDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try {
            Connection con = null;
            Statement stnt = null;
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "UPDATE student SET name ='"+tfName.getText()+"', fathername = '"+tfFatherName.getText()+"', emailaddress = '"+tfEmail.getText()+"', course = '"+tfCourse.getText()+"', dob='"+sqlDate+"'  WHERE id ="+HelloApplication.studID;
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();

            stnt.close();
            con.close();
            
            // Alert to notify student on updating data Successfully.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Data Updated Successfully!....");
            alert.show();


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /* Override the initialize() method defined in the studentController class*/
	// initialize() method used to show specific student data in the text fields.   
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "SELECT * FROM student";
            ResultSet rs = stnt.executeQuery(query);

            while (rs.next()){
                if(rs.getInt("id") == HelloApplication.studID){
                    tfName.setText(rs.getString("name"));
                    tfFatherName.setText(rs.getString("fathername"));
                    dpDob.setValue(LocalDate.from(rs.getTimestamp("dob").toLocalDateTime()));
                    tfEmail.setText(rs.getString("emailaddress"));
                    tfCourse.setText(rs.getString("course"));
                }
            }
            stnt.close();
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
