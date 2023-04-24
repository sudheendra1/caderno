package com.example.caderno;

public class FileinModel1 {
    String filename,fileurl,fileurl2;

    public FileinModel1() {

    }

    public FileinModel1(String filename, String fileurl,String fileurl2) {
        this.filename = filename;
        this.fileurl = fileurl;
        this.fileurl2=fileurl2;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getFileurl2() {
        return fileurl2;
    }

    public void setFileurl2(String fileurl2) {
        this.fileurl2 = fileurl2;
    }
}
