package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connect.Connect;

public class DAO_Login {
  @SuppressWarnings("static-access")
  public static boolean checkLogin(String username, String password) {
    PreparedStatement s = null;
    String sql = "select * from [User] where username = ? and password = ?";
    boolean check = false;
    try {
      Connect.getInstance().connect();
      s = Connect.getInstance().getCon().prepareStatement(sql);
      s.setString(1, username);
      s.setString(2, password);
      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        check = true;
      }
      Connect.getInstance().disconnect();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      Connect.getInstance().disconnect();
    }
    return check;
  }
}
