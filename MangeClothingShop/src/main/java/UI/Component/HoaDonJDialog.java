/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI.Component;

import DAO.ChiTietHoaDonDAO;
import DAO.HoaDonDao;
import DAO.KhachHangDAO;
import DAO.KhuyenMaiDAO;
import DAO.SanPhamDAO;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;
import Entity.SanPham;
import Entity.KhachHang;
import Map.MapChiTietSanPham;
import Map.MapDonGia;
import Map.MapKhuyenMai;
import UI.Detail.DonGiaJDialog;
import Utils.Auth;
import Utils.DialogBox;
import Utils.ValidateInput;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.NumberFormat;
import javax.swing.JDialog;
/**
 *
 * @author ADMIN
 */
enum colHD {
    TENSP(0),
    DONGIA(1),
    SOLUONG(2),
    GIAMGIA(3),
    THUE(4),
    THANHTIEN(5);
    int i;

    private colHD(int i) {
        this.i = i;
    }
}

public class HoaDonJDialog extends javax.swing.JFrame {
    private SanPhamDAO daoSP = new SanPhamDAO();
    private KhachHangDAO daoKH = new KhachHangDAO();
    private HoaDonDao daoHD = new HoaDonDao();
    private KhuyenMaiDAO daoKM = new KhuyenMaiDAO();
    private ChiTietHoaDonDAO daoCTHD = new ChiTietHoaDonDAO();
    private MapChiTietSanPham mapCTSP = new MapChiTietSanPham();
    private MapKhuyenMai mapKM = new MapKhuyenMai();
    private MapDonGia mapDG = new MapDonGia();
    private Date date = new Date();
    private DecimalFormat dfInt = new DecimalFormat("#");
    private DecimalFormat dfMoney = new DecimalFormat("#,###");
    private NumberFormat numFormat = new NumberFormat();
    private ValidateInput input = new ValidateInput();
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private final int THUE_VAT = 10;
    private DefaultTableModel model = new DefaultTableModel();
    
    /**
     * Creates new form HoaDonFormJDialog
     */
    public HoaDonJDialog() throws SQLException {
        initComponents();
        
        init();
    }
    
    // Khởi tạo cho form hóa đơn
    public void init() throws SQLException {
        fillToSanPhamTable();
        fillToHoaDonTable();
        generateForm();
        setGiamGiaThue();
        
        setLocationRelativeTo(null);
    }
    
    // Điền hết tất cả thông tin của hóa đơn vào bảng
    public void fillToSanPhamTable() {
        // Tạo model cho bảng danh sách sản phẩm
        DefaultTableModel model = new DefaultTableModel(); 
        
        // Lấy dữ liệu từ database
        List<SanPham> list = daoSP.getAllData(); 
        
        // Tạo mảng chứa tên cột
        String[] col = {"Danh sách sản phẩm"}; 
        
        // Tạo cột cho bảng theo col
        model.setColumnIdentifiers(col); 
        
        // Thêm dòng và dữ liệu cho bảng
        for (SanPham o : list) { 
            model.addRow(new Object[]{
                mapCTSP.getValueByID(o.getMaSP())
            });
        }
        
        // Thiết lập model cho bảng danh sách sản phẩm
        tblDanhSachSanPham.setModel(model); 
    }
    
    // Điền thông tin của hóa đơn sao khi tìm kiếm
    public void filterToSanPhamTable() {
        // Tạo model cho bảng danh sách sản phẩm
        DefaultTableModel modelSP = new DefaultTableModel(); 
        
        // Lấy dữ liệu theo tên sản phẩm
        List<SanPham> list = daoSP.getDataByValue(txtTenSanPham.getText());
        
        // Tạo mảng chứa tên cột
        String[] col = {"Danh sách sản phẩm"}; 
        
        // Tạo cột cho bảng theo col
        modelSP.setColumnIdentifiers(col); 
        
        // Thêm dòng và dữ liệu cho bảng
        for (SanPham o : list) { 
            modelSP.addRow(new Object[]{
                mapCTSP.getValueByID(o.getMaSP())
            });
        }
        
        // Thiết lập model cho bảng danh sách sản phẩm
        tblDanhSachSanPham.setModel(modelSP);
    }
    
