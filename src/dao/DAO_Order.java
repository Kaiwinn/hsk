package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connect.Connect;
import entity.Order;

public class DAO_Order {
  @SuppressWarnings("static-access")
  public static ArrayList<Order> getOrderAdmin() {
    PreparedStatement s = null;
    String sql = "select * from v_order_admin";
    ArrayList<Order> list = new ArrayList<Order>();
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        list.add(new Order(
            rs.getInt("OrderID"),
            rs.getInt("CustomerID"),
            rs.getString("CustomerName"),
            rs.getString("Phone"),
            rs.getInt("ProductID"),
            rs.getString("ProductName"),
            rs.getInt("Quantity"),
            rs.getDouble("Price"),
            rs.getString("OrderDate"),
            rs.getString("EndDate")));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return list;
  }

  @SuppressWarnings("static-access")
  public static ArrayList<Order> getOrder() {
    PreparedStatement s = null;
    String sql = "select * from v_order";
    ArrayList<Order> list = new ArrayList<Order>();
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        list.add(new Order(
            rs.getInt("OrderID"),
            rs.getInt("CustomerID"),
            rs.getString("CustomerName"),
            rs.getString("Address"),
            rs.getString("Phone"),
            rs.getString("Email"),
            rs.getInt("EmployeeID"),
            rs.getString("shipaddress"),
            rs.getString("OrderDate"),
            rs.getString("ShipDate"),
            rs.getString("EndDate")));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return list;
  }

  @SuppressWarnings("static-access")
  public static ArrayList<Order> getOrderDetails() {
    PreparedStatement s = null;
    String sql = "select * from OrderDetails";
    ArrayList<Order> list = new ArrayList<Order>();
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        list.add(new Order(
            rs.getInt("OrderDetailsID"),
            rs.getInt("ProductID"),
            rs.getInt("Quantity"),
            rs.getDouble("Price"),
            rs.getDouble("Discount")));

      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return list;
  }

  @SuppressWarnings("static-access")
  public static String getMaxCustomer() {
    PreparedStatement s = null;
    String sql = "select * from v_order_getMaxCustomer";
    String name = "";
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        name = rs.getString("CustomerName");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return name;
  }

