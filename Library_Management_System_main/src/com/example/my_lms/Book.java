package com.example.my_lms;

import java.util.Date;
// Represents class Book 
public class Book {

    public int ID, studID;
    public String bookName, authorName,type, status, studName;  
    public Date arrivalDate, issueDate, dueDate, returnDate;

    //getStatus() method to retrieve status of book
  
    public String getStatus() {
        return status;
    }

    //Book() constructor with input parameters ID, bookName, authorName, arrivalDate, type, status   
    public Book(int ID, String bookName, String authorName, Date arrivalDate, String type, String status) {
        this.ID = ID;
        this.bookName = bookName;
        this.authorName = authorName;
        this.type = type;
        this.arrivalDate = arrivalDate;
        this.status = status;
    }

    //Book() constructor with input parameters ID, bookName, studID, studName    
    public Book(int ID, String bookName, int studID, String studName){
        this.ID = ID;
        this.bookName = bookName;
        this.studID = studID;
        this.studName = studName;
    }
    
  //Book() constructor with input parameters ID, bookName, issueDate, dueDate
    public Book(int ID, String bookName, Date issueDate, Date dueDate){
        this.ID = ID;
        this.bookName = bookName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
    
  //Book() constructor with input parameters studID, studName, issueDate, dueDate   
    public Book(int ID, String bookName, int studID, String studName, Date issueDate, Date dueDate){
        this.ID = ID;
        this.bookName = bookName;
        this.studID = studID;
        this.studName = studName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
    
  //Book() constructor with input parameters ID, bookName, studID, studName, issueDate, dueDate, returnDate
    public Book(int ID, String bookName, int studID, String studName, Date issueDate, Date dueDate, Date returnDate){
        this.ID = ID;
        this.bookName = bookName;
        this.studID = studID;
        this.studName = studName;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }
    
    //getID() to retrieve Book Id   
    public int getID() {
        return ID;
    }
    
    //getBookName() to retrieve Book Name
    public String getBookName() {
        return bookName;
    }
    
    //getAuthorName() to retrieve author name   
    public String getAuthorName() {
        return authorName;
    }

    //getType() to retrieve Type 
    public String getType() {
        return type;
    }
    
    //getArrivalDate() to retrieve arrival date
    public Date getArrivalDate() {
        return arrivalDate;
    }
    
    //getStudID to retrieve student Id
    public int getStudID() {
        return studID;
    }
    
    //getStudName() to retrieve student name
    public String getStudName() {
        return studName;
    }
    
    //getIssueDate() to retrieve issue date
    public Date getIssueDate() {
        return issueDate;
    }
    
    //getDueDate() to retrieve due date
    public Date getDueDate() {
        return dueDate;
    }
    
    ////getReturnDate() to retrieve return date
    public Date getReturnDate() {
        return returnDate;
    }

}
