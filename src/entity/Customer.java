package entity;

//id, ten, diachi, sdt, email
public class Customer {
	private int customerID;
	private String customerName, address, email, phone;

	public Customer() {
		super();
	}

	public Customer(int customerID, String customerName, String address,
			String phone, String email) {
		super();
		this.customerID = customerID;
		this.phone = phone;
		this.customerName = customerName;
		this.address = address;
		this.email = email;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
