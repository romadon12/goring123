package com.example.apkdatamhs;
public class InfoMahasiswa {

    private int ID;
    private String NIM;
    private String NAMA;
    private String DOB;
    private String GENDER;
    private String ADDRESS;

    public InfoMahasiswa() {}

    public InfoMahasiswa(int ID, String NIM, String NAMA, String DOB, String GENDER, String ADDRESS) {
        this.ID = ID;
        this.NIM = NIM;
        this.NAMA = NAMA;
        this.DOB = DOB;
        this.GENDER = GENDER;
        this.ADDRESS = ADDRESS;
    }

    public int getId() { return ID; }
    public void setId(int ID) { this.ID = ID; }
    public String getNIM() { return NIM; }
    public void setNIM(String NIM) { this.NIM = NIM; }
    public String getNAMA() { return NAMA; }
    public void setNAMA(String NAMA) { this.NAMA = NAMA; }
    public String getDOB() { return DOB; }
    public void setDOB(String DOB) { this.DOB = DOB; }
    public String getGENDER() { return GENDER; }
    public void setGENDER(String GENDER) { this.GENDER = GENDER; }
    public String getADDRESS() { return ADDRESS; }
    public void setADDRESS(String ADDRESS) { this.ADDRESS = ADDRESS; }

}