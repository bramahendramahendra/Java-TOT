/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import View.ViewMahasiswa;
import Model.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fikri
 */
public class ControllerMahasiswa extends MouseAdapter implements ActionListener {
    
    private ViewMahasiswa view;
    private Database db;

    public ControllerMahasiswa() {
        view = new ViewMahasiswa();
        db = new Database();
        view.addActionListener(this);
        view.addMouseAdapter(this);
        view.setVisible(true);
        loadTable();
    }
    
    public void loadTable(){
        DefaultTableModel model = new DefaultTableModel(new String[]{"NIM", "Nama", "Jurusan", "JK"}, 0);
        ArrayList<Mahasiswa> mahasiswa = db.getMahasiswa();
        for (Mahasiswa m : mahasiswa) {
            model.addRow(new Object[]{m.getNim(), m.getNama(), m.getJurusan(), m.getJk()});
        }
        view.setTbMahasiswa(model);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source.equals(view.getBtnTambah())){
            btnTambahActionPerformed();
            loadTable();
        }else if (source.equals(view.getBtnHapus())){
            btnHapusActionPerformed();
            loadTable();
        }else if (source.equals(view.getBtnUbah())){
            btnUbahActionPerformed();
            loadTable();
        }else if (source.equals(view.getBtnReset())){
            view.reset();
            loadTable();
        }else if (source.equals(view.getBtnCari())){
            btnCariActionPerformed();
        }
    }
    
    public void btnTambahActionPerformed(){
        String nim = view.getNIM();
        String nama = view.getNama();
        String jurusan = view.getJurusan();
        char jk = view.getJK();
        if (nim.isEmpty() || nama.isEmpty() || jurusan.isEmpty() || jk==' '){
            view.showMessage("Data Kosong", "Error", 0);
        }else{
            if (db.cekDuplikatNIM(nim)){
                view.showMessage("NIM Sudah Ada", "Error", 0);
            }else{
                db.addMahasiswa(new Mahasiswa(nim,nama,jurusan,jk));
                view.reset();
                view.showMessage("Data Berhasil Ditambah", "Success", 1);
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent me){
        Object source = me.getSource();
        if (source.equals(view.getTbMahasiswa())){
            int i = view.getSelectedMahasiswa();
            String nim = view.getTbMahasiswa().getModel().getValueAt(i, 0).toString();
            String nama = view.getTbMahasiswa().getModel().getValueAt(i, 1).toString();
            String jurusan = view.getTbMahasiswa().getModel().getValueAt(i, 2).toString();
            char jk = view.getTbMahasiswa().getModel().getValueAt(i, 3).toString().charAt(0);
            
            view.setNIM(nim);
            view.setNama(nama);
            view.setJurusan(jurusan);
            view.setJK(jk);
        }
    }
    
    public void btnHapusActionPerformed(){
        String nim = view.getNIM();
        db.delMahasiswa(nim);
        view.reset();
        view.showMessage("Data Berhasil Dihapus", "Success", 1);
    }
    
    public void btnUbahActionPerformed(){
        String nim = view.getNIM();
        String nama = view.getNama();
        String jurusan = view.getJurusan();
        char jk = view.getJK();
        if (nim.isEmpty() || nama.isEmpty() || jurusan.isEmpty() || jk==' '){
            view.showMessage("Data Kosong", "Error", 0);
        }else{
            if (!db.cekDuplikatNIM(nim)){
                view.showMessage("NIM Tidak Ditemukan", "Error", 0);
            }else{
                db.updateMahasiswa(new Mahasiswa(nim,nama,jurusan,jk));
                view.reset();
                view.showMessage("Data Berhasil Diubah", "Success", 1);
            }
        }
    }
    
    public void btnCariActionPerformed(){
        String cari = view.getCari();
        DefaultTableModel model = new DefaultTableModel(new String[]{"NIM", "Nama", "Jurusan", "JK"}, 0);
        ArrayList<Mahasiswa> mahasiswa = db.getMahasiswa();
        for (Mahasiswa m : mahasiswa) {
            if (m.getNim().contains(cari) || m.getNama().contains(cari) || m.getJurusan().contains(cari)){
                model.addRow(new Object[]{m.getNim(), m.getNama(), m.getJurusan(), m.getJk()});
            }
        }
        view.setTbMahasiswa(model);
    }
    
}
