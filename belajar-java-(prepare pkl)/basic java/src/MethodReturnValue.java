public class MethodReturnValue {
    static void main(String[] args) {
        int sum = myMethod(10, 5);
        System.out.println(sum);

        for (int i = 1; i <= 5; i++ ) {
            System.out.println(doubleGame(i));
        }

        int  c = 100;
        int d = 200;
        int e = sum(c,d);
        System.out.println(e);

    }


    static int myMethod(int x, int y) {
        return x + y;
    }

    static int doubleGame(int x) {
        return x * 2;
    }

    static int sum(int a, int b) {
        int total = a + b;
        return total;
    }

}
