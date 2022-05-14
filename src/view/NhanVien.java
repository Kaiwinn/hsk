package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.DAO_KhachHang;
import dao.DAO_KhoHang;
import dao.DAO_NhanVien;
import dao.DAO_Order;
import entity.Customer;
import entity.Employee;
import entity.Order;

public class NhanVien extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	// menuBar
	private JMenuBar menuBar;
	private JMenu menu_Ht, menu_Tg;
	private JMenuItem ht1, ht2, tg1, tg2;

	// tabbedPane
	private JTabbedPane tabbedPane;
	private JComponent comCustomer, comCategory, comOrder, comOrderDetail, comEmployee;

	// model-table
	private DefaultTableModel modelCustomer, modelOrder, modelOrderDetail, modelCategory;
	private JTable tableCustomer, tableOrder, tableOrderDetail, tableCategory;

	// Customer
	private JLabel lbTitleCustomer, lbCustomerID, lbCustomerName, lbCustomerPhone, lbCustomerAddress, lbCustomerEmail,
			lbCustomer_Search;
	private JTextField txtCustomerID, txtCustomerName, txtCustomerPhone, txtCustomerAddress, txtCustomerEmail,
			txtCustomer_Search;
	private JButton btnCustomer_Add, btnCustomer_Del, btnCustomer_Update, btnCustomer_Search;

	// Order
	private JLabel lbTitleOrder, lbOrderID, lbOrder_CustomerID, lbTitleEmployee, lbEmployeeID, lbEmployeeName,
			lbEmployeeSalary, lbEmployeeAddress, lbOrder_EmployeeID, lbOrderDate, lbRecieveDate,
			lbShipDate, lbRecievePlace, lbOrder_Search;
	private JDatePicker txtOrderDate, txtReceiveDate, txtShipDate;
	private JTextField txtOrderID, txtOrder_CustomerID, txtOrder_EmployeeID,
			txtReceivePlace, txtOrder_CustomerName, txtOrder_CustomerPhone, txtOrder_CustomerAddress, txtOrder_CustomerEmail,
			txtOrder_Search;
	private JButton btnOrder_Add, btnOrder_Del, btnOrder_Update, btnOrder_Search;

	// OrderDetail
	private JLabel lbTitleOrderDetail, lbOrderDetail_OrderID, lbOrderDetail_ProductID, lbQuantity, lbPrice, lbDiscount,
			lbOrderDetail_Search;
	private JTextField txtOrderDetail_OrderID, txtOrderDetail_ProductID, txtPrice, txtQuantity, txtDiscount,
			txtOrderDetail_Search;
	private JButton btnOrderDetail_Add, btnOrderDetail_Del, btnOrderDetail_Update, btnOrderDetail_Search;

	// Category
	private JLabel lbTitleCategory, lbCategory_Search;
	private JTextField txtCaterogy_Search;
	private JButton btnCategory_Search;

	// Employee
	private JTextField txtEmployeeID, txtEmployeeName, txtEmployeeSalary, txtEmployeeAddress;

	public NhanVien(String username) {
		setTitle("Quản lý bán hàng linh kiện điện tử");
		setSize(1000, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		creat_gui(username);

	}

	private void creat_gui(String username) {
		// Year-Month-Day
		UtilDateModel modeOrderDate = new UtilDateModel();
		UtilDateModel modeReceiveDate = new UtilDateModel();
		UtilDateModel modeShipDate = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanelOrderDate = new JDatePanelImpl(modeOrderDate, p);
		JDatePanelImpl datePanelReceiveDate = new JDatePanelImpl(modeReceiveDate, p);
		JDatePanelImpl datePanelShipDate = new JDatePanelImpl(modeShipDate, p);
		txtOrderDate = new JDatePickerImpl(datePanelOrderDate, new DateLabelFormatter());
		txtReceiveDate = new JDatePickerImpl(datePanelReceiveDate, new DateLabelFormatter());
		txtShipDate = new JDatePickerImpl(datePanelShipDate, new DateLabelFormatter());

		// Thanh menu
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		// menu Hệ thống
		menu_Ht = new JMenu("Hệ thống");
		ht1 = new JMenuItem("Đăng xuất", new ImageIcon(getClass().getResource("/image/logout.png")));
		ht2 = new JMenuItem("Thoát", new ImageIcon(getClass().getResource("/image/exit.png")));
		ht1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
		ht2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		menu_Ht.add(ht1);
		menu_Ht.add(ht2);
		menuBar.add(menu_Ht);
		// menu Trợ giúp
		menu_Tg = new JMenu("Trợ giúp");
		tg1 = new JMenuItem("Nội dung", new ImageIcon(getClass().getResource("/image/noidung.png")));
		tg2 = new JMenuItem("Giới thiệu", new ImageIcon(getClass().getResource("/image/help.png")));
		menu_Tg.add(tg1);
		menu_Tg.add(tg2);
		menuBar.add(menu_Tg);

		// TabbedPane
		tabbedPane = new JTabbedPane();
		this.add(tabbedPane);
		comCustomer = new JPanel();
		comOrder = new JPanel();
		comOrderDetail = new JPanel();
		comCategory = new JPanel();
		comEmployee = new JPanel();
		tabbedPane.setBackground(new java.awt.Color(203,
				255, 243));
		tabbedPane.add("Thông tin khách hàng", comCustomer);
		tabbedPane.add("Đơn hàng", comOrder);
		tabbedPane.add("Chi tiết đơn hàng", comOrderDetail);
		tabbedPane.add("Kho hàng", comCategory);
		tabbedPane.add("Thông Tin Nhân viên ", comEmployee);

		// icon
		tabbedPane.setIconAt(0, new ImageIcon(getClass().getResource(
				"/image/customer.png")));
		tabbedPane.setIconAt(1, new ImageIcon(getClass().getResource(
				"/image/order.png")));
		tabbedPane.setIconAt(2, new ImageIcon(getClass().getResource(
				"/image/detail.png")));
		tabbedPane.setIconAt(3, new ImageIcon(getClass().getResource(
				"/image/store.png")));
		tabbedPane.setIconAt(4, new ImageIcon(getClass().getResource(
				"/image/info.png")));

		// Thông tin khách hàng
		comCustomer.setLayout(new BorderLayout());
		JPanel northCustomer = new JPanel();
		comCustomer.add(northCustomer, BorderLayout.NORTH);
		northCustomer.setLayout(null);
		northCustomer.setPreferredSize(new Dimension(0, 160));

		northCustomer.add(lbTitleCustomer = new JLabel("Thông Tin Khách Hàng"));
		lbTitleCustomer.setFont(new Font("arial", Font.BOLD, 25));
		lbTitleCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cus32.png")));
		northCustomer.add(lbCustomerID = new JLabel("Mã khách hàng: "));
		northCustomer.add(lbCustomerName = new JLabel("Tên khách hàng: "));
		northCustomer.add(lbCustomerAddress = new JLabel("Địa chỉ: "));
		northCustomer.add(lbCustomerPhone = new JLabel("Số điện thoại: "));
		northCustomer.add(lbCustomerEmail = new JLabel("Email: "));

		northCustomer.add(txtCustomerID = new JTextField());
		northCustomer.add(txtCustomerName = new JTextField());
		northCustomer.add(txtCustomerAddress = new JTextField());
		northCustomer.add(txtCustomerPhone = new JTextField());
		northCustomer.add(txtCustomerEmail = new JTextField());

		int x1 = 90, x2 = 230, x3 = 530, x4 = 620, w = 230, h = 26;
		lbTitleCustomer.setBounds(330, 10, 400, 30);
		lbCustomerID.setBounds(x1, 60, w, h);
		txtCustomerID.setBounds(x2, 60, w, h);

		lbCustomerName.setBounds(x1, 90, w, h);
		txtCustomerName.setBounds(x2, 90, w, h);
		lbCustomerAddress.setBounds(x3, 90, w, h);
		txtCustomerAddress.setBounds(x4, 90, w, h);

		lbCustomerPhone.setBounds(x1, 120, w, h);
		txtCustomerPhone.setBounds(x2, 120, w, h);
		lbCustomerEmail.setBounds(x3, 120, w, h);
		txtCustomerEmail.setBounds(x4, 120, w, h);

		JPanel centerCustomer = new JPanel();
		comCustomer.add(centerCustomer, BorderLayout.CENTER);
		modelCustomer = new DefaultTableModel();
		tableCustomer = new JTable(modelCustomer);
		modelCustomer.addColumn("Mã khách hàng");
		modelCustomer.addColumn("Tên khách hàng");
		modelCustomer.addColumn("Địa chỉ");
		modelCustomer.addColumn("Số điện thoại");
		modelCustomer.addColumn("Email");
		tableCustomer.setRowHeight(30);
		JScrollPane scrollCustomer = new JScrollPane(tableCustomer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollCustomer.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));
		scrollCustomer.setPreferredSize(new Dimension(990, 300));
		centerCustomer.add(scrollCustomer, BorderLayout.CENTER);

		JPanel southCustomer = new JPanel();
		comCustomer.add(southCustomer, BorderLayout.SOUTH);
		JSplitPane splitCustomer;
		southCustomer.add(splitCustomer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT), BorderLayout.SOUTH);
		splitCustomer.setResizeWeight(0.1);
		splitCustomer.setPreferredSize(new Dimension(990, 44));

		JPanel pnCustomer_Search = new JPanel();
		JPanel pnCustomer_Funt = new JPanel();
		JLabel imgCustomer_Search = new JLabel(new ImageIcon(getClass().getResource("/image/search.png")));
		pnCustomer_Search.add(imgCustomer_Search);

		lbCustomer_Search = new JLabel("Mã khách hàng cần tìm: ");
		pnCustomer_Search.add(lbCustomer_Search);
		txtCustomer_Search = new JTextField(15);
		pnCustomer_Search.add(txtCustomer_Search);
		pnCustomer_Search.add(btnCustomer_Search = new JButton("Tìm"));
		pnCustomer_Funt.add(btnCustomer_Add = new JButton("Thêm"));
		pnCustomer_Funt.add(btnCustomer_Del = new JButton("Xóa"));
		pnCustomer_Funt.add(btnCustomer_Update = new JButton("Cập nhật"));
		btnCustomer_Add.setBackground(new Color(86, 187, 241));
		btnCustomer_Add.setForeground(new Color(255, 255, 255));
		btnCustomer_Add.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnCustomer_Update.setBackground(new Color(107, 203, 119));
		btnCustomer_Update.setForeground(new Color(255, 255, 255));
		btnCustomer_Update.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnCustomer_Search.setBackground(new Color(255, 141, 41));
		btnCustomer_Search.setForeground(new Color(255, 255, 255));
		btnCustomer_Search.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnCustomer_Del.setBackground(new Color(255, 73, 73));
		btnCustomer_Del.setForeground(new Color(255, 255, 255));
		btnCustomer_Del.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnCustomer_Del.setBorderPainted(false);
		btnCustomer_Add.setBorderPainted(false);
		btnCustomer_Update.setBorderPainted(false);
		btnCustomer_Search.setBorderPainted(false);
		txtCustomer_Search.setPreferredSize(new Dimension(200, 28));
		splitCustomer.add(pnCustomer_Search);
		splitCustomer.add(pnCustomer_Funt);
		Border borderCustomer = BorderFactory.createLineBorder(Color.GRAY);
		pnCustomer_Funt.setBorder(borderCustomer);
		pnCustomer_Search.setBorder(borderCustomer);

		// Đơn hàng
		comOrder.setLayout(new BorderLayout());
		JPanel northOrder = new JPanel();
		comOrder.add(northOrder, BorderLayout.NORTH);
		northOrder.setLayout(null);
		northOrder.setPreferredSize(new Dimension(0, 180));

		northOrder.add(lbTitleOrder = new JLabel("Đơn hàng"));
		lbTitleOrder.setFont(new Font("arial", Font.BOLD, 25));
		lbTitleOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/order32.png")));
		northOrder.add(lbOrderID = new JLabel("Mã đơn hàng: "));
		northOrder.add(lbOrder_CustomerID = new JLabel("Mã khách hàng: "));
		JLabel lbOrder_CustomerName;
		northOrder.add(lbOrder_CustomerName = new JLabel("Tên khách hàng: "));
		JLabel lbOrder_CustomerPhone;
		northOrder.add(lbOrder_CustomerPhone = new JLabel("Số điện thoại: "));
		JLabel lbOrder_CustomerAddress;
		northOrder.add(lbOrder_CustomerAddress = new JLabel("Địa chỉ: "));
		JLabel lbOrder_CustomerEmail;
		northOrder.add(lbOrder_CustomerEmail = new JLabel("Email: "));
		northOrder.add(lbOrder_EmployeeID = new JLabel("Mã nhân viên: "));
		northOrder.add(lbOrderDate = new JLabel("Ngày đặt hàng: "));
		northOrder.add(lbRecieveDate = new JLabel("Ngày giao hàng: "));
		northOrder.add(lbShipDate = new JLabel("Ngày chuyển hàng: "));
		northOrder.add(lbRecievePlace = new JLabel("Nơi nhận hàng: "));

		northOrder.add(txtOrderID = new JTextField());
		northOrder.add(txtOrder_CustomerID = new JTextField());
		northOrder.add(txtOrder_CustomerName = new JTextField());
		northOrder.add(txtOrder_CustomerPhone = new JTextField());
		northOrder.add(txtOrder_CustomerAddress = new JTextField());
		northOrder.add(txtOrder_CustomerEmail = new JTextField());
		northOrder.add(txtOrder_EmployeeID = new JTextField());
		northOrder.add(txtReceivePlace = new JTextField());
		northOrder.add((Component) txtOrderDate);
		northOrder.add((Component) txtReceiveDate);
		northOrder.add((Component) txtShipDate);
		lbTitleOrder.setBounds(420, 10, 300, 30);
		lbOrderID.setBounds(20, 50, 180, h);
		txtOrderID.setBounds(110, 50, 180, h);
		lbOrder_CustomerID.setBounds(330, 50, 180, h);
		txtOrder_CustomerID.setBounds(430, 50, 180, h);
		lbOrder_CustomerName.setBounds(670, 50, 180, h);
		txtOrder_CustomerName.setBounds(790, 50, 180, h);

		lbOrder_CustomerAddress.setBounds(20, 80, 180, h);
		txtOrder_CustomerAddress.setBounds(110, 80, 180, h);
		lbOrder_CustomerPhone.setBounds(330, 80, 180, h);
		txtOrder_CustomerPhone.setBounds(430, 80, 180, h);
		lbOrder_CustomerEmail.setBounds(679, 80, 180, h);
		txtOrder_CustomerEmail.setBounds(790, 80, 180, h);

		lbOrder_EmployeeID.setBounds(20, 110, 180, h);
		txtOrder_EmployeeID.setBounds(110, 110, 180, h);
		lbRecievePlace.setBounds(330, 110, 180, h);
		txtReceivePlace.setBounds(430, 110, 180, h);
		lbOrderDate.setBounds(670, 110, 180, h);
		((Component) txtOrderDate).setBounds(790, 110, 180, h);

		lbRecieveDate.setBounds(20, 140, 180, h);
		((Component) txtReceiveDate).setBounds(110, 140, 180, h);
		lbShipDate.setBounds(330, 140, 180, h);
		((Component) txtShipDate).setBounds(430, 140, 180, h);

		JPanel centerOrder = new JPanel();
		comOrder.add(centerOrder, BorderLayout.CENTER);
		modelOrder = new DefaultTableModel();
		tableOrder = new JTable(modelOrder);
		modelOrder.addColumn("Mã đơn hàng");
		modelOrder.addColumn("Mã khách hàng");
		modelOrder.addColumn("Tên khách hàng");
		modelOrder.addColumn("Địa chỉ");
		modelOrder.addColumn("Số điện thoại");
		modelOrder.addColumn("Email");
		modelOrder.addColumn("Mã nhân viên");
		modelOrder.addColumn("Nơi nhận hàng");
		modelOrder.addColumn("Ngày đặt hàng");
		modelOrder.addColumn("Ngày giao hàng");
		modelOrder.addColumn("Ngày chuyển hàng");

		tableOrder.setRowHeight(30);
		JScrollPane scrollOrder = new JScrollPane(tableOrder, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollOrder.setBorder(BorderFactory.createTitledBorder("Danh sách đơn hàng"));
		scrollOrder.setPreferredSize(new Dimension(990, 270));
		centerOrder.add(scrollOrder, BorderLayout.CENTER);

		JPanel southOrder = new JPanel();
		JSplitPane splitOrder;
		comOrder.add(southOrder, BorderLayout.SOUTH);
		southOrder.add(splitOrder = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT), BorderLayout.SOUTH);
		splitOrder.setResizeWeight(0.1);
		splitOrder.setPreferredSize(new Dimension(990, 44));

		JPanel pnOrder_Search = new JPanel();
		JPanel pnOrder_Funt = new JPanel();
		JLabel imgOrder_Search = new JLabel(new ImageIcon(getClass().getResource("/image/search.png")));
		pnOrder_Search.add(imgOrder_Search);
		lbOrder_Search = new JLabel("Mã đơn hàng cần tìm: ");
		pnOrder_Search.add(lbOrder_Search);
		txtOrder_Search = new JTextField(15);
		pnOrder_Search.add(txtOrder_Search);
		pnOrder_Search.add(btnOrder_Search = new JButton("Tìm"));
		pnOrder_Funt.add(btnOrder_Add = new JButton("Thêm"));
		pnOrder_Funt.add(btnOrder_Del = new JButton("Xóa"));
		pnOrder_Funt.add(btnOrder_Update = new JButton("Cập nhật"));
		btnOrder_Add.setBackground(new Color(86, 187, 241));
		btnOrder_Add.setForeground(new Color(255, 255, 255));
		btnOrder_Add.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnOrder_Update.setBackground(new Color(107, 203, 119));
		btnOrder_Update.setForeground(new Color(255, 255, 255));
		btnOrder_Update.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnOrder_Search.setBackground(new Color(255, 141, 41));
		btnOrder_Search.setForeground(new Color(255, 255, 255));
		btnOrder_Search.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnOrder_Del.setBackground(new Color(255, 73, 73));
		btnOrder_Del.setForeground(new Color(255, 255, 255));
		btnOrder_Del.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnOrder_Del.setBorderPainted(false);
		btnOrder_Add.setBorderPainted(false);
		btnOrder_Update.setBorderPainted(false);
		btnOrder_Search.setBorderPainted(false);
		txtOrder_Search.setPreferredSize(new Dimension(200, 28));
		splitOrder.add(pnOrder_Search);
		splitOrder.add(pnOrder_Funt);
		Border borderOrder = BorderFactory.createLineBorder(Color.gray);
		pnOrder_Search.setBorder(borderOrder);
		pnOrder_Funt.setBorder(borderOrder);

		// Chi tiết đơn hàng
		comOrderDetail.setLayout(new BorderLayout());
		JPanel northOrDetail = new JPanel();
		comOrderDetail.add(northOrDetail, BorderLayout.NORTH);
		northOrDetail.setLayout(null);
		northOrDetail.setPreferredSize(new Dimension(0, 160));

		northOrDetail.add(lbTitleOrderDetail = new JLabel("Chi Tiết Đơn Hàng"));
		lbTitleOrderDetail.setFont(new Font("arial", Font.BOLD, 25));
		lbTitleOrderDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/de32.png")));
		northOrDetail.add(lbOrderDetail_OrderID = new JLabel("Mã đơn hàng: "));
		northOrDetail.add(lbOrderDetail_ProductID = new JLabel("Mã sản phẩm: "));
		northOrDetail.add(lbQuantity = new JLabel("Số lượng: "));
		northOrDetail.add(lbPrice = new JLabel("Giá: "));
		northOrDetail.add(lbDiscount = new JLabel("Khuyến mãi: "));

		northOrDetail.add(txtOrderDetail_OrderID = new JTextField());
		northOrDetail.add(txtOrderDetail_ProductID = new JTextField());
		northOrDetail.add(txtQuantity = new JTextField());
		northOrDetail.add(txtPrice = new JTextField());
		northOrDetail.add(txtDiscount = new JTextField());
		lbTitleOrderDetail.setBounds(350, 10, 300, 30);
		lbOrderDetail_OrderID.setBounds(x1, 60, w, h);
		txtOrderDetail_OrderID.setBounds(x2, 60, w, h);

		lbOrderDetail_ProductID.setBounds(x1, 90, w, h);
		txtOrderDetail_ProductID.setBounds(x2, 90, w, h);
		lbQuantity.setBounds(x3, 90, w, h);
		txtQuantity.setBounds(660, 90, w, h);

		lbPrice.setBounds(x1, 120, w, h);
		txtPrice.setBounds(x2, 120, w, h);
		lbDiscount.setBounds(x3, 120, w, h);
		txtDiscount.setBounds(660, 120, w, h);

		JPanel centerOrDetail = new JPanel();
		comOrderDetail.add(centerOrDetail, BorderLayout.CENTER);
		modelOrderDetail = new DefaultTableModel();
		tableOrderDetail = new JTable(modelOrderDetail);
		modelOrderDetail.addColumn("Mã đơn hàng");
		modelOrderDetail.addColumn("Mã sản phẩm");
		modelOrderDetail.addColumn("Số lượng");
		modelOrderDetail.addColumn("Giá");
		modelOrderDetail.addColumn("Khuyến mãi");
		tableOrderDetail.setRowHeight(30);
		JScrollPane scrollOrDetail = new JScrollPane(tableOrderDetail, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollOrDetail.setBorder(BorderFactory.createTitledBorder("Danh sách đơn hàng"));
		scrollOrDetail.setPreferredSize(new Dimension(990, 300));
		centerOrDetail.add(scrollOrDetail, BorderLayout.CENTER);

		JPanel southOrDetail = new JPanel();
		JSplitPane splitOrDetail;
		comOrderDetail.add(southOrDetail, BorderLayout.SOUTH);
		southOrDetail.add(splitOrDetail = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT), BorderLayout.SOUTH);
		splitOrDetail.setResizeWeight(0.1);
		splitOrDetail.setPreferredSize(new Dimension(990, 44));

		JPanel pnOrDetail_Search = new JPanel();
		JPanel pnOrDetail_Funt = new JPanel();
		JLabel imgOrderDetail_Search = new JLabel(new ImageIcon(getClass().getResource("/image/search.png")));
		pnOrDetail_Search.add(imgOrderDetail_Search);
		lbOrderDetail_Search = new JLabel("Mã đơn hàng cần tìm: ");
		pnOrDetail_Search.add(lbOrderDetail_Search);
		txtOrderDetail_Search = new JTextField(15);
		pnOrDetail_Search.add(txtOrderDetail_Search);
		pnOrDetail_Search.add(btnOrderDetail_Search = new JButton("Tìm"));
		pnOrDetail_Funt.add(btnOrderDetail_Add = new JButton("Thêm"));
		pnOrDetail_Funt.add(btnOrderDetail_Del = new JButton("Xóa"));
		pnOrDetail_Funt.add(btnOrderDetail_Update = new JButton("Cập nhật"));
		btnOrderDetail_Add.setBackground(new Color(86, 187, 241));
		btnOrderDetail_Add.setForeground(new Color(255, 255, 255));
		btnOrderDetail_Add.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnOrderDetail_Update.setBackground(new Color(107, 203, 119));
		btnOrderDetail_Update.setForeground(new Color(255, 255, 255));
		btnOrderDetail_Update.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnOrderDetail_Search.setBackground(new Color(255, 141, 41));
		btnOrderDetail_Search.setForeground(new Color(255, 255, 255));
		btnOrderDetail_Search.setFont(new java.awt.Font("Tahoma", 1, 12));
		btnOrderDetail_Del.setBackground(new Color(255, 73, 73));
		btnOrderDetail_Del.setForeground(new Color(255, 255, 255));
		btnOrderDetail_Del.setFont(new java.awt.Font("Tahoma", 1, 12));

		btnOrderDetail_Del.setBorderPainted(false);
		btnOrderDetail_Add.setBorderPainted(false);
		btnOrderDetail_Update.setBorderPainted(false);
		btnOrderDetail_Search.setBorderPainted(false);
		txtOrderDetail_Search.setPreferredSize(new Dimension(200, 28));
		splitOrDetail.add(pnOrDetail_Search);
		splitOrDetail.add(pnOrDetail_Funt);
		Border borderOrDetail = BorderFactory.createLineBorder(Color.gray);
		pnOrDetail_Search.setBorder(borderOrDetail);
		pnOrDetail_Funt.setBorder(borderOrDetail);

		// Kho hàng
		comCategory.setLayout(new BorderLayout());
		JPanel northCategory = new JPanel();
		comCategory.add(northCategory, BorderLayout.NORTH);
		northCategory.setLayout(null);
		northCategory.setPreferredSize(new Dimension(0, 160));
		JLabel imgCategory_Search = new JLabel(new ImageIcon(getClass().getResource("/image/search.png")));

		northCategory.add(lbTitleCategory = new JLabel("Kho Hàng"));
		lbTitleCategory.setFont(new Font("arial", Font.BOLD, 25));
		lbTitleCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/store32.png")));
		northCategory.add(imgCategory_Search);
		northCategory.add(lbCategory_Search = new JLabel("Mã sản phẩm cần tìm: "));
		northCategory.add(txtCaterogy_Search = new JTextField(15));
		northCategory.add(btnCategory_Search = new JButton("Tìm"));

		btnCategory_Search.setBackground(new Color(255, 141, 41));
		btnCategory_Search.setForeground(new Color(255, 255, 255));
		btnCategory_Search.setFont(new java.awt.Font("Tahoma", 1, 12));

		btnCategory_Search.setBorderPainted(false);

		lbTitleCategory.setBounds(420, 10, 300, 30);
		imgCategory_Search.setBounds(220, 80, 50, 32);
		lbCategory_Search.setBounds(270, 80, 200, 30);
		txtCaterogy_Search.setBounds(440, 80, 200, 30);
		btnCategory_Search.setBounds(650, 80, 80, 30);

		JPanel centerCategory = new JPanel();
		comCategory.add(centerCategory, BorderLayout.CENTER);
		modelCategory = new DefaultTableModel();
		tableCategory = new JTable(modelCategory);
		modelCategory.addColumn("Mã kho hàng");
		modelCategory.addColumn("Mã sản phẩm");
		modelCategory.addColumn("Giá");
		modelCategory.addColumn("Số lượng");
		modelCategory.addColumn("Tình trạng");
		tableCategory.setRowHeight(30);
		JScrollPane scrollCategory = new JScrollPane(tableCategory, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollCategory.setBorder(BorderFactory.createTitledBorder("Danh sách hàng còn trong kho"));
		scrollCategory.setPreferredSize(new Dimension(990, 350));
		centerCategory.add(scrollCategory, BorderLayout.CENTER);

		// Nhân viên
		comEmployee.setLayout(null);

		comEmployee.add(lbTitleEmployee = new JLabel("Nhân Viên" + " " + username));
		lbTitleEmployee.setFont(new Font("arial", Font.BOLD, 25));

		lbTitleEmployee.setBounds(410, 15, 300, 35);
		lbTitleEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/kai.png")));

		lbEmployeeID = new JLabel("Mã nhân viên: ");
		lbEmployeeName = new JLabel("Tên nhân viên: ");
		lbEmployeeAddress = new JLabel("Địa chỉ: ");
		lbEmployeeSalary = new JLabel("Lương: ");
		txtEmployeeID = new JTextField(15);
		txtEmployeeName = new JTextField(15);
		txtEmployeeAddress = new JTextField(15);
		txtEmployeeSalary = new JTextField(15);

		lbEmployeeID.setBounds(270, 100, 200, 30);
		txtEmployeeID.setBounds(440, 100, 200, 30);
		lbEmployeeName.setBounds(270, 140, 200, 30);
		txtEmployeeName.setBounds(440, 140, 200, 30);
		lbEmployeeAddress.setBounds(270, 180, 200, 30);
		txtEmployeeAddress.setBounds(440, 180, 200, 30);
		lbEmployeeSalary.setBounds(270, 220, 200, 30);
		txtEmployeeSalary.setBounds(440, 220, 200, 30);

		comEmployee.add(lbEmployeeID);
		comEmployee.add(lbEmployeeName);
		comEmployee.add(lbEmployeeAddress);
		comEmployee.add(lbEmployeeSalary);
		comEmployee.add(txtEmployeeID);
		comEmployee.add(txtEmployeeName);
		comEmployee.add(txtEmployeeAddress);
		comEmployee.add(txtEmployeeSalary);

		// Add sự kiện
		ht1.addActionListener(this);
		ht2.addActionListener(this);

		btnCustomer_Search.addActionListener(this);
		btnCustomer_Add.addActionListener(this);
		btnCustomer_Del.addActionListener(this);
		btnCustomer_Update.addActionListener(this);

		btnOrder_Search.addActionListener(this);
		btnOrder_Add.addActionListener(this);
		btnOrder_Del.addActionListener(this);
		btnOrder_Update.addActionListener(this);

		btnOrderDetail_Search.addActionListener(this);
		btnOrderDetail_Add.addActionListener(this);
		btnOrderDetail_Del.addActionListener(this);
		btnOrderDetail_Update.addActionListener(this);

		btnCategory_Search.addActionListener(this);

		// load data
		loadDataCustomer();
		loadDataOrder();
		loadDataOrderDetails();
		loadDataCategory();
		loadDataEmployee(username);

		tableCustomer.addMouseListener(this);
		tableOrder.addMouseListener(this);
		tableOrderDetail.addMouseListener(this);
		tableCategory.addMouseListener(this);

	}

	public class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return "";
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(ht1)) {
			new Login().setVisible(true);
			this.dispose();
		} else if (o.equals(ht2))
			System.exit(0);
		else if (o.equals(btnCustomer_Search))
			customer_search();
		else if (o.equals(btnOrder_Search) || o.equals(btnOrderDetail_Search))
			order_search();
		else if (o.equals(btnCategory_Search))
			category_search();
		else if (o.equals(btnCustomer_Del))
			customer_del();
		else if (o.equals(btnOrder_Del))
			order_del();
		else if (o.equals(btnOrderDetail_Del))
			orderDetails_del();
		else if (o.equals(btnCustomer_Add))
			customer_add();
		else if (o.equals(btnCustomer_Update))
			customer_update();
		else if (o.equals(btnOrder_Update))
			order_update();
		else if (o.equals(btnOrderDetail_Update))
			order_details_update();
		else if (o.equals(btnOrder_Add))
			order_add();
		else if (o.equals(btnOrderDetail_Add))
			orderdetails_add();
	}

	// Clear Form
	public void clearFormCustomer() {
		txtCustomerID.setText("");
		txtCustomerName.setText("");
		txtCustomerAddress.setText("");
		txtCustomerPhone.setText("");
		txtCustomerEmail.setText("");
	}

	public void clearFormOrder() {
		txtOrderID.setText("");
		txtOrder_CustomerID.setText("");
		txtOrder_CustomerName.setText("");
		txtOrder_CustomerAddress.setText("");
		txtOrder_CustomerPhone.setText("");
		txtOrder_CustomerEmail.setText("");
		txtOrder_EmployeeID.setText("");
		txtReceivePlace.setText("");
		((JDatePickerImpl) txtOrderDate).getJFormattedTextField().setText("");
		((JDatePickerImpl) txtShipDate).getJFormattedTextField().setText("");
		((JDatePickerImpl) txtReceiveDate).getJFormattedTextField().setText("");

	}

	public void clearFormOrderDetail() {
		txtOrderDetail_OrderID.setText("");
		txtOrderDetail_ProductID.setText("");
		txtQuantity.setText("");
		txtPrice.setText("");
		txtDiscount.setText("");
	}

	// load data
	public void loadDataCustomer() {
		ArrayList<Customer> getCustomer = DAO_KhachHang.getCustomer();
		try {
			for (int i = 0; i < getCustomer.size(); i++) {
				modelCustomer.addRow(new Object[] {
						getCustomer.get(i).getCustomerID(),
						getCustomer.get(i).getCustomerName(),
						getCustomer.get(i).getAddress(),
						getCustomer.get(i).getPhone(),
						getCustomer.get(i).getEmail() });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadDataOrder() {
		ArrayList<Order> getOrder = DAO_Order.getOrder();
		try {
			for (int i = 0; i < getOrder.size(); i++) {
				String startDate = getOrder.get(i).getOrderDate().toString();
				String[] startDateSplit = startDate.split(" ");
				String[] startDateSplit2 = startDateSplit[0].split("-");
				String startDate1 = startDateSplit2[2] + "/"
						+ startDateSplit2[1] + "/" + startDateSplit2[0];
				String shipDate = getOrder.get(i).getShipDate().toString();
				String[] shipDateSplit = shipDate.split(" ");
				String[] shipDateSplit2 = shipDateSplit[0].split("-");
				String shipDate1 = shipDateSplit2[2] + "/"
						+ shipDateSplit2[1] + "/" + shipDateSplit2[0];
				String endDate = getOrder.get(i).getEndDate().toString();
				String[] endDateSplit = endDate.split(" ");
				String[] endDateSplit2 = endDateSplit[0].split("-");
				String endDate1 = endDateSplit2[2] + "/" + endDateSplit2[1]
						+ "/" + endDateSplit2[0];
				modelOrder.addRow(new Object[] { getOrder.get(i).getOrderID(),
						getOrder.get(i).getCustomerID(),
						getOrder.get(i).getCustomerName(),
						getOrder.get(i).getCustomerAddress(),
						getOrder.get(i).getPhone(),
						getOrder.get(i).getEmail(),
						getOrder.get(i).getEmployeeID(),
						getOrder.get(i).getShipAddress(),
						startDate1,
						shipDate1,
						endDate1
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadDataOrderDetails() {
		ArrayList<Order> getOrderDetails = DAO_Order.getOrderDetails();
		try {
			for (Order order : getOrderDetails) {
				modelOrderDetail.addRow(new Object[] { order.getOrderID(),
						order.getProductID(), order.getQuantity(),
						order.getPrice(), order.getDiscount() + "%" });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadDataCategory() {
		ArrayList<entity.Category> list = DAO_KhoHang.getCategory();
		try {
			for (entity.Category category : list) {
				modelCategory.addRow(new Object[] { category.getCategoryID(),
						category.getProductID(),
						category.getPrice(), category.getQuantity(), category.getQuantity() < 10 ? "bán chạy" : "tồn kho" });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadDataEmployee(String username) {
		ArrayList<Employee> list = DAO_NhanVien.getOneEmployee(username);
		txtEmployeeID.setEnabled(false);
		txtEmployeeName.setEnabled(false);
		txtEmployeeAddress.setEnabled(false);
		txtEmployeeSalary.setEnabled(false);
		try {
			for (Employee employee : list) {
				txtEmployeeID.setText(employee.getEmployeeID() + "");
				txtEmployeeName.setText(employee.getEmployeeName());
				txtEmployeeAddress.setText(employee.getAddress());
				txtEmployeeSalary.setText(employee.getSalary() + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// search
	private void order_search() {
		String id = txtOrder_Search.getText();
		String idDetail = txtOrderDetail_Search.getText();
		if (idDetail.equals("")) {

			if (DAO_Order.findOrderById(id) <= 0) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy");
				tableOrder.clearSelection();
				txtOrder_Search.requestFocus();
			} else {
				JOptionPane.showMessageDialog(null, " tìm thấy");
				for (int i = 0; i < tableOrder.getRowCount(); i++) {
					if (tableOrder.getValueAt(i, 0).equals(DAO_Order.findOrderById(id))) {
						tableOrder.setRowSelectionInterval(i, i);
						break;
					}
				}
			}
		} else {
			if (DAO_Order.findOrderById(idDetail) <= 0) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy");
				tableOrderDetail.clearSelection();
				txtOrderDetail_Search.requestFocus();
			} else {
				JOptionPane.showMessageDialog(null, " tìm thấy");
				for (int i = 0; i < tableOrderDetail.getRowCount(); i++) {
					if (tableOrderDetail.getValueAt(i, 0).equals(DAO_Order.findOrderById(idDetail))) {
						tableOrderDetail.setRowSelectionInterval(i, i);
						break;
					}
				}
			}
		}
	}

	private void customer_search() {
		String id = txtCustomer_Search.getText();
		if (DAO_KhachHang.findCustomerById(id) <= 0) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy");
			tableCustomer.clearSelection();
			txtCustomer_Search.requestFocus();
		} else {
			JOptionPane.showMessageDialog(null, " tìm thấy");
			for (int i = 0; i < tableCustomer.getRowCount(); i++) {
				if (tableCustomer.getValueAt(i, 0).equals(DAO_KhachHang.findCustomerById(id))) {
					tableCustomer.setRowSelectionInterval(i, i);
					break;
				}
			}
		}
	}

	private void category_search() {
		String id = txtCaterogy_Search.getText();
		if (DAO_KhoHang.findCategoryById(id) <= 0) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy");
			tableCategory.clearSelection();
			txtCaterogy_Search.requestFocus();
		} else {
			JOptionPane.showMessageDialog(null, " tìm thấy");
			for (int i = 0; i < tableCategory.getRowCount(); i++) {
				if (tableCategory.getValueAt(i, 0).equals(DAO_KhoHang.findCategoryById(id))) {
					tableCategory.setRowSelectionInterval(i, i);
					break;
				}
			}
		}
	}

	// delete
	public void customer_del() {
		int row = tableCustomer.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Chưa chọn khách hàng");
			return;
		} else {
			int id = Integer.parseInt(tableCustomer.getValueAt(row, 0)
					.toString());
			int thongbao = JOptionPane.showConfirmDialog(null,
					"Bạn có chắc muốn xóa", "cảnh báo",
					JOptionPane.YES_NO_OPTION);
			if (thongbao == JOptionPane.YES_OPTION) {
				if (DAO_KhachHang.deleteCustomerById(id) == true) {
					JOptionPane.showMessageDialog(null, "Xóa thành công");
					modelCustomer.setRowCount(0);
					loadDataCustomer();
					clearFormCustomer();
				} else {
					JOptionPane.showMessageDialog(null, "Xóa thất bại");
				}
			}
		}
	}

	public void order_del() {
		int row = tableOrder.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Chưa chọn đơn hàng");
			return;
		} else {
			int id = Integer.parseInt(tableOrder.getValueAt(row, 0)
					.toString());
			int thongbao = JOptionPane.showConfirmDialog(null,
					"Bạn có chắc muốn xóa", "cảnh báo",
					JOptionPane.YES_NO_OPTION);
			if (thongbao == JOptionPane.YES_OPTION) {
				if (DAO_Order.deleteOrderById(id) == true) {
					JOptionPane.showMessageDialog(null, "Xóa thành công");
					modelOrder.setRowCount(0);
					loadDataOrder();
					clearFormOrder();
				} else {
					JOptionPane.showMessageDialog(null, "Xóa thất bại");
				}
			}
		}
	}

	public void orderDetails_del() {
		int row = tableOrderDetail.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Chưa chọn chi tiết đơn hàng");
			return;
		} else {

			int id = Integer.parseInt(tableOrderDetail.getValueAt(row, 0)
					.toString());
			int thongbao = JOptionPane.showConfirmDialog(null,
					"Bạn có chắc muốn xóa", "cảnh báo",
					JOptionPane.YES_NO_OPTION);
			if (thongbao == JOptionPane.YES_OPTION) {
				if (DAO_Order.deleteOrderById(id) == true) {
					JOptionPane.showMessageDialog(null, "Xóa thành công");
					clearFormOrderDetail();
					modelOrderDetail.setRowCount(0);
					loadDataOrderDetails();
					clearFormOrderDetail();
				} else {
					JOptionPane.showMessageDialog(null, "Xóa thất bại");
				}
			}
		}
	}

	// insert
	public void customer_add() {
		String id = txtCustomerID.getText();
		String name = txtCustomerName.getText();
		String phone = txtCustomerPhone.getText();
		String address = txtCustomerAddress.getText();
		String email = txtCustomerEmail.getText();
		if (id.equals("") || name.equals("") || phone.equals("") || address.equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
			return;
		} else {
			if (regexCustomer()) {
				int thongbao = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn thêm", "cảnh báo",
						JOptionPane.YES_NO_OPTION);
				if (thongbao == JOptionPane.YES_OPTION) {
					if (DAO_KhachHang.insertCustomer(id, name, phone, address, email) == true) {
						JOptionPane.showMessageDialog(null, "Thêm thành công");
						clearFormCustomer();
						modelCustomer.setRowCount(0);
						loadDataCustomer();
					} else {
						JOptionPane.showMessageDialog(null, "Thêm thất bại");
					}
				}
			}
		}
	}

	public void order_add() {
		String orderID = txtOrderID.getText();
		String customerID = txtOrder_CustomerID.getText();
		String customerName = txtOrder_CustomerName.getText();
		String address = txtOrder_CustomerAddress.getText();
		String phone = txtOrder_CustomerPhone.getText();
		String email = txtOrder_CustomerEmail.getText();
		String employeeID = txtOrder_EmployeeID.getText();
		String shipAddress = txtReceivePlace.getText();
		String orderDate = ((JDatePickerImpl) txtOrderDate).getJFormattedTextField().getText();
		String shipDate = ((JDatePickerImpl) txtShipDate).getJFormattedTextField().getText();
		String endDate = ((JDatePickerImpl) txtReceiveDate).getJFormattedTextField().getText();
		// 016
		if (orderID.equals("") || customerID.equals("") || customerName.equals("") || address.equals("")
				|| phone.equals("") || email.equals("") || employeeID.equals("") || shipAddress.equals("")
				|| orderDate.equals("") || shipDate.equals("") || endDate.equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
			return;
		} else {
			if (regexOrder()) {
				int thongbao = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn thêm",
						"cảnh báo",
						JOptionPane.YES_NO_OPTION);
				if (thongbao == JOptionPane.YES_OPTION) {
					for (int i = 0; i < tableOrder.getRowCount(); i++) {
						if (tableOrder.getValueAt(i, 1).toString().equals(customerID)) {
							if (!tableOrder.getValueAt(i, 6).toString().equals(employeeID)) {
								// employee khác customer trùng
								if (DAO_Order.insertOrder_Order_Employee(orderID, customerID, customerName,
										address, phone, email,
										employeeID,
										shipAddress, orderDate, shipDate, endDate) == true) {
									JOptionPane.showMessageDialog(null, "Thêm thành công");
									clearFormOrder();
									modelOrder.setRowCount(0);
									loadDataOrder();
									break;
								}
							} else {
								// employee và customer trùng
								if (DAO_Order.insertOrder_Order(orderID, customerID, customerName, address,
										phone,
										email,
										employeeID,
										shipAddress, orderDate, shipDate, endDate) == true) {
									JOptionPane.showMessageDialog(null, "Thêm thành công");
									clearFormOrder();
									modelOrder.setRowCount(0);
									loadDataOrder();
									break;
								} else {
									JOptionPane.showMessageDialog(null, "Thêm thất bại");
									break;
								}

							}
						} else if (tableOrder.getValueAt(i, 6).toString().equals(employeeID)) {
							if (!tableOrder.getValueAt(i, 1).toString().equals(customerID)
									&& tableOrder.getValueAt(i, 6).toString().equals(employeeID)) {
								// employee trùng customer khác
								if (DAO_Order.insertOrder_Order_Customer(orderID, customerID, customerName,
										address, phone, email,
										employeeID,
										shipAddress, orderDate, shipDate, endDate) == true) {
									JOptionPane.showMessageDialog(null, "Thêm thành công");
									clearFormOrder();
									modelOrder.setRowCount(0);
									loadDataOrder();
									break;
								} else {
									JOptionPane.showMessageDialog(null, "Thêm thất bại");
									break;
								}
							} else {

								// employee và customer trùng
								if (DAO_Order.insertOrder_Order(orderID, customerID, customerName, address,
										phone,
										email,
										employeeID,
										shipAddress, orderDate, shipDate, endDate) == true) {
									JOptionPane.showMessageDialog(null, "Thêm thành công");
									clearFormOrder();
									modelOrder.setRowCount(0);
									loadDataOrder();
									break;
								} else {
									JOptionPane.showMessageDialog(null, "Thêm thất bại");
									break;
								}
							}
						}
					}
				}
			}

		}

	}

	public void orderdetails_add() {
		String orderID = txtOrderDetail_OrderID.getText();
		String productID = txtOrderDetail_ProductID.getText();
		String quantity = txtQuantity.getText();
		String price = txtPrice.getText();
		String discount = txtDiscount.getText();
		if (orderID.equals("") || productID.equals("") || quantity.equals("") || price.equals("")
				|| discount.equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
			return;
		} else {
			if (regexOrderDetail()) {
				int thongbao = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn thêm", "cảnh báo",
						JOptionPane.YES_NO_OPTION);
				if (thongbao == JOptionPane.YES_OPTION) {
					if (DAO_Order.insertOrderDetails(orderID, productID, quantity, price, discount) == true) {
						JOptionPane.showMessageDialog(null, "Thêm thành công");
						clearFormOrderDetail();
						modelOrderDetail.setRowCount(0);
						loadDataOrderDetails();
					} else {
						JOptionPane.showMessageDialog(null, "Thêm thất bại");
					}
				}
			}
		}
	}

	// update
	public void customer_update() {
		String id = txtCustomerID.getText();
		String name = txtCustomerName.getText();
		String phone = txtCustomerPhone.getText();
		String address = txtCustomerAddress.getText();
		String email = txtCustomerEmail.getText();
		if (DAO_KhachHang.findCustomerById(id) <= 0) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng");
			return;
		} else {
			ArrayList<Customer> list = DAO_KhachHang.getCustomer();
			for (Customer customer : list) {
				if (customer.getCustomerID() == Integer.parseInt(id)) {
					if (regexCustomer()) {
						int thongbao = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật", "cảnh báo",
								JOptionPane.YES_NO_OPTION);
						if (thongbao == JOptionPane.YES_OPTION) {
							if (DAO_KhachHang.updateCustomer(id, name, phone, address, email) == true) {
								JOptionPane.showMessageDialog(null, "Cập nhật thành công");
								clearFormCustomer();
								modelCustomer.setRowCount(0);
								loadDataCustomer();
							} else {
								JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
							}
						}
					}
				}
			}
		}
	}

	public void order_update() {
		String orderID = txtOrderID.getText();
		String customerID = txtOrder_CustomerID.getText();
		String customerName = txtOrder_CustomerName.getText();
		String address = txtOrder_CustomerAddress.getText();
		String phone = txtOrder_CustomerPhone.getText();
		String email = txtOrder_CustomerEmail.getText();
		String employeeID = txtOrder_EmployeeID.getText();
		String shipAddress = txtReceivePlace.getText();
		String orderDate = ((JDatePickerImpl) txtOrderDate).getJFormattedTextField().getText();
		String shipDate = ((JDatePickerImpl) txtShipDate).getJFormattedTextField().getText();
		String endDate = ((JDatePickerImpl) txtReceiveDate).getJFormattedTextField().getText();
		if (DAO_Order.findOrderById(orderID) <= 0) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy đơn hàng");
			return;
		} else {
			ArrayList<Order> list = DAO_Order.getOrder();
			for (Order order : list) {
				if (order.getOrderID() == Integer.parseInt(orderID)
						&& order.getCustomerID() == Integer.parseInt(customerID)
						&& order.getCustomerName().equals(customerName)
						&& order.getCustomerAddress().equals(address)
						&& order.getPhone().equals(phone)
						&& order.getEmail().equals(email)
						&& order.getEmployeeID() == Integer.parseInt(employeeID)
						&& order.getShipAddress().equals(shipAddress)
						&& order.getOrderDate().equals(orderDate)
						&& order.getShipDate().equals(shipDate)
						&& order.getEndDate().equals(endDate)) {
					JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
					return;
				} else {
					if (regexOrder()) {
						int thongbao = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật", "cảnh báo",
								JOptionPane.YES_NO_OPTION);
						if (thongbao == JOptionPane.YES_OPTION) {
							if (DAO_Order.updateOrder(orderID, customerID, customerName, address,
									phone, email, employeeID, shipAddress, orderDate, shipDate, endDate) == true) {
								JOptionPane.showMessageDialog(null, "Cập nhật thành công");
								clearFormOrder();
								modelOrder.setRowCount(0);
								loadDataOrder();
								return;
							} else {
								JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
								return;
							}
						}
					}
				}
			}
		}

	}

	public void order_details_update() {
		String orderDetailID = txtOrderDetail_OrderID.getText();
		String productID = txtOrderDetail_ProductID.getText();
		String quantity = txtQuantity.getText();
		String price = txtPrice.getText();
		String discount = txtDiscount.getText();
		String[] arr = discount.split("%");
		String discount1 = arr[0];

		if (DAO_Order.findOrderById(orderDetailID) <= 0) {
			JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết đơn hàng");
			return;
		} else {
			ArrayList<Order> list = DAO_Order.getOrder();
			for (Order order : list) {
				if (order.getOrderID() == Integer.parseInt(orderDetailID)
						&& order.getProductID() == Integer.parseInt(productID)
						&& order.getQuantity() == Integer.parseInt(quantity)
						&& order.getPrice() == Integer.parseInt(price)
						&& order.getDiscount() == Integer.parseInt(discount)) {
					JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
					return;
				} else {
					if (regexOrderDetail()) {
						int thongbao = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật", "cảnh báo",
								JOptionPane.YES_NO_OPTION);
						if (thongbao == JOptionPane.YES_OPTION) {
							if (DAO_Order.updateOrderDetails(orderDetailID, productID, quantity, price, discount1) == true) {
								JOptionPane.showMessageDialog(null, "Cập nhật thành công");
								clearFormOrderDetail();
								modelOrderDetail.setRowCount(0);
								loadDataOrderDetails();
								return;
							} else {
								JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
								return;
							}
						}
					}
				}
			}
		}
	}

	// ValidData
	private boolean regexCustomer() {
		String customerID = txtCustomerID.getText().trim();
		String customerName = txtCustomerName.getText().trim();
		String customerPhone = txtCustomerPhone.getText().trim();
		String customerAddress = txtCustomerAddress.getText().trim();
		String customerEmail = txtCustomerEmail.getText().trim();
		if (!customerID.matches("^\\d{1,5}$")) {
			JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ");
			txtCustomerID.requestFocus();
			return false;
		} else if (!customerName.matches("(^[A-Z][a-z]+(\\s[A-Z][a-z]*)*){1,}$")) {
			JOptionPane.showMessageDialog(this, "Họ tên phải nhập đúng: Chữ cái đầu viết hoa và có ít nhất 2 kí tự");
			txtCustomerName.requestFocus();
			return false;
		} else if (!customerPhone.matches("^(0)\\d{7,9}$")) {
			JOptionPane.showMessageDialog(this, "Số điện thoại từ 8-10 số bắt đầu là số 0");
			txtCustomerPhone.requestFocus();
			return false;
		} else if (!customerAddress.matches("^[#.0-9a-zA-Z\\s,-]+$")) {
			JOptionPane.showMessageDialog(this, "Địa chỉ không hợp lệ");
			txtCustomerAddress.requestFocus();
			return false;
		} else if (!customerEmail.matches("[A-Za-z0-9_-]+(@gmail.com)$")) {
			JOptionPane.showMessageDialog(this, "Email theo dạng: taikhoan@gmail.com (không có ký tự đặc biệt)");
			txtCustomerEmail.requestFocus();
			return false;
		}
		return true;
	}

	private boolean regexOrder() {
		String orderID = txtOrderID.getText().trim();
		String order_CustomerID = txtOrder_CustomerID.getText().trim();
		String order_CustomerName = txtOrder_CustomerName.getText().trim();
		String order_CustomerAddress = txtOrder_CustomerAddress.getText().trim();
		String order_CustomerPhone = txtOrder_CustomerPhone.getText().trim();
		String order_CustomerEmail = txtOrder_CustomerEmail.getText().trim();
		String Order_EmployeeID = txtOrder_EmployeeID.getText().trim();
		String receivePlace = txtReceivePlace.getText().trim();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate orderDate = LocalDate.parse(((JDatePickerImpl) txtOrderDate).getJFormattedTextField().getText());
		LocalDate shipDate = LocalDate.parse(((JDatePickerImpl) txtShipDate).getJFormattedTextField().getText());
		LocalDate receiveDate = LocalDate.parse(((JDatePickerImpl) txtReceiveDate).getJFormattedTextField().getText());

		if (!orderID.matches("^\\d{1,5}$")) {
			JOptionPane.showMessageDialog(this, "Mã đơn hàng không hợp lệ");
			txtOrderID.requestFocus();
			return false;
		} else if (!order_CustomerID.matches("^\\d{1,5}$")) {
			JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ");
			txtOrder_CustomerID.requestFocus();
			return false;
		} else if (!order_CustomerName.matches("(^[A-Z][a-z]+(\\s[A-Z][a-z]*)*){1,}$")) {
			JOptionPane.showMessageDialog(this, "Họ tên phải nhập đúng: Chữ cái đầu viết hoa và có ít nhất 2 kí tự");
			txtOrder_CustomerName.requestFocus();
			return false;
		} else if (!order_CustomerPhone.matches("^(0)\\d{7,9}$")) {
			JOptionPane.showMessageDialog(this, "Số điện thoại từ 8-10 số bắt đầu là số 0");
			txtOrder_CustomerPhone.requestFocus();
			return false;
		} else if (!order_CustomerAddress.matches("^[#.0-9a-zA-Z\\s,-]+$")) {
			JOptionPane.showMessageDialog(this, "Địa chỉ không hợp lệ");
			txtOrder_CustomerAddress.requestFocus();
			return false;
		} else if (!order_CustomerEmail.matches("[A-Za-z0-9_-]+(@gmail.com)$")) {
			JOptionPane.showMessageDialog(this, "Email theo dạng: taikhoan@gmail.com (không có ký tự đặc biệt)");
			txtOrder_CustomerEmail.requestFocus();
			return false;
		} else if (!Order_EmployeeID.matches("^\\d{1,5}$")) {
			JOptionPane.showMessageDialog(this, "Mã nhân viên không hợp lệ");
			txtOrder_EmployeeID.requestFocus();
			return false;
		} else if (!receivePlace.matches("^[#.0-9a-zA-Z\\s,-]+$")) {
			JOptionPane.showMessageDialog(this, "Nơi nhận không hợp lệ");
			txtReceivePlace.requestFocus();
			return false;
		} else if (orderDate.isAfter(LocalDate.now())) {
			JOptionPane.showMessageDialog(this, "Ngày đặt phải trước ngày hiện tại");
			((Component) txtOrderDate).requestFocus();
			return false;
		} else if (receiveDate.isBefore(orderDate)) {
			JOptionPane.showMessageDialog(this, "Ngày giao phải sau ngày đặt");
			((Component) txtReceiveDate).requestFocus();
			return false;
		} else if (shipDate.isBefore(orderDate)) {
			JOptionPane.showMessageDialog(this, "Ngày chuyển phải sau ngày đặt");
			((Component) txtShipDate).requestFocus();
			return false;
		}
		return true;
	}

	private boolean regexOrderDetail() {
		String OrderDetail_OrderID = txtOrderDetail_OrderID.getText().trim();
		String OrderDetail_ProductID = txtOrderDetail_ProductID.getText().trim();
		String quantity = txtQuantity.getText().trim();
		String price = txtPrice.getText().trim();
		String discount = txtDiscount.getText().trim();
		if (!OrderDetail_OrderID.matches("^\\d{1,5}$")) {
			JOptionPane.showMessageDialog(this, "Mã đơn hàng không hợp lệ");
			txtOrderDetail_OrderID.requestFocus();
			return false;
		} else if (!OrderDetail_ProductID.matches("^\\d{1,5}$")) {
			JOptionPane.showMessageDialog(this, "Mã sản phẩm không hợp lệ");
			txtOrderDetail_ProductID.requestFocus();
			return false;
		} else if (Integer.parseInt(quantity) < 0 || quantity.length() < 0) {
			JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ");
			txtQuantity.requestFocus();
			return false;
		} else if (Double.parseDouble(price) < 0 || price.length() < 0) {
			JOptionPane.showMessageDialog(this, "Giá không hợp lệ");
			txtPrice.requestFocus();
			return false;
		} else if (Double.parseDouble(discount) < 0 || discount.length() < 0) {
			JOptionPane.showMessageDialog(this, "Khuyến mãi không hợp lệ");
			txtDiscount.requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getComponent();
		if (o == tableCustomer) {
			int row = tableCustomer.getSelectedRow();
			txtCustomerID.setText(tableCustomer.getValueAt(row, 0).toString());
			txtCustomerName.setText(tableCustomer.getValueAt(row, 1).toString());
			txtCustomerAddress.setText(tableCustomer.getValueAt(row, 2).toString());
			txtCustomerPhone.setText(tableCustomer.getValueAt(row, 3).toString());
			txtCustomerEmail.setText(tableCustomer.getValueAt(row, 4).toString());
		} else if (o == tableOrder) {
			int row = tableOrder.getSelectedRow();
			txtOrderID.setText(tableOrder.getValueAt(row, 0).toString());
			txtOrder_CustomerID.setText(tableOrder.getValueAt(row, 1).toString());
			txtOrder_CustomerName.setText(tableOrder.getValueAt(row, 2).toString());
			txtOrder_CustomerAddress.setText(tableOrder.getValueAt(row, 3).toString());
			txtOrder_CustomerPhone.setText(tableOrder.getValueAt(row, 4).toString());
			txtOrder_CustomerEmail.setText(tableOrder.getValueAt(row, 5).toString());
			txtOrder_EmployeeID.setText(tableOrder.getValueAt(row, 6).toString());
			txtReceivePlace.setText(tableOrder.getValueAt(row, 7).toString());
			String orderDate = tableOrder.getValueAt(row, 8).toString();
			String[] orderDateArr = orderDate.split("/");
			String orderDateFormat = orderDateArr[2] + "-" + orderDateArr[1] + "-" + orderDateArr[0];
			((JDatePickerImpl) txtOrderDate).getJFormattedTextField().setText(orderDateFormat);
			String shipDate = tableOrder.getValueAt(row, 9).toString();
			String[] shipDateArr = shipDate.split("/");
			String shipDateFormat = shipDateArr[2] + "-" + shipDateArr[1] + "-" + orderDateArr[0];
			((JDatePickerImpl) txtShipDate).getJFormattedTextField().setText(shipDateFormat);
			String endDate = tableOrder.getValueAt(row, 9).toString();
			String[] endDateArr = endDate.split("/");
			String endDateFormat = endDateArr[2] + "-" + endDateArr[1] + "-" + orderDateArr[0];
			((JDatePickerImpl) txtReceiveDate).getJFormattedTextField().setText(endDateFormat);
		} else if (o == tableOrderDetail) {
			int row = tableOrderDetail.getSelectedRow();
			txtOrderDetail_OrderID.setText(tableOrderDetail.getValueAt(row, 0).toString());
			txtOrderDetail_ProductID.setText(tableOrderDetail.getValueAt(row, 1).toString());
			txtQuantity.setText(tableOrderDetail.getValueAt(row, 2).toString());
			txtPrice.setText(tableOrderDetail.getValueAt(row, 3).toString());
			txtDiscount.setText(tableOrderDetail.getValueAt(row, 4).toString());
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
