
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PENJUALAN;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.File;
import java.awt.Desktop;
import java.util.List;
import java.util.Date;
import ACCOUNT.ACCOUNT;
import HALUT.HALAMANUTAMA;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import KONEKSI.konek;
import static KONEKSI.konek.getConnection;
import LAPORAN.LAPORAN;
import MEMBER.MEMBER;
import PEMBELIAN.PEMBELIAN;
import STOK.STOK;
import SUPPLIER.SUPPLIER;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Types;
import com.fazecast.jSerialComm.SerialPort;
import java.io.OutputStream;
import com.itextpdf.text.Document;
import com.fazecast.jSerialComm.SerialPort;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.awt.Desktop;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.Date;
import java.io.File;
import java.awt.Desktop;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.File;
import java.awt.Desktop;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Date;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;




/**
 *
 * @author devipermata
 */
public class PENJUALAN extends javax.swing.JFrame {
 private Connection conn;
  private JTextField rfidInput;
    private boolean isMember;
    private double discountRate;
    private JTextField totalHargaField;
    private DefaultTableModel model;
    private String idMember;
    private boolean isProcessingRFID = false;
    private List<ItemTransaksi> keranjang = new ArrayList<>();
    private StringBuilder barcode = new StringBuilder();


   public PENJUALAN() {
    initComponents();
    conn = konek.getConnection();

    model = new DefaultTableModel(); // Inisialisasi awal model
    tabelpenjualan.setModel(model);  // Hubungkan model ke tabel

    getData(); // Setelah model diset
    id();


    }
    private void getData() {
    String[] columnNames = {
        "ID Transaksi", 
        "Tanggal Jual",    
        "ID Barang",       
        "Nama Barang",
        "Jenis Barang",
        "Nama Member",
        "Harga Jual (Rp)", 
        "Jumlah",          
        "Total Penjualan (Rp)"
    };

    model = new DefaultTableModel(null, columnNames);
    tabelpenjualan.setModel(model);

    String sql = "SELECT tj.id_transaksi_jual, tj.tgl_jual, tj.id_barang, tj.harga_jual, tj.jumlah, " +
                 "b.nama_barang, b.jenis_barang, m.nama_member " +
                 "FROM transaksi_jual tj " +
                 "LEFT JOIN barang b ON tj.id_barang = b.id_barang " +
                 "LEFT JOIN member m ON tj.idMember = m.idMember";

    try (
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery()
    ) {
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        while (rs.next()) {
            String idTransaksi = rs.getString("id_transaksi_jual");
            String tanggal = rs.getString("tgl_jual");
            String idBarang = rs.getString("id_barang");
            String namaBarang = rs.getString("nama_barang");
            String jenisBarang = rs.getString("jenis_barang");
            String namaMember = rs.getString("nama_member");
            int harga = rs.getInt("harga_jual");
            int jumlah = rs.getInt("jumlah");
            int total = harga * jumlah;

            Object[] row = {
                idTransaksi,
                tanggal,
                idBarang,
                (namaBarang != null ? namaBarang : "-"),
                (jenisBarang != null ? jenisBarang : "-"),
                (namaMember != null ? namaMember : "-"),
                rupiah.format(harga),
                jumlah,
                rupiah.format(total)
            };

            model.addRow(row);
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data transaksi ditemukan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public PENJUALAN(Connection conn, JTextField totalHargaField, JTable tabel, JTextField rfidInput) {
    this.conn = conn;
    this.totalHargaField = totalHargaField;
    this.tabelpenjualan = tabel;
    this.rfidInput = rfidInput;
    DefaultTableModel model = (DefaultTableModel) tabelpenjualan.getModel();
    model.setRowCount(0); 
   

    // Tambahkan listener untuk mendeteksi input otomatis dari RFID
    rfidInput.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            processRFID();
        }
        @Override
        public void removeUpdate(DocumentEvent e) { }
        @Override
        public void changedUpdate(DocumentEvent e) { }
    });
    }
    
private void processRFID() {
    if (isProcessingRFID) return;
    isProcessingRFID = true;

    idMember = rfidInput.getText().trim();
    if (!idMember.isEmpty()) {
        checkMember(idMember);
    }
    
    rfidInput.setText(""); // Kosongkan input setelah pemrosesan
    isProcessingRFID = false;
}

private void id() {
    try (PreparedStatement st = conn.prepareStatement("SELECT DISTINCT id_barang, nama_barang FROM barang");
         ResultSet rs = st.executeQuery()) {

        namabarang.removeAllItems();

        while (rs.next()) {
            String id_barang = rs.getString("id_barang");
            String nama_barang = rs.getString("nama_barang");
            String combined = id_barang + " - " + nama_barang;
            namabarang.addItem(combined); // Pastikan JComboBox sudah diinisialisasi
        }
    } catch (SQLException ex) {
        Logger.getLogger(PENJUALAN.class.getName()).log(Level.SEVERE, "Error executing query", ex);
    }
}

public class PopupScanTransaksi extends JDialog {
    private JTextField idTransaksiField, scanBarangField, scanMemberField, jumlahField, uangField, kembalianField;
    private JLabel totalLabel;
    private JDateChooser tanggalChooser;
    private JButton tambahBtn, cetakNotaBtn, simpanBtn, batalBtn;
    private JTable barangTable;
    private DefaultTableModel tableModel;
    private Connection conn;
    private boolean memberScanned = false;
    private String idMember = "";
    private double diskonMember = 0.0;
    private String idTransaksiJual;
    private JTable tabelpenjualan;
    private List<ItemTransaksi> daftarBarang = new ArrayList<>();
    private boolean transaksiSudahTersimpan = false;

