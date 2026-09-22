import java.util.Scanner;

public class Scanner3 {
    static void main(String[] args) {
        Scanner myScanner = new Scanner (System.in);
        System.out.println("enter your name ");


        String name = myScanner.nextLine();
        System.out.println("Your name is : " + name
        );
    }
}
