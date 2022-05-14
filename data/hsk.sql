create database hsk
use hsk

create table Category(
	CategoryID int not null primary key,
	CategoryName nvarchar(50)
)
create table Supplier(
	SupplierID int not null primary key,
	SupplierName nvarchar(50),
	Address nvarchar(300),
	City nvarchar(300)
)

create table Product(
	ProductID int not null primary key,
	ProductName nvarchar(50),
	Discount float,
	Price float,
	Quantity int,
	CategoryID INT FOREIGN KEY REFERENCES Category(CategoryID) on delete cascade on update cascade,
	SupplierID INT FOREIGN KEY REFERENCES Supplier(SupplierID) on delete cascade on update cascade
)



create table Customer(
	CustomerID int not null primary key,
	CustomerName nvarchar(50),
	Address nvarchar(300),
	Phone nvarchar(50),
	Email nvarchar(50)
)

create table Employee(
	EmployeeID int not null primary key,
	EmployeeName nvarchar(50),
	Address nvarchar(300),
	Salary float
)


create table [Order](
	OrderID int not null primary key,
	OrderDate datetime,
	EndDate datetime,
	ShipDate datetime,
	ShipAddress nvarchar(300),
	CustomerID INT FOREIGN KEY REFERENCES Customer(CustomerID) on delete cascade on update cascade,
	EmployeeID INT FOREIGN KEY REFERENCES Employee(EmployeeID) on delete cascade on update cascade
)

create table OrderDetails(
	OrderDetailsID int not null primary key,
	Quantity int,
	Price float,
	Discount float,
	OrderID INT FOREIGN KEY REFERENCES [Order](OrderID) on delete cascade on update cascade,
	ProductID INT FOREIGN KEY REFERENCES Product(ProductID) on delete cascade on update cascade,
)

create table RevenueOfDay(
	RevenueOfDayID int not null primary key,
	Revenue float,
	OrderDate datetime
)
create table Revenue(
	RevenueID int not null primary key,
	RevenueOfDayID INT FOREIGN KEY REFERENCES RevenueOfDay(RevenueOfDayID) on delete cascade on update cascade,
	OrderDetailsID INT FOREIGN KEY REFERENCES OrderDetails(OrderDetailsID) on delete cascade on update cascade
)

create table [User](
	UserName nvarchar(100),
	PassWord nvarchar(100)
)

--data sample
insert into [User] values('kuga', 'khongcopass@1')
insert into [User] values('kaiwin', 'kaikai')
insert into [User] values('kenzn2', 'khazix')
insert into [User] values('tinh', '14092002')
insert into [User] values('hao', 'hao')

insert into Category values(1,'A11')
insert into Category values(2,'A22')
insert into Category values(3,'A32')
insert into Category values(4,'A44')
insert into Category values(5,'A55')

insert into Supplier values(1,'Hung','66 QL1A','VietNam')
insert into Supplier values(2,'Thanh','25 Nguyen Van Bao','VietNam')
insert into Supplier values(3,'Nguyen','200 Binh Long','VietNam')
insert into Supplier values(4,'Van','11 Tan Ky Tan Quy','VietNam')
insert into Supplier values(5,'Phuong','55 Tan Quy','VietNam')


insert into Product values(1,'A11',55000,25,5,1,1)
insert into Product values(2,'A22',65000,5,7,2,2)
insert into Product values(3,'A32',20000,35,10,3,3)
insert into Product values(4,'A44',40000,55,9,4,4)
insert into Product values(5,'A55',90000,22,3,5,5)


insert into Employee values(1,'tinh','50 Pham Dang Giang',200000)
insert into Employee values(2,'kaiwin','2 Nguyen Van Bao',150000)
insert into Employee values(3,'hao','46 Le Van Sy',770000)
insert into Employee values(4,'kuga','150 QL1A',50000)
insert into Employee values(5,'kenzn2','77 Ho Van Long',9500000)