    public PopupScanTransaksi(JFrame parent, Connection conn, JTable tabelpenjualan) {
        super(parent, "Scan Transaksi", true);
        this.conn = conn;
        this.tabelpenjualan = tabelpenjualan;

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(new Color(224, 255, 255));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        idTransaksiField = new JTextField();
        scanBarangField = new JTextField();
        scanMemberField = new JTextField();
        jumlahField = new JTextField();
        tanggalChooser = new JDateChooser();
        tanggalChooser.setDateFormatString("yyyy-MM-dd");
        tanggalChooser.setDate(new java.util.Date());
        tanggalChooser.setEnabled(false);

        scanMemberField.addActionListener(e -> scanMember());

        inputPanel.add(new JLabel("🆔 ID Transaksi Jual:", JLabel.RIGHT));
        inputPanel.add(idTransaksiField);
        inputPanel.add(new JLabel("📦 Scan Barcode Barang:", JLabel.RIGHT));
        inputPanel.add(scanBarangField);
        inputPanel.add(new JLabel("🎫 Scan Kartu Member:", JLabel.RIGHT));
        inputPanel.add(scanMemberField);
        inputPanel.add(new JLabel("🔢 Jumlah Barang:", JLabel.RIGHT));
        inputPanel.add(jumlahField);
        inputPanel.add(new JLabel("📅 Tanggal Transaksi:", JLabel.RIGHT));
        inputPanel.add(tanggalChooser);

        tambahBtn = new JButton("Tambah Barang");
        cetakNotaBtn = new JButton("Cetak Nota");
        simpanBtn = new JButton("Simpan");
        batalBtn = new JButton("Batal");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(224, 255, 255));
        buttonPanel.add(tambahBtn);
        buttonPanel.add(cetakNotaBtn);
        buttonPanel.add(simpanBtn);
        buttonPanel.add(batalBtn);

        tableModel = new DefaultTableModel(new String[]{
                "ID Transaksi", "ID Barang", "Nama Barang", "Jenis", "Jumlah", "Harga", "Total", "Tanggal", "ID Member", "Diskon"
        }, 0);
        barangTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(barangTable);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        totalLabel = new JLabel("Total Bayar: Rp 0");
        uangField = new JTextField();
        kembalianField = new JTextField();
        kembalianField.setEditable(false);

        bottomPanel.add(new JLabel("💵 Bayar:"));
        bottomPanel.add(uangField);
        bottomPanel.add(new JLabel("🔁 Kembali:"));
        bottomPanel.add(kembalianField);
        bottomPanel.add(new JLabel("💰 Total Pembayaran:"));
        bottomPanel.add(totalLabel);

        uangField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                hitungKembalian();
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.EAST);

