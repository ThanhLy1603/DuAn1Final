/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Component;

import DAO.ChiTietHoaDonDAO;
import DAO.HoaDonDao;
import DAO.NhanVienDao;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;
import Entity.NhanVien;
import Interfaces.Initialize;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class QuanLyHoaDonJDialog extends javax.swing.JFrame implements Initialize<HoaDon> {

    /**
     * Creates new form HoaDonChiTietJDialog
     */
    public QuanLyHoaDonJDialog() {
        initComponents();
        init();
    }
    HoaDonDao hddao = new HoaDonDao();
    ChiTietHoaDonDAO ctdao = new ChiTietHoaDonDAO();
    NhanVienDao nvdao = new NhanVienDao();

//    void fillToComboboxHoaDon(JComboBox comboBox, List<?> dataList, Object specialValue) {
////        DefaultComboBoxModel model = (DefaultComboBoxModel) comboBox.getModel();
////        model.removeAllElements(); // Xóa toàn bộ phần tử cũ
////
////        // Thêm giá trị đặc biệt (ví dụ: "All")
////        if (specialValue != null) {
////            model.addElement(specialValue);
////        }
////
////        // Thêm các giá trị từ danh sách
////        for (Object item : dataList) {
////            model.addElement(item);
////        }
//    }
    @Override
    public void init() {
        fillToTable();
        filltoTableChiTiet();
        generateCbx();
    }
    public void filltoTableChiTiet(){
        DefaultTableModel model=(DefaultTableModel) tblHoaDoChiTiet.getModel();
        List<ChiTietHoaDon> list=ctdao.getAllData();
        list.forEach(ct->{
            Object[] values={
                ct.getMaHD(),
                ct.getMaSP(),
                ct.getMaKM(),
                ct.getSoLuong(),
                ct.getGia(),
                ct.getThue(),
        };
            model.addRow(values);
        });
    }

    @Override
    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        List<HoaDon> list = hddao.getAllData();
        list.forEach(hd -> {
            Object[] values = {
                hd.getMaHD(),
                hd.getMaNV(),
                hd.getNgayLap(),
                hd.getHinhThuc(),
                hd.isTrangThai() ? "Đã Thanh Toán" : "Chưa Thanh Toán"
            };
            model.addRow(values);
        });
    }

    @Override
    public void filterTable() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
//        String selectedMaNV = (String) cbNhanVien.getSelectedItem();
//        Integer selectedThang = (Integer) cbThang.getSelectedItem();
//        Integer selectedNam = (Integer) cbNam.getSelectedItem();

