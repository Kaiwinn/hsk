package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connect.Connect;
import entity.Customer;

public class DAO_KhachHang {
  @SuppressWarnings("static-access")
  public static ArrayList<Customer> getCustomer() {
    PreparedStatement s = null;
    String sql = "SELECT * FROM Customer";
    ArrayList<Customer> list = new ArrayList<Customer>();
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        list.add(new Customer(rs.getInt("CustomerID"), rs.getString("CustomerName"),
            rs.getString("Address"), rs.getString("Phone"), rs.getString("Email")));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return list;
  }

  @SuppressWarnings("static-access")
  public static int findCustomerById(String id) {
    CallableStatement callableStatement = null;
    int customerIDIN = Integer.parseInt(id);
    int customerId = 0;
    try {
      Connect.getInstance().connect();
      callableStatement = Connect.getInstance().getCon().prepareCall("{call find_customer(?,?)}");
      callableStatement.setInt(1, customerIDIN);
      callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
      callableStatement.execute();
      customerId = callableStatement.getInt(2);
      Connect.getInstance().disconnect();
      return customerId;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return 0;
  }

  @SuppressWarnings("static-access")
  public static boolean deleteCustomerById(int customerIDIN) {
    PreparedStatement s = null;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareCall("delete Customer where CustomerID=?");
      s.setInt(1, customerIDIN);
      s.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return false;
  }

  @SuppressWarnings("static-access")
  public static boolean insertCustomer(String customerID, String customerName, String address,
      String phone, String email) {
    PreparedStatement s = null;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareCall(
          "insert into Customer values(?,?,?,?,?)");
      s.setInt(1, Integer.parseInt(customerID));
      s.setString(2, customerName);
      s.setString(3, address);
      s.setString(4, phone);
      s.setString(5, email);
      s.executeUpdate();
      Connect.getInstance().disconnect();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return false;
  }

  @SuppressWarnings("static-access")
  public static boolean updateCustomer(String customerID, String customerName, String address,
      String phone, String email) {
    PreparedStatement s = null;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareCall(
          "update Customer set CustomerName=?, Address=?, Phone=?, Email=? where CustomerID=?");
      s.setString(1, customerName);
      s.setString(2, address);
      s.setString(3, phone);
      s.setString(4, email);
      s.setInt(5, Integer.parseInt(customerID));
      s.executeUpdate();
      Connect.getInstance().disconnect();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return false;
  }
}
