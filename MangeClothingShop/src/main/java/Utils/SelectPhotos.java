/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ADMIN
 */
public class SelectPhotos {
    private JFileChooser file = new JFileChooser();
    
    public void selectPhotos(JLabel lable) {
        String defaultPath = "src\\main\\java\\Photos";
        file.setCurrentDirectory(new File(defaultPath));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "jpeg");
        file.setFileFilter(filter);
        if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            String filePath = file.getSelectedFile().getName();
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Photos/" + filePath));
            Image image = imageIcon.getImage();
            image.getScaledInstance(lable.getWidth(), lable.getHeight(), 0);
            lable.setIcon((imageIcon));
        }
    }
    
    public String getPhotoName() {
        String image;
        
        try {
                image = file.getSelectedFile().getName();
        } catch (NullPointerException e){
                image = "";
        }
        
        return image;
    }
    
    public void setImage(JLabel lable, String imageName) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Photos/" + imageName));
        lable.setIcon(imageIcon);
    }
}