    // Tạo danh sách mã hóa đơn đã tạo
    public List<String> listMaHD() {
        List<HoaDon> list = daoHD.getAllData();
        List<String> maHD = new ArrayList<>();
        
        for (HoaDon o : list) {
            maHD.add(o.getMaHD());
        }
        
        return maHD;
    }
    
    // Tạo mã hóa đơn
    public String generateMaHD() {
        String maHD = null;      
        int count = 1;
        
        // Kiếm tra xem mã hóa đơn vào tạo có nằm trong danh sách đã có hay không
        do {
            maHD = String.format("HD%d", count++);
        } while (listMaHD().contains(maHD));
        
        return maHD;
    } 
    
    // Tạo danh sách mã khách hàng
    public List<String> listMaKH() {
        List<KhachHang> list = daoKH.getAllData();
        List<String> maKH = new ArrayList<>();
        
        for (KhachHang o : list) {
            maKH.add(o.getMaKH());
        }
        
        return maKH;
    }
    
    // Tạo mã khách hàng
    public String generateMaKH() {
        String maKH = null;
        int count = 1;
        
        // Kiểm tra xem mã khách hàng có nằm trong danh sách hay không
        do {
            maKH = String.format("KH%d", count++);    
        } while (listMaKH().contains(maKH));
                
        return maKH;
    }
    
    // Tạo danh sách mã chi tiết hóa đơn
    public List<String> listMaCTHD() {
        List<ChiTietHoaDon> list = daoCTHD.getAllData();
        List<String> maCTHD = new ArrayList<>();
        
        for (ChiTietHoaDon o : list) {
            maCTHD.add(o.getMaCTHD());
        }
        
        return maCTHD;
    }
    
    // Tạo mã chi tiết hóa đơn
    public String generateMaCTHD() {
        String maCTHD = null;
        int count = 1;
        
        do {
            maCTHD = String.format("CTHD%d", count++);    
        } while (listMaCTHD().contains(maCTHD));
                
        return maCTHD;
    }
    
    // Tạo mã nhân viên và tên nhân viên
    public void generateForm() {
        String maNhanVien = "PS43010";
        String tenNhanVien = "Trần Lê Duy Thiện";
        
        // Lấy mã và tên đăng nhập sau khi đăng nhập tài khoảng
        if (Auth.isLogin()) {
            tenNhanVien = Auth.user.getTenNV();
            maNhanVien = Auth.user.getMaNV();
        }
        
        txtMaNV.setText(maNhanVien);
        txtTenNhanVien.setText(tenNhanVien);
        txtNgayBan.setText(formatter.format(date));
        txtMaHD.setText(generateMaHD());
        txtMaKH.setText(generateMaKH());
    }
     
    // Điền thông tin của sản phẩm vào form
    public void showTenSanPham() {
        int index = tblDanhSachSanPham.getSelectedRow();
        String tenSP = (String) tblDanhSachSanPham.getValueAt(index, 0);
        txtTenSanPham.setText(tenSP);
        txtDonGia.setText(dfInt.format(mapDG.getValueByID(tenSP)));
    }
    
    // Tạo bảng hóa đơn và thêm thông tin hóa đơn vào bảng
    public void fillToHoaDonTable() {
        String[] col = {
            "Tên mặt hàng",
            "Đơn giá",
            "Số lượng",
            "Giảm giá (%)",
            "Thuế (%)",
            "Thành tiền"
        };
        
        model.setColumnIdentifiers(col);
        
        tblHoaDon.setModel(model);
    }
    
    // Khởi tạo giảm giá và thuế
    public void setGiamGiaThue() throws SQLException {
        float mucKM = daoKM.getDataByDate(date);
        txtGiamGia.setText(dfInt.format(mucKM));
        txtThue.setText(String.valueOf(THUE_VAT));
    }
    
    // Điền tổng tiền vào form
    public void setTongTien() {
        double tongTien = 0;
        final int COL_THANHTIEN = 5;
        
        for (int i = 0; i < tblHoaDon.getRowCount(); i++) {
            String thanhTienInTable = numFormat.removeCommas((String) tblHoaDon.getValueAt(i,COL_THANHTIEN));
            Double thanhTien = Double.valueOf(thanhTienInTable);
            tongTien += thanhTien;
        }
        
        txtTongTien.setText(dfMoney.format(tongTien));
    }
    
