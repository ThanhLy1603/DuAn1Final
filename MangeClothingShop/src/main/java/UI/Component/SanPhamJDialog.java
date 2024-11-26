/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Component;

import Interfaces.Panel;
import UI.Detail.ChiTietSPDetailJDialog;
import UI.Detail.LoaiSPDetailJDialog;
import UI.Detail.SanPhamDetailJDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ADMIN
 */
public class SanPhamJDialog extends javax.swing.JFrame implements Panel{
    /**
     * Creates new form SanPhamDJlogTest
     */
    public SanPhamJDialog() {
        initComponents();
        
        init();
    }
    
    public void init() {
        showPanelSanPham();
        showPanelChiTietSP();
        showLoaiSanPham();
        
        setLocationRelativeTo(null);
    }
    
    @Override
    public void showPanel(JPanel visiblePanel) {
        visiblePanel.setVisible(true);
    }
    
    @Override
    public void showDialogInPanel(JPanel targetPanel, JFrame dialog) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout()); // Thiết lập layout BorderLayout cho contentPanel

        // Thêm nội dung của dialog vào giữa contentPanel
        contentPanel.add(dialog.getContentPane(), BorderLayout.CENTER);

        // Đặt kích thước ưu tiên của contentPanel bằng kích thước của panel đích
        contentPanel.setPreferredSize(new Dimension(targetPanel.getWidth(), targetPanel.getHeight()));

        // Xóa tất cả các thành phần hiện có trong targetPanel
        targetPanel.removeAll();

        // Thiết lập layout cho targetPanel
        targetPanel.setLayout(new BorderLayout());

        // Thêm contentPanel vào targetPanel
        targetPanel.add(contentPanel, BorderLayout.CENTER);

        // Cập nhật lại giao diện của targetPanel để đảm bảo các thay đổi được áp dụng
        targetPanel.revalidate();
        targetPanel.repaint();

        // Điều chỉnh kích thước của dialog để vừa với nội dung
        dialog.pack();
    }
    
    private void showPanelSanPham() {
        SanPhamDetailJDialog dialog = new SanPhamDetailJDialog();
        
        showPanel(panelSanPham);
        showDialogInPanel(panelSanPham, dialog);
    }
    
    private void showPanelChiTietSP() {
        ChiTietSPDetailJDialog dialog = new ChiTietSPDetailJDialog();
        
        showPanel(pnlChiTietSanPham);
        showDialogInPanel(pnlChiTietSanPham, dialog);
    }
    
    private void showLoaiSanPham() {
        LoaiSPDetailJDialog dialog = new LoaiSPDetailJDialog();
        
        showPanel(pnlLoaiSanPham);
        showDialogInPanel(pnlLoaiSanPham, dialog);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        tabSanPham = new javax.swing.JPanel();
        panelSanPham = new javax.swing.JPanel();
        pnlChiTietSanPham = new javax.swing.JPanel();
        pnlLoaiSanPham = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        tabSanPham.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout panelSanPhamLayout = new javax.swing.GroupLayout(panelSanPham);
        panelSanPham.setLayout(panelSanPhamLayout);
        panelSanPhamLayout.setHorizontalGroup(
            panelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1055, Short.MAX_VALUE)
        );
        panelSanPhamLayout.setVerticalGroup(
            panelSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 722, Short.MAX_VALUE)
        );

        tabSanPham.add(panelSanPham, "card2");

        jTabbedPane1.addTab("Sản phẩm", tabSanPham);

        javax.swing.GroupLayout pnlChiTietSanPhamLayout = new javax.swing.GroupLayout(pnlChiTietSanPham);
        pnlChiTietSanPham.setLayout(pnlChiTietSanPhamLayout);
        pnlChiTietSanPhamLayout.setHorizontalGroup(
            pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1055, Short.MAX_VALUE)
        );
        pnlChiTietSanPhamLayout.setVerticalGroup(
            pnlChiTietSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 722, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Chi tiết sản phẩm", pnlChiTietSanPham);

        javax.swing.GroupLayout pnlLoaiSanPhamLayout = new javax.swing.GroupLayout(pnlLoaiSanPham);
        pnlLoaiSanPham.setLayout(pnlLoaiSanPhamLayout);
        pnlLoaiSanPhamLayout.setHorizontalGroup(
            pnlLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1055, Short.MAX_VALUE)
        );
        pnlLoaiSanPhamLayout.setVerticalGroup(
            pnlLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 722, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Loại sản phẩm", pnlLoaiSanPham);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SanPhamJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panelSanPham;
    private javax.swing.JPanel pnlChiTietSanPham;
    private javax.swing.JPanel pnlLoaiSanPham;
    private javax.swing.JPanel tabSanPham;
    // End of variables declaration//GEN-END:variables
}
