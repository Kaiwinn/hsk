package entity;

//madon, ngaydat, thanhtien
public class Revenue {
	private int orderID;
	private String orderDate;
	private double revenue;

	public Revenue() {
		super();
	}

	public Revenue(int orderID, String orderDate, double revenue) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.revenue = revenue;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

}
