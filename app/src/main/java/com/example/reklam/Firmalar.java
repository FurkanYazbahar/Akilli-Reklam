package com.example.reklam;

import android.location.Location;
import android.util.Log;

public class Firmalar {

    private String kategori;
    private String kampanyaLokasyon;
    private String kampanyaSuresi;
    private String kampanyaIcerik;
    private String firmaAdi;
    private Location location= new Location("");

    public Firmalar() {
    }

    public Firmalar(String kategori, String kampanyaLokasyon, String kampanyaSuresi, String kampanyaIcerik, String firmaAdi) {
        this.kategori = kategori;
        this.kampanyaLokasyon = kampanyaLokasyon;
        this.kampanyaSuresi = kampanyaSuresi;
        this.kampanyaIcerik = kampanyaIcerik;
        this.firmaAdi = firmaAdi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKampanyaLokasyon() {
        return kampanyaLokasyon;
    }

    public void setKampanyaLokasyon(String kampanyaLokasyon) {
        this.kampanyaLokasyon = kampanyaLokasyon;
    }

    public String getKampanyaSuresi() {
        return kampanyaSuresi;
    }

    public void setKampanyaSuresi(String kampanyaSuresi) {
        this.kampanyaSuresi = kampanyaSuresi;
    }

    public String getKampanyaIcerik() {
        return kampanyaIcerik;
    }

    public void setKampanyaIcerik(String kampanyaIcerik) {
        this.kampanyaIcerik = kampanyaIcerik;
    }

    public String getFirmaAdi() {
        return firmaAdi;
    }

    public void setFirmaAdi(String firmaAdi) {
        this.firmaAdi = firmaAdi;
    }

    public Location getLocation() {
        String[] s = kampanyaLokasyon.split(",");
        location.setLatitude(Double.parseDouble(s[0]));
        location.setLongitude(Double.parseDouble(s[1]));
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