    // Kiểm tra tính hợp lệ khi người dùng nhập vào
    public boolean isCheckValid() {
        StringBuilder sb = new StringBuilder();
        String maHD = txtMaHD.getText();
        String maKH = txtMaKH.getText();
        String tenSP = txtTenSanPham.getText();
        String soLuong = txtSoLuong.getText();
        String patternNumber = "\\d*";
        String patternText = "\\s+";
        int count = 0;
        
        if (maHD.equals("") || maHD.matches(patternText)) {
            sb.append("Bạn chưa nhập mã hóa đơn\n");
            count++;
        }
        
        if (maKH.equals("") || maKH.matches(patternText)) {
            sb.append("Bạn chưa nhập mã khách hàng\n");
            count++;
        }
        
        if (tenSP.equals("") || tenSP.matches(patternText)) {
            sb.append("Bạn chưa nhập mã khách hàng\n");
            count++;
        }
        
        if (soLuong.equals("") || !soLuong.matches(patternNumber)) {
            sb.append("Bạn chưa nhập số lượng \n");
            count++;
        }
        
        if (sb.length() > 0) {
            DialogBox.notice(this, sb.toString());
        }
        
        return count == 0;
    }
    
    // Kiểm tra tên sản phẩm người dùng nhập vào có nằm trong danh sách hay không
    public boolean isCheckContain() {
        String tenSP = txtTenSanPham.getText();
        Map<String, String> map = mapCTSP.getMapData();
        int count = 0;
        
        // Kiếm tra xem mã có kiếm trong danh sách hay không
        for (Map.Entry<String, String> o : map.entrySet()) {
            if (tenSP.equals(o.getValue())) count++;
        }
        
        if (count > 0) {
            return true;
        } else {
            DialogBox.notice(this, "Mặt hàng này không có trong kho");
            return false;
        } 
    }
    
    // Kiếm tra số lượng mặt hàng trong hóa đơn có lớn hơn số lượng tồn kho hay không
    public boolean isInventory(String sanPham, int soLuong) {
        SanPham sp = null;
        
        // Dò tìm và nếu tìm được tên sản phẩm thì gán dữ liệu của sản phẩm vào sp
        for (SanPham o : daoSP.getAllData()) {
            if (o.getMaSP().equals(mapCTSP.getIDByValue(sanPham))) sp = o;   
        }
         
        // Kiếm tra xem số lượng chi tiết hóa đơn có lớn hơn số lượng tồn kho hay không
        if (sp.getSoLuong() < soLuong) {
            DialogBox.notice(this, "Số lượng tồn kho không đủ\n"
                    + "Số lượng sản phẩm " + mapCTSP.getValueByID(sp.getMaSP()) +
                    ": " + sp.getSoLuong());
            return false;
        } else {
            return true;
        }  
    }
    
    // Kiếm tra xem người dùng click chuột vào bảng hóa đơn hay chưa
    public boolean isCheckIndex() {
        int index = -1;
        
        index = tblHoaDon.getSelectedRow();
        
        if(index < 0) {
            DialogBox.notice(this, "Bạn chưa chọn sản phẩm\n");
            return false;
        } else {
            return true;
        }
    }
    
    public boolean isCheckLengthHoaDon() {
        System.out.println(tblHoaDon.getRowCount());
        if (tblHoaDon.getRowCount() == 0) {
            DialogBox.notice(this, "Chưa có giỏ hàng");
            return false;
        } else {
            return true;
        }
    }
    
    // Thêm khách hàng vào database
    public void createKhachHang() {
        if (isCheckValid()) {
            daoKH.insertData(new KhachHang(
                    txtMaKH.getText(), 
                    txtTenKH.getText(), 
                    true, 
                    txtDiaChi.getText(), 
                    txtSoDT.getText()
          ));
        }
    }
    
    // Thêm hóa đơn vào database
    public void createHoaDon() {
        if (isCheckValid()) {
            daoHD.insertData(new HoaDon(
                    txtMaHD.getText(), 
                    txtMaNV.getText(), 
                    txtMaKH.getText(),
                    date,
                    "Tiền mặt", 
                    true
            ));
        }
    }
    
