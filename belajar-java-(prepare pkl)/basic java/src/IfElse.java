public class IfElse {
    static void main() {


        boolean isRaining = true;

        if (isRaining) {
            System.out.println("Bring an umbrella!");


            if (20 > 18) {
                System.out.println("20 is greater than 18");
            }

            //membandingkan variabel
            int x = 20;
            int y = 18;
            if (x > y) {
                System.out.println("x is greater than y");
            }

            boolean isLightOn = true;

            if (isLightOn) {
                System.out.println("The light is on.");
            }

            boolean isLightOn1 = false;

            if (isLightOn1) {
                System.out.println("The light is on.");  // This will not be printed
            }

            System.out.println("This line runs no matter what, because it is outside the if statement.");

            if (20 > 18)
                System.out.println("20 is greater than 18");

            int x1 = 20;
            int y1 = 18;

            if (x1 > y1) {
                System.out.println("x is greater than y");
                System.out.println("Both lines are part of the if");
            }

// Some code outside if
            System.out.println("I am outside if, not part of if!");

            int time = 18;
            if (time>10) {
                System.out.println("berhasil");
            }






        }
    }

}
