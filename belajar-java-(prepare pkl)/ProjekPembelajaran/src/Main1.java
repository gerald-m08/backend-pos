import java.util.Scanner;

/*
 * =====================================================
 * CLASS : Main
 * -----------------------------------------------------
 * Class ini adalah titik awal program.
 *
 * Tugas Main:
 * - Menampilkan menu
 * - Membaca pilihan user
 * - Memanggil method pada MahasiswaService
 *
 * Main TIDAK menyimpan data mahasiswa.
 * Main hanya mengatur alur program.
 * =====================================================
 */

public class Main1 {

    public static void main(String[] args) {

        /*
         * Scanner untuk membaca input user.
         */
        Scanner input = new Scanner(System.in);

        /*
         * Membuat object MahasiswaService.
         *
         * Semua proses CRUD dilakukan oleh object ini.
         */
        MahasiswaService service = new MahasiswaService();

        int pilihan;

        do {

            System.out.println("\n=================================");
            System.out.println(" STUDENT MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Tambah Mahasiswa");
            System.out.println("2. Tampilkan Mahasiswa");
            System.out.println("3. Edit Mahasiswa");
            System.out.println("4. Hapus Mahasiswa");
            System.out.println("5. Keluar");
            System.out.println("=================================");
            System.out.print("Pilih menu : ");

            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {

                case 1:
                    service.tambahMahasiswa();
                    break;

                case 2:
                    service.tampilkanMahasiswa();
                    break;

                case 3:
                    service.editMahasiswa();
                    break;

                case 4:
                    service.hapusMahasiswa();
                    break;

                case 5:
                    System.out.println("\nTerima kasih.");
                    break;

                default:
                    System.out.println("\nMenu tidak tersedia.");

            }

        } while (pilihan != 5);

        input.close();

    }

}