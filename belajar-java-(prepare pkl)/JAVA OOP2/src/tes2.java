public class tes2 {

    String nama;
    int umur;


    public tes2 (String nama, int umur) {
        this.nama = nama;
        this.umur = umur;
    }

    static void main(String[] args) {
        var person = new tes2 ("Gerald", 17);
        System.out.println("Nama saya adalah " + person.nama + ", Umur saya baru menginjak " + person.umur + " tahun hari ini" );

    }

}
