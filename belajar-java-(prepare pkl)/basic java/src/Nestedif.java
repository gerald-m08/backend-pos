public class Nestedif {
    static void main(String[] args) {

        int x = 15;
        int y = 25;

        if (x > 10) {
            System.out.println("x is greater than 10");

            // Nested if
            if (y > 20) {
                System.out.println("y is also greater than 20");
            }
        }

        int age = 20;
        boolean isCitizen = true;

        if (age >= 18) {
            System.out.println("Old enough to vote.");

            if (isCitizen) {
                System.out.println("And you are a citizen, so you can vote!");
            }
            else {
                System.out.println("But you must be a citizen to vote.");
            }
        }
        else {
            System.out.println("Not old enough to vote.");
        }


    }
}
