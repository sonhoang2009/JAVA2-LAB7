package lab7;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookController {
    List<Book> bookList = new ArrayList<>();
    public List<Book> loading(){
        try(
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
                ){
            String select = "Select *from books";
            ResultSet rset = stmt.executeQuery(select);
            while(rset.next()){
                int id = rset.getInt("bookID");
                String title = rset.getString("title");
                String author = rset.getString("author");
                int years = rset.getInt("years");
                int genre = rset.getInt("genre");
                int qty= rset.getInt("qty");
                int status = rset.getInt("status");
                double price = rset.getDouble("price");
                String createddate = rset.getString("createddate");
                String updateddate = rset.getString("updateddate");
                Book obj = new Book (id,title,author,years,genre,status,qty,price,createddate,updateddate);
                bookList.add(obj);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return bookList;
    }

    public boolean insertBook(Book book){
        try(
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
                ){
            String insert = "Insert into books(bookId,title,author,qty,price) values(" + book.getBookID() + ",'" + book.getTitle() + "','" + book.getAuthor() + "'," + book.getQty() + "," + book.getPrice() + ")";
            stmt.executeUpdate(insert);
            loading();
            System.out.println("Book has been inserted");
            return true;
        }catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public int updateBook(Book book){
        try(
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
                ){
            String update = "update books set qty="+ book.getQty()+",price="+book.getPrice()+ " where bookID =" + book.getBookID();
            int count = stmt.executeUpdate(update);
            loading();
            if (count == 0) {
                return 0;
            } else {
                return 1;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
            return -1;
        }
    }

    public boolean deleteBook(Book book) {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
        ) {
            String select = "select * from orderdetail where bookID =" + book.getBookID();
            ResultSet rset = stmt.executeQuery(select);
            if (!rset.next()) {
                String delete = "delete from books where bookID =" + book.getBookID();
                stmt.executeUpdate(delete);
                loading();
                System.out.println("Book has been deleted");
                return true;
            } else
                System.out.println("Cannot delete/No book found");
            return false;
        } catch (SQLException ex) {
            System.out.println("Cannot delete this book");
            ex.printStackTrace();
            return false;
        }
    }

    public void displayBook() {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
        ) {
            String stt = "Select * from books";
            ResultSet rset = stmt.executeQuery(stt);
            ResultSetMetaData rsetMD = rset.getMetaData();
            int numColums = rsetMD.getColumnCount();
            for (int i = 1; i <= numColums; i++) {
                System.out.printf("%-30s", rsetMD.getColumnName(i));
            }
            loading();
            System.out.println();
            while (rset.next()) {
                for (int i = 1; i <= numColums; i++) {
                    System.out.printf("%-30s", rset.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
