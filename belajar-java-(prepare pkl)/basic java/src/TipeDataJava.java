public class TipeDataJava {
    static void main(String[] args) {
        int myNum = 5;               // Integer (whole number)
        float myFloatNum = 5.99f;    // Floating point number
        char myLetter = 'D';         // Character
        boolean myBool = true;       // Boolean
        String myText = "Hello";     // String



        // Tipe data number
        /*
       Tipe bilangan primitif dibagi menjadi dua kelompok:

       Tipe data integer menyimpan bilangan bulat, positif atau negatif (seperti 123 atau -456), tanpa desimal. Tipe data yang valid adalah byte, short, int dan long. Tipe data mana yang harus Anda gunakan bergantung pada nilai numeriknya.

       Tipe floating point merepresentasikan angka dengan bagian pecahan, yang mengandung satu atau lebih angka desimal. Ada dua tipe: floatdan double.
       */
        byte myNum1 = 100;
        System.out.println(myNum1);
        short myNum2 = 5000;
        System.out.println(myNum2);
        int myNum3 = 100000;
        System.out.println(myNum3);
        long myNum4 = 15000000000L;
        System.out.println(myNum4);
        float myNum5 = 5.75f;
        System.out.println(myNum5);
        double myNum6 = 19.99d;
        System.out.println(myNum6);

        //Tipe data boolean

        boolean isJavaFun = true;
        boolean isFishTasty = false;
        System.out.println(isJavaFun);     // Outputs true
        System.out.println(isFishTasty);   // Outputs false

        //Tipe data char
        /*
        Tipe data ini char digunakan untuk menyimpan satu karakter.
        Karakter tersebut harus diapit oleh tanda kutip tunggal, seperti 'A' atau 'c':
         */

        char myGrade = 'B';
        System.out.println(myGrade);

        //Tipe data String
        //Tipe data ini Stringdigunakan untuk menyimpan urutan karakter (teks).
        // Nilai string harus diapit oleh tanda kutip ganda:

        String greeting = "Hello World";
        System.out.println(greeting);



        //contoh nyata penerapan tipe data java

        // Create variables of different data types
        int items = 50;
        float costPerItem = 9.99f;
        float totalCost = items * costPerItem;
        char currency = '$';

// Print variables
        System.out.println("Number of items: " + items);
        System.out.println("Cost per item: " + costPerItem + currency);
        System.out.println("Total cost = " + totalCost + currency);


        // kata kunci var
        var myNum7 = 5;         // int
        var myDouble = 9.98;   // double
        var myChar = 'D';      // char
        var myBoolean = true;  // boolean
        var myString = "Hello"; // String







    }
}






/*
Tipe data dibagi menjadi dua kelompok:

Tipe data primitif - meliputi byte, short, int, long, float, double, booleandanchar
Tipe data non-primitif - seperti Stringarray dan kelas (Anda akan mempelajari lebih lanjut tentang ini di bab selanjutnya)
 */
