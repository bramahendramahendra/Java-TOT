/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author fikri
 */
public class Database {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private ArrayList<Mahasiswa> mahasiswa = new ArrayList<>();

    public Database() {
        loadMahasiswa();
    }
    
    public void connect(){
        try {
            String url = "jdbc:mysql://localhost/project_java_testo";
            String user = "root";
            String pass = "";
            conn = DriverManager.getConnection(url, user, pass);
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void disconnect(){
        try {
            conn.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean manipulate(String query){
        boolean cek = false;
        try {
            int rows = stmt.executeUpdate(query);
            if (rows > 0) cek = true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cek;
    }
    
    public void loadMahasiswa() {
        connect();
        try {
            String query = "SELECT * FROM mahasiswa";
            rs = stmt.executeQuery(query);
            while (rs.next()){
                mahasiswa.add(new Mahasiswa(rs.getString("nim"), rs.getString("nama"), rs.getString("jurusan"), rs.getString("jk").charAt(0)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        disconnect();
    }

    public ArrayList<Mahasiswa> getMahasiswa() {
        return mahasiswa;
    }
    
    public void addMahasiswa(Mahasiswa m) {
        connect();
        String query = "INSERT INTO mahasiswa VALUES (";
        query += "'" + m.getNim() + "',";
        query += "'" + m.getNama() + "',";
        query += "'" + m.getJurusan() + "',";
        query += "'" + m.getJk() + "'";
        query += ")";
        if (manipulate(query)) mahasiswa.add(m);
        disconnect();
    }
     
    public boolean cekDuplikatNIM(String nim){
        boolean cek = false;
        for (Mahasiswa mhs : mahasiswa) {
            if (mhs.getNim().equals(nim)){
                cek = true;
                break;
            }
        }
        return cek;
    }
    
    public void delMahasiswa(String nim) {
        connect();
        String query = "DELETE FROM mahasiswa WHERE nim='" + nim + "'";
        if (manipulate(query)){
            for (Mahasiswa mhs : mahasiswa) {
                if (mhs.getNim().equals(nim)){
                    mahasiswa.remove(mhs);
                    break;
                }
            }
        }
        disconnect();
    }
    
    public void updateMahasiswa(Mahasiswa m) {
        connect();
        String query = "UPDATE mahasiswa SET";
        query += " nama='" + m.getNama() + "',";
        query += " jurusan='" + m.getJurusan() + "',";
        query += " jk='" + m.getJk() + "'";
        query += " WHERE nim='" + m.getNim() + "'";
        if (manipulate(query)){
            for (Mahasiswa mhs : mahasiswa) {
                if (mhs.getNim().equals(m.getNim())){
                    mhs.setNama(m.getNama());
                    mhs.setJurusan(m.getJurusan());
                    mhs.setJk(m.getJk());
                    break;
                }
            }
        }
        disconnect();
    }
}
