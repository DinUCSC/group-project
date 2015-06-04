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
            if (rs.next()){
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
            }else {
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
}
