/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Component;

import DAO.NhanVienDao;
import Entity.NhanVien;
import Interfaces.CheckForm;
import Interfaces.CrudController;
import Interfaces.Initialize;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import Map.MapGioiTinh;
import Map.MapChucVu;
import Utils.DialogBox;
import java.util.ArrayList;

/**
 *
 * @author PHONG
 */
public class NhanVienJDialog extends javax.swing.JFrame implements CrudController, CheckForm<NhanVien, String>, Initialize<NhanVien> {

    private NhanVienDao dao = new NhanVienDao();
    private DecimalFormat df = new DecimalFormat("#");
    private MapChucVu mapRole = new MapChucVu();
    private MapGioiTinh mapGender = new MapGioiTinh();

    /**
     * Creates new form NhanVien
     */
    public NhanVienJDialog() {
        initComponents();
        init();
    }

    @Override
    public void init() {
        this.fillToTable();
        this.generateCbx();
        setLocationRelativeTo(null);
    }

    @Override
    public void fillToTable() {
        DefaultTableModel model = new DefaultTableModel();
        List<NhanVien> list = dao.getAllData();

        String[] col = {
            "MaNV",
            "Tên nhân viên",
            "Mật Khẩu",
            "Giới tính",
            "Chức vụ",
            "Lương",
            "Số điện thoại",
            "Email"
        };

        model.setColumnIdentifiers(col);

        list.forEach(nv -> {
            Object[] values = {
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getMatKhau(),
                nv.isGioiTinh() ? "Nam" : "Nữ",
                nv.isChucVu() ? "Quản lý" : "Nhân viên",
                df.format(nv.getLuong()),
                nv.getSoDT(),
                nv.getEmail()
            };
            model.addRow(values);
        });

        tblNhanVien.setModel(model);
    }

    @Override
    public void filterTable() {
        DefaultTableModel model = new DefaultTableModel();
        String itemGioiTinh = (String) cbGioiTinh.getSelectedItem();
        String itemChucVu = (String) cbVaiTro.getSelectedItem();

        int indexGioiTinh = mapGender.getIDByValue(itemGioiTinh);
        int indexChucVu = mapRole.getIDByValue(itemChucVu);

        List<NhanVien> list = dao.getDataByValue(indexGioiTinh, indexChucVu);
        String[] col = {
            "MaNV",
            "Tên nhân viên",
            "Mật Khẩu",
            "Giới tính",
            "Chức vụ",
            "Lương",
            "Số điện thoại",
            "Email"
        };

        model.setColumnIdentifiers(col);

        list.forEach(nv -> {
            Object[] values = {
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getMatKhau(),
                nv.isGioiTinh() ? "Nam" : "Nữ",
                nv.isChucVu() ? "Quản lý" : "Nhân viên",
                df.format(nv.getLuong()),
                nv.getSoDT(),
                nv.getEmail()
            };

            model.addRow(values);
        });

        tblNhanVien.setModel(model);

        System.out.println("ChucVu = " + indexChucVu);
        System.out.println("Gioi Tinh = " + indexGioiTinh);
        System.out.println("-----------------------------");
    }

    @Override
    public void generateCbx() {
        cbxGioiTinh();
        cbxChucVu();
        rbtnNhanVien.setSelected(true); // Giả sử "Nhân viên" là lựa chọn mặc định
        rdbNam.setSelected(true);       // Giả sử "Nam" là giới tính mặc định
    }

    public void cbxGioiTinh() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        String[] gender = {
            "Tất cả",
            "Nam",
            "Nữ"
        };

        for (String o : gender) {
            model.addElement(o);
        }

