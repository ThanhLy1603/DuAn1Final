/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Detail;

import DAO.LoaiSanPhamDAO;
import Interfaces.CheckForm;
import Interfaces.CrudController;
import Interfaces.Initialize;
import Entity.LoaiSanPham;
import Utils.Auth;
import Utils.DialogBox;
import Utils.ValidateInput;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
enum colLoaiSP {
    MALOAI(0),
    TENLOAI(1);
    int i;

    private colLoaiSP(int i) {
        this.i = i;
    }
}

public class LoaiSPDetailJDialog extends javax.swing.JFrame implements Initialize<LoaiSanPham>,
        CheckForm<LoaiSanPham, String>, CrudController {

    private LoaiSanPhamDAO dao = new LoaiSanPhamDAO();
    private SanPhamDetailJDialog dialog = new SanPhamDetailJDialog();
    private ValidateInput input = new ValidateInput();

    /**
     * Creates new form LoaiSPDetailJDialog
     */
    public LoaiSPDetailJDialog() {
        initComponents();

        init();
    }

    @Override
    public void init() {
        fillToTable();

        setLocationRelativeTo(null);
    }

    @Override
    public void fillToTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<LoaiSanPham> list = dao.getAllData();
        
        list.removeIf(o -> o.getMaLoai().equals("NONE"));
                
        String[] col = {
            "Mã loại",
            "Tên loại"
        };

        model.setColumnIdentifiers(col);

        for (LoaiSanPham o : list) {
            model.addRow(new Object[]{
                o.getMaLoai(),
                o.getTenLoai()
            });
            
            System.out.println(o.toString());
        }
        
        tblLoaiSanPham.setModel(model);
        
        
    }

    @Override
    public void filterTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<LoaiSanPham> list = dao.getDataByValue(txtTenLoai.getText());

        String[] col = {
            "Mã loại",
            "Tên loại"
        };

        model.setColumnIdentifiers(col);

        for (LoaiSanPham o : list) {
            model.addRow(new Object[]{
                o.getMaLoai(),
                o.getTenLoai()
            });
        }

        tblLoaiSanPham.setModel(model);
    }

    @Override
    public void setForm(LoaiSanPham o) {
        txtMaLoai.setText(o.getMaLoai());
        txtTenLoai.setText(o.getTenLoai());
    }

    @Override
    public void getForm(int index) {
        String maLoai = (String) tblLoaiSanPham.getValueAt(index, colLoaiSP.MALOAI.i);
        String tenLoai = (String) tblLoaiSanPham.getValueAt(index, colLoaiSP.TENLOAI.i);

        LoaiSanPham o = new LoaiSanPham(maLoai, tenLoai);
        setForm(o);
    }

    @Override
    public void showDetail() {
        int index = tblLoaiSanPham.getSelectedRow();
        getForm(index);
    }

    @Override
    public boolean isCheckValid() {
        StringBuilder sb = new StringBuilder();
        String ma = txtMaLoai.getText();
        String ten = txtTenLoai.getText();
        String patternText = "\\s+";
        int count = 0;

        if (ma.equals("") || ma.matches(patternText)) {
            sb.append("Bạn chưa nhập mã sản phẩm\n");
            count++;
        }

        if (ten.equals("") || ten.matches(patternText)) {
            sb.append("Bạn chưa nhập tên\n");
            count++;
        }

        if (sb.length() > 0) {
            DialogBox.notice(this, sb.toString());
        }

        return count == 0;
    }

    @Override
    public boolean isCheckContain(List<LoaiSanPham> list, String ma) {
        int count = 0;
        for (LoaiSanPham o : list) {
            if (ma.equals(o.getMaLoai())) {
                count++;
            }
        }

        return count != 0;
    }

    @Override
    public boolean isCheckDuplicate() {
        List<LoaiSanPham> list = dao.getAllData();
        String ma = txtMaLoai.getText();

        if (isCheckContain(list, ma) || ma.equals("NONE")) {
            DialogBox.notice(this, "Mã loại này có rồi. Vui lòng nhập mã loại khác");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCheckUpdate() {
        List<LoaiSanPham> list = dao.getAllData();
        String ma = txtMaLoai.getText();

        if (!isCheckContain(list, ma)) {
            DialogBox.notice(this, "Không tìm thấy loại sản phẩm cần sửa");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCheckDelete() {
        List<LoaiSanPham> list = dao.getAllData();
        String ma = txtMaLoai.getText();

        if (!isCheckContain(list, ma)) {
            DialogBox.notice(this, "Không tìm thấy loại sản phẩm cần xóa");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void create() {
        if (isCheckValid()) {
            if (isCheckDuplicate()) {
                String ma = txtMaLoai.getText();
                String ten = txtTenLoai.getText();

                dao.insertData(new LoaiSanPham(ma, ten));
                DialogBox.notice(this, "Thêm thành công");
                fillToTable();
            }
        }
    }

    @Override
    public void reset() {
        LoaiSanPham o = new LoaiSanPham(
                "",
                ""
        );

        setForm(o);
    }

    @Override
    public void update() {
        if (isCheckValid()) {
            if (isCheckUpdate()) {
                String ma = txtMaLoai.getText();
                String ten = txtTenLoai.getText();

                dao.updateData(new LoaiSanPham(ma, ten));
                DialogBox.notice(this, "Sửa thành công");
                fillToTable();
            }
        }
    }

    @Override
    public void delete() {
        if (Auth.isManager()) {
            if (isCheckDelete()) {
                if (DialogBox.confirm(this, "Bạn có muốn xóa loại sản phẩm này không?")) {
                    dao.deleteById(txtMaLoai.getText());
                    DialogBox.notice(this, "Xóa thành công");
                    fillToTable();
                    reset();
                }
            }
        } else {
            DialogBox.alert(this, "Bạn không có quyền xóa");
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

        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtMaLoai = new javax.swing.JTextField();
        txtTenLoai = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLoaiSanPham = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel12.setText("Mã loại");

        jLabel13.setText("Tên loại");

        txtMaLoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaLoaiKeyPressed(evt);
            }
        });

        txtTenLoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenLoaiKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenLoai, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                    .addComponent(txtMaLoai))
                .addGap(108, 108, 108))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(43, 43, 43)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(164, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(144, 144, 144))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        tblLoaiSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã loại", "Tên loại"
            }
        ));
        tblLoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblLoaiSanPham);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 964, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblLoaiSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiSanPhamMouseClicked
        showDetail();
    }//GEN-LAST:event_tblLoaiSanPhamMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        create();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        reset();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtTenLoaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenLoaiKeyPressed
        input.inputUnicode(txtTenLoai, 50);
        filterTable();
    }//GEN-LAST:event_txtTenLoaiKeyPressed

    private void txtMaLoaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaLoaiKeyPressed
        input.inputString(txtMaLoai, 10);
    }//GEN-LAST:event_txtMaLoaiKeyPressed

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
            java.util.logging.Logger.getLogger(LoaiSPDetailJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoaiSPDetailJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoaiSPDetailJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoaiSPDetailJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoaiSPDetailJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblLoaiSanPham;
    private javax.swing.JTextField txtMaLoai;
    private javax.swing.JTextField txtTenLoai;
    // End of variables declaration//GEN-END:variables

    @Override
    public void generateCbx() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isCheckLength() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
