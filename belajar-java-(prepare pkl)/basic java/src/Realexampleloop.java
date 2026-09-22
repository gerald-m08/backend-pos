public class Realexampleloop {
    static void main(String[] args) {

//menghitung sampai 100 dengan kelipatan sepuluh:
        for (int i = 0; i <= 100; i += 10) {
            System.out.println(i);
        }


        //mencetak nilai genap antara 0 dan 10:
        for (int i = 0; i <= 10; i = i + 2) {
            System.out.println(i);
        }

        //mencetak tabel perkalian untuk angka tertentu:
        int number = 2;

// Print the multiplication table for the number 2
        for (int i = 1; i <= 10; i++) {
            System.out.println(number + " x " + i + " = " + (number * i));
        }

//mencetak nomor kursi dalam satu baris teater:
        for (int seat = 1; seat <= 5; seat++) {
            System.out.println("Seat number: " + seat);
        }

        //perulangan untuk menghitung faktorial dari suatu bilangan:

        int n = 5;
        int fact = 1;

        for (int i = 1; i <= n; i++) {
            fact *= i;
        }

        System.out.println("Factorial of " + n + " is " + fact);
// Output: Factorial of 5 is 120






    }
}
