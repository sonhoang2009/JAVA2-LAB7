package lab7;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    List<Customer> customerList = new ArrayList<>();

    public List<Customer> loading() {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
        ) {
            String select = "Select * from customers";
            ResultSet rset = stmt.executeQuery(select);
            while (rset.next()) {
                int id = rset.getInt("customerID");
                String name = rset.getString("name");
                String address = rset.getString("address");
                String email = rset.getString("email");
                String phone = rset.getString("phone");
                int member = rset.getInt("member");
                String createddate = rset.getString("createddate");
                String updateddate = rset.getString("updateddate");
                Customer obj = new Customer(id, name, address, email, phone, member, createddate, updateddate);
                customerList.add(obj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customerList;

    }

    public void display1Customer(int x) {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
        ) {
            String stt = "Select * from customers where customerID = " + x;
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

    public void displayCustomer() {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
        ) {
            String stt = "Select * from customers";
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

    public boolean insertCustomer(Customer customer) {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
        ) {
            String insert = "Insert into customers(customerID,name,address,email,phone,member) values(" + customer.getCustomerID() + ",'" + customer.getName() + "','" + customer.getAddress() + "','" + customer.getEmail() + "','" + customer.getPhone() + "'," + customer.getMember() + ")";
            stmt.executeUpdate(insert);
            loading();
            System.out.println("Customer has been inserted");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int updateCustomer(Customer customer) {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
        ) {
            String update = "update customers set email ='" + customer.getEmail() + "' phone ='" + customer.getPhone() + "' member=" + customer.getMember() + " where customerID =" + customer.getCustomerID();
            int count = stmt.executeUpdate(update);
            loading();
            if (count == 0) {
                return 0;
            } else {
                return 1;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public boolean deleteCustomer(Customer customer) {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore", "root", "");
                Statement stmt = conn.createStatement();
        ) {
            String select = "select * from orders where customerID=" + customer.getCustomerID();
            ResultSet rset = stmt.executeQuery(select);
            if (!rset.next()) {
                String delete = "delete from customers  where customerID =" + customer.getCustomerID();
                stmt.executeUpdate(delete);
                loading();
                System.out.println("Customer has been deleted");
                return true;
            } else
                System.out.println("Cannot delete/No customer found");
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Cannot");
            return false;
        }
    }
}