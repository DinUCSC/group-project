/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pilgrimscrutinizer;

import java.awt.HeadlessException;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.*;
import javax.swing.JOptionPane;
//import Gui.Login;

public class DBOperation {
    
    String url = "jdbc:mysql://localhost:3306/plgrimscrutinizer";
    String usernamel = "root";
    String passwordl = "";
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ResultSet lg = null;
    
    public int login(String username, String password) {
        // TODO add your handling code here:
        try {
            con = (Connection) DriverManager.getConnection(url, this.usernamel, this.passwordl);
            String sql;        // TODO add your handling code here:
            sql = "SELECT Name,Password,Emptype FROM user WHERE Username=?";
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                
                
                if (password.equals(rs.getString(2)) && "Admin Staff".equals(rs.getString(3))) {
                    //JOptionPane.showMessageDialog(null, "password is correct , admin");
                    return 1;
                } else if (password.equals(rs.getString(2)) && "Regular Staff".equals(rs.getString(3))) {
                    //JOptionPane.showMessageDialog(null, "password is correct , regular");
                    return 2;
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Password..!");
                    return 3;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Username..!");
                return 4;
                
            }
            
            
        } catch (SQLException | HeadlessException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Network Error..!");
            return 5;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
            
        }
    }
    
    public int checkUsername(String username) {
        try {
            con = (Connection) DriverManager.getConnection(url, this.usernamel, this.passwordl);
            String query = "SELECT Username FROM user";
            pst = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (username.equals(rs.getString(1))) {
                    return 0;
                    
                }
            }
            return 1;
            
        } catch (Exception e) {
            System.out.print(e);
            return 2;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
            
        }
    }
    
    public boolean addNewUser(UserDetails ud) {
        try {
            con = (Connection) DriverManager.getConnection(url, this.usernamel, this.passwordl);
            String query = "INSERT INTO user VALUES(?,?,?,?,?,?,?,?)";
            pst = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(query);
            pst.setInt(1, ud.getEmpID());
            pst.setString(2, ud.getEmployeeType());
            pst.setString(3, ud.getName());
            pst.setString(4, ud.getAddress());
            pst.setInt(5, ud.getMobile());
            pst.setString(6, ud.getNic());
            pst.setString(7, ud.getUsername());
            pst.setString(8, ud.getPassword());
            pst.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.print(ex);
            return false;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
    }
    
    public int checkNIC(String NIC) {
        try {
            con = (Connection) DriverManager.getConnection(url, this.usernamel, this.passwordl);
            String query = "SELECT NIC FROM user";
            pst = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (NIC.equals(rs.getString(1))) {
                    return 0;
                    
                }
            }
            return 1;
            
        } catch (Exception e) {
            System.out.print(e);
            return 2;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
            
        }
    }
    
    public UserDetails getUserDetails(String NIC) {
        UserDetails ud = new UserDetails();
        try {
            con = (Connection) DriverManager.getConnection(url, this.usernamel, this.passwordl);
            String query = "SELECT * FROM user WHERE NIC =?";
            pst = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(query);
            pst.setString(1, NIC);
            rs = pst.executeQuery();
            while (rs.next()) {
                ud.setEmpID(rs.getInt(1));
                ud.setEmployeeType(rs.getString(2));
                ud.setName(rs.getString(3));
                ud.setAddress(rs.getString(4));
                ud.setMobile(rs.getInt(5));
                ud.setNic(rs.getString(6));
                ud.setUsername(rs.getString(7));
            }
            return ud;
            
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }
    
    public boolean editUser(UserDetails user, String Nic) {
        try {
            con = (Connection) DriverManager.getConnection(url, this.usernamel, this.passwordl);
            String query;
            query = "UPDATE user SET Emptype = ?,Name=?,Address=?,Mobile=?,NIC=? WHERE NIC=?";
            pst = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(query);
            pst.setString(1, user.getEmployeeType());
            pst.setString(2, user.getName());
            pst.setString(3, user.getAddress());
            pst.setInt(4, user.getMobile());
            pst.setString(5, user.getNic());
            pst.setString(6, Nic);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
    public boolean changePassword(String username, String newPassword) {
        try {
            con = (Connection) DriverManager.getConnection(url, this.usernamel, this.passwordl);
            String query;
            query = "UPDATE user SET Password = ? WHERE Username=?";
            pst = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(query);
            pst.setString(1, newPassword);
            pst.setString(2, username);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
}
