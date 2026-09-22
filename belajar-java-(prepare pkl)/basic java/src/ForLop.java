public class ForLop {
    static void main(String[] args) {


        for (int i = 0; i < 5; i++) {
            System.out.println(i); //mencetak angka 0 hingga 4:
        }

        for (int i = 0; i <= 10; i = i + 2) {
            System.out.println(i); //mencetak angka genap dari 0-10
        }

        //menjumlahkan angka
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            sum = sum + i;
        }
        System.out.println("Sum is " + sum);

        //hitung mundur
        for (int i = 5; i > 0; i--) {
            System.out.println(i);
        }


    }
}