        // Scan Barang action saat tekan Enter
        scanBarangField.addActionListener(e -> {
            if (idTransaksiField.isEditable()) {
                JOptionPane.showMessageDialog(this, "Masukkan ID Transaksi dan scan member dulu sebelum tambah barang.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                idTransaksiField.requestFocus();
                return;
            }
            tambahPesanan();
            scanBarangField.setText("");
            jumlahField.setText("");
            scanBarangField.requestFocus();
        });

        // Tombol tambah barang
        tambahBtn.addActionListener(e -> {
            // Cek apakah ini input pertama kali (ID transaksi masih editable)
            if (idTransaksiField.isEditable()) {
                String idTransaksiInput = idTransaksiField.getText().trim();
                if (idTransaksiInput.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID Transaksi harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Jika member belum discan, coba scan member otomatis
                if (!memberScanned && !scanMemberField.getText().trim().isEmpty()) {
                    scanMember();
                    if (!memberScanned) {
                        JOptionPane.showMessageDialog(this, "Member tidak valid atau belum discan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                // Bekukan input ID Transaksi & Member
                idTransaksiField.setEditable(false);
                scanMemberField.setEditable(false);
                idTransaksiJual = idTransaksiInput;
            }

            tambahPesanan();
            scanBarangField.setText("");
            jumlahField.setText("");
            scanBarangField.requestFocus();
        });

        simpanBtn.addActionListener(e -> simpanTransaksi());
        cetakNotaBtn.addActionListener(e -> cetakNota());
        batalBtn.addActionListener(e -> {
            resetForm();
            dispose();
        });

        setSize(1000, 500);
        setLocationRelativeTo(parent);
    }

    private void scanMember() {
        if (memberScanned) return;
        idMember = scanMemberField.getText().trim();
        if (idMember.isEmpty()) return;

        try {
            String sql = "SELECT idMember, diskon FROM member WHERE idMember = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, idMember);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                memberScanned = true;
                diskonMember = rs.getDouble("diskon");
                scanMemberField.setEditable(false);
                scanMemberField.setBackground(new Color(200, 255, 200));
                JOptionPane.showMessageDialog(this, "Member ditemukan! ID: " + idMember + " Diskon: " + diskonMember + "%");
            } else {
                JOptionPane.showMessageDialog(this, "Member tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                scanMemberField.setText("");
            }
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void tambahPesanan() {
    String idTransaksiJual = idTransaksiField.getText().trim();
    if (idTransaksiJual.isEmpty()) {
        JOptionPane.showMessageDialog(this, "ID Transaksi harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String barcode = scanBarangField.getText().trim();
    String jumlahStr = jumlahField.getText().trim();

    if (barcode.isEmpty() || jumlahStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Barcode dan jumlah barang harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int jumlah = Integer.parseInt(jumlahStr);
        String sql = "SELECT id_barang, nama_barang, harga_jual FROM barang WHERE barcode = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, barcode);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String idBarang = rs.getString("id_barang");
            String namaBarang = rs.getString("nama_barang");
            double harga = rs.getDouble("harga_jual");

            boolean barangSudahAda = false;
            for (ItemTransaksi item : daftarBarang) {
                if (item.getIdBarang().equals(idBarang)) {
                    item.setJumlah(item.getJumlah() + jumlah);
                    barangSudahAda = true;
                    break;
                }
            }

            if (!barangSudahAda) {
ItemTransaksi item = new ItemTransaksi(idBarang, namaBarang, jumlah, harga, diskonMember);
                daftarBarang.add(item);
            }

            refreshTable(idTransaksiJual);
            updateTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Barang tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        rs.close();
        pst.close();
    } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(this, "Jumlah barang harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

private void refreshTable(String idTransaksiJual) {
    tableModel.setRowCount(0);
    String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(tanggalChooser.getDate());

    for (ItemTransaksi item : daftarBarang) {
        double total = item.getTotal(); // pastikan total sudah memperhitungkan diskon
        tableModel.addRow(new Object[]{
            idTransaksiJual,
            item.getIdBarang(),
            item.getNamaBarang(),
            "", // jenis barang jika tidak digunakan
            item.getJumlah(),
            item.getHarga(),
            total,
            tanggal,
            idMember,
            item.getDiskon() // diskon dimunculkan di kolom terakhir
        });
    }
}


private void updateTotal() {
    double totalBayar = 0;
    for (ItemTransaksi item : daftarBarang) {
        totalBayar += item.getTotal();
    }

    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(0);
    nf.setGroupingUsed(false);
    totalLabel.setText("Total Bayar: Rp " + nf.format(totalBayar));
}



private void hitungKembalian() {
    try {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += Double.parseDouble(tableModel.getValueAt(i, 6).toString());
        }
        String uangText = uangField.getText().replaceAll("[.,]", "");
        double uang = Double.parseDouble(uangText);
        double kembali = uang - total;
        uangField.setText(String.format("%.0f", uang));
        kembalianField.setText("Rp " + String.format("%.0f", kembali));
    } catch (Exception e) {
        kembalianField.setText("Rp 0");
    }
}

private void simpanTransaksi() {
    String idTransaksiJual = idTransaksiField.getText().trim();
    if (idTransaksiJual.isEmpty()) {
        JOptionPane.showMessageDialog(this, "ID Transaksi harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (transaksiSudahTersimpan) {
        JOptionPane.showMessageDialog(this, "Transaksi sudah disimpan.");
        return;
    }

    if (daftarBarang.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Tidak ada barang yang ditambahkan dalam transaksi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        conn.setAutoCommit(false);

        String sql = "INSERT INTO transaksi_jual (id_transaksi_jual, id_barang, jumlah, harga_jual, total_jual, tgl_jual, idMember) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);

        for (ItemTransaksi item : daftarBarang) {
            pst.setString(1, idTransaksiJual);
            pst.setString(2, item.getIdBarang());
            pst.setInt(3, item.getJumlah());
            pst.setDouble(4, item.getHarga());
            pst.setDouble(5, item.getTotal());
            pst.setDate(6, new java.sql.Date(tanggalChooser.getDate().getTime()));
            pst.setString(7, idMember.isEmpty() ? null : idMember);
            pst.addBatch();
        }

        pst.executeBatch();
        conn.commit();

        DefaultTableModel modelUtama = (DefaultTableModel) tabelpenjualan.getModel();
        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(tanggalChooser.getDate());
        for (ItemTransaksi item : daftarBarang) {
            modelUtama.addRow(new Object[]{
                idTransaksiJual, tanggal, item.getIdBarang(), item.getNamaBarang(), "", idMember, item.getHarga(), item.getJumlah(), item.getTotal()
            });
        }

        transaksiSudahTersimpan = true;
        JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan");
        pst.close();

    } catch (SQLException ex) {
        try {
            conn.rollback();
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private void cetakNota() {
    new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() {
            try {
                // 1. Simpan transaksi terlebih dahulu
                simpanTransaksi();

                if (!transaksiSudahTersimpan) {
                    JOptionPane.showMessageDialog(null, "Transaksi gagal disimpan, tidak bisa cetak nota.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return null;
                }

                // 2. Ambil data pembayaran
                String idTransaksi = idTransaksiField.getText().trim();
                String total = totalLabel.getText().replaceAll("[^\\d]", "");
                String uang = uangField.getText().replaceAll("[^\\d]", "");
                String kembali = kembalianField.getText().replaceAll("[^\\d]", "");

                if (idTransaksi.isEmpty() || total.isEmpty() || uang.isEmpty() || kembali.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Lengkapi semua data!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return null;
                }

                // 3. Load file .jasper
                InputStream jasperStream = getClass().getResourceAsStream("/notaoke.jasper");
                if (jasperStream == null) {
                    JOptionPane.showMessageDialog(null, "Template nota tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                // 4. Siapkan parameter
                Map<String, Object> params = new HashMap<>();
                params.put("id_transaksi_jual", idTransaksi);
                params.put("total_bayar", Long.parseLong(total));
                params.put("uang_bayar", Long.parseLong(uang));
                params.put("kembalian", Long.parseLong(kembali));
                params.put("diskon", diskonMember); // Jika ada diskon member

                // 5. Cetak laporan
                JasperPrint print = JasperFillManager.fillReport(jasperStream, params, conn);
                if (print.getPages().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Data kosong.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                // 6. Tampilkan viewer
                JasperViewer viewer = new JasperViewer(print, false);
                viewer.setTitle("Nota Penjualan");
                viewer.setVisible(true);

                // 7. Reset form setelah cetak
                resetForm();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal mencetak: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }
    }.execute();
}


    private void resetForm() {
    tableModel.setRowCount(0);
    daftarBarang.clear();   // Kosongkan ArrayList juga
    idTransaksiField.setText("");
    uangField.setText("");
    kembalianField.setText("");
    totalLabel.setText("Total Bayar: Rp 0");
    scanMemberField.setEditable(true);
    scanMemberField.setText("");
    scanMemberField.setBackground(Color.WHITE);
    memberScanned = false;
    idMember = "";
    diskonMember = 0.0;
    transaksiSudahTersimpan = false;
}

    // CompileReport.java
public class CompileReport {
    public static void main(String[] args) {
        try {
            // Compile JRXML dari lokasi asli (src)
            String jrxmlPath = "D:/Punya Ku/Projek/kode kurang nota/gobooka4/src/notaoke.jrxml";

            // Outputkan ke folder resources
            String jasperOutput = "D:/Punya Ku/Projek/kode kurang nota/gobooka4/resources/notaoke.jasper";

            JasperCompileManager.compileReportToFile(jrxmlPath, jasperOutput);
            System.out.println("✅ Sukses compile ke: " + jasperOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}}


public class ItemTransaksi {
    private String idBarang;
    private String namaBarang;
    private int jumlah;
    private double harga;
    private double diskon; // dalam persen (contoh: 10.0)

    public ItemTransaksi(String idBarang, String namaBarang, int jumlah, double harga, double diskon) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.harga = harga;
        this.diskon = diskon;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getHarga() {
        return harga;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getTotal() {
        double total = jumlah * harga;
        if (diskon > 0) {
            total = total - (total * diskon / 100);
        }
        return total;
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

        search = new javax.swing.JTextField();
        a = new javax.swing.JTextField();
        c = new javax.swing.JTextField();
        e = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpenjualan = new javax.swing.JTable();
        harga = new javax.swing.JTextField();
        j = new javax.swing.JTextField();
        namabarang = new javax.swing.JComboBox<>();
        member = new javax.swing.JTextField();
        back = new javax.swing.JLabel();
        tambahbtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        hapustn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        CETAK = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        search.setBackground(new java.awt.Color (0,0,0,0));
        search.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        search.setBorder(null);
        getContentPane().add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 38, 200, 40));

        a.setBackground(new java.awt.Color (0,0,0,0));
        a.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        a.setBorder(null);
        a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aActionPerformed(evt);
            }
        });
        getContentPane().add(a, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 230, 30));

        c.setBackground(new java.awt.Color(0,0,0,0)
        );
        c.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        c.setBorder(null);
        c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cActionPerformed(evt);
            }
        });
        getContentPane().add(c, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 230, 240, 30));

        e.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        getContentPane().add(e, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 160, 240, 30));

        tabelpenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tabelpenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpenjualanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelpenjualan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 920, 340));

        harga.setBackground(new java.awt.Color(0,0,0,0));
        harga.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        harga.setBorder(null);
        harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaActionPerformed(evt);
            }
        });
        getContentPane().add(harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 250, 30));

        j.setBackground(new java.awt.Color(0,0,0,0));
        j.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        j.setBorder(null);
        j.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jActionPerformed(evt);
            }
        });
        getContentPane().add(j, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 230, 250, 30));

        namabarang.setBackground(new java.awt.Color(0,0,0,0)
        );
        namabarang.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        namabarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        namabarang.setBorder(null);
        namabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namabarangActionPerformed(evt);
            }
        });
        getContentPane().add(namabarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 160, 240, 30));

        member.setBackground(new java.awt.Color(0,0,0,0));
        member.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        member.setBorder(null);
        member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberActionPerformed(evt);
            }
        });
        getContentPane().add(member, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, 230, 20));

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PENJUALAN/pembn.jpg"))); // NOI18N
        getContentPane().add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tambahbtn.setText("TAMBAH");
        tambahbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahbtnActionPerformed(evt);
            }
        });
        getContentPane().add(tambahbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 370, 160, 40));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        hapustn.setText("HAPUS");
        hapustn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapustnActionPerformed(evt);
            }
        });
        getContentPane().add(hapustn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 620, 160, 40));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 50));

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 80, 50));

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, 50));

        jButton4.setText("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, 60));

        jButton5.setText("jButton5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, -1, 50));

        jButton6.setText("jButton6");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, 50));

        jButton7.setText("jButton7");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 670, -1, 30));

        jButton8.setText("jButton8");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 730, -1, -1));

        jButton12.setText("jButton12");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 730, -1, 50));

        jButton9.setText("jButton9");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 480, 160, 50));

        CETAK.setText("CETAK");
        CETAK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CETAKActionPerformed(evt);
            }
        });
        getContentPane().add(CETAK, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 760, 160, 40));

        jButton10.setText("jButton10");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 40, -1, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
    dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         new HALAMANUTAMA().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       MEMBER pjln = new MEMBER();
       pjln.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
    String idTransaksi = a.getText().trim(); 
    java.util.Date tanggalJual = e.getDate(); 

    // Ambil ID barang dari ComboBox
    String selectedCombo = namabarang.getSelectedItem().toString(); 
    if (!selectedCombo.contains(" - ")) {
        JOptionPane.showMessageDialog(this, "Format ComboBox harus 'ID - Nama Barang'", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String idBarang = selectedCombo.split(" - ")[0]; 

    String jumlahStr = c.getText().trim();  
    String hargaStr = harga.getText().trim(); 

    if (idTransaksi.isEmpty() || tanggalJual == null || idBarang.isEmpty() ||
        jumlahStr.isEmpty() || hargaStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua kolom harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int jumlah = 0;
    int hargaJual = 0;  
    
    try {
        jumlah = Integer.parseInt(jumlahStr); 
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        hargaJual = Integer.parseInt(hargaStr.replaceAll("[^\\d]", "")); 
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    java.sql.Date sqlTanggalJual = new java.sql.Date(tanggalJual.getTime());
    double totalPenjualan = hargaJual * jumlah; 
    String totalFormatted = String.format("%.0f", totalPenjualan); 

    // UPDATE berdasarkan id_transaksi_jual dan id_barang
    String sql = "UPDATE transaksi_jual SET tgl_jual = ?, harga_jual = ?, jumlah = ?, total_jual = ? "
               + "WHERE id_transaksi_jual = ? AND id_barang = ?";

    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setDate(1, sqlTanggalJual); 
        st.setInt(2, hargaJual); 
        st.setInt(3, jumlah); 
        st.setDouble(4, totalPenjualan); 
        st.setString(5, idTransaksi); 
        st.setString(6, idBarang); 

        int rowsUpdated = st.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Transaksi berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            getData(); 
        } else {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan. Periksa ID transaksi dan ID barang.", "Gagal", JOptionPane.ERROR_MESSAGE);
        }

        j.setText(totalFormatted); // Menampilkan total ke komponen field j

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error saat memperbarui data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton9ActionPerformed

   

    

   
    private void tabelpenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpenjualanMouseClicked
    int selectedRow = tabelpenjualan.getSelectedRow();

    if (selectedRow != -1) {
        // Ambil data dari kolom sesuai urutan tabel
        String idTransaksi = tabelpenjualan.getValueAt(selectedRow, 0).toString(); // ID Transaksi
        String tglTransaksi = tabelpenjualan.getValueAt(selectedRow, 1).toString(); // Tanggal
        String idBarang = tabelpenjualan.getValueAt(selectedRow, 2).toString();     // ID Barang
        String idMember = tabelpenjualan.getValueAt(selectedRow, 5).toString();     // ID Member
        // Di dalam tabelpenjualanMouseClicked
        String hargaStr = tabelpenjualan.getValueAt(selectedRow, 6).toString(); // Harga Jual
        hargaStr = hargaStr.replace("Rp", "").replace(".", "").trim();
        if (hargaStr.contains(",")) {
        hargaStr = hargaStr.split(",")[0]; // Ambil angka sebelum koma
}


        String jumlahStr = tabelpenjualan.getValueAt(selectedRow, 7).toString();    // Jumlah

        // Set ke input field
        a.setText(idTransaksi); // ID Transaksi
        e.setDate(java.sql.Date.valueOf(tglTransaksi)); // Tanggal (pakai JDateChooser)
        member.setText(idMember); // ID Member
        harga.setText(hargaStr); // Set harga ke text field        
        c.setText(jumlahStr); // Jumlah

        // Cari dan set item di ComboBox namabarang berdasarkan ID Barang
        for (int i = 0; i < namabarang.getItemCount(); i++) {
            String item = namabarang.getItemAt(i); // Misal: "2 - CLEO"
            String idItem = item.split(" - ")[0];  // Ambil ID sebelum " - "
            if (idItem.equals(idBarang)) {
                namabarang.setSelectedIndex(i); // Pilih item yang cocok
                break;
            }
        }

        try {
            // Ubah harga dan jumlah ke angka
            int hargaJual = Integer.parseInt(hargaStr.replace("Rp", "").replace(".", "").trim());
            int jumlahBarang = Integer.parseInt(jumlahStr);

            // Hitung total
            double total = hargaJual * jumlahBarang;

            // Format ke Rupiah
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String totalFormatted = currencyFormat.format(total);

            // Tampilkan total beli
            j.setText(totalFormatted);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Data harga atau jumlah tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_tabelpenjualanMouseClicked
 
    

    private void cariBarang(String barcode) {
        try {
            String sql = "SELECT id_barang, nama_barang, harga FROM barang WHERE barcode = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, barcode);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String kode = rs.getString("id_barang");
                String nama = rs.getString("nama_barang");
                double hargaJual = rs.getDouble("harga_jual");
                
                // Update ComboBox
                if (namabarang.getItemCount() == 0) {
                    namabarang.addItem(nama);
                }
                namabarang.setSelectedItem(nama);

                // Update harga
                harga.setText(String.valueOf(hargaJual));

                // Cek apakah barang sudah ada di tabel transaksi
                boolean barangAda = false;
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(kode)) {
                        int jumlah = (int) model.getValueAt(i, 3) + 1;
                        model.setValueAt(jumlah, i, 3);
                        barangAda = true;
                        break;
                    }
                }

                // Jika belum ada, tambahkan ke tabel
                if (!barangAda) {
                    model.addRow(new Object[]{kode, nama, harga, 1});
                }
            } else {
                JOptionPane.showMessageDialog(this, "Barang tidak ditemukan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       STOK stk = new STOK();
        stk.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       PEMBELIAN pb = new PEMBELIAN();
        pb.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        LAPORAN lp = new LAPORAN();
       lp.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
         SUPPLIER sp = new SUPPLIER();
       sp.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
         ACCOUNT acc = new ACCOUNT();
       acc.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        PENJUALAN pjln = new PENJUALAN();
       pjln.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jActionPerformed

    private void cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cActionPerformed
       String jumlahStr = c.getText().trim();  // Ambil nilai jumlah
String hargaJualStr = harga.getText().trim();  // Ambil nilai harga beli

if (jumlahStr.isEmpty() || hargaJualStr.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Jumlah dan harga beli harus diisi.");
    return;
}

try {
    int jumlah = Integer.parseInt(jumlahStr);
    double hargaJual = Double.parseDouble(hargaJualStr.replace("Rp ", "").replace(",", "").trim()); // Menghapus simbol "Rp" dan koma jika ada

    double totalJual = hargaJual * jumlah;

    String totalFormatted = String.format("Rp %.2f", totalJual);

    j.setText(totalFormatted); 

} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(this, "Input jumlah atau harga tidak valid.");
}

    }//GEN-LAST:event_cActionPerformed

    private void hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaActionPerformed

    private void tambahbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahbtnActionPerformed

    tambahbtn.addActionListener(e -> {
        JFrame parentFrame = null;
   PopupScanTransaksi popup = new PopupScanTransaksi(this, conn, tabelpenjualan); 
        popup.setVisible(true);  

});
    }//GEN-LAST:event_tambahbtnActionPerformed

    

    

    private void hapustnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapustnActionPerformed
    String idTransaksiJual = a.getText().trim(); // JTextField ID Transaksi

    // Ambil ID barang dari ComboBox yang berformat "id_barang - nama_barang"
    String selectedCombo = namabarang.getSelectedItem().toString(); 
    if (!selectedCombo.contains(" - ")) {
        JOptionPane.showMessageDialog(this, "Format ComboBox tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    String idBarang = selectedCombo.split(" - ")[0];

    if (idTransaksiJual.isEmpty() || idBarang.isEmpty()) {
        JOptionPane.showMessageDialog(this, "ID Transaksi dan ID Barang harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Yakin ingin menghapus barang ini dari transaksi?",
        "Konfirmasi Hapus Barang",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    // Hapus hanya satu baris dari transaksi berdasarkan ID transaksi dan ID barang
    String sql = "DELETE FROM transaksi_jual WHERE id_transaksi_jual = ? AND id_barang = ?";

    try (PreparedStatement pst = conn.prepareStatement(sql)) {
        pst.setString(1, idTransaksiJual);
        pst.setString(2, idBarang);

        int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Barang berhasil dihapus dari transaksi.");
            getData();      // Refresh tabel
            resetForm();    // Kosongkan input
        } else {
            JOptionPane.showMessageDialog(this, "Barang tidak ditemukan dalam transaksi.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_hapustnActionPerformed

    private void CETAKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CETAKActionPerformed

    }//GEN-LAST:event_CETAKActionPerformed

    private void memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberActionPerformed
    String rfid = member.getText().trim(); // Ambil ID dari field input

    if (rfid.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Silakan scan kartu RFID terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Panggil metode checkMember untuk mengecek di database
    checkMember(rfid);
    }//GEN-LAST:event_memberActionPerformed

    private void namabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namabarangActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
    String keyword = search.getText(); // Ambil teks dari input pencarian

    if (keyword.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Masukkan ID Transaksi untuk pencarian!");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) tabelpenjualan.getModel();
    model.setRowCount(0); // Kosongkan tabel sebelum pencarian

    try {
        String sql = "SELECT tj.id_transaksi_jual, dj.id_barang, b.nama_barang, dj.jumlah, dj.harga, dj.diskon, dj.total, tj.tanggal, tj.id_member " +
                     "FROM transaksi_jual tj " +
                     "JOIN detail_jual dj ON tj.id_transaksi_jual = dj.id_transaksi_jual " +
                     "JOIN barang b ON dj.id_barang = b.id_barang " +
                     "WHERE tj.id_transaksi_jual LIKE ?";
        
        PreparedStatement st = conn.prepareStatement(sql);
        String searchPattern = "%" + keyword + "%";
        st.setString(1, searchPattern);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String idTransaksi = rs.getString("id_transaksi_jual");
            String idBarang = rs.getString("id_barang");
            String namaBarang = rs.getString("nama_barang");
            int jumlah = rs.getInt("jumlah");
            double harga = rs.getDouble("harga");
            double diskon = rs.getDouble("diskon");
            double total = rs.getDouble("total");
            Date tanggal = rs.getDate("tanggal");
            String idMember = rs.getString("id_member");

            Object[] rowData = {
                idTransaksi,
                idBarang,
                namaBarang,
                "", // kolom kosong untuk jenis_barang jika belum digunakan
                jumlah,
                harga,
                total,
                tanggal,
                idMember,
                diskon
            };

            model.addRow(rowData);
        }

        rs.close();
        st.close();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan!");
        }

    } catch (Exception e) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat pencarian: " + e.getMessage());
    }

    }//GEN-LAST:event_jButton10ActionPerformed

// ✅ Cek RFID di database dan hitung diskon
private void checkMember(String rfid) {
    String sql = "SELECT nama_member, diskon FROM member WHERE idMember = ?";
    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setString(1, rfid);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            // Jika RFID ditemukan di database sebagai member
            isMember = true;
            discountRate = rs.getDouble("diskon") / 100; // Ambil diskon dari database
            this.idMember = rfid;
            String namaMember = rs.getString("nama_member"); // Ambil nama member

            // Tampilkan Nama Member di field, bukan ID
            member.setText(namaMember);

            JOptionPane.showMessageDialog(this, "Member ditemukan! Nama: " + namaMember + "\nDiskon: " + (discountRate * 100) + "%");
        } else {
            // Jika bukan member, reset data diskon
            isMember = false;
            this.idMember = null;
            discountRate = 0;

            // Tetap tampilkan ID yang di-scan di pesan
            member.setText("Bukan Member");

            JOptionPane.showMessageDialog(this, "RFID " + rfid + " tidak terdaftar sebagai member. Tidak ada diskon.");
        }

        // **Pastikan tabel diperbarui dan total jual dihitung ulang setelah cek member**
        updateTable();
        updateTotalJualFromField();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


private void updateTable() {
    model = (DefaultTableModel) tabelpenjualan.getModel();
    double totalJual = 0;

    for (int i = 0; i < model.getRowCount(); i++) {
        Object hargaObj = model.getValueAt(i, 5);
        Object jumlahObj = model.getValueAt(i, 3); // Sesuaikan index jumlah dengan tabel

        if (hargaObj != null && jumlahObj != null) {
            try {
                double hargaJual = Double.parseDouble(hargaObj.toString());
                int jumlah = Integer.parseInt(jumlahObj.toString());

                double totalPenjualan = hargaObj instanceof Number ? 
                    hargaObj instanceof Double ? (double) hargaObj * jumlah : (int) hargaObj * jumlah
                    : 0;

                // Terapkan diskon jika member
                double totalSetelahDiskon = isMember ? totalPenjualan * (1 - discountRate) : totalPenjualan;

                // Debugging: Cek nilai sebelum dan setelah diskon
                System.out.println("Harga Jual: " + hargaJual);
                System.out.println("Jumlah: " + jumlah);
                System.out.println("Total Sebelum Diskon: Rp " + String.format("%,.0f", totalPenjualan));
                System.out.println("Diskon: " + (isMember ? discountRate * 100 : 0) + "%");
                System.out.println("Total Setelah Diskon: Rp " + String.format("%,.0f", totalJual));

                // Update nilai total penjualan di tabel
                model.setValueAt(totalJual, i, 7);
            } catch (Exception e) {
                System.err.println("Error perhitungan diskon: " + e.getMessage());
            }
        }
    }

    // Set total jual setelah diskon
    j.setText("Rp " + String.format("%,.0f", totalJual));
}

private void updateTotalJualFromField() {
    String hargaText = harga.getText().trim();
    String jumlahText = c.getText().trim();
    
    // Pastikan harga dan jumlah tidak kosong
    if (!hargaText.isEmpty() && !jumlahText.isEmpty()) {
        try {
            // Konversi dari String ke Double dan Integer
            double hargaJualValue = Double.parseDouble(hargaText.replace("Rp", "").replace(".", ""));
            int jumlahValue = Integer.parseInt(jumlahText);

            // Hitung total awal
            double totalJual = hargaJualValue * jumlahValue;

            // Terapkan diskon jika member
            if (isMember) {
                totalJual -= totalJual * discountRate;
            }

            // Debugging: Cek nilai
            System.out.println("Harga: Rp " + hargaJualValue);
            System.out.println("Jumlah: " + jumlahText);
            System.out.println("Diskon: " + (discountRate * 100) + "%");
            System.out.println("Total Setelah Diskon: Rp " + String.format("%,.0f", totalJual));

            // Update nilai total beli di field j
            j.setText("Rp " + String.format("%,.0f", totalJual));
        } catch (NumberFormatException e) {
            System.out.println("Error parsing angka: " + e.getMessage());
            j.setText("Rp 0"); // Set ke 0 jika terjadi kesalahan parsing
        }
    }
}



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
            java.util.logging.Logger.getLogger(PENJUALAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PENJUALAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PENJUALAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PENJUALAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PENJUALAN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CETAK;
    private javax.swing.JTextField a;
    private javax.swing.JLabel back;
    private javax.swing.JTextField c;
    private com.toedter.calendar.JDateChooser e;
    private javax.swing.JButton hapustn;
    private javax.swing.JTextField harga;
    private javax.swing.JTextField j;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField member;
    private javax.swing.JComboBox<String> namabarang;
    private javax.swing.JTextField search;
    private javax.swing.JTable tabelpenjualan;
    private javax.swing.JButton tambahbtn;
    // End of variables declaration//GEN-END:variables

    private void resetForm() {
         a.setText("");                      // Field ID Transaksi
    c.setText("");                      // Field Jumlah
    harga.setText("");                  // Field Harga
    j.setText("");                      // Field Total (misalnya)
   member.setText(""); 

    e.setDate(null);                    // JDateChooser untuk tanggal jual

    if (namabarang.getItemCount() > 0) {
        namabarang.setSelectedIndex(0); // Kembali ke pilihan pertama
    }

    // Jika pakai tabel preview transaksi di tabelpenjualan, bersihkan juga jika perlu
    // DefaultTableModel model = (DefaultTableModel) tabelpenjualan.getModel();
    // model.setRowCount(0);

    // Jika kamu punya flag transaksi sudah tersimpan, reset juga
    }
}