        cbGioiTinh.setModel(model);
    }

    public void cbxChucVu() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        String[] VaiTro = {
            "Tất cả",
            "Quản lý",
            "Nhân viên"
        };

        for (String o : VaiTro) {
            model.addElement(o);
        }

        cbVaiTro.setModel(model);
    }

    @Override
    public void getForm(int index) {
        String maNV = (String) tblNhanVien.getValueAt(index, 0); // MaNV
        String tenNV = (String) tblNhanVien.getValueAt(index, 1); // TenNV
        String matKhau = (String) tblNhanVien.getValueAt(index, 2); // MatKhau
        String gioiTinhStr = (String) tblNhanVien.getValueAt(index, 3); // "Nam" hoặc "Nữ"
        String chucVuStr = (String) tblNhanVien.getValueAt(index, 4);  // "Quản lý" hoặc "Nhân viên"
        String luongStr = (String) tblNhanVien.getValueAt(index, 5); // Lương
        String soDT = (String) tblNhanVien.getValueAt(index, 6); // Số điện thoại
        String email = (String) tblNhanVien.getValueAt(index, 7); // Email

        double luong = 0.0;
        try {
            luong = Double.parseDouble(luongStr); // Chuyển đổi lương nếu là số
        } catch (NumberFormatException e) {
            // Xử lý nếu không phải số (có thể ghi log hoặc set lương mặc định)
            System.out.println("Lương không hợp lệ: " + luongStr);
        }
        boolean gioiTinh = gioiTinhStr.equals("Nam"); // Nếu giá trị là "Nam" thì true, ngược lại false
        rdbNam.setSelected(gioiTinh);
        rdbNu.setSelected(!gioiTinh);

        // Xử lý trạng thái chức vụ
        boolean chucVu = chucVuStr.equals("Quản lý"); // Nếu giá trị là "Quản lý" thì true, ngược lại false
        rbtnTruongPhong.setSelected(chucVu);
        rbtnNhanVien.setSelected(!chucVu);

        NhanVien nv = new NhanVien(maNV, matKhau, tenNV, gioiTinh, chucVu, luong, soDT, email);
        setForm(nv);
    }

    @Override
    public void setForm(NhanVien nv) {
        txtMaNV.setText(nv.getMaNV());
        txtPassword.setText(nv.getMatKhau());
        txtTenNV.setText(nv.getTenNV());
        rdbNam.setSelected(nv.isNam()); // true nếu là "Nam"
        rdbNu.setSelected(nv.isNu()); // false nếu là "Nam"

        // Gán giá trị cho radio button chức vụ
        rbtnTruongPhong.setSelected(nv.isTruongPhong()); // true nếu là "Quản lý"
        rbtnNhanVien.setSelected(nv.isNhanVien()); // false nếu là "Quản lý"
        txtLuong.setText(df.format((long) nv.getLuong()));
        txtEmail.setText(nv.getEmail());
        txtSDT.setText(nv.getSoDT());
    }

    @Override
    public void showDetail() {
        int index = tblNhanVien.getSelectedRow();
        getForm(index);
    }

    @Override
    public void create() {
        if (isCheckValid()) {
            if (isCheckDuplicate()) {
                dao.insertData(new NhanVien(
                        txtMaNV.getText(), // Mã nhân viên
                        txtPassword.getText(), // Mật khẩu
                        txtTenNV.getText(), // Tên nhân viên
                        rdbNam.isSelected(), // Giới tính
                        rbtnTruongPhong.isSelected(), // Chức vụ
                        Float.parseFloat(txtLuong.getText()), // Lương
                        txtSDT.getText(), // Số điện thoại
                        txtEmail.getText()
                )); // Email
                DialogBox.notice(this, "tạo Thành công");
                reset();
                fillToTable();
            }
        }
    }

    @Override
    public void reset() {
        txtMaNV.setText("");
        txtPassword.setText("");
        txtTenNV.setText("");
        rbtnNhanVien.setSelected(true); // Giả sử "Nhân viên" là lựa chọn mặc định
        rdbNam.setSelected(true);       // Giả sử "Nam" là giới tính mặc định
        txtLuong.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
    }

    @Override
    public void update() {
        if (isCheckValid()) {
            dao.updateData(new NhanVien(
                    txtMaNV.getText(), // Mã nhân viên
                    txtPassword.getText(), // Mật khẩu
                    txtTenNV.getText(), // Tên nhân viên
                    rdbNam.isSelected(), // Giới tính
                    rbtnTruongPhong.isSelected(), // Chức vụ
                    Float.parseFloat(txtLuong.getText()), // Lương
                    txtSDT.getText(), // Số điện thoại
                    txtEmail.getText()
            )); // Email
            DialogBox.notice(this, "Update Thành Công");
            fillToTable();
        }
    }

    @Override
    public void delete() {
        if (isCheckDelete()) {
            if (DialogBox.confirm(this, "Bạn có muốn xóa nhân viên này không")) {
                dao.deleteById(txtMaNV.getText());
                reset();
                fillToTable();
            }
        }
    }

    @Override
    public boolean isCheckValid() {
        StringBuilder sb = new StringBuilder();
        String luong = txtLuong.getText().replaceAll(",", "");  // Loại bỏ dấu phẩy nếu có
        String patternNumber = "\\d*";
        String patternText = "\\s+";
        String patternEmail = "\\w+@\\w+\\.\\w+";
        String maNV = txtMaNV.getText();
        String matKhau = txtPassword.getText();
        String tenNV = txtTenNV.getText();
        String patternSDT = "0\\d{9}";
        String soDT = txtSDT.getText();
        String email = txtEmail.getText();
        int count = 0;
        
        if (maNV.equals("") || maNV.matches(patternText)) {
            sb.append("Mã nhân viên Không được trống\n");
            count++;
        }
        
        if (matKhau.equals("") || matKhau.isEmpty()) {
            sb.append("Bạn chưa nhập mật khẩu\n");
            count++;
        }

        if (tenNV.equals("") || tenNV.matches(patternText)) {
            sb.append("Tên Nhân viên không được trống!\n");
            count++;
        }
        
        if (luong.equals("") || !luong.matches(patternNumber)) {
            sb.append("Lương Không được trống\n");
            count++;
        }
        
        if (soDT.equals("") || !soDT.matches(patternSDT)) {
            sb.append("Số điện thoại Không được trống\n");
            count++;
        }
        
        if (email.equals("") || !email.matches(patternEmail)) {
            sb.append("Email Không được trống\n");
            count++;
        }
        
        if (sb.length() > 0) {
            DialogBox.notice(this, sb.toString());
        }
        
        return count == 0;
    }

    @Override
    public boolean isCheckContain(List<NhanVien> list, String ma) {
        int count = 0;
        for (NhanVien o : list) {
            if (ma.equals(o.getMaNV())) {
                count++;
            }
        }
        return count != 0;
    }

    @Override
    public boolean isCheckDuplicate() {
        String maNV = txtMaNV.getText();
        List<NhanVien> list = dao.getAllData();
        if (isCheckContain(list, maNV)) {
            DialogBox.alert(this, "Mã nhân viên này có rồi, vui lòng nhập mã khác!");
            return false;
        }
        return true;
    }

    @Override
    public boolean isCheckUpdate() {
        String maNV = txtMaNV.getText();
        List<NhanVien> list = dao.getAllData();
        if (!isCheckContain(list, maNV)) {
            DialogBox.alert(this, "Không tìm thấy mã nhân viên cần sửa");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCheckLength() {
//        String maNV = txtMaNV.getText();  // Lấy mã nhân viên từ input
//        String matKhau = txtPassword.getText();  // Lấy mật khẩu từ input
//        String tenNV = txtTenNV.getText();  // Lấy tên nhân viên từ input
//        String soDT = txtSDT.getText();  // Lấy số điện thoại từ input
//        String email = txtEmail.getText();  // Lấy email từ input
//
//        // Kiểm tra độ dài mã nhân viên (ví dụ: phải có ít nhất 3 ký tự và tối đa 10 ký tự)
//        if (maNV.length() < 3 || maNV.length() > 10) {
//            DialogBox.alert(this, "Mã nhân viên phải có độ dài từ 3 đến 10 ký tự");
//            return false;
//        }
//
//        // Kiểm tra độ dài mật khẩu (ví dụ: phải có ít nhất 6 ký tự)
//        if (matKhau.length() < 6) {
//            DialogBox.alert(this, "Mật khẩu phải có ít nhất 6 ký tự");
//            return false;
//        }
//
//        // Kiểm tra độ dài tên nhân viên (ví dụ: từ 3 đến 50 ký tự)
//        if (tenNV.length() < 3 || tenNV.length() > 50) {
//            DialogBox.alert(this, "Tên nhân viên phải có độ dài từ 3 đến 50 ký tự");
//            return false;
//        }
//
//        // Kiểm tra độ dài số điện thoại (ví dụ: 10 hoặc 11 ký tự)
//        if (soDT.length() != 10 && soDT.length() != 11) {
//            DialogBox.alert(this, "Số điện thoại phải có 10 hoặc 11 ký tự");
//            return false;
//        }
//
//        // Kiểm tra độ dài email (ví dụ: phải có ít nhất 5 ký tự)
//        if (email.length() < 5) {
//            DialogBox.alert(this, "Email phải có ít nhất 5 ký tự");
//            return false;
//        }
//
//        // Nếu tất cả các trường đều hợp lệ, trả về true
        return true;
    }

    @Override
    public boolean isCheckDelete() {
        String maNV = txtMaNV.getText(); // Lấy mã nhân viên từ input
        // Kiểm tra nếu mã nhân viên trống
        if (maNV == null || maNV.trim().isEmpty()) {
            DialogBox.alert(this, "Mã nhân viên không được để trống");
            return false;  // Không thể xóa nếu mã nhân viên không hợp lệ
        }

        return true;
    }

    public void SearchByName() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);  // Xóa các dòng hiện tại trong bảng
        String timKiem = txtTimKiem.getText().trim();
        System.out.println("Searching for: " + timKiem); // In ra tên đang tìm kiếm
        // Lấy kết quả tìm kiếm từ phương thức getDateByName
        List<NhanVien> list = dao.getDateByName(timKiem);

        // Duyệt qua danh sách nhân viên và thêm vào bảng
        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getMatKhau(),
                nv.isGioiTinh() ? "Nam" : "Nữ",
                nv.isChucVu() ? "Quản lý" : "Nhân viên",
                df.format(nv.getLuong()),
                nv.getSoDT(),
                nv.getEmail()
            });
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

        btGgioiTinh = new javax.swing.ButtonGroup();
        btGChucVu = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        rbtnTruongPhong = new javax.swing.JRadioButton();
        rbtnNhanVien = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        txtLuong = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbGioiTinh = new javax.swing.JComboBox<>();
        cbVaiTro = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        Tabs = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Thiết lập thông tin nhân viên");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Mã nhân viên");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Tên nhân viên");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Chức vụ");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Điện thoại");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Email");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Giới tính");

        btGgioiTinh.add(rdbNam);
        rdbNam.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rdbNam.setText("Nam");

        btGgioiTinh.add(rdbNu);
        rdbNu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rdbNu.setText("Nữ");

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

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel4.setText("Mật Khẩu");

        btGChucVu.add(rbtnTruongPhong);
        rbtnTruongPhong.setText("Trưởng Phòng");
        rbtnTruongPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnTruongPhongActionPerformed(evt);
            }
        });

        btGChucVu.add(rbtnNhanVien);
        rbtnNhanVien.setText("Nhân Viên");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("Lương");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtMaNV)
                                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(rbtnTruongPhong)
                                        .addGap(84, 84, 84)))))
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail)
                            .addComponent(txtSDT)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(118, 118, 118)
                        .addComponent(rbtnNhanVien)
                        .addGap(78, 78, 78)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbNu, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtLuong))))
                .addGap(160, 160, 160))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(btnThem)
                .addGap(46, 46, 46)
                .addComponent(btnSua)
                .addGap(39, 39, 39)
                .addComponent(btnLamMoi)
                .addGap(29, 29, 29)
                .addComponent(btnDelete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(rbtnTruongPhong)
                        .addComponent(rbtnNhanVien))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdbNu)
                        .addComponent(rdbNam))
                    .addComponent(jLabel10))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSua)
                        .addComponent(btnLamMoi)
                        .addComponent(btnDelete)))
                .addGap(10, 10, 10))
        );

        jLabel12.setText("Lọc");

        jLabel14.setText("Lọc theo giới tính");

        jLabel15.setText("Lọc theo vai trò");

        cbGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGioiTinhActionPerformed(evt);
            }
        });

        cbVaiTro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVaiTroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cbVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jLabel13.setText("Tìm kiếm");

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MaNV", "Mật Khẩu", "Tên nhân viên", "Giới tính", "Chức vụ", "Lương", "Số điện thoại", "Email"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );

        Tabs.addTab("Nhân viên", jPanel4);

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Tabs)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(150, 150, 150)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Tabs))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        Tabs.getAccessibleContext().setAccessibleName("Nhân Viên");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.init();
    }//GEN-LAST:event_formWindowOpened

    private void rbtnTruongPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnTruongPhongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnTruongPhongActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        showDetail();
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        create();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void cbGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGioiTinhActionPerformed
        filterTable();
    }//GEN-LAST:event_cbGioiTinhActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cbVaiTroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVaiTroActionPerformed
        filterTable();
    }//GEN-LAST:event_cbVaiTroActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        SearchByName();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemKeyPressed

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
            java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Tabs;
    private javax.swing.ButtonGroup btGChucVu;
    private javax.swing.ButtonGroup btGgioiTinh;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cbGioiTinh;
    private javax.swing.JComboBox<String> cbVaiTro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtnNhanVien;
    private javax.swing.JRadioButton rbtnTruongPhong;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
