package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import dao.DAO_DoanhThu;
import dao.DAO_KhachHang;
import dao.DAO_KhoHang;
import dao.DAO_NhanVien;
import dao.DAO_Order;
import entity.Customer;
import entity.Employee;
import entity.Order;
import entity.Revenue;

public class Admin extends JFrame implements ActionListener, MouseListener {
  // menu bar
  private JMenuBar menuBar;
  private JMenu menu_Ht, menu_Tg;
  private JMenuItem ht1, ht2, tg1, tg2;

  // customer
  private JLabel lbTitleCustomer;
  private JTextField txtCustomer_Search;
  private JButton btnCustomer_Del, btnCustomer_Search;

  // category
  private JLabel lbCategoryID, lbCategoryName, lbCategoryProductID, lbCategoryProductName, lbCategoryQuantity,
      lbCategoryPrice, lbCategorySupplierID, lbCategorySupplierName, lbCategoryAddress, lbCategoryCity,
      lbTitleCategory;
  private JTextField txtCategoryID, txtCategoryName, txtCategoryProductID,
      txtCategoryProductName, txtCategoryQuantity,
      txtCategoryPrice, txtCategorySupplierID, txtCategorySupplierName,
      txtCategoryAddress, txtCategoryCity,
      txtCategory_Search;
  private JButton btnCategory_Del, btnCategory_Add, btnCategory_Update,
      btnCategory_Search;

  // order
  private JLabel lbTitleOrder, lbVIPCustomer, lbVIPCustomerName;
  private JTextField txtOrder_Search;
  private JButton btnOrder_Search;

  // employee
  private JLabel lbEmployeeID, lbEmployeeName, lbEmployeeAddress,
      lbEmployeeSalary, lbTitleEmployee;
  private JTextField txtEmployeeID, txtEmployeeName, txtEmployeeAddress,
      txtEmployeeSalary, txtEmployee_Search;
  private JButton btnEmployee_Del, btnEmployee_Add, btnEmployee_Update,
      btnEmployee_Search;

  private JComboBox<String> employeeComboBox;
  private DefaultComboBoxModel<String> employeeDfComboBox;

  // revenue
  private JLabel lbRevenueSearch, lbRevenueOfDay, lbRevenueOfMonth,
      lbTitleRevenue;
  private JTextField txtRevenueOfDay, txtRevenueOfMonth;
  private JButton btnRevenue_Search, showAllRevenue;
  JDatePicker datePicker;

  // model
  private DefaultTableModel modelCustomer, modelCategory, modelOrder,
      modelEmployee, modelRevenue;

  // table
  private JTable tableCustomer, tableCategory, tableOrder, tableEmployee,
      tableRevenue;

  // panel for jtabbedPane
  private JPanel customer, category, order, employee,
      revenue, report;

