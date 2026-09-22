import exception.InvalidInputException;
import exception.StudentNotFoundException;
import service.StudentService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentManagementApplication {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        StudentService service = new StudentService();

        int pilihan = -1;

        do {

            System.out.println("\n===== STUDENT MANAGEMENT =====");
            System.out.println("1. Tambah Student");
            System.out.println("2. Tampilkan Semua Student");
            System.out.println("3. Cari Student");
            System.out.println("4. Update Student");
            System.out.println("5. Hapus Student");
            System.out.println("0. Keluar");
            System.out.print("Pilih Menu : ");

            try {

                pilihan = input.nextInt();
                input.nextLine();

                switch (pilihan) {

                    case 1:

                        System.out.print("Nama : ");
                        String name = input.nextLine();

                        System.out.print("Umur : ");
                        int age = input.nextInt();

                        System.out.print("Nilai UTS : ");
                        double uts = input.nextDouble();

                        System.out.print("Nilai UAS : ");
                        double uas = input.nextDouble();
                        input.nextLine();

                        service.addStudent(name, age, uts, uas);

                        break;

                    case 2:

                        service.showAllStudents();

                        break;

                    case 3:

                        System.out.print("Masukkan ID Student : ");
                        int searchId = input.nextInt();
                        input.nextLine();

                        System.out.println(service.findStudentById(searchId));

                        break;

                    case 4:

                        System.out.print("Masukkan ID Student : ");
                        int updateId = input.nextInt();
                        input.nextLine();

                        System.out.print("Nama Baru : ");
                        String updateName = input.nextLine();

                        System.out.print("Umur Baru : ");
                        int updateAge = input.nextInt();

                        System.out.print("Nilai UTS Baru : ");
                        double updateUts = input.nextDouble();

                        System.out.print("Nilai UAS Baru : ");
                        double updateUas = input.nextDouble();
                        input.nextLine();

                        service.updateStudent(
                                updateId,
                                updateName,
                                updateAge,
                                updateUts,
                                updateUas
                        );

                        break;

                    case 5:

                        System.out.print("Masukkan ID Student : ");
                        int deleteId = input.nextInt();
                        input.nextLine();

                        service.deleteStudent(deleteId);

                        break;

                    case 0:

                        System.out.println("Program selesai.");

                        break;

                    default:

                        System.out.println("Menu tidak tersedia.");

                }

            } catch (InputMismatchException e) {

                System.out.println("Input harus berupa angka.");
                input.nextLine();

            } catch (InvalidInputException | StudentNotFoundException e) {

                System.out.println(e.getMessage());

            }

        } while (pilihan != 0);

        input.close();

    }

}