//        String maNV = (selectedMaNV != null && !selectedMaNV.equals("All")) ? selectedMaNV.toString() : "All";
//        int thang = 0;
//        if (selectedThang != null) {
//            thang = (Integer) selectedThang; // Giả sử cbThang là Integer
//        }
//
//        // Xử lý năm
//        int nam = 0;
//        if (selectedNam != null) {
//            nam = (Integer) selectedNam; // Giả sử cbNam là Integer
//        }
        String selectValueNV = (String) cbNhanVien.getSelectedItem();
        String resultNV = selectValueNV.equals("Tất cả") ? "" : selectValueNV;

        String selectValueNam = (String) cbNam.getSelectedItem();
        String resultNam = selectValueNam.equals("Tất cả") ? "" : selectValueNam;

        String selectValueThang = (String) cbThang.getSelectedItem();
        String resultThang = selectValueThang.equals("Tất cả") ? "" : selectValueThang;
        List<HoaDon> list = null;

        if (resultNam.equals("") && resultThang.equals("") && resultNV.equals("")) {
            // Không có Mã NV, Tháng và Năm
            list = hddao.getAllData(); // Viết phương thức để lấy tất cả dữ liệu
        } else if (resultNam.equals("") && resultThang.equals("")) {
            // Không có Tháng và Năm, chỉ lọc theo Mã NV
            list = hddao.getDataByValues(resultNV);
        } else if (resultNam.equals("") && resultNV.equals("")) {
            // Không có Năm và Mã NV, chỉ lọc theo Tháng
            list = hddao.getDataByOnlyMonth(Integer.parseInt(resultThang));
        } else if (resultThang.equals("") && resultNV.equals("")) {
            // Không có Tháng và Mã NV, chỉ lọc theo Năm
            list = hddao.getDataByOnlyYear(Integer.parseInt(resultNam));
        } else if (resultNam.equals("")) {
            // Không có Năm, lọc theo Tháng và Mã NV
            list = hddao.getDataByThang(resultNV, Integer.parseInt(resultThang));
        } else if (resultThang.equals("")) {
            // Không có Tháng, lọc theo Năm và Mã NV
            list = hddao.getDataByNam(resultNV, Integer.parseInt(resultNam));
        } else if (resultNV.equals("")) {
            // Không có Mã NV, lọc theo Tháng và Năm
            list = hddao.getDataTime(Integer.parseInt(resultThang), Integer.parseInt(resultNam));
        } else {
            // Có đủ cả Mã NV, Tháng và Năm
            list = hddao.getDataByValue(resultNV, Integer.parseInt(resultThang), Integer.parseInt(resultNam));
        }
        list.forEach(hd -> {
            Object[] values = {
                hd.getMaHD(),
                hd.getMaNV(),
                hd.getNgayLap(),
                hd.getHinhThuc(),
                hd.isTrangThai() ? "Đã Thanh Toán" : "Chưa Thanh Toán"
            };
            model.addRow(values);
        });
    }

    @Override
    public void generateCbx() {
//        List<NhanVien> nhanVienList = nvdao.getAllData();
//        fillToComboboxHoaDon(cbNhanVien, nhanVienList, "All");
        cbxMaNV();
        cbxNam();
        cbxThang();
    }

    public void cbxNam() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<Integer> list = hddao.getYear();

        model.addElement("Tất cả");
        for (Integer o : list) {
            model.addElement(String.valueOf(o));
        }

        cbNam.setModel(model);
    }

    public void cbxThang() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<Integer> list = hddao.getMonth();

        model.addElement("Tất cả");
        for (Integer o : list) {
            model.addElement(String.valueOf(o));
        }

        cbThang.setModel(model);
    }

    public void cbxMaNV() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<String> list = hddao.getMaNV();

        model.addElement("Tất cả");
        for (String o : list) {
            model.addElement(String.valueOf(o));
        }
        cbNhanVien.setModel(model);
    }

    @Override
    public void setForm(HoaDon o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void getForm(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void showDetail() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pnThoiGian = new javax.swing.JPanel();
        cbThang = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbNam = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        pnThoiGian1 = new javax.swing.JPanel();
        cbNhanVien = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDoChiTiet = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        pnThoiGian.setBackground(new java.awt.Color(255, 255, 255));
        pnThoiGian.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cbThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbThangActionPerformed(evt);
            }
        });

        jLabel1.setText("Tháng");

        jLabel2.setText("Năm");

        cbNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnThoiGianLayout = new javax.swing.GroupLayout(pnThoiGian);
        pnThoiGian.setLayout(pnThoiGianLayout);
        pnThoiGianLayout.setHorizontalGroup(
            pnThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThoiGianLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cbThang, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnThoiGianLayout.setVerticalGroup(
            pnThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThoiGianLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(pnThoiGianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnThoiGianLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnThoiGianLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbThang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã nhân viên", "Ngày lập", "Hình thức", "Trạng thái"
            }
        ));
        jScrollPane1.setViewportView(tblHoaDon);

        pnThoiGian1.setBackground(new java.awt.Color(255, 255, 255));
        pnThoiGian1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cbNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNhanVienActionPerformed(evt);
            }
        });

        jLabel5.setText("Nhân Viên");

        javax.swing.GroupLayout pnThoiGian1Layout = new javax.swing.GroupLayout(pnThoiGian1);
        pnThoiGian1.setLayout(pnThoiGian1Layout);
        pnThoiGian1Layout.setHorizontalGroup(
            pnThoiGian1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThoiGian1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnThoiGian1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(cbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnThoiGian1Layout.setVerticalGroup(
            pnThoiGian1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThoiGian1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 221, Short.MAX_VALUE)
                        .addComponent(pnThoiGian1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(265, 265, 265)
                        .addComponent(pnThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnThoiGian1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblHoaDoChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã sản phẩm", "Mã khuyên mại", "Sô lượng", "Giá", "Thuế"
            }
        ));
        jScrollPane2.setViewportView(tblHoaDoChiTiet);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Hóa Đơn");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Hóa đơn chi tiết");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 755, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 869, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbThangActionPerformed
        // TODO add your handling code here:
        filterTable();
    }//GEN-LAST:event_cbThangActionPerformed

    private void cbNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNhanVienActionPerformed
        // TODO add your handling code here:
        filterTable();
    }//GEN-LAST:event_cbNhanVienActionPerformed

    private void cbNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNamActionPerformed
        // TODO add your handling code here:
        filterTable();
    }//GEN-LAST:event_cbNamActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyHoaDonJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbNam;
    private javax.swing.JComboBox<String> cbNhanVien;
    private javax.swing.JComboBox<String> cbThang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnThoiGian;
    private javax.swing.JPanel pnThoiGian1;
    private javax.swing.JTable tblHoaDoChiTiet;
    private javax.swing.JTable tblHoaDon;
    // End of variables declaration//GEN-END:variables

}
