public interface ArrayJava {
    static void main(String[] args) {


        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        cars[0] = "Opel";
        System.out.println(cars[0]);
// Now outputs Opel instead of Volvo
        System.out.println(cars.length);
// Outputs 4

        int[] myNum = {10, 20, 30, 40};

        //kata kunci new
        String[] cars1 = new String[4]; // size is 4

        cars1[0] = "Volvo";
        cars1[1] = "BMW";
        cars1[2] = "Ford";
        cars1[3] = "Mazda";

        System.out.println(cars1[0]); // Outputs Volvo



    }
}
