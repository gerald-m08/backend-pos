public class NestedLoop {
    static void main(String[] args) {
        // Outer loop
        for (int i = 1; i <= 2; i++) {
            System.out.println("Outer: " + i); // Executes 2 times

            // Inner loop
            for (int j = 1; j <= 3; j++) {
                System.out.println(" Inner: " + j); // Executes 6 times (2 * 3)
            }
        }
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print(i * j + " ");
            }
            System.out.println();
        }

        for (int x = 1; x <= 3; x++) {
            System.out.println("Data " + x);
            for (int z = 1; z <= 5; z++) {
                System.out.println("Peserta: " + z);
            }
        }

        for (int a = 1; a <= 3; a++) {
            for (int b = 1; b <= 3; b++) {
                System.out.print(a * b + " ");
            }
            System.out.println();
        }



    }
}
