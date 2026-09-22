public class MethodParameters {

    public static void main(String[] args) {
        myMethod("Liam", 5);
        myMethod("Jenny", 8);
        myMethod("Anja", 31);


        sayHello("Gerald", "Marentek");
        sayHello("Putri", "Balqis");

        checkAge(18);

    }

    static void myMethod(String fname, int age) {
        System.out.println(fname + " is " + age);
    }

    static void sayHello(String firstName, String lastName) {
        System.out.println("Hello " + firstName + " " + lastName);
    }

    static void checkAge(int age) {
        if (age >= 18) {
            System.out.println("Akses diberikan, umur kamu sudah mencukupi");
        }
        else {
            System.out.println("Akses ditolak, umur kamu belum cukup");
        }

    }


}
