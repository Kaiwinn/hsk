package dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Connect.Connect;
import entity.Category;

public class DAO_KhoHang {
	@SuppressWarnings("static-access")
	public static ArrayList<Category> getCategory() {
		PreparedStatement s = null;
		String sql = "select * from v_category";
		ArrayList<Category> list = new ArrayList<Category>();
		try {
			Connect.getInstance().connect();
			s = Connect.getInstance().getCon().prepareStatement(sql);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				list.add(new Category(rs.getInt("ProductID"),
						rs
								.getString("ProductName"),
						rs.getInt("Quantity"), rs
								.getDouble("Price"),
						rs.getInt("CategoryID"), rs
								.getString("CategoryName"),
						rs.getInt("SupplierID"), rs
								.getString("SupplierName"),
						rs.getString("Address"), rs
								.getString("City")));
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
	public static int findCategoryById(String id) {
		CallableStatement callableStatement = null;
		int categoryIDIN = Integer.parseInt(id);
		int categoryId = 0;
		try {
			Connect.getInstance().connect();
			callableStatement = Connect.getInstance().getCon()
					.prepareCall("{call find_category(?,?)}");
			callableStatement.setInt(1, categoryIDIN);
			callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
			callableStatement.execute();
			categoryId = callableStatement.getInt(2);
			Connect.getInstance().disconnect();
			return categoryId;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connect.getInstance().disconnect();
		}
		return 0;
	}

	@SuppressWarnings("static-access")
	public static int findSupplierById(String id) {
		CallableStatement callableStatement = null;
		int supplierIdIN = Integer.parseInt(id);
		int supplierId = 0;
		try {
			Connect.getInstance().connect();
			callableStatement = Connect.getInstance().getCon()
					.prepareCall("{call find_Supplier(?,?)}");
			callableStatement.setInt(1, supplierIdIN);
			callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
			callableStatement.execute();
			supplierId = callableStatement.getInt(2);
			Connect.getInstance().disconnect();
			return supplierId;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connect.getInstance().disconnect();
		}
		return 0;
	}

	@SuppressWarnings("static-access")
	public static int findProductById(String id) {
		CallableStatement callableStatement = null;
		int productIDIN = Integer.parseInt(id);
		int productId = 0;
		try {
			Connect.getInstance().connect();
			callableStatement = Connect.getInstance().getCon()
					.prepareCall("{call find_product(?,?)}");
			callableStatement.setInt(1, productIDIN);
			callableStatement.registerOutParameter(2, java.sql.Types.INTEGER);
			callableStatement.execute();
			productId = callableStatement.getInt(2);
			Connect.getInstance().disconnect();
			return productId;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connect.getInstance().disconnect();
		}
		return 0;
	}

	@SuppressWarnings("static-access")
	public static boolean deleteCategoryById(int categoryIDIN, int supplierID) {
		PreparedStatement s = null;
		try {
			Connect.getInstance().connect();
			s = Connect.getInstance().getCon()
					.prepareCall("delete Category where CategoryID=? delete Supplier where SupplierID=?");
			s.setInt(1, categoryIDIN);
			s.setInt(2, supplierID);
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
	public static boolean insertCategory_Product(String productID, String productName,
			String quantity, String price, String categoryID,
			String categoryName, String supplierID,
			String supplierName, String address, String city) {
		PreparedStatement s = null;
		try {
			Connect.getInstance().connect();
			s = Connect
					.getInstance()
					.getCon()
					.prepareStatement(
							"insert into Product(ProductID, ProductName, Price,Quantity) values(?, ?, ?, ?)"
									+ "update Product set CategoryID=?, SupplierID=? where ProductID=?");
			s.setInt(1, Integer.parseInt(productID));
			s.setString(2, productName);
			s.setDouble(3, Double.parseDouble(price));
			s.setInt(4, Integer.parseInt(quantity));
			s.setInt(5, Integer.parseInt(categoryID));
			s.setInt(6, Integer.parseInt(supplierID));
			s.setInt(7, Integer.parseInt(productID));
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
	public static boolean insertCategory_Product_Category(String productID, String productName,
			String quantity, String price, String categoryID,
			String categoryName, String supplierID,
			String supplierName, String address, String city) {
		PreparedStatement s = null;
		try {
			Connect.getInstance().connect();
			s = Connect
					.getInstance()
					.getCon()
					.prepareStatement(
							"insert into Product(ProductID, ProductName, Price,Quantity) values(?, ?, ?, ?)"
									+ "insert into Category(CategoryID,CategoryName) values(?,?)"
									+ "update Product set SupplierID=? where ProductID=?");
			s.setInt(1, Integer.parseInt(productID));
			s.setString(2, productName);
			s.setDouble(3, Double.parseDouble(price));
			s.setInt(4, Integer.parseInt(quantity));
			s.setInt(5, Integer.parseInt(categoryID));
			s.setString(6, categoryName);
			s.setInt(7, Integer.parseInt(supplierID));
			s.setInt(8, Integer.parseInt(productID));
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
	public static boolean insertCategory_Product_Supplier(String productID, String productName,
			String quantity, String price, String categoryID,
			String categoryName, String supplierID,
			String supplierName, String address, String city) {
		PreparedStatement s = null;
		try {
			Connect.getInstance().connect();
			s = Connect.getInstance().getCon()
					.prepareStatement(
							"insert into Product(ProductID, ProductName, Price,Quantity) values(?, ?, ?, ?)"
									+ "insert into Supplier(SupplierID, SupplierName, Address) values(?,?,?)"
									+ "update Product set CategoryID=? where ProductID=?");
			s.setInt(1, Integer.parseInt(productID));
			s.setString(2, productName);
			s.setDouble(3, Double.parseDouble(price));
			s.setInt(4, Integer.parseInt(quantity));
			s.setInt(5, Integer.parseInt(supplierID));
			s.setString(6, supplierName);
			s.setString(7, address);
			s.setInt(8, Integer.parseInt(categoryID));
			s.setInt(9, Integer.parseInt(productID));
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
	public static boolean updateCategory(String categoryID,
			String categoryName, String supplierID,
			String supplierName, String address, String city, String productID, String productName,
			String quantity, String price) {
		PreparedStatement s = null;
		String category = "update Category set CategoryName=? where CategoryID=?";
		String supplier = "update Supplier set SupplierName=?, Address=?, City=? where SupplierID=?";
		String product = "update Product set ProductName=?, Price=?, Quantity=? where ProductID=?";
		try {
			Connect.getInstance().connect();
			s = Connect
					.getInstance()
					.getCon()
					.prepareStatement(
							category + " " + supplier + " " + product);
			s.setString(1, categoryName);
			s.setInt(2, Integer.parseInt(categoryID));
			s.setString(3, supplierName);
			s.setString(4, address);
			s.setString(5, city);
			s.setInt(6, Integer.parseInt(supplierID));
			s.setString(7, productName);
			s.setDouble(8, Double.parseDouble(price));
			s.setInt(9, Integer.parseInt(quantity));
			s.setInt(10, Integer.parseInt(productID));
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
