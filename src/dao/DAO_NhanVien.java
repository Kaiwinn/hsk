package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connect.Connect;
import entity.Employee;

public class DAO_NhanVien {
	@SuppressWarnings("static-access")
	public static ArrayList<Employee> getEmployee() {
		PreparedStatement s = null;
		String sql = "SELECT * FROM Employee";
		ArrayList<Employee> list = new ArrayList<Employee>();
		try {
			Connect.getInstance().connect();
			s = Connect.getInstance().getCon().prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				list.add(new Employee(rs.getInt("EmployeeID"), rs
						.getString("EmployeeName"), rs.getString("Address"),
						rs
								.getDouble("Salary")));
			}
			Connect.getInstance().disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connect.getInstance().disconnect();
		}
		return list;
	}

	@SuppressWarnings("static-access")
	public static ArrayList<Employee> getOneEmployee(String employeeName) {
		PreparedStatement s = null;
		String sql = "SELECT * FROM Employee where EmployeeName = ?";
		ArrayList<Employee> list = new ArrayList<Employee>();
		try {
			Connect.getInstance().connect();
			s = Connect.getInstance().getCon().prepareStatement(sql);
			s.setString(1, (employeeName));
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				list.add(new Employee(rs.getInt("EmployeeID"), rs
						.getString("EmployeeName"), rs.getString("Address"),
						rs
								.getDouble("Salary")));
			}
			Connect.getInstance().disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connect.getInstance().disconnect();
		}
		return list;
	}

	@SuppressWarnings("static-access")
	public static int findEmployeeById(String id) {
		CallableStatement callableStatement = null;
		int employeeIDIN = Integer.parseInt(id);
		int employeeId = 0;
		try {
			Connect.getInstance().connect();
			callableStatement = Connect.getInstance().getCon()
					.prepareCall("{call find_employee(?,?)}");
			callableStatement.setInt(1, employeeIDIN);
			callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
			callableStatement.execute();
			employeeId = callableStatement.getInt(2);
			Connect.getInstance().disconnect();
			return employeeId;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connect.getInstance().disconnect();
		}
		return 0;
	}

	@SuppressWarnings("static-access")
	public static boolean deleteEmployeeById(int employeeIDIN) {
		PreparedStatement s = null;
		try {
			Connect.getInstance().connect();
			s = Connect.getInstance().getCon()
					.prepareCall("delete Employee where EmployeeID=?");
			s.setInt(1, employeeIDIN);
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
	public static boolean insertEmployee(String employeeID, String employeeName,
			String address, String salary) {
		PreparedStatement s = null;
		try {
			Connect.getInstance().connect();
			s = Connect.getInstance().getCon()
					.prepareStatement("insert into Employee values(?,?,?,?)");
			s.setInt(1, Integer.parseInt(employeeID));
			s.setString(2, employeeName);
			s.setString(3, address);
			s.setFloat(4, Float.parseFloat(salary));
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
	public static boolean updateEmployee(String employeeID, String employeeName,
			String address, String salary) {
		PreparedStatement s = null;
		try {
			Connect.getInstance().connect();
			s = Connect.getInstance().getCon()
					.prepareStatement("update Employee set EmployeeName=?,Address=?,Salary=? where EmployeeID=?");
			s.setString(1, employeeName);
			s.setString(2, address);
			s.setFloat(3, Float.parseFloat(salary));
			s.setInt(4, Integer.parseInt(employeeID));
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
