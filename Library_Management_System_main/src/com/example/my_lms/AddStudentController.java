package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

//Represents AddStudentController class implements Initializable
public class AddStudentController implements Initializable {
    public Label lblName;
    public Label lblFatherName;
    public Label lblEmail;

    public Label lblCourse;
    public Label lblDob;
    public Label lblMessage;

    public ImageView ivBack;

    private String studPassword = null;
    private int count, id;
    private java.sql.Date sqlDate;

    Connection con = null;
    Statement stnt = null;


    //CancelAction() method used to cancel student add request.
    //Action Event when admin clicks on Cancel button.
    public void CancelAction(ActionEvent actionEvent) throws SQLException {

        if(count == 0){
        	
        	//Alert message when admin clicks on Cancel when no student available to add or cancel
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Action not Performed because there is no Student data to add or cancel");
            a.show();
            return;
        }
        remove();
        
      //Alert message displays student data is cancelled Successfully.
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Student Data Canceled Sucessfully!...");
        a.show();

        displayData();
    }

    //AddAction() method used to add student to the database.
    //Action Event when admin clicks on Add button.
    public void AddAction(ActionEvent actionEvent) throws SQLException {
        if(count == 0){
        	
        	//Alert message when admin clicks on Cancel when no student available to add
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Action not Performed because there is no Student data to add");
            a.show();
            return;
        }
        try {
            // Connection to database
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            // Insert student data
            String sql = "INSERT INTO student(name, fathername, dob, emailaddress, course, password)" + "Values('"+lblName.getText()+"', '"+lblFatherName.getText() +"', '"+sqlDate+"', '"+ lblEmail.getText()+"', '"+lblCourse.getText()+"', '"+studPassword+"')";
            stnt.execute(sql);


            stnt.close();
            con.close();
            
            //Alert message displays student data is added Successfully.
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Student Data Added Sucessfully!...");
            a.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        remove();
        displayData();

    }
    
    /* Override the initialize() method defined in the studentController class*/
   	// initialize() method used to call display data method
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        displayData();
    }

    //display add students request by accessing it from the database
    public void displayData(){
        try {
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "select count(*) from addstudent";
            ResultSet rs = stnt.executeQuery(query);
            rs.next();
            count = rs.getInt(1);
            if(count == 0){
                lblMessage.setText("There is no student data to add");
                lblName.setText("");
                lblFatherName.setText("");
                lblDob.setText("");
                lblEmail.setText("");
                lblCourse.setText("");
            }
            else{
                query = "SELECT * FROM addstudent";
                rs = stnt.executeQuery(query);
                rs.next();
                lblName.setText(rs.getString("name"));
                lblFatherName.setText(rs.getString("fathername"));
                lblDob.setText(String.valueOf(rs.getDate("dob")));
                sqlDate = rs.getDate("dob");
                lblEmail.setText(rs.getString("emailaddress"));
                lblCourse.setText(rs.getString("course"));
                studPassword = rs.getString("password");
                id = rs.getInt("id");
            }
            stnt.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //remove() method used to remove the request from the database
    public void remove() throws SQLException {
        con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
        stnt = con.createStatement();

        String query = "Delete from addstudent where id = "+ id + " ";
        stnt.execute(query);

        stnt.close();
        con.close();

    }
    
    //Implemented method ivBackAction to switch to Admin page.
    public void ivBackAction(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Admin.fxml","Admin", stage );
    }
}
