class Person {
    public String name =  "Gerald";  // Public - accessible everywhere
    private int age = 17; // Private - only accessible inside this class
}

public class Modifier {

    static void main(String[] args) {

        Person p = new Person();
        System.out.println(p.name); //works fine


    }

}