    // Thêm chi tiết hóa đơn vào database
    public void createChiTietHoaDon() {
        if (isCheckValid()) {
            for (int i = 0; i < tblHoaDon.getRowCount(); i++) {
                String maCTHD = generateMaCTHD();
                String maHD = txtMaHD.getText();
                String maSP = mapCTSP.getIDByValue((String) tblHoaDon.getValueAt(i, colHD.TENSP.i));
                String soLuong = String.valueOf(tblHoaDon.getValueAt(i, colHD.SOLUONG.i));
                String donGia = numFormat.removeCommas((String.valueOf(tblHoaDon.getValueAt(i, colHD.DONGIA.i))));
                String maKM = mapKM.getIDByValue(Integer.valueOf((String) tblHoaDon.getValueAt(i, colHD.GIAMGIA.i)));
                String thue = String.valueOf(tblHoaDon.getValueAt(i, colHD.THUE.i));
                
                daoCTHD.insertData(new ChiTietHoaDon(
                        maCTHD, 
                        maHD, 
                        maSP, 
                        maKM, 
                        Integer.parseInt(soLuong), 
                        Double.parseDouble(donGia), 
                        Integer.parseInt(thue)
                ));
            }
        }
    }

    // Thêm sản phẩm vào bảng hóa đơn
    public void addSanPhamVaoHD() {       
        if (isCheckValid() || txtSoLuong.equals("0") ) {
            if (isCheckContain()) {
                if (isInventory(txtTenSanPham.getText() ,Integer.parseInt(txtSoLuong.getText()))) {
                    String tenSP = txtTenSanPham.getText();
                    double donGia = Double.parseDouble(txtDonGia.getText());
                    int soLuong = Integer.parseInt(txtSoLuong.getText());
                    float giamGia = Float.parseFloat(txtGiamGia.getText());
                    int thue = Integer.parseInt(txtThue.getText());
                    double thanhTien = donGia * soLuong * ((1 - giamGia/100))*(100 + thue)/100;

                    Object[] row = {
                        tenSP,
                        dfMoney.format(donGia),
                        soLuong,
                        dfInt.format(giamGia),
                        dfInt.format(thue), 
                        dfMoney.format(thanhTien)
                    };

                    model.addRow(row);

                    tblHoaDon.setModel(model);  
                    setTongTien();
                    txtSoLuong.setText("0");
                }
            }
        }  
    }
    
    // Tạo thông tin cho hóa đơn
    public StringBuilder inHoaDon() {
        StringBuilder thongTinHoaDon = new StringBuilder();
        String tenKH = txtTenKH.getText();
        String tenKhachHang = tenKH.equals("")?"Khách lẻ":txtTenKH.getText();
        String tongTien = txtTongTien.getText();
        StringBuilder sanPham = new StringBuilder();
        
        // Tạo danh sách sản phẩm đã bán
        for (int i = 0; i < tblHoaDon.getRowCount(); i++) {
            String maSP = (String) tblHoaDon.getValueAt(i, colHD.TENSP.i);
            String donGia = (String.valueOf(tblHoaDon.getValueAt(i, colHD.DONGIA.i)));
            String soLuong = String.valueOf(tblHoaDon.getValueAt(i, colHD.SOLUONG.i));
            String thanhTien = String.valueOf(tblHoaDon.getValueAt(i, colHD.THANHTIEN.i));
            
            sanPham.append(maSP + ": " + donGia + " x " + soLuong + " = " + thanhTien + "\n");
        } 
        
        // Tạo thông tin của hóa đơn
        thongTinHoaDon.append("Thông tin hóa đơn\n" +
                "---------------------------------------------------------------\n" +
                "Ngày tạo hóa đơn: " + txtNgayBan.getText() + "\n" +
                "Người lập hóa đơn: " + txtTenNhanVien.getText() + "\n" +
                "Tên khách hàng: " + tenKhachHang + "\n" +
                "Sản phẩm: \n" +
                "---------------------------------------------------------------" + "\n" +
                sanPham +
                "---------------------------------------------------------------" + "\n" +
                "Tổng số tiền cần thanh toán: " + tongTien);
        
        return thongTinHoaDon;
    }
    
    // Xóa form
    public void clearForm() {
        txtMaHD.setText("");
        txtMaKH.setText("");
        txtDiaChi.setText("");
        txtSoDT.setText("");
        txtTenSanPham.setText("");
        txtDonGia.setText("0");
        txtSoLuong.setText("0");
    }
    
