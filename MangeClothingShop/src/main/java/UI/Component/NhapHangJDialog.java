package UI.Component;

import Interfaces.Initialize;
import Interfaces.CheckForm;
import Interfaces.CrudController;
import Entity.NhapHang;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import DAO.NhapHangDAO;
import Entity.SanPham;
import Map.MapSanPham;
import Utils.DialogBox;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import DAO.SanPhamDAO;
import Utils.ValidateInput;

public class NhapHangJDialog extends javax.swing.JFrame implements Initialize<NhapHang>, CheckForm<NhapHang, Integer>, CrudController {

    private NhapHangDAO dao = new NhapHangDAO();
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private MapSanPham mapsp = new MapSanPham();
    private SanPhamDAO spDao = new SanPhamDAO();
    private Date date = new Date();
    private ValidateInput input = new ValidateInput();

    public NhapHangJDialog() {
        initComponents();
        init();
    }

    public void comboBoxTenSanPham() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxMaSanPham.getModel();
        model.removeAllElements();
        List<SanPham> list = spDao.getAllData();
        for (SanPham sp : list) {
            model.addElement(sp.getTenSP());
        }
        // Đảm bảo chọn một mục mặc định
        if (model.getSize() > 0) {
            cbxMaSanPham.setSelectedIndex(0);
        }
    }

    @Override
    public void init() {
        fillToTable();
        txtNgayNhapHang.setText(formatter.format(date));
        //filterTable();
        setLocationRelativeTo(null);
        comboBoxTenSanPham();
    }

    @Override
    public void fillToTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<NhapHang> list = dao.getAllData();

        String[] col = {
            "Mã Nhập",
            "Tên Sản Phẩm",
            "Ngày Nhập",
            "Số Lượng"
        };
        model.setColumnIdentifiers(col);

        for (NhapHang o : list) {
            model.addRow(new Object[]{
                o.getMaNhap(),
                mapsp.getValueByID(o.getMaSP()),
                formatter.format(o.getNgayNhap()),
                o.getSoLuong()
            });
        }
        tblNhapHang.setModel(model);
    }

    @Override
    public void filterTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<NhapHang> list = dao.getDataByValue(txtTimKiem.getText());

        String[] col = {
            "Mã Nhập",
            "Tên Sản Phẩm",
            "Ngày Nhập",
            "Số Lượng"
        };
        model.setColumnIdentifiers(col);

        for (NhapHang o : list) {
            model.addRow(new Object[]{
                o.getMaNhap(),
                mapsp.getValueByID(o.getMaSP()),
                formatter.format(o.getNgayNhap()),
                o.getSoLuong()
            });
        }
        tblNhapHang.setModel(model);
    }

    @Override
    public void generateCbx() {

    }

    @Override
    public void setForm(NhapHang o) {
        txtMaNH.setText(String.valueOf(o.getMaNhap()));
        cbxMaSanPham.setSelectedItem(mapsp.getValueByID(o.getMaSP()));
        txtNgayNhapHang.setText(formatter.format(o.getNgayNhap()));
        txtSoLuongHang.setText(String.valueOf(o.getSoLuong()));
    }

    @Override
    public void getForm(int index) {
        NhapHang o = new NhapHang(
                (int) tblNhapHang.getValueAt(index, 0), 
                mapsp.getIDByValue((String) tblNhapHang.getValueAt(index, 1)), 
                date,
                (int) tblNhapHang.getValueAt(index, 3)
        );
        
        setForm(o);
    }

    @Override
    public void showDetail() {
        int index = tblNhapHang.getSelectedRow();
        getForm(index);
    }

    @Override
    public boolean isCheckValid() {
        StringBuilder sb = new StringBuilder();
        String NgayNhap = txtNgayNhapHang.getText();
        String SoLuong = txtSoLuongHang.getText();
        String patternNumber = "\\d+";
        String patternText = "\\s+";
        int count = 0;

        if (NgayNhap.equals("") || NgayNhap.matches(patternText)) {
            sb.append("Bạn chưa nhập ngày nhập hàng");
            count++;
        }
        if (SoLuong.equals("") || !SoLuong.matches(patternNumber)) {
            sb.append("Bạn chưa nhập số lượng");
            count++;
        }
        
        if (sb.length() > 0) {
            DialogBox.notice(this, sb.toString());
        }
        
        return count == 0;
    }

    @Override
    public boolean isCheckContain(List<NhapHang> list, Integer ma) {
        int count = 0;
        for (NhapHang o : list) {
            if (ma.equals(o.getMaNhap())) {
                count++;
            }
        }
        return count != 0;
    }

    @Override
    public boolean isCheckDuplicate() {
        int maNH = Integer.parseInt(txtMaNH.getText());
        List<NhapHang> list = dao.getAllData();
        if (!isCheckContain(list, maNH)) {
            DialogBox.notice(this, "Mã nhập hàng này có rồi. Vui lòng mã nhập hàng khác ");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCheckUpdate() {
        int maNH = Integer.parseInt(txtMaNH.getText());
        List<NhapHang> list = dao.getAllData();

        if (!isCheckContain(list, maNH)) {
            DialogBox.notice(this, "Không tìm tháy mã nhập hàng cần sửa");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCheckLength() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isCheckDelete() {
        int maNH = Integer.parseInt(txtMaNH.getText());
        List<NhapHang> list = dao.getAllData();

        if (!isCheckContain(list, maNH)) {
            DialogBox.notice(this, "Không tìm tháy mã nhập hàng cần xóa");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void create() {
        if (isCheckValid()) {
            String selectedSanPham = (String) cbxMaSanPham.getSelectedItem();
            System.out.println(selectedSanPham);
            NhapHang nhapHang = new NhapHang(
                    0,
                    mapsp.getIDByValue(selectedSanPham),
                    date,
                    Integer.parseInt(txtSoLuongHang.getText())
            );
            dao.insertData(nhapHang);
            DialogBox.notice(this, "Thêm thành công");
            fillToTable();
        }
    }

    @Override
    public void reset() {
//        NhapHang nh = new NhapHang(
//                0,
//                mapsp.getIDByValue(cbxMaSanPham.getItemAt(0)),
//                new Date(0 - 1900, 01, 0),
//                0
//        );
//        setForm(nh);
        txtSoLuongHang.setText("");
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNgayNhapHang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMaNH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSoLuongHang = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        cbxMaSanPham = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Tabs = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhapHang = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Mã nhập hàng :");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Tên sản phẩm :");

        txtNgayNhapHang.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Ngày nhập hàng:");

        txtMaNH.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Số lượng hàng :");

        txtSoLuongHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSoLuongHangKeyPressed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnThem.setBackground(new java.awt.Color(255, 255, 0));
        btnThem.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(255, 255, 0));
        btnLamMoi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        cbxMaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMaSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaNH, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(cbxMaSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNgayNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuongHang, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(318, 318, 318)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNgayNhapHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaNH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(txtSoLuongHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setText("Thiết lập thông tin nhập hàng");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Tabs.setToolTipText("");
        Tabs.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tblNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã nhập", "Tên sản phẩm", "Ngày nhập", "Số lượng"
            }
        ));
        tblNhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhapHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhapHang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Tabs.addTab("Thông tin đã thêm", jPanel4);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Tìm kiếm: ");

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addGap(148, 148, 148))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Tabs.getAccessibleContext().setAccessibleName("Thông tin đã thêm");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel6.setText("Thông tin sản phẩm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        create();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        reset();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void tblNhapHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhapHangMouseClicked
        showDetail();
    }//GEN-LAST:event_tblNhapHangMouseClicked

    private void cbxMaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMaSanPhamActionPerformed
        System.out.println(mapsp.getIDByValue((String) cbxMaSanPham.getSelectedItem()));
    }//GEN-LAST:event_cbxMaSanPhamActionPerformed

    private void txtSoLuongHangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongHangKeyPressed
        input.inputNumber(txtSoLuongHang, 5);
    }//GEN-LAST:event_txtSoLuongHangKeyPressed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        input.inputUnicode(txtTimKiem, 50);
        filterTable();
    }//GEN-LAST:event_txtTimKiemKeyPressed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhapHangJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Tabs;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cbxMaSanPham;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JTable tblNhapHang;
    private javax.swing.JTextField txtMaNH;
    private javax.swing.JTextField txtNgayNhapHang;
    private javax.swing.JTextField txtSoLuongHang;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
