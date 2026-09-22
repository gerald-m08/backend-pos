public class WhileLoop {
    static void main(String[] args) {


        int i = 0;
        while (i < 5) {
            System.out.println(i);
            i++; // kode dalam loop akan berjalan berulang kali, selama sebuah variabel ( i) kurang dari 5:
        }

        int countdown = 3;

        while (countdown > 0) {
            System.out.println(countdown);
            countdown--;
        }

        System.out.println("Happy New Year!!");
        //menghitung dari 3 hingga 1, lalu mencetak "Selamat Tahun Baru!!" di akhir:



        int i1 = 10;

        while (i1 < 5) {
            System.out.println("This will never be printed");
            i++;
        }


        int a = 10;
        while (a < 20) {
            System.out.println("perulangan " + a);
            a++;
        }

    }
}
