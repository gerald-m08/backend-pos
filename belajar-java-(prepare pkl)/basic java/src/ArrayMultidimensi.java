public class ArrayMultidimensi {
    static void main(String[] args) {

//Pernyataan ini mengakses elemen pada baris kedua (indeks 1) dan kolom ketiga (indeks 2) dari array myNumbers :
        int[][] myNumbers = { {1, 4, 2}, {3, 6, 8} };
        System.out.println(myNumbers[1][2]); // Outputs 8

        int[][] myNumbers1 = { {1, 4, 2}, {3, 6, 8} };
        myNumbers1[1][2] = 9;
        System.out.println(myNumbers1[1][2]); // Outputs 9 instead of 8

        int[][] myNumbers2 = { {1, 4, 2}, {3, 6, 8, 5, 2} };

        System.out.println("Rows: " + myNumbers2.length);             // 2
        System.out.println("Cols in row 0: " + myNumbers2[0].length); // 3
        System.out.println("Cols in row 1: " + myNumbers2[1].length); // 5



        int[][] myNumbers3 = { {1, 4, 2}, {3, 6, 8, 5, 2} };

        for (int row = 0; row < myNumbers3.length; row++) {
            for (int col = 0; col < myNumbers3[row].length; col++) {
                System.out.println("myNumbers[" + row + "][" + col + "] = " + myNumbers3[row][col]);

            }
        }

        //for each
        int[][] myNumbers4 = { {1, 4, 2}, {3, 6, 8, 5, 2} };

        for (int[] row1 : myNumbers4) {
            for (int num : row1) {
                System.out.println(num);
            }
        }

        String [][] name = { {"Gerald", "Piqisdut"}, {"Marentek", "Bobby"} };
        for (String[] nama1 : name) {
            for (String nama2 : nama1) {
                System.out.println(nama2);
            }
        }






    }
}