    // Lưu hóa đơn vào database
    public void saveHoaDon() {
        if (isCheckLengthHoaDon()) {
            createKhachHang();
            createHoaDon();
            createChiTietHoaDon();
            
            DialogBox.notice(this, inHoaDon().toString());
            clearHoaDon();
        }
    }
    
    // Xóa sản phẩm đã chọn trong bảng hóa đơn
    public void removeSanPham() {
        if (isCheckIndex()) {
            int index = tblHoaDon.getSelectedRow();
            model.removeRow(index);

            tblHoaDon.setModel(model);

            setTongTien();
        }
    }
    
    // Xóa bảng hóa đơn
    public void clearHoaDon() {
        clearForm();
        model.setRowCount(0);
        tblHoaDon.setModel(model);
        generateForm();
        
        setTongTien();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnThongTinChung = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNgayBan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTenNhanVien = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        txtSoDT = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        pnThongTinHoaDon = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtGiamGia = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachSanPham = new javax.swing.JTable();
        txtThue = new javax.swing.JTextField();
        btnThemHoaDon = new javax.swing.JButton();
        btnLuuHoaDon = new javax.swing.JButton();
        btnHuyHoaDon = new javax.swing.JButton();
        btnXoaKhoiHD = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnThongTinChung.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Mã hóa đơn");

        txtMaHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaHDKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Ngày bán");

        txtNgayBan.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Mã nhân viên");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Tên nhân viên");

