/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MEMBER;

import HALUT.HALAMANUTAMA;
import KONEKSI.konek;
import LAPORAN.LAPORAN;
import PEMBELIAN.PEMBELIAN;
import PENJUALAN.PENJUALAN;
import STOK.STOK;
import SUPPLIER.SUPPLIER;
import com.mysql.cj.xdevapi.Statement;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;




/**
 *
 * @author devipermata
 */
public class MEMBER extends javax.swing.JFrame {

    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel model;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
   private static final double DISKON_DEFAULT = 10.0; // 10 persen


    public MEMBER() {
        initComponents();
        conn = konek.getConnection();
        loadTable();

        // Set default diskon dan kunci field
        diskon.setText(String.valueOf(DISKON_DEFAULT));
        diskon.setEnabled(false);
        diskon.setEditable(false);
    }

   private void loadTable() {
    String keyword = jTextField1.getText().trim();

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Member");
    model.addColumn("Nama Member");
    model.addColumn("Diskon");

    try {
        String sql = "SELECT idMember, nama_member, diskon FROM member WHERE nama_member LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = {
                rs.getString("idMember"),
                rs.getString("nama_member"),
                rs.getString("diskon")
            };
            model.addRow(row);
        }

        tblMember.setModel(model);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal mengambil data member!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void resetForm() {
        idMember.setText("");
        nama_member.setText("");
        diskon.setText(String.valueOf(DISKON_DEFAULT));
        diskon.setEnabled(false);
        diskon.setEditable(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblMember = new javax.swing.JTable();
        idMember = new javax.swing.JTextField();
        nama_member = new javax.swing.JTextField();
        diskon = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        tambahbtn = new javax.swing.JButton();
        ubahbtn = new javax.swing.JButton();
        hapusbtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblMember.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMemberMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMember);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, 610, 510));

        idMember.setBackground(new java.awt.Color (0,0,0,0));
        idMember.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        idMember.setBorder(null);
        idMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idMemberActionPerformed(evt);
            }
        });
        getContentPane().add(idMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 260, 20));

        nama_member.setBackground(new java.awt.Color (0,0,0,0));
        nama_member.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nama_member.setBorder(null);
        getContentPane().add(nama_member, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 260, 30));

        diskon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        diskon.setBorder(null);
        diskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diskonActionPerformed(evt);
            }
        });
        getContentPane().add(diskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 380, 270, 20));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField1.setBorder(null);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 40, 200, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MEMBER/member.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jButton7.setText("jButton7");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 100, 50));

        tambahbtn.setText("tambah");
        tambahbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahbtnActionPerformed(evt);
            }
        });
        getContentPane().add(tambahbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 160, 40));

        ubahbtn.setText("ubah");
        ubahbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahbtnActionPerformed(evt);
            }
        });
        getContentPane().add(ubahbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 440, 170, 40));

        hapusbtn.setText("hapus");
        hapusbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusbtnActionPerformed(evt);
            }
        });
        getContentPane().add(hapusbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 500, 180, 50));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 43, 50, 30));

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 100, 50));

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 90, 50));

        jButton4.setText("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 90, 50));

        jButton5.setText("jButton5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 90, 50));

        jButton6.setText("jButton6");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 100, 50));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tambahbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahbtnActionPerformed
     String id = idMember.getText().trim();
    String nama = nama_member.getText().trim();

    if (id.isEmpty() || nama.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Kolom ID Member dan Nama Member tidak boleh kosong");
        return;
    }

    try {
        // Persiapan query insert
        String sql = "INSERT INTO member (idMember, nama_member, diskon) VALUES (?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id);
            pst.setString(2, nama);
            pst.setDouble(3, DISKON_DEFAULT); // Simpan sebagai 10.0 (artinya 10%)
            pst.executeUpdate();
        }

        // Refresh tabel dan reset form
        loadTable(); // metode untuk reload JTable
        resetForm(); // metode untuk clear input

        JOptionPane.showMessageDialog(this, "Data member berhasil ditambahkan dengan diskon " + DISKON_DEFAULT + "%");

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Gagal menambahkan data: " + ex.getMessage());
    }
    }//GEN-LAST:event_tambahbtnActionPerformed

    private void ubahbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahbtnActionPerformed
        try {
            String id = idMember.getText().trim();
            String nama = nama_member.getText().trim();

            if (id.isEmpty() || nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Member dan Nama Member tidak boleh kosong");
                return;
            }

            pst = conn.prepareStatement("UPDATE member SET nama_member=?, diskon=? WHERE idMember=?");
            pst.setString(1, nama);
            pst.setDouble(2, DISKON_DEFAULT);
            pst.setString(3, id);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                loadTable();
                JOptionPane.showMessageDialog(this, "Data berhasil diperbarui");
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan atau tidak berubah");
            }
            resetForm();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + ex.getMessage());
        }
    }//GEN-LAST:event_ubahbtnActionPerformed

    private void hapusbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusbtnActionPerformed
         try {
            pst = conn.prepareStatement("DELETE FROM member WHERE idMember=?");
            pst.setString(1, idMember.getText());
            pst.executeUpdate();
            loadTable();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
            resetForm();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage());
        }

    }//GEN-LAST:event_hapusbtnActionPerformed

    private void diskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diskonActionPerformed

    private void idMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idMemberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idMemberActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        HALAMANUTAMA HAL = new HALAMANUTAMA();
       HAL.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       SUPPLIER S = new SUPPLIER();
       S.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       PEMBELIAN PM = new PEMBELIAN();
       PM.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
      STOK ST = new STOK();
       ST.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       PENJUALAN PJ = new PENJUALAN();
       PJ.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       LAPORAN L = new LAPORAN();
       L.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tblMemberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMemberMouseClicked
    int row = tblMember.getSelectedRow();  // Ambil baris yang diklik

    if (row != -1) {
        try {
            // Ambil data dari kolom tabel
            String id = tblMember.getValueAt(row, 0).toString();
            String nama = tblMember.getValueAt(row, 1).toString();
            String diskonStr = tblMember.getValueAt(row, 2).toString();

            // Bersihkan string diskon dari simbol jika ada, misal persen
            diskonStr = diskonStr.replaceAll("[^\\d.]", "");

            // Ubah ke double
            double diskonVal = Double.parseDouble(diskonStr);

            // Tampilkan ke field input
            idMember.setText(id);
            nama_member.setText(nama);
            diskon.setText(String.valueOf(diskonVal));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kesalahan parsing diskon: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Silakan pilih baris terlebih dahulu.");
    }
    }//GEN-LAST:event_tblMemberMouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        loadTable();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadTable();
    }//GEN-LAST:event_jButton1ActionPerformed


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
            java.util.logging.Logger.getLogger(MEMBER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MEMBER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MEMBER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MEMBER.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MEMBER().setVisible(true);
            }
        });
    }
 





 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField diskon;
    private javax.swing.JButton hapusbtn;
    private javax.swing.JTextField idMember;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField nama_member;
    private javax.swing.JButton tambahbtn;
    private javax.swing.JTable tblMember;
    private javax.swing.JButton ubahbtn;
    // End of variables declaration//GEN-END:variables
}