insert into Customer values(1,'Khanh','88 Nguyen Van Bao','0988580844','nguyenthuytinh1409@gmail.com')
insert into Customer values(2,'Khanh','56 Nguyen Van Long','0922453489','nguyenvanphuong@gmail.com')
insert into Customer values(3,'Khanh','89 Tan Quy','0932673649','huynhthithuy@gmail.com')
insert into Customer values(4,'Khanh','157 Thanh Tan','0169811411','nguyenthanhhung@gmail.com')
insert into Customer values(5,'Khanh','20 Tan Huong','0987654373','nguyenthihachung@gmail.com')


insert into [Order] values(1,'2022-09-14','2022-09-19','2022-09-15','88 Nguyen Van Bao',1,1)
insert into [Order] values(2,'2022-09-19','2022-10-19','2022-10-01','56 Nguyen Van Long',2,2)
insert into [Order] values(3,'2022-01-14','2022-01-19','2022-01-12','89 Tan Quy',3,3)
insert into [Order] values(4,'2022-05-19','2022-05-22','2022-05-17','157 Thanh Tan',4,4)
insert into [Order] values(5,'2022-03-06','2022-03-18','2022-03-11','20 Tan Huong',5,5)


insert into OrderDetails values(1,25,60000, 10, 1,1)
insert into OrderDetails values(2,35,70000, 10,2,2)
insert into OrderDetails values(3,22,22000,10,3,3)
insert into OrderDetails values(4,67,67000,10,4,4)
insert into OrderDetails values(5,22,20000,10,5,5)

insert into RevenueOfDay values(1,2,'2022-09-14')
insert into RevenueOfDay values(2,3,'2022-08-09')
insert into RevenueOfDay values(3,2,'2022-02-28')
insert into RevenueOfDay values(4,7,'2022-03-06')
insert into RevenueOfDay values(5,8,'2022-05-25')

insert into Revenue values(1,1,1)
insert into Revenue values(2,2,2)
insert into Revenue values(3,3,3)
insert into Revenue values(4,4,4)
insert into Revenue values(5,5,5)

--load data

--view_revenue
go
create view v_revenue_getMaxRevenueOfDay
as
SELECT top 1	Revenue, OrderDate 
FROM			RevenueOfDay 
where			DAY(OrderDate) > 1 and  DAY(OrderDate) < 31 and MONTH(OrderDate) = 5
order by		Revenue desc
go


create view v_revenue_getMaxRevenueOfMonth
as
SELECT  tongTien = sum(Revenue) FROM RevenueOfDay where MONTH(OrderDate) = 5
go


create view v_revenue
as
SELECT    top 500    Revenue.OrderDetailsID, RevenueOfDay.OrderDate, tongTien = sum(RevenueOfDay.Revenue)
FROM            OrderDetails INNER JOIN
                         Revenue ON OrderDetails.OrderDetailsID = Revenue.OrderDetailsID INNER JOIN
                         RevenueOfDay ON Revenue.RevenueOfDayID = RevenueOfDay.RevenueOfDayID
group by  Revenue.OrderDetailsID, RevenueOfDay.OrderDate
order by Revenue.OrderDetailsID asc
go


--view order
create view v_order_getMaxCustomer
as
SELECT top 1 Customer.CustomerName, Customer.CustomerID,tongtien = sum(OrderDetails.Price * OrderDetails.Quantity)
FROM    Customer INNER JOIN
        [Order] ON Customer.CustomerID = [Order].CustomerID INNER JOIN
        OrderDetails ON [Order].OrderID = OrderDetails.OrderID
group by Customer.CustomerName, Customer.CustomerID, OrderDetails.Price,OrderDetails.Quantity
order by sum(OrderDetails.Price * OrderDetails.Quantity) desc
go

create view v_order
as
SELECT      [Order].OrderID, Customer.CustomerID, Customer.CustomerName, Customer.Address, Customer.Phone, Customer.Email, Employee.EmployeeID, [Order].ShipAddress, [Order].OrderDate, [Order].EndDate, [Order].ShipDate
FROM        Customer INNER JOIN
            [Order] ON Customer.CustomerID = [Order].CustomerID INNER JOIN
            Employee ON [Order].EmployeeID = Employee.EmployeeID


