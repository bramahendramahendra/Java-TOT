/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author fikri
 */
public class Mahasiswa {
    private String nim;
    private String nama;
    private String jurusan;
    private char jk;

    public Mahasiswa(String nim, String nama, String jurusan, char jk) {
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.jk = jk;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public char getJk() {
        return jk;
    }

    public void setJk(char jk) {
        this.jk = jk;
    }
    
    
}
