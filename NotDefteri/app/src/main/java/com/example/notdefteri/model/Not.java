package com.example.notdefteri.model;

import java.io.Serializable;

public class Not implements Serializable {
    int id;
    String baslik;
    String notMetin;
    String webUrl;
    String imageUrl;
    String tarih;
    String renk;

// buradan constraction oluştur id'yi select none

    public Not() {
    }

    public Not(String baslik, String notMetin, String webUrl, String imageUrl, String tarih, String renk) {
        this.baslik = baslik;
        this.notMetin = notMetin;
        this.webUrl = webUrl;
        this.imageUrl = imageUrl;
        this.tarih = tarih;
        this.renk = renk;
    }
    public Not(int id, String baslik, String notMetin, String webUrl, String imageUrl, String tarih, String renk) {
        this.id = id;
        this.baslik = baslik;
        this.notMetin = notMetin;
        this.webUrl = webUrl;
        this.imageUrl = imageUrl;
        this.tarih = tarih;
        this.renk = renk;
    }
    // buradan getter-setter oluştur

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getNotMetin() {
        return notMetin;
    }

    public void setNotMetin(String notMetin) {
        this.notMetin = notMetin;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }
}
