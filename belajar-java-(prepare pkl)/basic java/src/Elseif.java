public class Elseif {
    static void main(String[] args) {


        int weather = 2; // 1 = raining, 2 = sunny, 3 = cloudy

        if (weather == 1) {
            System.out.println("Bring an umbrella.");
        } else if (weather == 2) {
            System.out.println("Wear sunglasses.");
        } else {
            System.out.println("Just go outside normally.");
        }
// Outputs "Wear sunglasses."


        int time = 16;

        if (time < 12) {
            System.out.println("Good morning.");
        } else if (time < 18) {
            System.out.println("Good day.");
        } else {
            System.out.println("Good evening.");
        }
// Outputs "Good day."





    }
}
