/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Detail;

import DAO.SanPhamDAO;
import Entity.SanPham;
import Interfaces.CheckForm;
import Interfaces.CrudController;
import Interfaces.Initialize;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import Map.MapLoaiSanPham;
import Utils.DialogBox;
import Utils.SelectPhotos;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;

/**
 *
 * @author ADMIN
 */
enum colCTSP {
    MASP(0),
    TENSP(1),
    MAU(2),
    CHATLIEU(3),
    SIZE(4),
    HINH(5);
    int i;
    
    private colCTSP(int i) {
        this.i = i;
    }
}

public class ChiTietSPDetailJDialog extends javax.swing.JFrame implements Initialize<SanPham>,
        CheckForm<SanPham, String>, CrudController{
    private SanPhamDAO dao = new SanPhamDAO();
    private MapLoaiSanPham map = new MapLoaiSanPham();
    private JFileChooser file = new JFileChooser();
    private SelectPhotos photo = new SelectPhotos();
    /**
     * Creates new form ChiTietSPDetailJDialog
     */
    public ChiTietSPDetailJDialog() {
        initComponents();
        
        init();
    }
    
    @Override
    public void init() {
        generateCbx();
        fillToTable();
        
        setLocationRelativeTo(null);
    }

    @Override
    public void fillToTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<SanPham> list = dao.getAllData();
        String[] col = {
            "Tên sản phẩm",
            "Tên loại",
            "Màu sắc",
            "Chất liệu",
            "Size",
            "Hình ảnh"
        };
        
        model.setColumnIdentifiers(col);
        
        for (SanPham o : list) {
            model.addRow(new Object[]{
                o.getMaSP(),
                o.getTenSP(),
                o.getMauSac(),
                o.getChatLieu(),
                o.getSize(),
                o.getHinhAnh()
            });
        }
        
        tblChiTietSanPham.setModel(model);
    }
    
    @Override
    public void filterTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<SanPham> list = dao.getDataByValue(txtTenSanPham.getText());
        String[] col = {
            "Tên sản phẩm",
            "Tên loại",
            "Màu sắc",
            "Chất liệu",
            "Size",
            "Hình ảnh"
        };
        
        model.setColumnIdentifiers(col);
        
        for (SanPham o : list) {
            model.addRow(new Object[]{
                o.getMaSP(),
                o.getTenSP(),
                o.getMauSac(),
                o.getChatLieu(),
                o.getSize(),
                o.getHinhAnh()
            });
        }
        
        tblChiTietSanPham.setModel(model);
    }
    
    @Override
    public void generateCbx() {
        CbxMauSac();
        CbxSize();
        CbxChatLieu();
    }
    
    public void CbxMauSac() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        String[] items = {
            "Đen",
            "Đỏ",
            "Xanh",
            "Trắng",
            "Xanh lá",
            "Hồng",
            "Cam"
        };
        
        for (String o : items) {
            model.addElement(o);
        }
        
        cbxMauSac.setModel(model);
    }
    
    public void CbxSize() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        String[] items = {
            "M",
            "L",
            "XL",
            "XXL",
            "XXXL"
        };
        
        for (String o : items) {
            model.addElement(o);
        }
        
        cbxSize.setModel(model);
    }
    
    public void CbxChatLieu() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        String[] items = {
            "Vải",
            "Cotton",
            "Jean",
            "Thun"
        };
        
        for (String o : items) {
            model.addElement(o);
        }
        
        cbxChatLieu.setModel(model);
    }
    
    @Override
    public void setForm(SanPham o) {
        txtMaSanPham.setText(o.getMaSP());
        txtTenSanPham.setText(o.getTenSP());
        cbxMauSac.setSelectedItem(o.getMauSac());
        cbxSize.setSelectedItem(o.getSize());
        cbxChatLieu.setSelectedItem(o.getChatLieu());
        photo.setImage(lblHinhAnh, o.getHinhAnh());
    }

    @Override
    public void getForm(int index) {
        String maSP = (String) tblChiTietSanPham.getValueAt(index, colCTSP.MASP.i);
        String tenSP = (String) tblChiTietSanPham.getValueAt(index, colCTSP.TENSP.i);
        String mauSac = (String) tblChiTietSanPham.getValueAt(index, colCTSP.MAU.i);
        String chatLieu = (String) tblChiTietSanPham.getValueAt(index, colCTSP.CHATLIEU.i);
        String size = (String) tblChiTietSanPham.getValueAt(index, colCTSP.SIZE.i);
        String hinh = (String) tblChiTietSanPham.getValueAt(index, colCTSP.HINH.i);
        
        SanPham o = new SanPham(
                maSP, 
                tenSP, 
                mauSac, 
                chatLieu, 
                size, 
                hinh
        );
        
        setForm(o);
    }

    @Override
    public void showDetail() {
        int index = tblChiTietSanPham.getSelectedRow();
        
        getForm(index);
    }
    
    @Override
    public boolean isCheckValid() {
        String maSP = txtMaSanPham.getText();
        int count = 0;
        
        if (maSP.equals("")) {
            DialogBox.notice(this, "Bạn chưa chọn sản phẩm");
            count++;
        }
        
        return count == 0;
    }

    @Override
    public void reset() {
        SanPham o = new SanPham(
                "", 
                "", 
                "", 
                0, 
                0, 
                "", 
                "", 
                "", 
                "", 
                true
        );
        setForm(o);
        
        lblHinhAnh.setText("Nhấn để chọn ảnh");
        fillToTable();
    }

    @Override
    public void update() {
        if (isCheckValid()){
            SanPham o = dao.getDataById(txtMaSanPham.getText());
            String mauSac = (String)cbxMauSac.getSelectedItem();
            String chatLieu = (String)cbxChatLieu.getSelectedItem();
            String size = (String)cbxSize.getSelectedItem();

            String hinhAnh = photo.getPhotoName();

            dao.updateChiTietSanPham(new SanPham(
                    txtMaSanPham.getText(), 
                    o.getMaLoai(), 
                    txtTenSanPham.getText(), 
                    o.getDonGia(), 
                    o.getSoLuong(), 
                    mauSac, 
                    chatLieu, 
                    size, 
                    hinhAnh, 
                    true
            ));
            DialogBox.notice(this, "Thêm thành công");
            fillToTable(); 
            System.out.println(hinhAnh);
        }
    } 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        txtTenSanPham = new javax.swing.JTextField();
        cbxMauSac = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbxSize = new javax.swing.JComboBox<>();
        lblHinhAnh = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTietSanPham = new javax.swing.JTable();
        cbxChatLieu = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("Mã sản phẩm");

        jLabel8.setText("Tên sản phẩm");

        jLabel9.setText("Màu Sắc");

        txtMaSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtMaSanPham.setEnabled(false);

        txtTenSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenSanPhamKeyPressed(evt);
            }
        });

        jLabel10.setText("Chất Liệu");

        jLabel11.setText("Size");

        lblHinhAnh.setText("Nhấn để chọn hình");
        lblHinhAnh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });

        btnThem.setText("Thêm thông tin");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        tblChiTietSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Màu Sắc", "Chất Liệu ", "Size", "Hình Ảnh"
            }
        ));
        tblChiTietSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblChiTietSanPham);

        cbxChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cbxMauSac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxSize, 0, 123, Short.MAX_VALUE)
                    .addComponent(cbxChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(107, 107, 107)
                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(62, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(cbxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(cbxSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnThem)
                        .addGap(46, 46, 46)
                        .addComponent(btnLamMoi))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblChiTietSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietSanPhamMouseClicked
        showDetail();
    }//GEN-LAST:event_tblChiTietSanPhamMouseClicked

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        photo.selectPhotos(lblHinhAnh);
    }//GEN-LAST:event_lblHinhAnhMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        update();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        reset();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtTenSanPhamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenSanPhamKeyPressed
        filterTable();
    }//GEN-LAST:event_txtTenSanPhamKeyPressed

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
            java.util.logging.Logger.getLogger(ChiTietSPDetailJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChiTietSPDetailJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChiTietSPDetailJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChiTietSPDetailJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChiTietSPDetailJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cbxChatLieu;
    private javax.swing.JComboBox<String> cbxMauSac;
    private javax.swing.JComboBox<String> cbxSize;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JTable tblChiTietSanPham;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables
    @Override
    public boolean isCheckLength() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
        @Override
    public boolean isCheckDuplicate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isCheckUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isCheckDelete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isCheckContain(List<SanPham> list, String ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void create() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
