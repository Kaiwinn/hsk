package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import dao.DAO_Login;

public class Login extends JFrame implements ActionListener, MouseListener {
  private JLabel lblLogin, lblUsername, lblPassword;
  private JTextField txtUsername;
  private JPasswordField txtPassword;
  private JButton btnLogin, btnCancel;

  public Login() {
    setTitle("Login");
    setSize(300, 220);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(null);
    setResizable(false);
    ui();
  }

  public void ui() {
    lblLogin = new JLabel("Login");
    lblLogin.setForeground(java.awt.Color.BLUE);
    lblLogin.setFont(new java.awt.Font("Tahoma", 1, 20));
    lblLogin.setBounds(120, 10, 100, 30);
    add(lblLogin);

    lblUsername = new JLabel("Username");
    lblUsername.setBounds(10, 50, 100, 30);
    add(lblUsername);

    lblPassword = new JLabel("Password");
    lblPassword.setBounds(10, 90, 100, 30);
    add(lblPassword);

    txtUsername = new JTextField();
    txtUsername.setBounds(120, 50, 150, 30);
    add(txtUsername);

    txtPassword = new JPasswordField();
    txtPassword.setBounds(120, 90, 150, 30);
    add(txtPassword);

    btnLogin = new JButton("Login", new ImageIcon(getClass().getResource("/image/login.png")));
    btnLogin.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Login");
    btnLogin.getActionMap().put("Login", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String username = txtUsername.getText();
        char[] password = txtPassword.getPassword();
        String pass = new String(password);
        if (username.equals("admin") && pass.equals("admin")) {
          dispose();
          JOptionPane.showMessageDialog(null, "Login Successfully");
          new Admin().setVisible(true);
        } else if (DAO_Login.checkLogin(username, pass) == true || username.equals("1") && pass.equals("1")) {
          dispose();
          JOptionPane.showMessageDialog(null, "Login Successfully");
          new NhanVien(username).setVisible(true);
        } else {
          JOptionPane.showMessageDialog(null, "Username or Password is incorrect");
        }
      }
    });

    btnLogin.setBounds(20, 130, 110, 30);
    add(btnLogin);

    btnCancel = new JButton("Cancel", new ImageIcon(getClass().getResource("/image/exit.png")));
    btnCancel.setBounds(160, 130, 100, 30);

    btnCancel.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(
        javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0), "Escape");
    btnCancel.getActionMap().put("Escape", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    add(btnCancel);

    btnLogin.addActionListener(this);
    btnCancel.addActionListener(this);
  }

  public static void main(String[] args) {
    new Login().setVisible(true);
  }

  @Override
  public void mouseClicked(MouseEvent e) {

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

  @Override
  public void actionPerformed(ActionEvent e) {
    Object o = e.getSource();
    if (o.equals(btnLogin)) {
      String username = txtUsername.getText();
      char[] password = txtPassword.getPassword();
      String pass = new String(password);
      if (username.equals("admin") && pass.equals("admin")) {
        this.dispose();
        JOptionPane.showMessageDialog(null, "Login Successfully");
        new Admin().setVisible(true);
      } else if (DAO_Login.checkLogin(username, pass) == true) {
        this.dispose();
        JOptionPane.showMessageDialog(null, "Login Successfully");
        new NhanVien(username).setVisible(true);
      } else {
        JOptionPane.showMessageDialog(null, "Username or Password is incorrect");
      }
    } else if (o.equals(btnCancel)) {
      System.exit(0);
    }
  }
}