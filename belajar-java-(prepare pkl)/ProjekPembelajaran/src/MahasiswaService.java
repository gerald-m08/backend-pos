import java.util.ArrayList;
import java.util.Scanner;

/*
 * =====================================================
 * CLASS : MahasiswaService
 * -----------------------------------------------------
 * Class ini bertugas mengelola data mahasiswa.
 *
 * Semua proses CRUD dilakukan di class ini.
 *
 * C = Create
 * R = Read
 * U = Update
 * D = Delete
 * =====================================================
 */

public class MahasiswaService {

    /*
     * Scanner digunakan untuk membaca input user.
     */

    private Scanner input = new Scanner(System.in);

    /*
     * ArrayList digunakan sebagai "database sementara".
     *
     * Semua object Mahasiswa akan disimpan di sini.
     */

    private ArrayList<Mahasiswa> daftarMahasiswa = new ArrayList<>();


    /*
     * =====================================================
     * METHOD : tambahMahasiswa()
     * -----------------------------------------------------
     * Fungsi:
     * Menambahkan data mahasiswa baru ke ArrayList.
     *
     * Return :
     * void
     * =====================================================
     */

    public void tambahMahasiswa() {

        System.out.println("\n===== Tambah Mahasiswa =====");

        // Input NIM
        System.out.print("NIM   : ");
        String nim = input.nextLine();

        // Input Nama
        System.out.print("Nama  : ");
        String nama = input.nextLine();

        // Input Nilai
        System.out.print("Nilai : ");
        double nilai = Double.parseDouble(input.nextLine());

        /*
         * Membuat object Mahasiswa baru.
         *
         * Setelah baris ini dijalankan,
         * object mahasiswa sudah ada di memori.
         */

        Mahasiswa mahasiswa = new Mahasiswa(nim, nama, nilai);

        /*
         * Menambahkan object ke ArrayList.
         */

        daftarMahasiswa.add(mahasiswa);

        System.out.println("\nData berhasil ditambahkan.");

    }

    /*
     * =====================================================
     * METHOD : tampilkanMahasiswa()
     * -----------------------------------------------------
     * Fungsi :
     * Menampilkan seluruh data mahasiswa yang ada di
     * dalam ArrayList.
     *
     * Konsep yang dipelajari:
     * - isEmpty()
     * - for-each
     * - Object
     * - Getter
     * - printf()
     * =====================================================
     */

    public void tampilkanMahasiswa() {

        // Mengecek apakah ArrayList masih kosong
        if (daftarMahasiswa.isEmpty()) {
            System.out.println("\nBelum ada data mahasiswa.");
            return;
        }

        // Menampilkan judul tabel
        System.out.println("\n==============================================================");
        System.out.printf("%-10s %-20s %-10s %-10s %-10s%n",
                "NIM", "Nama", "Nilai", "Grade", "Status");
        System.out.println("==============================================================");

        /*
         * Mengambil setiap object Mahasiswa
         * yang ada di dalam ArrayList.
         */
        for (Mahasiswa mahasiswa : daftarMahasiswa) {

            System.out.printf("%-10s %-20s %-10.2f %-10s %-10s%n",
                    mahasiswa.getNim(),
                    mahasiswa.getNama(),
                    mahasiswa.getNilai(),
                    mahasiswa.getGrade(),
                    mahasiswa.isLulus() ? "Lulus" : "Tidak");

        }

        System.out.println("==============================================================");
    }

    /*
     * =====================================================
     * METHOD : cariMahasiswaByNim()
     * -----------------------------------------------------
     * Fungsi :
     * Mencari mahasiswa berdasarkan NIM.
     *
     * Return :
     * Mahasiswa -> jika ditemukan
     * null      -> jika tidak ditemukan
     *
     * Kenapa return Mahasiswa?
     * Karena kita ingin mengembalikan object Mahasiswa,
     * bukan hanya nama atau nilainya.
     * =====================================================
     */

    public Mahasiswa cariMahasiswaByNim(String nim) {

        /*
         * Periksa setiap object Mahasiswa
         * yang ada di dalam ArrayList.
         */
        for (Mahasiswa mahasiswa : daftarMahasiswa) {

            /*
             * Bandingkan NIM object
             * dengan NIM yang dicari user.
             */
            if (mahasiswa.getNim().equals(nim)) {

                /*
                 * Jika ditemukan,
                 * kembalikan object tersebut.
                 */
                return mahasiswa;
            }
        }

        /*
         * Jika seluruh data sudah dicek
         * tetapi tidak ada yang cocok,
         * kembalikan null.
         */
        return null;
    }

    /*
     * =====================================================
     * METHOD : editMahasiswa()
     * -----------------------------------------------------
     * Fungsi :
     * Mengubah data mahasiswa berdasarkan NIM.
     *
     * Langkah kerja:
     * 1. Minta user memasukkan NIM.
     * 2. Cari object Mahasiswa.
     * 3. Jika ditemukan:
     *      - ubah nama
     *      - ubah nilai
     * 4. Jika tidak ditemukan:
     *      tampilkan pesan.
     * =====================================================
     */

    public void editMahasiswa() {

        System.out.println("\n===== EDIT MAHASISWA =====");

        // Meminta NIM yang ingin diedit
        System.out.print("Masukkan NIM : ");
        String nim = input.nextLine();

        /*
         * Cari object mahasiswa.
         *
         * Method ini akan mengembalikan:
         * - Object Mahasiswa
         * - null jika tidak ditemukan
         */
        Mahasiswa mahasiswa = cariMahasiswaByNim(nim);

        // Jika tidak ditemukan
        if (mahasiswa == null) {

            System.out.println("\nMahasiswa tidak ditemukan.");
            return;

        }

        /*
         * Jika sampai di sini berarti object ditemukan.
         */

        System.out.println("\nData ditemukan.");

        System.out.println("Nama Lama  : " + mahasiswa.getNama());
        System.out.println("Nilai Lama : " + mahasiswa.getNilai());

        // Input data baru
        System.out.print("\nNama Baru  : ");
        String namaBaru = input.nextLine();

        System.out.print("Nilai Baru : ");
        double nilaiBaru = Double.parseDouble(input.nextLine());

        /*
         * Mengubah isi object menggunakan Setter.
         */

        mahasiswa.setNama(namaBaru);
        mahasiswa.setNilai(nilaiBaru);

        System.out.println("\nData berhasil diperbarui.");
    }

    /*
     * =====================================================
     * METHOD : hapusMahasiswa()
     * -----------------------------------------------------
     * Fungsi :
     * Menghapus data mahasiswa berdasarkan NIM.
     *
     * Langkah:
     * 1. Minta NIM
     * 2. Cari object
     * 3. Jika ditemukan -> hapus dari ArrayList
     * 4. Jika tidak -> tampilkan pesan
     * =====================================================
     */

    public void hapusMahasiswa() {

        System.out.println("\n===== HAPUS MAHASISWA =====");

        // Input NIM
        System.out.print("Masukkan NIM : ");
        String nim = input.nextLine();

        /*
         * Cari object terlebih dahulu.
         */
        Mahasiswa mahasiswa = cariMahasiswaByNim(nim);

        if (mahasiswa == null) {

            System.out.println("\nMahasiswa tidak ditemukan.");
            return;

        }

        /*
         * remove(Object)
         *
         * Menghapus object dari ArrayList.
         */
        daftarMahasiswa.remove(mahasiswa);

        System.out.println("\nData berhasil dihapus.");

    }

}