go

create view v_order_admin
as
SELECT	top 500 OrderDetails.OrderID, Customer.CustomerID, Customer.CustomerName,Customer.Phone,
		OrderDetails.ProductID, Product.ProductName, OrderDetails.Quantity,OrderDetails.Price,
		[Order].OrderDate,[Order].EndDate
FROM	Customer INNER JOIN [Order] ON Customer.CustomerID = [Order].CustomerID 
		INNER JOIN OrderDetails ON [Order].OrderID = OrderDetails.OrderID 
		INNER JOIN Product ON OrderDetails.ProductID = Product.ProductID
		INNER JOIN Employee ON Employee.EmployeeID = [Order].EmployeeID
order by OrderDetails.OrderID asc
go

--view category
create view v_category
as
SELECT top 500	Product.ProductID, 	Product.ProductName, Product.Quantity, Product.Price,
			Category.CategoryID, Category.CategoryName, 
			Supplier.SupplierID, Supplier.SupplierName, Supplier.Address, Supplier.City
FROM		Category 
			INNER JOIN Product ON Category.CategoryID = Product.CategoryID 
			INNER JOIN Supplier ON Product.SupplierID = Supplier.SupplierID
group by	Product.ProductID, 	Product.ProductName, Product.Quantity, Product.Price, 
			Category.CategoryID, Category.CategoryName,
			Supplier.SupplierID, Supplier.SupplierName, Supplier.Address, Supplier.City
order by	Product.ProductID asc
go

--find
create proc find_userName @userNameIn nvarchar(10), @userNameOut nvarchar(10) output
as
begin
	select @userNameOut =  UserName from [User] where UserName = @userNameIn
end

go
create proc find_employee @employeeIdIn int, @employeeIdOut int output
as
begin
	SELECT @employeeIdOut = EmployeeID FROM Employee where EmployeeID = @employeeIdIn
end

go
create proc find_product @productIdIn int, @productIdOut int output
as
begin
	SELECT @productIdOut = productID FROM Product where productID = @productIdIn
end

go
create proc find_category @categoryIdIn int, @categoryIdOut int output
as
begin
	SELECT @categoryIdOut = CategoryID FROM category where CategoryID = @categoryIdIn
end
go

create proc find_supplier @supplierIdIn int, @supplierIdOut int output
as
begin
	SELECT @supplierIdOut = SupplierID FROM Supplier where SupplierID = @supplierIdIn
end

go
create proc find_order @orderIdIn int, @orderIdOut int output
as
begin
	SELECT @orderIdOut = OrderID FROM [Order] where OrderID = @orderIdIn
end

go
create proc find_customer @customerIdIn int, @customerIdOut int output
as
begin
	SELECT @customerIdOut = CustomerID FROM Customer where CustomerID = @customerIdIn
end

go

create proc findRevenueByDay @day nvarchar(10)
as
begin
	select * from RevenueOfDay where DAY(OrderDate) = @day
end
go

--insert 

go
create trigger update_categoryID on Category
for update, insert
as
	declare @categoryId int
	begin
		select @categoryId = CategoryID from inserted
		update Product
		set CategoryID = @categoryId
		where CategoryID is null
	end
go
create trigger update_supplierID on Supplier
for update, insert
as
	declare @supplierId int
	begin
		select @supplierId = SupplierID from inserted
		update Product
		set SupplierID = @supplierId
		where SupplierID is null
	end

go
create trigger update_customerID on Customer
for update, insert
as
	declare @customerId int
	begin
		select @customerId = CustomerID from inserted
		update [Order]
		set CustomerID = @customerId
		where CustomerID is null
	end
go
create trigger update_employeeID on Employee
for update, insert
as
	declare @employeeId int
	begin
		select @employeeId = EmployeeID from inserted
		update [Order]
		set EmployeeID = @employeeId
		where EmployeeID is null
	end
go
