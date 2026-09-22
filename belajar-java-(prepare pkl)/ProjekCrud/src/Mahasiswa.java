public class Mahasiswa {
    private String nim;
    private String nama;
    private double nilai;

    //konstruktor
    public Mahasiswa (String nim, String nama, double nilai) {
        this.nim = nim;
        this.nama = nama;
        this.nilai = nilai;
    }

   //getter mengambil nilai atribut

    public String getNim () {
        return nim;
    }
    public String getNama () {
        return nama;
    }
    public double getNilai () {
        return nilai;
    }

   //setter mengubah isi atribut
    public void setNim (String nim) {
        this.nim = nim;
    }
    public void setNama (String nama) {
        this.nama = nama;
    }
    public void setNilai (double nilai) {
        this.nilai = nilai;
    }

    //method getGrade
    public String getGrade () {
        if (nilai >= 85) {
            return "a";
        } else if (nilai >= 70) {
            return "b";
        } else if (nilai >= 60) {
            return "c";
        } else {
            return "d";
        }
    }

    //method isLulus
    public boolean isLulus () {
        return nilai >= 60;
    }

    @Override
    public String toString() {

        return "NIM    : " + nim +
                "\nNama   : " + nama +
                "\nNilai  : " + nilai +
                "\nGrade  : " + getGrade() +
                "\nStatus : " + (isLulus() ? "Lulus" : "Tidak Lulus");

    }




}
