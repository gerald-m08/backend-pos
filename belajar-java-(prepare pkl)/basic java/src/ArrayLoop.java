public class ArrayLoop {
    static void main(String[] args) {
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};

        for (int i = 0; i < cars.length; i++) {
            System.out.println(cars[i]);
        }

        int[] numbers = {10, 20, 30, 40};

        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }


        int[] numberss = {1, 5, 10, 25};
        int sum = 0;

// Loop through the array and add each element to sum
        for (int i = 0; i < numberss.length; i++) {
            sum += numberss[i];
        }

        System.out.println("The sum is: " + sum);





        String[] seats = {"Jenny", "Liam", "Angie", "Bo"};

        for (int i = 0; i < seats.length; i++) {
            System.out.println("Seat number " + i + " is taken by " + seats[i]);
        }






        String[] cars1 = {"Volvo", "BMW", "Ford", "Mazda"};

        for (String car : cars1) {
            System.out.println(car);
        }



    }
}
