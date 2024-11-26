/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class DialogBox {
    
    public static void alert(Component parent, String message){
        JOptionPane.showMessageDialog(
                parent,
                message,
                "Thông báo", 
                JOptionPane.ERROR_MESSAGE
        );
    }
    
    public static void notice(Component parent, String message){
        JOptionPane.showMessageDialog(
                parent, 
                message,
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public static boolean confirm(Component parent, String message){
        int result = JOptionPane.showConfirmDialog(
                parent, 
                message, 
                "Thông báo", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        
        return result == JOptionPane.YES_NO_OPTION;
    }
    
    public static String input(Component parent, String message){
        return JOptionPane.showInputDialog(
                parent,
                message,
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