        txtTenNhanVien.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Mã khách hàng");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Tên khách hàng");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Địa chỉ");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Số điện thoại");

        txtTenKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenKHKeyPressed(evt);
            }
        });

        txtSoDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSoDTKeyPressed(evt);
            }
        });

        txtMaNV.setEnabled(false);

        javax.swing.GroupLayout pnThongTinChungLayout = new javax.swing.GroupLayout(pnThongTinChung);
        pnThongTinChung.setLayout(pnThongTinChungLayout);
        pnThongTinChungLayout.setHorizontalGroup(
            pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinChungLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnThongTinChungLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenNhanVien))
                    .addGroup(pnThongTinChungLayout.createSequentialGroup()
                        .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnThongTinChungLayout.createSequentialGroup()
                                .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinChungLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                            .addComponent(txtNgayBan)
                            .addComponent(txtMaNV))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaKH)
                    .addComponent(txtTenKH)
                    .addComponent(txtDiaChi)
                    .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
        pnThongTinChungLayout.setVerticalGroup(
            pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinChungLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(pnThongTinChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("HÓA ĐƠN BÁN HÀNG");

        pnThongTinHoaDon.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Tên sản phẩm");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("Đơn giá");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText("Số lượng");

        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyPressed(evt);
            }
        });

        txtDonGia.setEnabled(false);
        txtDonGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDonGiaKeyPressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText("Giảm giá %");

        txtGiamGia.setEnabled(false);
        txtGiamGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGiamGiaKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel14.setText("Thuế");

        txtTenSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTenSanPhamKeyPressed(evt);
            }
        });

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tên mặt hàng", "Số lượng", "Đơn giá", "Giảm giá (%)", "Thuế (%)", "Thành tiền"
            }
        ));
        jScrollPane2.setViewportView(tblHoaDon);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel15.setText("Tổng số tiền cần thanh toán");

        txtTongTien.setEnabled(false);

        tblDanhSachSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Danh sách sản phẩm"
            }
        ));
        tblDanhSachSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachSanPham);

        txtThue.setEnabled(false);
        txtThue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtThueKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnThongTinHoaDonLayout = new javax.swing.GroupLayout(pnThongTinHoaDon);
        pnThongTinHoaDon.setLayout(pnThongTinHoaDonLayout);
        pnThongTinHoaDonLayout.setHorizontalGroup(
            pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinHoaDonLayout.createSequentialGroup()
                .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinHoaDonLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnThongTinHoaDonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThue, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
            .addGroup(pnThongTinHoaDonLayout.createSequentialGroup()
                .addGap(375, 375, 375)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTongTien)
                .addGap(27, 27, 27))
            .addGroup(pnThongTinHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 906, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnThongTinHoaDonLayout.setVerticalGroup(
            pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinHoaDonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinHoaDonLayout.createSequentialGroup()
                        .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(20, 20, 20)
                        .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtThue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnThongTinHoaDonLayout.createSequentialGroup()
                        .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnThongTinHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(216, 216, 216))
        );

        btnThemHoaDon.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnThemHoaDon.setText("Thêm vào hóa đơn");
        btnThemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHoaDonActionPerformed(evt);
            }
        });

        btnLuuHoaDon.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLuuHoaDon.setText("Lưu hóa đơn");
        btnLuuHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuHoaDonActionPerformed(evt);
            }
        });

        btnHuyHoaDon.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnHuyHoaDon.setText("Hủy hóa đơn");
        btnHuyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHoaDonActionPerformed(evt);
            }
        });

        btnXoaKhoiHD.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnXoaKhoiHD.setText("Xóa khỏi hóa đơn");
        btnXoaKhoiHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhoiHDActionPerformed(evt);
            }
        });

        btnCapNhat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCapNhat.setText("Cập nhật đơn giá");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(395, 395, 395)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnThongTinHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnThongTinChung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(btnThemHoaDon)
                        .addGap(36, 36, 36)
                        .addComponent(btnXoaKhoiHD)
                        .addGap(46, 46, 46)
                        .addComponent(btnLuuHoaDon)
                        .addGap(49, 49, 49)
                        .addComponent(btnHuyHoaDon)
                        .addGap(44, 44, 44)
                        .addComponent(btnCapNhat)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnThongTinChung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnThongTinHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaKhoiHD)
                    .addComponent(btnLuuHoaDon)
                    .addComponent(btnHuyHoaDon)
                    .addComponent(btnThemHoaDon)
                    .addComponent(btnCapNhat))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHoaDonActionPerformed
        addSanPhamVaoHD();
    }//GEN-LAST:event_btnThemHoaDonActionPerformed

    private void tblDanhSachSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachSanPhamMouseClicked
        showTenSanPham();
    }//GEN-LAST:event_tblDanhSachSanPhamMouseClicked

    private void txtTenSanPhamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenSanPhamKeyPressed
        filterToSanPhamTable();
    }//GEN-LAST:event_txtTenSanPhamKeyPressed

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed
        clearHoaDon();
    }//GEN-LAST:event_btnHuyHoaDonActionPerformed

    private void txtDonGiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDonGiaKeyPressed
        input.inputNumber(txtDonGia,15);
    }//GEN-LAST:event_txtDonGiaKeyPressed

    private void txtSoLuongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyPressed
        input.inputNumber(txtSoLuong,5);
    }//GEN-LAST:event_txtSoLuongKeyPressed

    private void btnLuuHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuHoaDonActionPerformed
        saveHoaDon();
    }//GEN-LAST:event_btnLuuHoaDonActionPerformed

    private void txtGiamGiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiamGiaKeyPressed
        input.inputNumber(txtGiamGia,2);
    }//GEN-LAST:event_txtGiamGiaKeyPressed

    private void txtThueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtThueKeyPressed
        input.inputNumber(txtThue,2);
    }//GEN-LAST:event_txtThueKeyPressed

    private void txtSoDTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoDTKeyPressed
        input.inputPhone(txtSoDT);
    }//GEN-LAST:event_txtSoDTKeyPressed

    private void btnXoaKhoiHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhoiHDActionPerformed
        removeSanPham();
    }//GEN-LAST:event_btnXoaKhoiHDActionPerformed

    private void txtTenKHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKHKeyPressed
        input.inputUnicode(txtTenKH,50);
    }//GEN-LAST:event_txtTenKHKeyPressed

    private void txtMaHDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaHDKeyPressed
        input.inputString(txtMaKH, 10);
    }//GEN-LAST:event_txtMaHDKeyPressed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed

        if (Auth.isManager()) {
            DonGiaJDialog dialog = new DonGiaJDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } else {
            DialogBox.notice(this, "Bạn không có quyền truy cập");
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

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
            java.util.logging.Logger.getLogger(HoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new HoaDonJDialog().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(HoaDonJDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnHuyHoaDon;
    private javax.swing.JButton btnLuuHoaDon;
    private javax.swing.JButton btnThemHoaDon;
    private javax.swing.JButton btnXoaKhoiHD;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnThongTinChung;
    private javax.swing.JPanel pnThongTinHoaDon;
    private javax.swing.JTable tblDanhSachSanPham;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtGiamGia;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayBan;
    private javax.swing.JTextField txtSoDT;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtThue;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
