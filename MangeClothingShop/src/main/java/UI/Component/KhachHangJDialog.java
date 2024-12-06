/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Component;

import DAO.KhachHangDAO;
import Interfaces.Initialize;
import Interfaces.CheckForm;
import Interfaces.CrudController;
import Entity.KhachHang;
import Utils.Auth;
import Utils.DialogBox;
import Utils.ValidateInput;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anhth
 */
public class KhachHangJDialog extends javax.swing.JFrame implements Initialize<KhachHang>, CheckForm<KhachHang, String>, CrudController {
    private KhachHangDAO dao = new KhachHangDAO();
    private ValidateInput input = new ValidateInput();

    public KhachHangJDialog() {
        initComponents();
        init();
    }

    @Override
    public void init() {
        fillToTable();
        
        setLocationRelativeTo(null);
    }

    @Override  // Đổ dữ liệu từ database vào bảng khách hàng
    public void fillToTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<KhachHang> list = dao.getAllData();
        
        // Tạo cột cho bảng 
        String[] col = {
            "Mã khách hàng",
            "Tên khách hàng",
            "Giới tính",
            "Địa chỉ",
            "Số điện thoại"
        };

        model.setColumnIdentifiers(col);
        
        // Thêm dòng cho bảng  nhân viên
        for (KhachHang o : list) {
            model.addRow(new Object[]{
                o.getMaKH(),
                o.getTenKH(),
                o.isGioiTinh() ? "Nam" : "Nữ",
                o.getDiaChi(),
                o.getSoDT()
            });
        }
        
