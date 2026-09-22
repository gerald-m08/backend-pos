/**
 * ===============================================
 * CLASS : Mahasiswa
 * -----------------------------------------------
 * Class ini digunakan sebagai blueprint / cetakan
 * untuk membuat object Mahasiswa.
 *
 * Setiap object Mahasiswa memiliki:
 * - NIM
 * - Nama
 * - Nilai
 *
 * Class ini hanya bertugas menyimpan data,
 * bukan mengelola data.
 * ===============================================
 */
public class Mahasiswa {

    /*
     * ===========================
     * ATTRIBUTE
     * ===========================
     *
     * Attribute adalah variabel yang dimiliki object.
     *
     * private digunakan agar data tidak bisa diakses
     * secara langsung dari luar class.
     *
     * Data hanya boleh diakses melalui Getter dan Setter.
     */

    private String nim;
    private String nama;
    private double nilai;

    /*
     * ===========================
     * CONSTRUCTOR
     * ===========================
     *
     * Constructor akan dipanggil setiap object dibuat.
     *
     * Contoh:
     *
     * Mahasiswa m = new Mahasiswa("23001", "Budi", 90);
     *
     * Maka constructor ini akan berjalan otomatis.
     */

    public Mahasiswa(String nim, String nama, double nilai) {

        /*
         * Keyword this digunakan untuk menunjukkan
         * bahwa attribute milik object yang sedang dibuat
         * akan diisi dengan parameter dari constructor.
         */

        this.nim = nim;
        this.nama = nama;
        this.nilai = nilai;
    }

    /*
     * ===========================
     * GETTER
     * ===========================
     *
     * Getter digunakan untuk mengambil nilai attribute.
     */

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public double getNilai() {
        return nilai;
    }

    /*
     * ===========================
     * SETTER
     * ===========================
     *
     * Setter digunakan untuk mengubah isi attribute.
     */

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNilai(double nilai) {
        this.nilai = nilai;
    }

    /*
     * ===========================
     * METHOD getGrade()
     * ===========================
     *
     * Method ini menghitung grade berdasarkan nilai.
     *
     * Return:
     * A, B, C, D, atau E
     */

    public String getGrade() {

        if (nilai >= 85) {
            return "A";
        } else if (nilai >= 70) {
            return "B";
        } else if (nilai >= 60) {
            return "C";
        } else if (nilai >= 50) {
            return "D";
        } else {
            return "E";
        }

    }

    /*
     * ===========================
     * METHOD isLulus()
     * ===========================
     *
     * Mengecek apakah mahasiswa lulus.
     *
     * Return:
     * true  -> jika nilai >= 60
     * false -> jika nilai < 60
     */

    public boolean isLulus() {
        return nilai >= 60;
    }

    /*
     * ===========================
     * METHOD toString()
     * ===========================
     *
     * Method ini dipanggil otomatis ketika object
     * dicetak menggunakan:
     *
     * System.out.println(mahasiswa);
     *
     * Tanpa toString(), yang tampil hanyalah alamat
     * object di memori.
     */

    @Override
    public String toString() {

        return "NIM    : " + nim +
                "\nNama   : " + nama +
                "\nNilai  : " + nilai +
                "\nGrade  : " + getGrade() +
                "\nStatus : " + (isLulus() ? "Lulus" : "Tidak Lulus");

    }

}