package entity;

public class Category {
	private int productID;
	private String productName;
	private int quantity;
	private double price;
	private int categoryID;
	private String categoryName;
	private int supplierID;
	private String supplierName;
	private String address;
	private String city;

	public Category() {
		super();
	}

	public Category(int productID,
			String productName, int quantity, double price, int categoryID, String categoryName, int supplierID,
			String supplierName, String address, String city) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.supplierID = supplierID;
		this.supplierName = supplierName;
		this.address = address;
		this.city = city;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