  @SuppressWarnings("static-access")
  public static int findOrderById(String id) {
    CallableStatement callableStatement = null;
    int orderIDIN = Integer.parseInt(id);
    int orderId = 0;
    try {
      Connect.getInstance().connect();
      callableStatement = Connect.getInstance().getCon().prepareCall("{call find_order(?,?)}");
      callableStatement.setInt(1, orderIDIN);
      callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
      callableStatement.execute();
      orderId = callableStatement.getInt(2);
      return orderId;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return 0;
  }

  @SuppressWarnings("static-access")
  public static boolean deleteOrderById(int orderIDIN) {
    PreparedStatement s = null;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareCall("delete [Order] where OrderID=?");
      s.setInt(1, orderIDIN);
      s.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @SuppressWarnings("static-access")
  public static boolean insertOrder_Order_Customer(String orderID, String customerID, String customerName,
      String address,
      String phone, String email, String employeeID, String shipAddress, String orderDate, String shipDate,
      String endDate) {
    PreparedStatement s = null;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(
          "insert into [Order](OrderID, OrderDate, EndDate, ShipDate, ShipAddress) values(?,?,?,?,?)"
              + "insert into Customer values(?,?,?,?,?)"
              + "update [Order] set EmployeeID=? where OrderID=?");
      s.setInt(1, Integer.parseInt(orderID));
      s.setString(2, orderDate);
      s.setString(3, endDate);
      s.setString(4, shipDate);
      s.setString(5, shipAddress);
      s.setInt(6, Integer.parseInt(customerID));
      s.setString(7, customerName);
      s.setString(8, address);
      s.setString(9, phone);
      s.setString(10, email);
      s.setInt(11, Integer.parseInt(employeeID));
      s.setInt(12, Integer.parseInt(orderID));
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
  public static boolean insertOrder_Order_Employee(String orderID, String customerID, String customerName,
      String address,
      String phone, String email, String employeeID, String shipAddress, String orderDate, String shipDate,
      String endDate) {
    PreparedStatement s = null;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(
          "insert into [Order](OrderID, OrderDate, EndDate, ShipDate, ShipAddress) values(?,?,?,?,?)"
              + "insert into Employee(EmployeeID) values(?)"
              + "update [Order] set CustomerID=? where OrderID=?");
      s.setInt(1, Integer.parseInt(orderID));
      s.setString(2, orderDate);
      s.setString(3, endDate);
      s.setString(4, shipDate);
      s.setString(5, shipAddress);
      s.setInt(6, Integer.parseInt(employeeID));
      s.setInt(7, Integer.parseInt(customerID));
      s.setInt(8, Integer.parseInt(orderID));
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
  public static boolean insertOrder_Order(String orderID, String customerID, String customerName, String address,
      String phone, String email, String employeeID, String shipAddress, String orderDate, String shipDate,
      String endDate) {
    PreparedStatement s = null;
    try {
      Connect.getInstance().connect();

      s = Connect.getInstance().getCon().prepareStatement(
          "insert into [Order](OrderID, OrderDate, EndDate, ShipDate, ShipAddress) values(?,?,?,?,?)"
              + "update [Order] set EmployeeID=?, CustomerID=? where OrderID=?");
      s.setInt(1, Integer.parseInt(orderID));
      s.setString(2, orderDate);
      s.setString(3, endDate);
      s.setString(4, shipDate);
      s.setString(5, shipAddress);
      s.setInt(6, Integer.parseInt(employeeID));
      s.setInt(7, Integer.parseInt(customerID));
      s.setInt(8, Integer.parseInt(orderID));
      s.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return false;
  }

  @SuppressWarnings("static-access")
  public static boolean insertOrderDetails(String orderID, String productID, String quantity, String price,
      String discount) {
    PreparedStatement s = null;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement("insert into OrderDetails values(?,?,?,?,?,?)");
      s.setInt(1, Integer.parseInt(orderID));
      s.setInt(2, Integer.parseInt(quantity));
      s.setDouble(3, Double.parseDouble(price));
      s.setDouble(4, Double.parseDouble(discount));
      s.setInt(5, Integer.parseInt(orderID));
      s.setInt(6, Integer.parseInt(productID));
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
  public static boolean updateOrder(String orderID, String customerID,
      String customerName, String address, String phone, String email, String employeeID, String shipAddress,
      String orderDate, String shipDate, String endDate) {
    PreparedStatement s = null;
    String order = "update [Order] set OrderDate=?, EndDate=?, ShipDate=?, ShipAddress=? where OrderID=?";
    String customer = "update Customer set CustomerName=?, Address=?, Phone=?, Email=? where CustomerID=?";
    try {

      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(
          customer + " " + order);
      s.setString(1, customerName);
      s.setString(2, address);
      s.setString(3, phone);
      s.setString(4, email);
      s.setInt(5, Integer.parseInt(customerID));
      s.setString(6, orderDate);
      s.setString(7, endDate);
      s.setString(8, shipDate);
      s.setString(9, shipAddress);
      s.setInt(10, Integer.parseInt(orderID));

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
  public static boolean updateOrderDetails(String orderDetailsID, String productID, String quantity, String price,
      String discount) {
    PreparedStatement s = null;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareCall(
          "update OrderDetails set ProductID=?,Quantity=?,Price=?,Discount=? where orderDetailsID=?");
      s.setInt(1, Integer.parseInt(productID));
      s.setInt(2, Integer.parseInt(quantity));
      s.setDouble(3, Double.parseDouble(price));
      s.setDouble(4, Double.parseDouble(discount));
      s.setInt(5, Integer.parseInt(orderDetailsID));
      s.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return false;
  }
}
