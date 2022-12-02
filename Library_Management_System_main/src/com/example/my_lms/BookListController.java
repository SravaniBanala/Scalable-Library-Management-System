package com.example.my_lms;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//Represents BookListController class implements Initializable
public class BookListController implements Initializable {

//public Data fields
    public TableColumn<Book, Integer> tcBookId;
    public TableColumn<Book, String> tcBookName;
    public TableColumn<Book, String> tcAuthorName;
    public TableColumn<Book, Date> tcArrivalDate;
    public TableColumn<Book, String> tcType;
    public TableColumn<Book, String> tcStatus;
    public ImageView ivBack;
    public TextField tfSearch;
    public TextField tfAuthorName;
    public TextField tfBookName;
    public ToggleGroup BookType;
    public DatePicker dpDate;
    public RadioButton rbBook;
    public RadioButton rbFreshArrival;
    public RadioButton rbReferenceBook;
    public TableView<Book> tBookList;
    public ImageView ivRefresh;
    public ImageView ivExcelIcon;
    ObservableList<Book> list = FXCollections.observableArrayList();


    Connection con = null;
    Statement stnt = null;

    /* Override the initialize() method defined in the studentController class*/
	// initialize() method used to show call showBookTable() method .   
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBookTable();

    }

// showBookTable() method to display book list with book details, arrival date and book status    
    public void showBookTable(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String sql = "SELECT * " +" FROM book";
            ResultSet rs = stnt.executeQuery(sql);
            tBookList.getItems().clear();
            list.clear();
            while(rs.next()) {
                if(rs.getString("type").equalsIgnoreCase("Fresh Arrival")){
                    if(rs.getString("status").equalsIgnoreCase("Requested") || rs.getString("status").equalsIgnoreCase("Issued")){

                    }
                    else {
                        java.sql.Date date = rs.getDate("arrivaldate");
                        java.util.Date current = new Date();
                        java.sql.Date currentDate = new java.sql.Date(current.getTime());
                        long diff = currentDate.getTime() - date.getTime();

                        BigInteger month = new BigInteger("5259600000");

                        if (diff > month.longValue()) {
                            String query = "Update book SET status = 'Issueable' WHERE id = " + rs.getInt("id") + " ";
                            PreparedStatement preparedStatement = con.prepareStatement(query);
                            preparedStatement.executeUpdate();

                        }
                    }

                }


                list.add(new Book(rs.getInt("id"), rs.getString("name"), rs.getString("authorname"),  rs.getDate("arrivalDate"), rs.getString("type"), rs.getString("status")));

            }


            tBookList.setItems(list);
            tcBookId.setCellValueFactory(new PropertyValueFactory<>("ID"));
            tcBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            tcAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
            tcArrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
            tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            stnt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //SearchAction() method used to search books in the book list.
    public void SearchAction(ActionEvent actionEvent) {

        try {
            int id = Integer.parseInt(tfSearch.getText());
            boolean found = false;

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "SELECT * FROM book";
            ResultSet rs = stnt.executeQuery(query);

            while (rs.next()) {
                if (rs.getInt("id") == id) {
                    found = true;
                    tBookList.getItems().clear();
                    list.clear();
                    list.add(new Book(rs.getInt("id"), rs.getString("name"), rs.getString("authorname"), rs.getDate("arrivaldate"), rs.getString("type"), rs.getString("status")));



                  tcBookId.setCellValueFactory(new PropertyValueFactory<>("ID"));
                    tcBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
                    tcAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
                    tcArrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
                    tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
                    tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
                    tBookList.setItems(list);

                    stnt.close();
                    con.close();

                    break;
                }

            }
            if (!found) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("No such Book Record found");
                a.show();
            }
        }
        catch (NumberFormatException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Enter only integer values");
            a.show();
        }
        catch (Exception e){

            e.printStackTrace();
        }

    }
    
    // RefreshAction() method to refresh the book list
    public void RefreshAction(MouseEvent mouseEvent) {
        showBookTable();
        tfSearch.setText("");

    }
    
    // Implemented method ivBackAction to switch to Admin window.
    public void ivBack(MouseEvent mouseEvent) throws IOException {

        Stage stage = (Stage) ivBack.getScene().getWindow();
        stage.close();
        HelloApplication.SceneSwitch( "Admin.fxml", "Admin",(Stage)ivBack.getScene().getWindow() );
    }
    
    // implemented DeleteAction() method to delete books from the book list
    public void DeleteAction(ActionEvent actionEvent) {
        try {


            int id = tBookList.getSelectionModel().getSelectedItem().getID();

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "DELETE FROM book where ID = " + id + " ";
            stnt.execute(query);

            stnt.close();
            con.close();
            
            // To alert user once book is successfully deleted.
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Book Data deleted Successfully");
            a.show();


            showBookTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    // implemented UpdateAction() method to update book details from the book list
    public void UpdateAction(ActionEvent actionEvent) {
        int id = tBookList.getSelectionModel().getSelectedItem().getID();
        if(tfBookName.getText().length()==0 || tfAuthorName.getText().length()==0 || dpDate.getValue()==null || (!rbBook.isSelected() && !rbReferenceBook.isSelected() && !rbFreshArrival.isSelected())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill all the fields!...");
            a.show();
            return;
        }
        java.util.Date date = java.util.Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String type = "", status = "";
        if(rbBook.isSelected()){
            type = "Book";
            status = "Issueable";
        }
        else if (rbReferenceBook.isSelected()){
            type = "Reference Book";
            status = "Not Issueable";
        }
        else if (rbFreshArrival.isSelected()) {
            type ="Fresh Arrival";


            java.util.Date current = new Date();
            java.sql.Date currentDate = new java.sql.Date(current.getTime());
            long diff =  currentDate.getTime() - sqlDate.getTime();

            BigInteger month = new BigInteger("5259600000");

            if(diff>month.longValue())
                status = "Issueable";
            else
                status = "Issueable after 2 months of arrival";
        }
        try{

            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "UPDATE book SET name = '"+tfBookName.getText()+"', authorname = '" + tfAuthorName.getText()+"', arrivaldate = '"+sqlDate+"', type = '"+type+"', status = '"+status+"' WHERE id = "+id +" ";
            stnt.executeUpdate(query);

            showBookTable();


            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Data Updated Successfully");
            a.show();

            tfBookName.setText("");
            tfAuthorName.setText("");
            dpDate.setValue(null);
            rbBook.setSelected(false);
            rbReferenceBook.setSelected(false);
            rbFreshArrival.setSelected(false);


        }catch (Exception e){
            e.printStackTrace();
        }

    }
    
    // implemented AddBookAction() method to Add books to the book list
    public void AddBookAction(ActionEvent actionEvent) {

        if(tfBookName.getText().length()==0 || tfAuthorName.getText().length()==0 || dpDate.getValue()==null || (!rbBook.isSelected() && !rbReferenceBook.isSelected() && !rbFreshArrival.isSelected())){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please fill all the fields!...");
            a.show();
            return;
        }
        java.util.Date date = java.util.Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String type = "", status = "";
        if(rbBook.isSelected()){
            type = "Book";
            status = "Issueable";
        }
        else if (rbReferenceBook.isSelected()){
            type = "Reference Book";
            status = "Not Issueable";
        }
        else if (rbFreshArrival.isSelected()) {
            type ="Fresh Arrival";


            java.util.Date current = new Date();
            java.sql.Date currentDate = new java.sql.Date(current.getTime());
            long diff =  currentDate.getTime() - sqlDate.getTime();

            BigInteger month = new BigInteger("5259600000");

            if(diff>month.longValue())
                status = "Issueable";
            else
                status = "Issueable after 2 months of arrival";
        }

        try {

            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String sql = "INSERT INTO book(name, authorname, arrivaldate, type, status)" + "Values('"+tfBookName.getText()+"','"+tfAuthorName.getText() +"', '"+sqlDate+"', '"+type+"', '"+status+"')";
            stnt.execute(sql);

            stnt.close();
            con.close();

            showBookTable();
            
            // alert user once the book is added successfully
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Book Added Sucessfully!...");
            a.show();

            tfBookName.setText("");
            tfAuthorName.setText("");
            dpDate.setValue(null);
            rbBook.setSelected(false);
            rbReferenceBook.setSelected(false);
            rbFreshArrival.setSelected(false);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    
    // Implemented ExcelDownloadAction to allow user to download Book details  
    public void ExcelDownloadAction(MouseEvent mouseEvent) {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(HelloApplication.dbUrl, HelloApplication.userName, HelloApplication.password);
            stnt = con.createStatement();

            String query = "SELECT * FROM book";
            ResultSet rs = stnt.executeQuery(query);

           XSSFWorkbook wb = new  XSSFWorkbook();
           XSSFSheet sheet =wb.createSheet("Book Details");
           
           XSSFRow header = sheet.createRow(0);
           header.createCell(0).setCellValue("BookId");
           header.createCell(1).setCellValue("BookName");
           header.createCell(2).setCellValue("Author Name");
           header.createCell(3).setCellValue("Arrival Date");
           header.createCell(4).setCellValue("Type");
           header.createCell(5).setCellValue("Status"); 
           
         int index =1;
         
         
           while (rs.next()) {
        	   XSSFRow row = sheet.createRow(index);
        	   row.createCell(0).setCellValue(rs.getString("id"));
        	   row.createCell(1).setCellValue(rs.getString("name"));
        	   row.createCell(2).setCellValue(rs.getString("authorname"));
        	   row.createCell(3).setCellValue(rs.getString("arrivalDate"));
        	   row.createCell(4).setCellValue(rs.getString("type"));
        	   row.createCell(5).setCellValue(rs.getString("status"));
        	   
        	   index++;
          }
           File f1 = new File("C:\\Users\\Sweety\\eclipse-workspace\\Library_Management_System_main\\src\\com\\example\\my_lms\\BookDetails.xlsx");
           FileOutputStream fileop = new FileOutputStream(f1);       
           wb.write(fileop);
           fileop.close();
           
           
          // wb.close();
          // Alert user with success response once Book details are exported.        
           Alert ab = new Alert(Alert.AlertType.INFORMATION);
           ab.setContentText("Book Details Exported!...");
           ab.show();
           stnt.close();
           con.close();
           
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
