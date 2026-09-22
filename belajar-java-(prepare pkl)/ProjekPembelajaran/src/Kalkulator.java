import java.util.Scanner;

public class Kalkulator {

    static Scanner input = new Scanner(System.in);

    // Method Penjumlahan
    public static double tambah(double a, double b) {
        return a + b;
    }

    // Method Pengurangan
    public static double kurang(double a, double b) {
        return a - b;
    }

    // Method Perkalian
    public static double kali(double a, double b) {
        return a * b;
    }

    // Method Pembagian
    public static double bagi(double a, double b) {
        return a / b;
    }

    // Method untuk menampilkan menu
    public static void tampilkanMenu() {
        System.out.println("==============================");
        System.out.println("      KALKULATOR JAVA");
        System.out.println("==============================");
        System.out.println("1. Penjumlahan");
        System.out.println("2. Pengurangan");
        System.out.println("3. Perkalian");
        System.out.println("4. Pembagian");
        System.out.println("5. Keluar");
        System.out.println("==============================");
        System.out.print("Pilih menu : ");
    }

    public static void main(String[] args) {

        int pilihan;

        do {

            tampilkanMenu();
            pilihan = input.nextInt();

            if (pilihan == 5) {
                System.out.println("\nTerima kasih telah menggunakan kalkulator.");
                break;
            }

            if (pilihan < 1 || pilihan > 5) {
                System.out.println("\nMenu tidak tersedia!\n");
                continue;
            }

            System.out.print("\nMasukkan angka pertama : ");
            double angka1 = input.nextDouble();

            System.out.print("Masukkan angka kedua : ");
            double angka2 = input.nextDouble();

            double hasil;

            switch (pilihan) {

                case 1:
                    hasil = tambah(angka1, angka2);
                    System.out.println("\nHasil Penjumlahan = " + hasil);
                    break;

                case 2:
                    hasil = kurang(angka1, angka2);
                    System.out.println("\nHasil Pengurangan = " + hasil);
                    break;

                case 3:
                    hasil = kali(angka1, angka2);
                    System.out.println("\nHasil Perkalian = " + hasil);
                    break;

                case 4:

                    if (angka2 == 0) {
                        System.out.println("\nError! Tidak bisa membagi dengan nol.");
                    } else {
                        hasil = bagi(angka1, angka2);
                        System.out.println("\nHasil Pembagian = " + hasil);
                    }

                    break;
            }

            System.out.println("\nTekan Enter untuk melanjutkan...");
            input.nextLine(); // membersihkan buffer
            input.nextLine(); // menunggu Enter

        } while (true);

        input.close();
    }
}