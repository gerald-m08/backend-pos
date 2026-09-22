public class KonversiTipeDataJava {
    static void main(String[] args) {


        //widening castling
        /*
        widening castling dilakukan secara otomatis saat memasukkan tipe ukuran lebih kecil ke dalam tipe ukuran lebih besar.
       Ini berhasil karena tidak ada risiko kehilangan informasi. Misalnya, sebuah int nilai dapat dengan aman masuk ke dalam sebuah double:
         */
        int myInt = 9;
        double myDouble = myInt; // Automatic casting: int to double

        System.out.println(myInt);    // Outputs 9
        System.out.println(myDouble); // Outputs 9.0

        int c = 350;
        float d = c;
        System.out.println(d);


        //Narrowing casting
        /*
        Narrowing casting harus dilakukan secara manual dengan menempatkan tipe data dalam tanda kurung ()di depan nilai.
        Hal ini diperlukan karena penyempitan dapat mengakibatkan kehilangan data (misalnya, menghilangkan angka desimal
        saat mengkonversi a double menjadi an int):
         */

        double myDouble1 = 9.78d;
        int myInt1 = (int) myDouble1; // Manual casting: double to int

        System.out.println(myDouble1); // Outputs 9.78
        System.out.println(myInt1);    // Outputs 9

        double a = 500.78d;
        int b = (int) a;
        System.out.println(b);//500


        //contoh kehidupan nyata

        // Set the maximum possible score in the game to 500
        int maxScore = 500;

// The actual score of the user
        int userScore = 423;

/* Calculate the percentage of the user's score in relation to the maximum available score.
Convert userScore to double to make sure that the division is accurate */
        double percentage = (double) userScore / maxScore * 100.0d;

        System.out.println("User's percentage is " + percentage);







    }
}


/*
Konversi Tipe Java
Konversi tipe data berarti mengubah satu tipe data menjadi tipe data lain. Misalnya, mengubah sebuah tipe data intmenjadi sebuah tipe data lain double.

Di Java, ada dua jenis casting utama:

Pelebaran Huruf (otomatis) - mengubah ukuran huruf yang lebih kecil menjadi ukuran huruf yang lebih besar
byte-> short-> char-> int- long> - float> ->double

Pengecoran Mempersempit (manual) - mengubah ukuran huruf yang lebih besar menjadi ukuran huruf yang lebih kecil
double-> float-> long-> int- char> - short> ->byte
 */