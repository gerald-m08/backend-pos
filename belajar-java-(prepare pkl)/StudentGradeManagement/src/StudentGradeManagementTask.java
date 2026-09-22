import java.util.Scanner;

public class StudentGradeManagementTask {

    static Scanner input = new Scanner (System.in);


    static void main(String[] args) {


        String nama;
        String nim;
        double uas;
        double uts;
        double rataRata;
        char grade;
        String status;

         nama = inputNama();
         nim = inputNim();
         uas = inputUas();
         uts = inputUts();


         rataRata = hitungRatarata(uts, uas);
         grade = tentukanGrade(rataRata);
         status = tentukanStatus(rataRata);

         tampilkanOutput( nama, nim, uts, uas, rataRata, grade, status);




    }


    //method input


    public static String inputNama () {
        System.out.print("Masukkan nama : ");
        return input.nextLine();
    }

    public static String inputNim () {
        System.out.print("Masukkan nim : ");
        return input.next();
    }

    public static double inputUts () {
        System.out.print("Masukkan Nilai UTS : ");
        return input.nextDouble();
    }
    public static double inputUas (){
        System.out.print("Masukkan nilai uas :");
        return input.nextDouble();
    }


    //Method hitung rata2

    public static double hitungRatarata (double uts, double uas) {
        return (uts + uas ) / 2 ;
    }

    //Method grade

    public static char tentukanGrade (double rata) {
        if (rata >= 85)
            return 'A';
        else if (rata >= 75)
            return 'B';
        else if (rata >= 65)
            return 'C';
        else
            return 'E';
    }

    //Method status

    public static String tentukanStatus (double rata) {
        if (rata>= 75)
            return "Lulus";
        else
            return "Tidak lulus";
    }

    //Method output
    public static void tampilkanOutput (String nama, String nim, double uts, double uas, double rata, char grade, String status)
    {
        System.out.println("Student grade management system");
        System.out.println("Nama : " + nama);
        System.out.println("Nim : " + nim);
        System.out.println("Uts : " + uts );
        System.out.println("Uas : " + uas);
        System.out.println("Rata - rata : " + rata);
        System.out.println("Grade : " + grade);
        System.out.println("Status : " + status);
    }



}
