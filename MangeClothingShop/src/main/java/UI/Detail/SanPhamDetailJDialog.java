/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Detail;

import DAO.LoaiSanPhamDAO;
import DAO.SanPhamDAO;
import Entity.LoaiSanPham;
import Entity.SanPham;
import Interfaces.CheckForm;
import Interfaces.CrudController;
import Interfaces.Initialize;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import Map.MapLoaiSanPham;
import Utils.Auth;
import Utils.DialogBox;
import Utils.ValidateInput;
import Utils.NumberFormat;
import java.text.DecimalFormat;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ADMIN
 */
enum colSP {
    MASP(0),
    LOAISP(1),
    TENSP(2),
    DONGIA(3),
    SOLUONG(4),
    TRANGTHAI(5);
    int i;

    private colSP(int i) {
        this.i = i;
    }
}

public class SanPhamDetailJDialog extends javax.swing.JFrame implements Initialize<SanPham>,
        CheckForm<SanPham, String>, CrudController {

    private SanPhamDAO dao = new SanPhamDAO();
    private LoaiSanPhamDAO daoLSP = new LoaiSanPhamDAO();
    private DecimalFormat df = new DecimalFormat("#,###");
    private MapLoaiSanPham map = new MapLoaiSanPham();
    private ChiTietSPDetailJDialog dialog = new ChiTietSPDetailJDialog();
    private ValidateInput input = new ValidateInput();
    private NumberFormat numFormat = new NumberFormat();

    /**
     * Creates new form SanPhamDetailJDialog
     */
    public SanPhamDetailJDialog() {
        initComponents();

        init();
    }

    @Override
    public void init() {
        fillToTable();
        generateCbx();

        setLocationRelativeTo(null);
    }

    @Override
    public void fillToTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<SanPham> list = dao.getAllData();

        String[] col = {
            "Mã sản phẩm",
            "Loại sản phẩm",
            "Tên sản phẩm",
            "Đơn giá",
            "Số lượng",
            "Trạng thái"
        };

        model.setColumnIdentifiers(col);

        for (SanPham o : list) {
            model.addRow(new Object[]{
                o.getMaSP(),
                map.getValueByID(o.getMaLoai()),
                o.getTenSP(),
                df.format(o.getDonGia()),
                o.getSoLuong(),
                o.isTrangThai() ? "Còn hàng" : "Hết hàng"
            });
        }

        tblSanPham.setModel(model);
    }

    @Override
    public void filterTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<SanPham> list = dao.getDataByValue(txtTenSP.getText());

        String[] col = {
            "Mã sản phẩm",
            "Loại sản phẩm",
            "Tên sản phẩm",
            "Đơn giá",
            "Số lượng",
            "Trạng thái"
        };

        model.setColumnIdentifiers(col);

        for (SanPham o : list) {
            model.addRow(new Object[]{
                o.getMaSP(),
                map.getValueByID(o.getMaLoai()),
                o.getTenSP(),
                df.format(o.getDonGia()),
                o.getSoLuong(),
                o.isTrangThai() ? "Còn hàng" : "Hết hàng"
            });
        }

        tblSanPham.setModel(model);
    }

    @Override
    public void generateCbx() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<LoaiSanPham> list = daoLSP.getAllData();

        for (LoaiSanPham o : list) {
            model.addElement(o.getTenLoai());
        }

        cbxLoaiSanPham.setModel(model);
    }

    @Override
    public void setForm(SanPham o) {
        String itemCbx = map.getValueByID(o.getMaLoai());
        String donGia = String.valueOf((int) o.getDonGia());

        txtMaSP.setText(o.getMaSP());
        cbxLoaiSanPham.setSelectedItem(itemCbx);
        txtTenSP.setText(o.getTenSP());
        txtDonGia.setText(donGia);
        txtSoLuong.setText(String.valueOf(o.getSoLuong()));
        txtTrangThai.setText(o.isTrangThai() ? "Còn hàng" : "Hết hàng");
    }

    @Override
    public void getForm(int index) {
        String maSP = (String) tblSanPham.getValueAt(index, colSP.MASP.i);
        String loai = (String) tblSanPham.getValueAt(index, colSP.LOAISP.i);
        String tenSP = (String) tblSanPham.getValueAt(index, colSP.TENSP.i);
        String donGia = (String) tblSanPham.getValueAt(index, colSP.DONGIA.i);
        Object soLuong = tblSanPham.getValueAt(index, colSP.SOLUONG.i);
        Object trangThai = tblSanPham.getValueAt(index, colSP.TRANGTHAI.i);

        SanPham o = new SanPham(
                maSP,
                map.getIDByValue(loai),
                tenSP,
                Double.parseDouble(numFormat.removeCommas(donGia)),
                (int) soLuong,
                trangThai == "Còn hàng"
        );

        setForm(o);
    }

    @Override
    public void showDetail() {
        int index = tblSanPham.getSelectedRow();
        getForm(index);
    }

    @Override
    public boolean isCheckValid() {
        StringBuilder sb = new StringBuilder();
        String maSP = txtMaSP.getText();
        String tenSP = txtTenSP.getText();
        String donGia = txtDonGia.getText();
        String soLuong = txtSoLuong.getText();
        String loaiSP = (String) cbxLoaiSanPham.getSelectedItem();
        String patternNumber = "\\d*";
        String patternText = "\\s+";
        int count = 0;

        if (maSP.equals("") || maSP.matches(patternText)) {
            sb.append("Bạn chưa nhập mã sản phẩm\n");
            count++;
        }

        if (tenSP.equals("") || tenSP.matches(patternText)) {
            sb.append("Bạn chưa nhập tên sản phẩm\n");
            count++;
        }

        if (donGia.equals("") || !donGia.matches(patternNumber)) {
            sb.append("Bạn chưa nhập đơn giá. Vui lòng nhập số nguyên\n");
            count++;
        }

        if (soLuong.equals("") || !soLuong.matches(patternNumber)) {
            sb.append("Bạn chưa nhập số lượng. Vui lòng nhập số nguyên\n");
            count++;
        }

        if (loaiSP == null) {
            sb.append("Bạn chưa chọn loại sản phẩm\n");
            count++;
        }

        if (sb.length() > 0) {
            DialogBox.notice(this, sb.toString());
        }

        return count == 0;
    }

    @Override
    public boolean isCheckContain(List<SanPham> list, String ma) {
        int count = 0;
        for (SanPham o : list) {
            if (ma.equals(o.getMaSP())) {
                count++;
            }
        }

        return count != 0;
    }

    @Override
    public boolean isCheckDuplicate() {
        String maSP = txtMaSP.getText();
        List<SanPham> list = dao.getAllData();

        if (isCheckContain(list, maSP)) {
            DialogBox.notice(this, "Mã sản phẩm này có rồi. Vui lòng nhập mã sản phẩm khác");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCheckUpdate() {
        String maSP = txtMaSP.getText();
        List<SanPham> list = dao.getAllData();

        if (!isCheckContain(list, maSP)) {
            DialogBox.notice(this, "Không tìm thấy mã sản phẩm cần sửa");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCheckDelete() {
        String maSP = txtMaSP.getText();
        List<SanPham> list = dao.getAllData();

        if (!isCheckContain(list, maSP)) {
            DialogBox.notice(this, "Không tìm thấy mã sản phẩm cần xóa");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void create() {
        if (isCheckValid()) {
            if (isCheckDuplicate()) {
                String itemCbx = map.getIDByValue((String) cbxLoaiSanPham.getSelectedItem());

                dao.insertData(new SanPham(
                        txtMaSP.getText(),
                        itemCbx,
                        txtTenSP.getText(),
                        Double.parseDouble(txtDonGia.getText()),
                        Integer.parseInt(txtSoLuong.getText()),
                        "",
                        "",
                        "",
                        "",
                        true
                ));

                DialogBox.notice(this, "Thêm thành công");
                fillToTable();
            }
        }
    }

    @Override
    public void reset() {
        SanPham sp = new SanPham(
                "",
                "",
                "",
                0,
                0,
                true
        );
        setForm(sp);
        txtDonGia.setText("");
        txtSoLuong.setText("");
        txtTrangThai.setText("");
        generateCbx();
        fillToTable();
    }

    @Override
    public void update() {
        if (isCheckValid()) {
            if (isCheckUpdate()) {
                String itemCbx = map.getIDByValue((String) cbxLoaiSanPham.getSelectedItem());

                dao.insertData(new SanPham(
                        txtMaSP.getText(),
                        itemCbx,
                        txtTenSP.getText(),
                        Double.parseDouble(txtDonGia.getText()),
                        Integer.parseInt(txtSoLuong.getText()),
                        "",
                        "",
                        "",
                        "",
                        true
                ));

                DialogBox.notice(this, "Sửa thành công");
                fillToTable();
            }
        }
    }

    @Override
    public void delete() {
        if (Auth.isManager()) {
            if (isCheckDelete()) {
                if (DialogBox.confirm(this, "Bạn có muốn xóa sản phẩm này không?")) {
                    dao.deleteById(txtMaSP.getText());
                    DialogBox.notice(this, "Xóa thành công");
                    fillToTable();
                    reset();
                }
            }
        } else {
            DialogBox.notice(this, "Bạn không có quyền xóa");
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        cbxLoaiSanPham = new javax.swing.JComboBox<>();
        txtTenSP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtTrangThai = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();

        jLabel1.setText("Mã sản phẩm");

        jLabel2.setText("Loại sản phẩm");

        jLabel3.setText("Tên sản phẩm");

        txtMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSPActionPerformed(evt);
            }
        });

        txtTenSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenSPKeyPressed(evt);
            }
        });

        jLabel4.setText("Đơn giá");

        jLabel5.setText("Số lượng ");

        jLabel6.setText("Trạng thái");

        txtDonGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDonGiaKeyPressed(evt);
            }
        });

        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyPressed(evt);
            }
        });

        txtTrangThai.setEnabled(false);

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

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Loại Sản Phẩm", "Tên Sản Phẩm", "Đơn Giá", "Số Lượng", "Trạng Thái"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(74, 74, 74)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtMaSP)
                                .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbxLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDonGia)
                            .addComponent(txtSoLuong)
                            .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(91, 91, 91)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(78, 78, 78))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThem)
                        .addComponent(jLabel4)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btnSua)
                                .addGap(8, 8, 8)
                                .addComponent(btnLamMoi))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cbxLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoa)
                            .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        showDetail();
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        create();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        reset();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSPActionPerformed
        input.inputString(txtMaSP, 10);
    }//GEN-LAST:event_txtMaSPActionPerformed

    private void txtTenSPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenSPKeyPressed
        input.inputUnicode(txtTenSP, 50);
        filterTable();
    }//GEN-LAST:event_txtTenSPKeyPressed

    private void txtDonGiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDonGiaKeyPressed
        input.inputNumber(txtDonGia, 15);
    }//GEN-LAST:event_txtDonGiaKeyPressed

    private void txtSoLuongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyPressed
        input.inputNumber(txtSoLuong, 5);
    }//GEN-LAST:event_txtSoLuongKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbxLoaiSanPham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean isCheckLength() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
