package entity;

//madon, makhachhang, tenkh, masp, sl, sdt, ngaydat, ngaygiao, ngaynhan, diachinhan,manv
public class Order {
	private int orderID, customerID, productID, quantity, employeeID;
	private String customerName, orderDate, shipDate, endDate, productName,
			customerAddress, phone, shipAddress, email;
	private double price, discount;

	public Order() {
		super();
	}

	public Order(int orderID,
			int productID, int quantity, double price, double discount) {
		super();
		this.orderID = orderID;
		this.productID = productID;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
	}

	public Order(int orderID, int customerID, String customerName, String address, String phone, String email,
			int employeeID, String shipAddress, String orderDate, String shipDate, String endDate) {
		super();
		this.orderID = orderID;
		this.customerID = customerID;
		this.customerName = customerName;
		this.customerAddress = address;
		this.phone = phone;
		this.email = email;
		this.employeeID = employeeID;
		this.shipAddress = shipAddress;
		this.orderDate = orderDate;
		this.shipDate = shipDate;
		this.endDate = endDate;
	}

	public Order(int orderID, int customerID, String customerName, String phone, int productID, String productName,
			int quantity, double price, String orderDate, String endDate) {
		super();
		this.orderID = orderID;
		this.customerID = customerID;
		this.customerName = customerName;
		this.phone = phone;
		this.productID = productID;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.orderDate = orderDate;
		this.endDate = endDate;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomeAddress(String customeAddress) {
		this.customerAddress = customeAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

}
