package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connect.Connect;
import entity.Revenue;

public class DAO_DoanhThu {
  @SuppressWarnings("static-access")
  public static ArrayList<Revenue> getRevenue() {
    PreparedStatement s = null;
    String sql = "select * from v_revenue";
    ArrayList<Revenue> list = new ArrayList<Revenue>();
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        list.add(new Revenue(rs.getInt("OrderDetailsID"), rs.getString("OrderDate"), rs.getDouble("tongTien")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return list;
  }

  @SuppressWarnings("static-access")
  public static float getMaxRevenueOfDay() {
    PreparedStatement s = null;
    String sql = "select * from v_revenue_getMaxRevenueOfDay";
    float maxRevenueOfDay = 0;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        maxRevenueOfDay = Float.parseFloat(rs.getString("Revenue"));
      }
      Connect.getInstance().disconnect();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return maxRevenueOfDay;
  }

  @SuppressWarnings("static-access")
  public static float getMaxRevenueOfMonth() {
    PreparedStatement s = null;
    String sql = "select * from v_revenue_getMaxRevenueOfMonth";
    float maxRevenueOfMonth = 0;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        maxRevenueOfMonth = Float.parseFloat(rs.getString("tongTien"));

      }
      Connect.getInstance().disconnect();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return maxRevenueOfMonth;
  }

  @SuppressWarnings("static-access")
  public static ArrayList<Revenue> findRevenueByDay(String day) {
    CallableStatement cs = null;
    ArrayList<Revenue> list = new ArrayList<Revenue>();
    try {
      Connect.getInstance().connect();
      cs = Connect.getInstance().getCon().prepareCall("{call findRevenueByDay(?)}");
      cs.setString(1, day);
      ResultSet rs = cs.executeQuery();
      while (rs.next()) {
        list.add(new Revenue(rs.getInt("RevenueOfDayID"), rs.getString("OrderDate"), rs.getDouble("revenue")));

      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return list;
  }

  @SuppressWarnings("static-access")
  public static ArrayList<Revenue> updateRevenue(String id) {
    PreparedStatement s = null;
    String sql = "insert RevenueOfDay(RevenueOfDayID, Revenue, OrderDate)"
        + "select OrderID, sum(Price*Quantity), OrderDate "
        + "from [Order] inner join OrderDetails"
        + "on [Order].OrderID = OrderDetails.OrderID  where OrderDetailsID > '" + id
        + "' group by OrderDetailsID, OrderDate";
    ArrayList<Revenue> list = new ArrayList<Revenue>();
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        list.add(new Revenue(rs.getInt("OrderDetailsID"), rs.getString("OrderDate"), rs.getDouble("tongTien")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return list;
  }

}
