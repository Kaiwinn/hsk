package entity;

public class Employee {
	private int employeeID;
	private String employeeName, address;
	private double salary;

	public Employee() {
		super();
	}

	public Employee(int employeeID, String employeeName, String address,
			double salary) {
		super();
		this.employeeID = employeeID;
		this.employeeName = employeeName;
		this.address = address;
		this.salary = salary;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

}
