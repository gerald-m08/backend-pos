public class MethodRecursion {
    static void main(String[] args) {

        // menjumlahkan semua angka dari 1 hingga 10.
        int result = sum(10);
        System.out.println(result);

        //menjumlahkan semua angka dari 5 hingga 10 (5+6+7+8+9+10):
        int hasil = sum(5, 10);
        System.out.println(hasil);

        //menggunakan rekursi untuk membuat fungsi hitung mundur:
        countdown(5);

        //menggunakan metode rekursif untuk menghitung faktorial dari 5:
        System.out.println("Factorial of 5 is " + factorial(5));


    }


    // menjumlahkan semua angka dari 1 hingga 10.
    public static int sum(int k) {
        if (k > 0) {
            return k + sum(k - 1);
        } else {
            return 0;
        }
    }


    //menjumlahkan semua angka dari 5 hingga 10 (5+6+7+8+9+10):
    public static int sum(int start, int end) {
        if (end > start) {
            return end + sum(start, end - 1);
        } else {
            return end;
        }
    }

    //menggunakan rekursi untuk membuat fungsi hitung mundur:
    static void countdown(int n) {
        if (n > 0) {
            System.out.print(n + " ");
            countdown(n - 1);
        }
    }

    //menggunakan metode rekursif untuk menghitung faktorial dari 5:
    static int factorial(int n) {
        if (n > 1) {
            return n * factorial(n - 1);
        } else {
            return 1;
        }
    }




}
