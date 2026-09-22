public class ArrayLifeExample {
    static void main(String[] args) {


        //menghitung rata-rata dari berbagai usia:

        // An array storing different ages
        int ages[] = {20, 22, 18, 35, 48, 26, 87, 70};

        float avg, sum = 0;

// Get the length of the array
        int length = ages.length;

// Loop through the elements of the array
        for (int age : ages) {
            sum += age;
        }

// Calculate the average by dividing the sum by the length
        avg = sum / length;

// Print the average
        System.out.println("The average age is: " + avg);



        //menemukan usia terendah di antara berbagai rentang usia:
        // An array storing different ages
        int ages1[] = {20, 22, 18, 35, 48, 26, 87, 70};

// Get the length of the array
        int[] length1 = ages;

// Create a 'lowest age' variable and assign the first array element of ages to it
        int lowestAge = ages[0];

// Loop through the elements of the ages array to find the lowest age
        for (int age : ages) {
            // Check if the current age is smaller than the current 'lowest age'
            if (lowestAge > age) {
                // If the smaller age is found, update 'lowest age' with that element
                lowestAge = age;
            }
        }

// Output the value of the lowest age
        System.out.println("The lowest age in the array is: " + lowestAge);


        //daftar angka di mana Anda ingin melewati nilai negatif, tetapi berhenti sepenuhnya jika menemukan angka nol:

        int[] numbers = {3, -1, 7, 0, 9};

        for (int n : numbers){
            if (n < 0) {
                continue;
            }
            if (n == 0) {
                break;
            }
            System.out.println(n);
        }


        //melacak nilai tertinggi dan terendah dalam sebuah array
        int[] numbers2 = {45, 12, 98, 33, 27};

        int max = numbers2[0];
        int min = numbers2[0];

        for (int n : numbers2) {
            if (n > max) {
                max = n;
            }
            if (n < min) {
                min = n;
            }
        }
        System.out.println("Max: " + max);
        System.out.println("Min: " + min);


        //mencetak nomor kursi dalam satu baris teater, menampilkan baik nomor kursi (indeks) maupun siapa yang duduk di sana (nilai):
        String[] seats = {"Jenny", "Liam", "Angie", "Bo"};
        for (int i = 0; i < seats.length; i++) {
            System.out.println("Seat number " + i + " is taken by " + seats[i]);
        }










    }
}
