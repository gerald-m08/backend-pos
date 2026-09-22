public class JavaVariable {
    static void main(String[] args) {

        String name = "John";
        System.out.println(name);


        int myNum = 15;
        System.out.println(myNum);

        int myNum1;
        myNum1 = 20;
        System.out.println(myNum1);


        int myNum2 = 15;
        myNum2 = 20;  // myNum is now 20
        System.out.println(myNum2);

           // will generate an error: cannot assign a value to a final variable

        int myNum5 = 5;
        float myFloatNum = 5.99f;
        char myLetter = 'D';
        boolean myBool = true;
        String myText = "Hello";


        //cetak variabel
        String firstName = "John ";
        String lastName = "Doe";
        String fullName = firstName + lastName;
        System.out.println(fullName);

        String name1 = "John";
        System.out.println("Hello " + name1);

        int x = 5;
        int y = 6;
        System.out.println(x + y); // Print the value of x + y


        int a = 5;
        int b = 6;




        System.out.println("The sum is " + a + b);   // Prints: The sum is 56
        System.out.println("The sum is " + (a + b)); // Prints: The sum is 11



        //mendeklarasikan beberapa variabel

        int x1 = 5, y1 = 6, z1 = 50; //banyak variabel, namun satu tipe data
        System.out.println(x1 + y1 + z1); // 61

        int x2, y2, z2; // banyak variabel dengan nilai yang sama
        x2 = y2 = z2 = 50;
        System.out.println(x + y + z2); // 150




        // Student data
        String studentName = "John Doe";
        int studentID = 15;
        int studentAge = 23;
        float studentFee = 75.25f;
        char studentGrade = 'B';

// Print variables
        System.out.println("Student name: " + studentName);
        System.out.println("Student id: " + studentID);
        System.out.println("Student age: " + studentAge);
        System.out.println("Student fee: " + studentFee);
        System.out.println("Student grade: " + studentGrade);


        // Create integer variables
        int length = 4;
        int width = 6;
        int area;

// Calculate the area of a rectangle
        area = length * width;

// Print variables
        System.out.println("Length is: " + length);
        System.out.println("Width is: " + width);
        System.out.println("Area of the rectangle is: " + area);

    }
}






/*
Variabel Java
Variabel adalah wadah untuk menyimpan nilai data.

Di Java, terdapat berbagai jenis variabel, misalnya:

String- Menyimpan teks, seperti "Halo". Nilai string dikelilingi oleh tanda kutip ganda.
int- menyimpan bilangan bulat (bilangan utuh), tanpa desimal, seperti 123 atau -123
float- menyimpan angka floating point, dengan desimal, seperti 19,99 atau -19,99
char- Menyimpan karakter tunggal, seperti 'a' atau 'B'. Nilai karakter dikelilingi oleh tanda kutip tunggal.
boolean- menyimpan nilai dengan dua keadaan: benar atau salah
Mendeklarasikan (Membuat) Variabel
Untuk membuat variabel di Java, Anda perlu:

Pilih tipe (seperti intatau String)
Beri nama pada variabel tersebut (seperti x, age, atau name).
Secara opsional, tetapkan nilainya menggunakan=

 */

/*
Aturan umum untuk penamaan variabel adalah:

Nama dapat berisi huruf, angka, garis bawah, dan tanda dolar.
Nama harus diawali dengan sebuah huruf.
Nama harus diawali dengan huruf kecil, dan tidak boleh mengandung spasi.
Nama juga bisa diawali dengan $ dan _
Nama bersifat peka huruf besar-kecil ("myVar" dan "myvar" adalah variabel yang berbeda)
Kata-kata yang dicadangkan (seperti kata kunci Java, misalnya intatau boolean) tidak dapat digunakan sebagai nama.
 */