        tblKhachHang.setModel(model);
    }

    @Override // Lọc dữ liệu bảng khách hàng
    public void filterTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<KhachHang> list = dao.getDataByValue(txtSearch.getText());
        
        // Tạo cột cho bảng khách hàng
        String[] col = {
            "Mã khách hàng",
            "Tên khách hàng",
            "Giới tính",
            "Địa chỉ",
            "Số điện thoại"
        };

        model.setColumnIdentifiers(col);
        
        // Tạo dòng và dữ liệu cho bảng khách hàng
        for (KhachHang o : list) {
            model.addRow(new Object[]{
                o.getMaKH(),
                o.getTenKH(),
                o.isGioiTinh() ? "Nam" : "Nữ",
                o.getDiaChi(),
                o.getSoDT()
            });
        }
        
        tblKhachHang.setModel(model);
    }

    @Override  // Nhận giá trị từ đối tượng rồi đổ dữ liệu lên form
    public void setForm(KhachHang o) {
        txtMaKH.setText(o.getMaKH());
        txtTenKH.setText(o.getTenKH());
        rdnNam.setSelected(o.isNam());
        rdnNu.setSelected(o.isNu());
        txtSDT.setText(o.getSoDT());
        txaDiaChi.setText(o.getDiaChi());
    }
    
    @Override // Tạo đối tượng rồi đưa vào setForm
    public void getForm(int index) {
        String maKH = (String) tblKhachHang.getValueAt(index, 0);
        String tenKH = (String) tblKhachHang.getValueAt(index, 1);
        String gioiTinhStr = (String) tblKhachHang.getValueAt(index, 2);
        String sdt = (String) tblKhachHang.getValueAt(index, 3);
        String diachi = (String) tblKhachHang.getValueAt(index, 4);
        boolean gioiTinh = gioiTinhStr.equals("Nam");

        KhachHang kh = new KhachHang(
                maKH, 
                tenKH,
                gioiTinh, 
                sdt, 
                diachi
        );
        
        setForm(kh);
    }

    @Override // Đổ dữ liệu vào form khi click vào bảng
    public void showDetail() {
        int index = tblKhachHang.getSelectedRow();
        getForm(index);
    }

    @Override // Kiếm tra tính hợp lệ khi người dùng nhập liệu lên form
    public boolean isCheckValid() {
        StringBuilder sb = new StringBuilder();
        String maKH = txtMaKH.getText();
        String tenKH = txtTenKH.getText();
        String sdt = txtSDT.getText();
        String diachi = txaDiaChi.getText();
        String patternText = "\\s+";
        int count = 0;

        if (tenKH.equals("") || tenKH.matches(patternText)) {
            sb.append("Bạn chưa nhập tên\n");
            count++;
        }

        if (maKH.equals("") || maKH.matches(patternText)) {
            sb.append("Bạn chưa nhập mã\n");
            count++;
        }

        if (sdt.equals("") || sdt.matches(patternText)) {
            sb.append("Bạn chưa nhập số điện thoại");
            count++;
        }

        if (diachi.equals("") || diachi.matches(patternText)) {
            sb.append("Bạn chưa nhập địa chỉ");
            count++;
        }

        if (sb.length() > 0) {
            DialogBox.notice(this, sb.toString());
        }

        return count == 0;
    }

    @Override // Kiếm tra xem mã khách hàng đã nhập có nằm trong danh sách có sẵn hay không
    public boolean isCheckContain(List<KhachHang> list, String ma) {
        int count = 0;
        
        for (KhachHang o : list) {
            if (ma.equals(o.getMaKH())) {
                count++;
            }
        }

        return count != 0;
    }

    @Override // Kiếm tra xem mã người dùng nhập có bị trùng hay không
    public boolean isCheckDuplicate() {
        List<KhachHang> list = dao.getAllData();
        String ma = txtMaKH.getText();

        if (isCheckContain(list, ma)) {
            DialogBox.notice(this, "Mã loại này có rồi. Vui lòng nhập mã loại khác");
            return false;
        } else {
            return true;
        }
    }

    @Override // Kiếm tra xem mã người dùng nhập có nằm trong danh sách hay không
    public boolean isCheckUpdate() {
        List<KhachHang> list = dao.getAllData();
        String ma = txtMaKH.getText();

        if (!isCheckContain(list, ma)) {
            DialogBox.notice(this, "Không tìm thấy khách hàng cần sửa");
            return false;
        } else {
            return true;
        }
    }

    @Override // Kiếm tra xem mã người dùng nhập có nằm trong danh sách hay không
    public boolean isCheckDelete() {
        List<KhachHang> list = dao.getAllData();
        String ma = txtMaKH.getText();

        if (!isCheckContain(list, ma)) {
            DialogBox.notice(this, "Không tìm thấy khách hàng cần xóa");
            return false;
        } else {
            return true;
        }
    }

    @Override  // Tạo khách hàng
    public void create() {
        if (isCheckValid()) {
            if (isCheckDuplicate()) {
                String maKH = txtMaKH.getText();
                String tenKH = txtTenKH.getText();
                String sdt = txtSDT.getText();
                String diachi = txaDiaChi.getText();
                Boolean gioiTinh = rdnNam.isSelected();

                dao.insertData(new KhachHang(maKH, tenKH, gioiTinh, sdt, diachi));
                DialogBox.notice(this, "Thêm thành công");
                reset();
                fillToTable();
            }
        }

    }

    @Override // Xóa hết dữ liệu trên form
    public void reset() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        rdnNam.setSelected(true);
        txtSDT.setText("");
        txaDiaChi.setText("");
    }

    @Override // Chỉnh sửa thông tin khách hàng
    public void update() {
        if (isCheckValid()) {
            if (isCheckUpdate()) {
                String maKH = txtMaKH.getText();
                String tenKH = txtTenKH.getText();
                String sdt = txtSDT.getText();
                String diachi = txaDiaChi.getText();
                Boolean gioiTinh = rdnNam.isSelected();

                dao.insertData(new KhachHang(maKH, tenKH, gioiTinh, sdt, diachi));
                DialogBox.notice(this, "Sửa thành công");
                reset();
                fillToTable();
            }
        }
    }

    @Override // Xóa khách hàng
    public void delete() {
        if (Auth.isManager()) {
            if (isCheckDelete()) {
                if (DialogBox.confirm(this, "Bạn có muốn xóa khách hàng này không?")) {
                    dao.deleteById(txtMaKH.getText());
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

        btgrGioiTinh = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnRenew = new javax.swing.JButton();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        rdnNam = new javax.swing.JRadioButton();
        rdnNu = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaDiaChi = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName(""); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Mã khách hàng:");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Tên khách hàng:");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Giới tính:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Số điện thoại:");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Địa chỉ");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAdd.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnRenew.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRenew.setText("Làm mới");
        btnRenew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRenew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(18, 18, 18)
                .addComponent(btnRenew)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        txtMaKH.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMaKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaKHKeyPressed(evt);
            }
        });

        txtTenKH.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTenKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenKHKeyPressed(evt);
            }
        });

        txtSDT.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtSDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSDTKeyPressed(evt);
            }
        });

        btgrGioiTinh.add(rdnNam);
        rdnNam.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rdnNam.setText("Nam");

        btgrGioiTinh.add(rdnNu);
        rdnNu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rdnNu.setText("Nữ");

        txaDiaChi.setColumns(20);
        txaDiaChi.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txaDiaChi.setRows(5);
        jScrollPane3.setViewportView(txaDiaChi);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdnNam, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdnNu, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel6)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addGap(47, 47, 47)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(rdnNam)
                            .addComponent(rdnNu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Tìm kiếm:");

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        tblKhachHang.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã KH", "Tên KH", "Giới tính", "SĐT", "Địa chỉ"
            }
        ));
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhachHang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(38, 38, 38)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Thiết lập thông tin khách hàng");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Thông tin khách hàng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel9)
                .addGap(5, 5, 5)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        create();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnRenewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenewActionPerformed
        reset();
    }//GEN-LAST:event_btnRenewActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        input.inputUnicode(txtTenKH, 50);
        filterTable();
    }//GEN-LAST:event_txtSearchKeyPressed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        showDetail();
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void txtMaKHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaKHKeyPressed
        input.inputString(txtMaKH, 10);
    }//GEN-LAST:event_txtMaKHKeyPressed

    private void txtTenKHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKHKeyPressed
        input.inputUnicode(txtTenKH, 50);
    }//GEN-LAST:event_txtTenKHKeyPressed

    private void txtSDTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSDTKeyPressed
        input.inputPhone(txtSDT);
    }//GEN-LAST:event_txtSDTKeyPressed

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
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhachHangJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgrGioiTinh;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRenew;
    private javax.swing.JButton btnSua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton rdnNam;
    private javax.swing.JRadioButton rdnNu;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextArea txaDiaChi;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTenKH;
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
