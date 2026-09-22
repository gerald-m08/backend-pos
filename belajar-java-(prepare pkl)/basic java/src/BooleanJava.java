public class BooleanJava {
    static void main(String[] args) {


        boolean isJavaFun = true;
        boolean isFishTasty = false;

        System.out.println(isJavaFun);    // Outputs true
        System.out.println(isFishTasty);  // Outputs false

        int x = 10;
        int y = 9;

        System.out.println(x > y); // Outputs true, because 10 is greater than 9

        int x1 = 10;

        System.out.println(x1 == 10); // Outputs true, because the value of x is equal to 10
        System.out.println(10 == 15); // Outputs false, because 10 is not equal to 15

        int x2 = 10;
        int y2 = 9;

        boolean isGreater = x > y;

        System.out.println(isGreater); // Outputs true

        int myAge = 25;
        int votingAge = 18;
        System.out.println(myAge >= votingAge); //true

        int myAge2 = 25;
        int votingAge2 = 18;

        if (myAge2 >= votingAge2) {
            System.out.println("Old enough to vote!");
        } else {
            System.out.println("Not old enough to vote.");
        }
    }
}