  public Admin() {
    setTitle("Quản lý bán hàng linh kiện điện tử");
    setSize(1000, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    setResizable(false);
    JTabbedPane tp = new JTabbedPane();
    tp.setBackground(new java.awt.Color(194, 255, 249));
    tp.setBounds(10, 20, 960, 530);

    menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);
    // menu Hệ thống
    menu_Ht = new JMenu("Hệ thống");
    ht1 = new JMenuItem("Đăng xuất", new ImageIcon(getClass().getResource(
        "/image/logout.png")));
    ht2 = new JMenuItem("Thoát", new ImageIcon(getClass().getResource(
        "/image/exit.png")));
    ht1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
        InputEvent.CTRL_DOWN_MASK));
    ht2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
        InputEvent.ALT_DOWN_MASK));
    menu_Ht.add(ht1);
    menu_Ht.add(ht2);
    menuBar.add(menu_Ht);
    // menu Trợ giúp
    menu_Tg = new JMenu("Trợ giúp");
    tg1 = new JMenuItem("Nội dung", new ImageIcon(getClass().getResource(
        "/image/noidung.png")));
    tg2 = new JMenuItem("Giới thiệu", new ImageIcon(getClass().getResource(
        "/image/help.png")));
    menu_Tg.add(tg1);
    menu_Tg.add(tg2);
    menuBar.add(menu_Tg);

    // customer
    customer = new JPanel();
    customer.setLayout(null);
    lbTitleCustomer = new JLabel("Thông tin khách hàng");
    lbTitleCustomer.setFont(new java.awt.Font("Tahoma", 1, 30));
    lbTitleCustomer.setForeground(new java.awt.Color(0, 0, 255));
    lbTitleCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cus32.png")));
    JPanel customerCenter = new JPanel();
    txtCustomer_Search = new JTextField(20);
    btnCustomer_Search = new JButton("Tìm kiếm");
    txtCustomer_Search.setPreferredSize(new Dimension(50, 28));
    btnCustomer_Del = new JButton("Xóa");
    btnCustomer_Search.setBackground(new Color(255, 141, 41));
    btnCustomer_Search.setForeground(new Color(255, 255, 255));
    btnCustomer_Search.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnCustomer_Del.setBackground(new Color(255, 73, 73));
    btnCustomer_Del.setForeground(new Color(255, 255, 255));
    btnCustomer_Del.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnCustomer_Del.setBorderPainted(false);
    btnCustomer_Search.setBorderPainted(false);
    customerCenter.add(txtCustomer_Search);
    customerCenter.add(btnCustomer_Search);
    customerCenter.add(btnCustomer_Del);

    lbTitleCustomer.setBounds(290, 10, 500, 50);
    customerCenter.setBounds(300, 50, 400, 50);

    String[] customerHeader = { "Mã khách hàng", "Tên khách hàng",
        "Địa chỉ", "SDT", "Email" };
    modelCustomer = new DefaultTableModel(customerHeader, 0);
    tableCustomer = new JTable(modelCustomer);
    JScrollPane scrollPaneCustomer = new JScrollPane(tableCustomer,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneCustomer.setBounds(10, 100, 930, 400);
    customer.add(lbTitleCustomer);
    customer.add(customerCenter);
    customer.add(scrollPaneCustomer);

    // category
    category = new JPanel();
    category.setLayout(null);
    lbTitleCategory = new JLabel("Kho Hàng");
    lbTitleCategory.setFont(new java.awt.Font("Tahoma", 1, 30));
    lbTitleCategory.setForeground(new java.awt.Color(0, 0, 255));
    lbTitleCategory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/store32.png")));
    lbCategoryProductID = new JLabel("Mã sản phẩm");
    lbCategoryProductName = new JLabel("Tên sản phẩm");
    lbCategoryQuantity = new JLabel("Số lượng");
    lbCategoryPrice = new JLabel("Giá");
    lbCategoryID = new JLabel("Mã kho");
    lbCategoryName = new JLabel("Tên kho");
    lbCategorySupplierID = new JLabel("Mã nhà cung cấp");
    lbCategorySupplierName = new JLabel("Tên nhà cung cấp");
    lbCategoryAddress = new JLabel("Địa chỉ");
    lbCategoryCity = new JLabel("Thành phố");
    txtCategoryProductID = new JTextField();
    txtCategoryProductName = new JTextField();
    txtCategoryQuantity = new JTextField();
    txtCategoryPrice = new JTextField();
    txtCategoryID = new JTextField();
    txtCategoryName = new JTextField();
    txtCategorySupplierID = new JTextField();
    txtCategorySupplierName = new JTextField();
    txtCategoryAddress = new JTextField();
    txtCategoryCity = new JTextField();

    JPanel categoryCenter = new JPanel();
    btnCategory_Del = new JButton("Xóa");
    btnCategory_Add = new JButton("Thêm");
    btnCategory_Update = new JButton("Cập nhật");
    btnCategory_Search = new JButton("Tìm kiếm");
    btnCategory_Add.setBackground(new Color(86, 187, 241));
    btnCategory_Add.setForeground(new Color(255, 255, 255));
    btnCategory_Add.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnCategory_Update.setBackground(new Color(107, 203, 119));
    btnCategory_Update.setForeground(new Color(255, 255, 255));
    btnCategory_Update.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnCategory_Search.setBackground(new Color(255, 141, 41));
    btnCategory_Search.setForeground(new Color(255, 255, 255));
    btnCategory_Search.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnCategory_Del.setBackground(new Color(255, 73, 73));
    btnCategory_Del.setForeground(new Color(255, 255, 255));
    btnCategory_Del.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnCategory_Del.setBorderPainted(false);
    btnCategory_Add.setBorderPainted(false);
    btnCategory_Update.setBorderPainted(false);
    btnCategory_Search.setBorderPainted(false);

    txtCategory_Search = new JTextField(20);
    txtCategory_Search.setPreferredSize(new Dimension(200, 28));
    categoryCenter.add(btnCategory_Del);
    categoryCenter.add(btnCategory_Add);
    categoryCenter.add(btnCategory_Update);
    categoryCenter.add(txtCategory_Search);
    categoryCenter.add(btnCategory_Search);
    int widthLb = 100;
    int widthTxt = 170;
    int height = 30;
    int y1 = 70;
    int y2 = y1 + height + 10;
    int y3 = y2 + height + 10;
    int y4 = y3 + height + 10;
    int y5 = y4 + height + 10;
    int y6 = y5 + height + 30;
    lbTitleCategory.setBounds(330, 10, 500, 50);
    lbCategoryProductID.setBounds(30, y1, widthLb, height);
    txtCategoryProductID.setBounds(140, y1, widthTxt, height);
    lbCategoryProductName.setBounds(330, y1, widthLb, height);
    txtCategoryProductName.setBounds(440, y1, widthTxt, height);
    lbCategoryQuantity.setBounds(620, y1, widthLb, height);
    txtCategoryQuantity.setBounds(720, y1, widthTxt, height);
    lbCategoryPrice.setBounds(30, y2, widthLb, height);
    txtCategoryPrice.setBounds(140, y2, widthTxt, height);
    lbCategoryID.setBounds(330, y2, widthLb, height);
    txtCategoryID.setBounds(440, y2, widthTxt, height);
    lbCategoryName.setBounds(620, y2, widthLb, height);
    txtCategoryName.setBounds(720, y2, widthTxt, height);
    lbCategorySupplierID.setBounds(30, y3, widthLb, height);
    txtCategorySupplierID.setBounds(140, y3, widthTxt, height);
    lbCategorySupplierName.setBounds(330, y3, widthLb, height);
    txtCategorySupplierName.setBounds(440, y3, widthTxt, height);
    lbCategoryAddress.setBounds(620, y3, widthLb, height);
    txtCategoryAddress.setBounds(720, y3, widthTxt, height);
    lbCategoryCity.setBounds(30, y4, widthLb, height);
    txtCategoryCity.setBounds(140, y4, widthTxt, height);
    categoryCenter.setBounds(50, y5, 700, 50);

    String[] categoryHeader = { "Mã sản phẩm",
        "Tên sản phẩm", "Số lượng", "Giá", "Mã kho", "Tên kho", "Mã NCC", "Tên NCC",
        "Địa chỉ", "Thành phố" };
    modelCategory = new DefaultTableModel(categoryHeader, 0);
    tableCategory = new JTable(modelCategory);

    JScrollPane scrollPaneCategory = new JScrollPane(tableCategory,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneCategory.setBounds(10, y6, 930, 200);
    category.add(lbTitleCategory);
    category.add(lbCategoryName);
    category.add(txtCategoryName);
    category.add(lbCategoryProductID);
    category.add(txtCategoryProductID);
    category.add(lbCategoryProductName);
    category.add(txtCategoryProductName);
    category.add(lbCategoryQuantity);
    category.add(txtCategoryQuantity);
    category.add(lbCategoryPrice);
    category.add(txtCategoryPrice);
    category.add(lbCategoryID);
    category.add(txtCategoryID);
    category.add(lbCategorySupplierID);
    category.add(txtCategorySupplierID);
    category.add(lbCategorySupplierName);
    category.add(txtCategorySupplierName);
    category.add(lbCategoryAddress);
    category.add(txtCategoryAddress);
    category.add(lbCategoryCity);
    category.add(txtCategoryCity);
    category.add(categoryCenter);
    category.add(scrollPaneCategory);

    // order
    order = new JPanel();
    order.setLayout(null);
    lbTitleOrder = new JLabel("Đơn hàng");
    lbTitleOrder.setFont(new java.awt.Font("Tahoma", 1, 30));
    lbTitleOrder.setForeground(new java.awt.Color(0, 0, 255));
    lbTitleOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/order32.png")));

    txtOrder_Search = new JTextField();
    btnOrder_Search = new JButton("Tìm kiếm");

    String[] orderHeader = { "Mã đơn", "Mã khách hàng", "Tên khách hàng",
        "Mã sản phẩm", "Tên sản phẩm", "Số lượnng", "Giá", "SDT",
        "Ngày đặt", "Ngày nhận" };
    modelOrder = new DefaultTableModel(orderHeader, 0);
    tableOrder = new JTable(modelOrder);
    JScrollPane scrollPaneOrder = new JScrollPane(tableOrder,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    lbVIPCustomer = new JLabel("khách hàng có tiền mua cao nhất:");
    lbVIPCustomerName = new JLabel("");

    lbVIPCustomerName.setForeground(new Color(0, 0, 255));

    lbTitleOrder.setBounds(360, 10, 500, 50);
    txtOrder_Search.setBounds(10, 70, 200, 30);
    btnOrder_Search.setBounds(220, 70, 100, 30);
    scrollPaneOrder.setBounds(10, 110, 930, 350);
    lbVIPCustomer.setBounds(10, 470, 200, 20);
    lbVIPCustomerName.setBounds(220, 470, 200, 20);

    txtOrder_Search.setPreferredSize(new Dimension(50, 28));
    btnOrder_Search.setBackground(new Color(255, 141, 41));
    btnOrder_Search.setForeground(new Color(255, 255, 255));
    btnOrder_Search.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnOrder_Search.setBorderPainted(false);

    order.add(lbTitleOrder);
    order.add(txtOrder_Search);
    order.add(btnOrder_Search);
    order.add(scrollPaneOrder);
    order.add(lbVIPCustomer);
    order.add(lbVIPCustomerName);

    // employee
    employee = new JPanel();
    employee.setLayout(null);
    lbTitleEmployee = new JLabel("Nhân viên");
    lbTitleEmployee.setFont(new java.awt.Font("Tahoma", 1, 30));
    lbTitleEmployee.setForeground(new java.awt.Color(0, 0, 255));
    lbTitleEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/staff32.png")));
    lbEmployeeID = new JLabel("Mã nhân viên");
    lbEmployeeName = new JLabel("Tên nhân viên");
    lbEmployeeAddress = new JLabel("Địa chỉ");
    lbEmployeeSalary = new JLabel("Lương");
    txtEmployeeID = new JTextField();
    txtEmployeeName = new JTextField();
    txtEmployeeAddress = new JTextField();
    txtEmployeeSalary = new JTextField();
    JPanel employeeCenter = new JPanel();
    btnEmployee_Del = new JButton("Xóa");
    btnEmployee_Add = new JButton("Thêm");
    btnEmployee_Update = new JButton("Cập nhật");
    btnEmployee_Search = new JButton("Tìm kiếm");
    txtEmployee_Search = new JTextField(20);
    txtEmployee_Search.setPreferredSize(new Dimension(200, 28));
    btnEmployee_Add.setBackground(new Color(86, 187, 241));
    btnEmployee_Add.setForeground(new Color(255, 255, 255));
    btnEmployee_Add.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnEmployee_Update.setBackground(new Color(107, 203, 119));
    btnEmployee_Update.setForeground(new Color(255, 255, 255));
    btnEmployee_Update.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnEmployee_Search.setBackground(new Color(255, 141, 41));
    btnEmployee_Search.setForeground(new Color(255, 255, 255));
    btnEmployee_Search.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnEmployee_Del.setBackground(new Color(255, 73, 73));
    btnEmployee_Del.setForeground(new Color(255, 255, 255));
    btnEmployee_Del.setFont(new java.awt.Font("Tahoma", 1, 12));
    btnEmployee_Del.setBorderPainted(false);
    btnEmployee_Add.setBorderPainted(false);
    btnEmployee_Update.setBorderPainted(false);
    btnEmployee_Search.setBorderPainted(false);

    employeeCenter.add(btnEmployee_Del);
    employeeCenter.add(btnEmployee_Add);
    employeeCenter.add(btnEmployee_Update);
    employeeCenter.add(txtEmployee_Search);
    employeeCenter.add(btnEmployee_Search);
    employeeDfComboBox = new DefaultComboBoxModel<String>();
    employeeDfComboBox.addElement("Lương tăng dần");
    employeeDfComboBox.addElement("Lương giảm dần");
    employeeComboBox = new JComboBox<String>(employeeDfComboBox);

    lbTitleEmployee.setBounds(340, 10, 500, 50);
    lbEmployeeID.setBounds(10, 70, 100, 30);
    txtEmployeeID.setBounds(120, 70, 200, 30);
    lbEmployeeName.setBounds(360, 70, 100, 30);
    txtEmployeeName.setBounds(470, 70, 200, 30);
    lbEmployeeAddress.setBounds(10, 120, 100, 30);
    txtEmployeeAddress.setBounds(120, 120, 200, 30);
    lbEmployeeSalary.setBounds(360, 120, 100, 30);
    txtEmployeeSalary.setBounds(470, 120, 200, 30);
    employeeCenter.setBounds(50, 170, 600, 50);
    employeeComboBox.setBounds(700, 170, 200, 30);

    String[] employeeHeader = { "Mã nhân viên", "Tên nhân viên", "Địa chỉ",
        "Lương" };
    modelEmployee = new DefaultTableModel(employeeHeader, 0);
    tableEmployee = new JTable(modelEmployee);

    JScrollPane scrollPaneEmployee = new JScrollPane(tableEmployee,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneEmployee.setBounds(10, 230, 930, 270);
    employee.add(lbTitleEmployee);
    employee.add(lbEmployeeID);
    employee.add(txtEmployeeID);
    employee.add(lbEmployeeName);
    employee.add(txtEmployeeName);
    employee.add(lbEmployeeAddress);
    employee.add(txtEmployeeAddress);
    employee.add(lbEmployeeSalary);
    employee.add(txtEmployeeSalary);
    employee.add(employeeCenter);
    employee.add(employeeComboBox);
    employee.add(scrollPaneEmployee);

    // revenue
    revenue = new JPanel();
    revenue.setLayout(null);
    lbTitleRevenue = new JLabel("Doanh thu");
    lbTitleRevenue.setFont(new java.awt.Font("Tahoma", 1, 30));
    lbTitleRevenue.setForeground(new java.awt.Color(0, 0, 255));
    lbTitleRevenue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/money32.png")));

    UtilDateModel modelng = new UtilDateModel();
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    JDatePanelImpl datePanel = new JDatePanelImpl(modelng, p);
    datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    lbRevenueSearch = new JLabel("Tìm kiếm theo ngày");
    btnRevenue_Search = new JButton("Tìm kiếm");
    showAllRevenue = new JButton("Xem tất cả");
    lbRevenueOfDay = new JLabel("Doanh thu ngày");
    lbRevenueOfMonth = new JLabel("Doanh thu tháng");
    txtRevenueOfDay = new JTextField();
    txtRevenueOfMonth = new JTextField();
    btnRevenue_Search.setBackground(new Color(255, 141, 41));
    btnRevenue_Search.setForeground(new Color(255, 255, 255));
    btnRevenue_Search.setFont(new java.awt.Font("Tahoma", 1, 12));
    showAllRevenue.setBackground(new Color(10, 161, 221));
    showAllRevenue.setForeground(new Color(255, 255, 255));
    showAllRevenue.setFont(new java.awt.Font("Tahoma", 1, 12));
    showAllRevenue.setBorderPainted(false);
    btnRevenue_Search.setBorderPainted(false);

    lbTitleRevenue.setBounds(360, 10, 500, 50);
    lbRevenueSearch.setBounds(10, 70, 120, 30);
    ((Component) datePicker).setBounds(140, 70, 200, 30);
    btnRevenue_Search.setBounds(350, 70, 100, 30);
    showAllRevenue.setBounds(460, 70, 120, 30);
    lbRevenueOfDay.setBounds(10, 120, 200, 30);
    txtRevenueOfDay.setBounds(140, 120, 200, 30);
    lbRevenueOfMonth.setBounds(360, 120, 200, 30);
    txtRevenueOfMonth.setBounds(490, 120, 200, 30);

    String[] revenueHeader = { "Mã hóa đơn", "Ngày đặt", "Thành tiền" };
    modelRevenue = new DefaultTableModel(revenueHeader, 0);
    tableRevenue = new JTable(modelRevenue);

    JScrollPane scrollPaneRevenue = new JScrollPane(tableRevenue,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneRevenue.setBounds(10, 200, 930, 350);
    revenue.add(lbTitleRevenue);
    revenue.add(lbRevenueSearch);
    revenue.add((Component) datePicker);
    revenue.add(btnRevenue_Search);
    revenue.add(showAllRevenue);
    revenue.add(lbRevenueOfDay);
    revenue.add(txtRevenueOfDay);
    revenue.add(lbRevenueOfMonth);
    revenue.add(txtRevenueOfMonth);
    revenue.add(scrollPaneRevenue);

    // report
    report = new JPanel();
    report.setLayout(null);

    JFreeChart chart = ChartFactory.createBarChart("Doanh thu", "Tháng",
        "Doanh thu", createDataset(), PlotOrientation.VERTICAL, true,
        true, false);
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setBounds(0, 0, 970, 500);
    report.add(chartPanel);

    tp.add("Khách hàng", customer);
    tp.add("Kho hàng", category);
    tp.add("Đơn hàng", order);
    tp.add("Nhân viên", employee);
    tp.add("Doanh thu", revenue);
    tp.add("Báo cáo", report);
    tp.setFont(new java.awt.Font("Tahoma", 1, 14));
    // icon

    tp.setIconAt(0, new ImageIcon(getClass().getResource(
        "/image/customer.png")));

    tp.setIconAt(1, new ImageIcon(getClass().getResource(
        "/image/store.png")));
    tp.setIconAt(2, new ImageIcon(getClass().getResource(
        "/image/order.png")));
    tp.setIconAt(3, new ImageIcon(getClass().getResource(
        "/image/staf.png")));
    tp.setIconAt(4, new ImageIcon(getClass().getResource(
        "/image/money.png")));
    tp.setIconAt(5, new ImageIcon(getClass().getResource(
        "/image/report.png")));

    add(tp);
    // add event
    ht1.addActionListener(this);
    ht2.addActionListener(this);
    btnCustomer_Del.addActionListener(this);
    btnCustomer_Search.addActionListener(this);
    btnCategory_Del.addActionListener(this);
    btnCategory_Add.addActionListener(this);
    btnCategory_Update.addActionListener(this);
    btnCategory_Search.addActionListener(this);
    btnOrder_Search.addActionListener(this);
    btnEmployee_Del.addActionListener(this);
    btnEmployee_Add.addActionListener(this);
    btnEmployee_Update.addActionListener(this);
    btnEmployee_Search.addActionListener(this);
    btnRevenue_Search.addActionListener(this);
    showAllRevenue.addActionListener(this);
    tableCustomer.addMouseListener(this);
    tableCategory.addMouseListener(this);
    tableOrder.addMouseListener(this);
    tableEmployee.addMouseListener(this);
    tableRevenue.addMouseListener(this);
    employeeComboBox.addActionListener(this);

    loadRevenue();
    loadDataOrder();
    loadDataCustomer();
    loadDataCategory();
    loadDataEmployee();
  }

  public class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "dd/MM/yyyy";
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

  private CategoryDataset createDataset() {

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    ArrayList<Revenue> getRevenue = DAO_DoanhThu.getRevenue();
    try {
      for (int i = 0; i < getRevenue.size(); i++) {
        // 2022-05-25 00:00:00.000
        String date = getRevenue.get(i).getOrderDate().toString();
        String[] dateSplit = date.split(" ");
        String[] dateSplit2 = dateSplit[0].split("-");
        String date2 = dateSplit2[2] + "/" + dateSplit2[1] + "/"
            + dateSplit2[0];
        String[] dateSplit3 = date2.split("/");
        String day = dateSplit3[0];
        String month = dateSplit3[1];
        dataset.addValue(getRevenue.get(i).getRevenue(), month, day);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return dataset;
  }

  public static void main(String[] args) {
    new Admin().setVisible(true);
  }

  @Override
  // add event
  public void actionPerformed(ActionEvent e) {
    Object o = e.getSource();
    if (o.equals(ht1)) {
      new Login().setVisible(true);
      this.dispose();
    } else if (o.equals(ht2))
      System.exit(0);
    else if (o.equals(btnEmployee_Search))
      employee_search();
    else if (o.equals(btnOrder_Search))
      order_search();
    else if (o.equals(btnCustomer_Search))
      customer_search();
    else if (o.equals(btnCategory_Search))
      category_search();
    else if (o.equals(btnRevenue_Search))
      revenue_search();
    else if (o.equals(showAllRevenue)) {
      modelRevenue.setRowCount(0);
      loadRevenue();
    } else if (o.equals(btnCustomer_Del))
      customer_del();
    else if (o.equals(btnCategory_Del))
      category_del();
    else if (o.equals(btnEmployee_Del))
      employee_del();
    else if (o.equals(btnCategory_Add))
      category_add();
    else if (o.equals(btnEmployee_Add))
      employee_add();
    else if (o.equals(btnEmployee_Update))
      employee_update();
    else if (o.equals(btnCategory_Update))
      category_update();
    else if (o.equals(employeeComboBox))
      employee_sort();

  }

  // sort
  public void employee_sort() {
    String salary = employeeComboBox.getSelectedItem().toString();
    if (salary.equals("Lương tăng dần")) {
      for (int i = 0; i < tableEmployee.getRowCount() - 1; i++) {
        for (int j = i + 1; j < tableEmployee.getRowCount(); j++) {
          if (Double.parseDouble(tableEmployee.getValueAt(i, 3).toString()) > Double
              .parseDouble(tableEmployee.getValueAt(j, 3).toString())) {
            // lấy ra hết giá trị của lương trước khi thay đổi
            Object[] rowData = { tableEmployee.getValueAt(i, 0),
                tableEmployee.getValueAt(i, 1),
                tableEmployee.getValueAt(i, 2), tableEmployee.getValueAt(i, 3) };
            tableEmployee.setValueAt(tableEmployee.getValueAt(j, 0), i, 0);
            tableEmployee.setValueAt(tableEmployee.getValueAt(j, 1), i, 1);
            tableEmployee.setValueAt(tableEmployee.getValueAt(j, 2), i, 2);
            tableEmployee.setValueAt(tableEmployee.getValueAt(j, 3), i, 3);
            tableEmployee.setValueAt(rowData[0], j, 0);
            tableEmployee.setValueAt(rowData[1], j, 1);
            tableEmployee.setValueAt(rowData[2], j, 2);
            tableEmployee.setValueAt(rowData[3], j, 3);
          }
        }
      }
    } else {
      for (int i = 0; i < tableEmployee.getRowCount() - 1; i++) {
        for (int j = i + 1; j < tableEmployee.getRowCount(); j++) {
          if (Double.parseDouble(tableEmployee.getValueAt(i, 3).toString()) < Double
              .parseDouble(tableEmployee.getValueAt(j, 3).toString())) {
            Object[] rowData = { tableEmployee.getValueAt(i, 0),
                tableEmployee.getValueAt(i, 1),
                tableEmployee.getValueAt(i, 2), tableEmployee.getValueAt(i, 3) };
            tableEmployee.setValueAt(tableEmployee.getValueAt(j, 0), i, 0);
            tableEmployee.setValueAt(tableEmployee.getValueAt(j, 1), i, 1);
            tableEmployee.setValueAt(tableEmployee.getValueAt(j, 2), i, 2);
            tableEmployee.setValueAt(tableEmployee.getValueAt(j, 3), i, 3);
            tableEmployee.setValueAt(rowData[0], j, 0);
            tableEmployee.setValueAt(rowData[1], j, 1);
            tableEmployee.setValueAt(rowData[2], j, 2);
            tableEmployee.setValueAt(rowData[3], j, 3);
          }
        }
      }
    }
  }

  // search
  private void employee_search() {
    String id = txtEmployee_Search.getText();
    if (DAO_NhanVien.findEmployeeById(id) <= 0) {
      JOptionPane.showMessageDialog(null, "Không tìm thấy");
      tableEmployee.clearSelection();
      txtEmployee_Search.requestFocus();
    } else {
      JOptionPane.showMessageDialog(null, " tìm thấy");
      for (int i = 0; i < tableEmployee.getRowCount(); i++) {
        if (tableEmployee.getValueAt(i, 0).equals(DAO_NhanVien.findEmployeeById(id))) {
          tableEmployee.setRowSelectionInterval(i, i);
          break;
        }
      }
    }
  }

  private void order_search() {
    String id = txtOrder_Search.getText();
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
    String id = txtCategory_Search.getText();
    if (DAO_KhoHang.findCategoryById(id) <= 0) {
      JOptionPane.showMessageDialog(null, "Không tìm thấy");
      tableCategory.clearSelection();
      txtCategory_Search.requestFocus();
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

  public void revenue_search() {
    String txtdate = ((JDatePickerImpl) datePicker).getJFormattedTextField().getText();
    String day = txtdate.substring(0, 2);
    ArrayList<Revenue> list = DAO_DoanhThu.findRevenueByDay(day);
    for (int i = 0; i < tableRevenue.getRowCount(); i++) {
      String dateOfRevenue = modelRevenue.getValueAt(i, 1).toString();
      if (list.size() > 0) {
        for (Revenue revenue : list) {
          String date = revenue.getOrderDate().toString();
          String[] dateSplit = date.split(" ");
          String[] dateSplit2 = dateSplit[0].split("-");
          String date2 = dateSplit2[2] + "/" + dateSplit2[1] + "/"
              + dateSplit2[0];
          if (date2.equals(dateOfRevenue)) {
            JOptionPane.showMessageDialog(null, "Tìm thấy");
            modelRevenue.setRowCount(0);
            modelRevenue.addRow(new Object[] { revenue.getOrderID(), date2,
                revenue.getRevenue() });
          }
        }
      } else {
        JOptionPane.showMessageDialog(null, "Không tìm thấy");
        modelRevenue.setRowCount(0);
        loadRevenue();
        break;
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
        } else {
          JOptionPane.showMessageDialog(null, "Xóa thất bại");
        }
      }
    }
  }

  public void category_del() {
    int row = tableCategory.getSelectedRow();
    if (row == -1) {
      JOptionPane.showMessageDialog(null, "Chưa chọn kho hàng");
      return;
    } else {
      int categoryID = Integer.parseInt(tableCategory.getValueAt(row, 0)
          .toString());
      int supplierID = Integer.parseInt(tableCategory.getValueAt(row, 6)
          .toString());
      int thongbao = JOptionPane.showConfirmDialog(null,
          "Bạn có chắc muốn xóa", "cảnh báo",
          JOptionPane.YES_NO_OPTION);
      if (thongbao == JOptionPane.YES_OPTION) {
        if (DAO_KhoHang.deleteCategoryById(categoryID, supplierID) == true) {
          JOptionPane.showMessageDialog(null, "Xóa thành công");
          modelCategory.setRowCount(0);
          loadDataCategory();
          clearFormCategory();
        } else {
          JOptionPane.showMessageDialog(null, "Xóa thất bại");
        }
      }
    }
  }

  public void employee_del() {
    int row = tableEmployee.getSelectedRow();
    if (row == -1) {
      JOptionPane.showMessageDialog(null, "Chưa chọn nhân viên");
      return;
    } else {
      int id = Integer.parseInt(tableEmployee.getValueAt(row, 0)
          .toString());
      int thongbao = JOptionPane.showConfirmDialog(null,
          "Bạn có chắc muốn xóa", "cảnh báo",
          JOptionPane.YES_NO_OPTION);
      if (thongbao == JOptionPane.YES_OPTION) {
        if (DAO_NhanVien.deleteEmployeeById(id) == true) {
          JOptionPane.showMessageDialog(null, "Xóa thành công");
          modelEmployee.setRowCount(0);
          loadDataEmployee();
          clearFormEmployee();
        } else {
          JOptionPane.showMessageDialog(null, "Xóa thất bại");
        }
      }
    }
  }

  // load data
  public void loadRevenue() {
    ArrayList<Revenue> getRevenue = DAO_DoanhThu.getRevenue();
    try {
      for (int i = 0; i < getRevenue.size(); i++) {
        String date = getRevenue.get(i).getOrderDate().toString();
        String[] dateSplit = date.split(" ");
        String[] dateSplit2 = dateSplit[0].split("-");
        String date2 = dateSplit2[2] + "/" + dateSplit2[1] + "/"
            + dateSplit2[0];
        modelRevenue.addRow(new Object[] {
            getRevenue.get(i).getOrderID(), date2,
            getRevenue.get(i).getRevenue() });
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    txtRevenueOfDay.setText(String.valueOf(DAO_DoanhThu
        .getMaxRevenueOfDay()));
    txtRevenueOfDay.setEditable(false);
    txtRevenueOfMonth.setText(String.valueOf(DAO_DoanhThu
        .getMaxRevenueOfMonth()));
    txtRevenueOfMonth.setEditable(false);

  }

  public void loadDataOrder() {
    ArrayList<Order> getOrder = DAO_Order.getOrderAdmin();
    try {
      for (int i = 0; i < getOrder.size(); i++) {
        String startDate = getOrder.get(i).getOrderDate().toString();
        String[] startDateSplit = startDate.split(" ");
        String[] startDateSplit2 = startDateSplit[0].split("-");
        String startDate1 = startDateSplit2[2] + "/"
            + startDateSplit2[1] + "/" + startDateSplit2[0];
        String endDate = getOrder.get(i).getEndDate().toString();
        String[] endDateSplit = endDate.split(" ");
        String[] endDateSplit2 = endDateSplit[0].split("-");
        String endDate1 = endDateSplit2[2] + "/" + endDateSplit2[1]
            + "/" + endDateSplit2[0];

        modelOrder.addRow(new Object[] { getOrder.get(i).getOrderID(),
            getOrder.get(i).getCustomerID(),
            getOrder.get(i).getCustomerName(),
            getOrder.get(i).getProductID(),
            getOrder.get(i).getProductName(),
            getOrder.get(i).getQuantity(),
            getOrder.get(i).getPrice(), getOrder.get(i).getPhone(),
            startDate1, endDate1 });
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    lbVIPCustomerName.setText(DAO_Order.getMaxCustomer());

  }

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

  public void loadDataCategory() {
    ArrayList<entity.Category> list = DAO_KhoHang.getCategory();
    try {
      for (entity.Category category : list) {
        modelCategory.addRow(new Object[] { category.getProductID(),
            category.getProductName(), category.getQuantity(),
            category.getPrice(), category.getCategoryID(),
            category.getCategoryName(), category.getSupplierID(),
            category.getSupplierName(), category.getAddress(),
            category.getCity() });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loadDataEmployee() {
    ArrayList<Employee> list = DAO_NhanVien.getEmployee();
    try {
      for (Employee employee : list) {
        modelEmployee.addRow(new Object[] { employee.getEmployeeID(),
            employee.getEmployeeName(), employee.getAddress(),
            employee.getSalary() });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // insert data
  public void category_add() {
    String productID = txtCategoryProductID.getText();
    String productName = txtCategoryProductName.getText();
    String quantity = txtCategoryQuantity.getText();
    String price = txtCategoryPrice.getText();
    String categoryID = txtCategoryID.getText();
    String categoryName = txtCategoryName.getText();
    String supplierID = txtCategorySupplierID.getText();
    String supplierName = txtCategorySupplierName.getText();
    String address = txtCategoryAddress.getText();
    String city = txtCategoryCity.getText();
    if (categoryID.equals("") || categoryName.equals("") || productID.equals("")
        || productName.equals("") || quantity.equals("")
        || price.equals("") || supplierID.equals("")
        || supplierName.equals("") || address.equals("")
        || city.equals("")) {
      JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");

      return;
    } else {
      if (regexCategory()) {
        int thongbao = JOptionPane.showConfirmDialog(null,
            "Bạn có chắc muốn thêm", "cảnh báo",
            JOptionPane.YES_NO_OPTION);
        if (thongbao == JOptionPane.YES_OPTION) {
          for (int i = 0; i < tableCategory.getRowCount(); i++) {
            if (tableCategory.getValueAt(i, 4).toString().equals(categoryID)) {
              if (tableCategory.getValueAt(i, 4).toString().equals(categoryID)
                  && !tableCategory.getValueAt(i, 6).toString().equals(supplierID)) {
                // supplier khác category trùng
                if (DAO_KhoHang.insertCategory_Product_Supplier(productID, productName, quantity, price, categoryID,
                    categoryName, supplierID, supplierName, address, city) == true) {
                  JOptionPane.showMessageDialog(null, "Thêm thành công");
                  clearFormCategory();
                  modelCategory.setRowCount(0);
                  loadDataCategory();
                  break;
                }
              } else {
                // supplier và category trùng
                if (DAO_KhoHang.insertCategory_Product(productID, productName, quantity, price, categoryID,
                    categoryName, supplierID, supplierName, address, city) == true) {
                  JOptionPane.showMessageDialog(null, "Thêm thành công");
                  clearFormCategory();
                  modelCategory.setRowCount(0);
                  loadDataCategory();
                  break;
                } else {
                  JOptionPane.showMessageDialog(null, "Thêm thất bại");
                  break;
                }
              }
            } else if (tableCategory.getValueAt(i, 6).toString().equals(supplierID)) {
              if (!tableCategory.getValueAt(i, 4).toString().equals(categoryID)
                  && tableCategory.getValueAt(i, 6).toString().equals(supplierID)) {
                // supplier trùng category khác
                if (DAO_KhoHang.insertCategory_Product_Category(productID, productName, quantity, price, categoryID,
                    categoryName, supplierID, supplierName, address, city) == true) {
                  JOptionPane.showMessageDialog(null, "Thêm thành công");
                  clearFormCategory();
                  modelCategory.setRowCount(0);
                  loadDataCategory();
                  break;
                } else {
                  JOptionPane.showMessageDialog(null, "Thêm thất bại");
                  break;
                }
              } else {
                if (!tableCategory.getValueAt(i, 4).toString().equals(categoryID)
                    && !tableCategory.getValueAt(i, 6).toString().equals(supplierID)) {
                  if (DAO_KhoHang.insertCategory_Product(productID, productName, quantity, price, categoryID,
                      categoryName, supplierID, supplierName, address, city) == true) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công");
                    clearFormCategory();
                    modelCategory.setRowCount(0);
                    loadDataCategory();
                    break;
                  } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại");
                    break;
                  }
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

  public void employee_add() {
    String employeeID = txtEmployeeID.getText();
    String employeeName = txtEmployeeName.getText();
    String address = txtEmployeeAddress.getText();
    String salary = txtEmployeeSalary.getText();
    if (employeeID.equals("") || employeeName.equals("") || address.equals("")
        || salary.equals("")) {
      JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
      return;
    } else {
      if (regexEmployee()) {
        int thongbao = JOptionPane.showConfirmDialog(null,
            "Bạn có chắc muốn thêm", "cảnh báo",
            JOptionPane.YES_NO_OPTION);
        if (thongbao == JOptionPane.YES_OPTION) {
          try {
            if (DAO_NhanVien.insertEmployee(employeeID, employeeName, address,
                salary) == true) {
              JOptionPane.showMessageDialog(null, "Thêm thành công");
              modelEmployee.setRowCount(0);
              loadDataEmployee();
              clearFormEmployee();
            } else {
              JOptionPane.showMessageDialog(null, "Thêm thất bại");
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  // vaidate
  public boolean regexCategory() {
    String categoryID = txtCategoryID.getText();
    String categoryName = txtCategoryName.getText();
    String productID = txtCategoryProductID.getText();
    String productName = txtCategoryProductName.getText();
    String quantity = txtCategoryQuantity.getText();
    String price = txtCategoryPrice.getText();
    String supplierID = txtCategorySupplierID.getText();
    String supplierName = txtCategorySupplierName.getText();
    String address = txtCategoryAddress.getText();
    String city = txtCategoryCity.getText();
    if (!categoryID.matches("[0-9]{1,5}")) {
      JOptionPane.showMessageDialog(null, "Mã loại sản phẩm không hợp lệ");
      return false;
    } else if (!categoryName.matches("[a-zA-Z0-9\\s]{1,50}")) {
      JOptionPane.showMessageDialog(null, "Tên loại sản phẩm không hợp lệ");
      return false;
    } else if (!productID.matches("[0-9]{1,5}")) {
      JOptionPane.showMessageDialog(null, "Mã sản phẩm không hợp lệ");
      return false;
    } else if (!productName.matches("[a-zA-Z0-9\\s]{1,50}")) {
      JOptionPane.showMessageDialog(null, "Tên sản phẩm không hợp lệ");
      return false;
    } else if (Integer.parseInt(quantity) < 0 || quantity.length() < 0) {
      JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ");
      return false;
    } else if (Double.parseDouble(price) < 0 || price.length() < 0) {
      JOptionPane.showMessageDialog(null, "Giá không hợp lệ");
      return false;
    } else if (!supplierID.matches("[0-9]{1,5}")) {
      JOptionPane.showMessageDialog(null, "Mã nhà cung cấp không hợp lệ");
      return false;
    } else if (!supplierName.matches("[a-zA-Z0-9\\s]{1,50}")) {
      JOptionPane.showMessageDialog(null, "Tên nhà cung cấp không hợp lệ");
      return false;
    } else if (!address.matches("^[#.0-9a-zA-Z\\s,-]+$")) {
      JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ");
      return false;
    } else if (!city.matches("[a-zA-Z\\s]{1,50}")) {
      JOptionPane.showMessageDialog(null, "Thành phố không hợp lệ");
      return false;
    } else
      return true;
  }

  public boolean regexEmployee() {
    String employeeID = txtEmployeeID.getText();
    String employeeName = txtEmployeeName.getText();
    String address = txtEmployeeAddress.getText();
    String salary = txtEmployeeSalary.getText();
    if (!employeeID.matches("[0-9]{1,5}")) {
      JOptionPane.showMessageDialog(null, "Mã nhân viên không hợp lệ");
      return false;
    } else if (!employeeName.matches("[a-zA-Z\\s]{1,50}")) {
      JOptionPane.showMessageDialog(null, "Tên nhân viên không hợp lệ");
      return false;
    } else if (!address.matches("^[#.0-9a-zA-Z\\s,-]+$")) {
      JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ");
      return false;
    } else if (Double.parseDouble(salary) < 0 || salary.length() < 0) {
      JOptionPane.showMessageDialog(null, "Lương không hợp lệ");
      return false;
    } else
      return true;
  }

  // cleart form
  public void clearFormCategory() {
    txtCategoryID.setText("");
    txtCategoryName.setText("");
    txtCategoryProductID.setText("");
    txtCategoryProductName.setText("");
    txtCategoryQuantity.setText("");
    txtCategoryPrice.setText("");
    txtCategorySupplierID.setText("");
    txtCategorySupplierName.setText("");
    txtCategoryAddress.setText("");
    txtCategoryCity.setText("");
  }

  public void clearFormEmployee() {
    txtEmployeeID.setText("");
    txtEmployeeName.setText("");
    txtEmployeeAddress.setText("");
    txtEmployeeSalary.setText("");
  }

  // update data
  public void category_update() {
    String productID = txtCategoryProductID.getText();
    String productName = txtCategoryProductName.getText();
    String quantity = txtCategoryQuantity.getText();
    String price = txtCategoryPrice.getText();
    String categoryID = txtCategoryID.getText();
    String categoryName = txtCategoryName.getText();
    String supplierID = txtCategorySupplierID.getText();
    String supplierName = txtCategorySupplierName.getText();
    String address = txtCategoryAddress.getText();
    String city = txtCategoryCity.getText();
    if (DAO_KhoHang.findProductById(productID) <= 0 || DAO_KhoHang.findCategoryById(categoryID) <= 0
        || DAO_KhoHang.findSupplierById(supplierID) <= 0) {
      JOptionPane.showMessageDialog(null, "Không tìm thấy mã này");
      return;
    } else {
      ArrayList<entity.Category> list = DAO_KhoHang.getCategory();
      try {
        for (entity.Category category : list) {
          if (category.getCategoryID() == Integer.parseInt(categoryID)
              && category.getCategoryName().equals(categoryName)
              && category.getProductID() == Integer.parseInt(productID)
              && category.getProductName().equals(productName)
              && category.getQuantity() == Integer.parseInt(quantity)
              && category.getPrice() == Double.parseDouble(price)
              && category.getSupplierID() == Integer.parseInt(supplierID)
              && category.getSupplierName().equals(supplierName) && category.getAddress().equals(address)
              && category.getCity().equals(city)) {
            JOptionPane.showMessageDialog(null, "Cập nhật không thành công");

          } else {
            if (regexCategory()) {
              int thongbao = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật", "cảnh báo",
                  JOptionPane.YES_NO_OPTION);
              if (thongbao == JOptionPane.YES_OPTION) {
                if (DAO_KhoHang.updateCategory(categoryID, categoryName, supplierID, supplierName,
                    address, city, productID, productName, quantity, price) == true) {
                  JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                  clearFormCategory();
                  modelCategory.setRowCount(0);
                  loadDataCategory();
                  return;
                } else {
                  JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
                  return;
                }
              }
            }
          }
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void employee_update() {
    String employeeID = txtEmployeeID.getText();
    String employeeName = txtEmployeeName.getText();
    String address = txtEmployeeAddress.getText();
    String salary = txtEmployeeSalary.getText();
    if (DAO_NhanVien.findEmployeeById(employeeID) <= 0) {
      JOptionPane.showMessageDialog(null, "Không tìm thấy mã này");
      return;
    } else {
      ArrayList<entity.Employee> list = DAO_NhanVien.getEmployee();
      try {
        for (entity.Employee employee : list) {
          if (employee.getEmployeeID() == Integer.parseInt(employeeID)
              && employee.getEmployeeName().equals(employeeName)
              && employee.getAddress().equals(address)
              && employee.getSalary() == Double.parseDouble(salary)) {
            JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
          } else {
            if (regexEmployee()) {
              int thongbao = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn cập nhật", "cảnh báo",
                  JOptionPane.YES_NO_OPTION);
              if (thongbao == JOptionPane.YES_OPTION) {
                if (DAO_NhanVien.updateEmployee(employeeID, employeeName, address, salary) == true) {
                  JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                  clearFormEmployee();
                  modelEmployee.setRowCount(0);
                  loadDataEmployee();
                  return;
                } else {
                  JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
                  return;
                }
              }
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent arg0) {
    Object o = arg0.getComponent();
    if (o == tableCategory) {
      int row = tableCategory.getSelectedRow();
      txtCategoryProductID.setText(tableCategory.getValueAt(row, 0).toString());
      txtCategoryProductName.setText(tableCategory.getValueAt(row, 1).toString());
      txtCategoryQuantity.setText(tableCategory.getValueAt(row, 2).toString());
      txtCategoryPrice.setText(tableCategory.getValueAt(row, 3).toString());
      txtCategoryID.setText(tableCategory.getValueAt(row, 4).toString());
      txtCategoryName.setText(tableCategory.getValueAt(row, 5).toString());
      txtCategorySupplierID.setText(tableCategory.getValueAt(row, 6).toString());
      txtCategorySupplierName.setText(tableCategory.getValueAt(row, 7).toString());
      txtCategoryAddress.setText(tableCategory.getValueAt(row, 8).toString());
      txtCategoryCity.setText(tableCategory.getValueAt(row, 9).toString());

    } else if (o == tableEmployee) {
      int row = tableEmployee.getSelectedRow();
      txtEmployeeID.setText(tableEmployee.getValueAt(row, 0).toString());
      txtEmployeeName.setText(tableEmployee.getValueAt(row, 1).toString());
      txtEmployeeAddress.setText(tableEmployee.getValueAt(row, 2).toString());
      txtEmployeeSalary.setText(tableEmployee.getValueAt(row, 3).toString());
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }
}
