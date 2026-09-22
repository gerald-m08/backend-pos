public class ConcatenationString {
    static void main(String[] args) {
        String name = "John";
        int age = 25;
        System.out.println("My name is " + name + " and I am " + age + " years old."); //My name is John and I am 25 years old.


        String firstName = "John ";
        String lastName = "Doe";
        System.out.println(firstName.concat(lastName));

        String a = "Java ";
        String b = "is ";
        String c = "fun!";
        String result = a.concat(b).concat(c);
        System.out.println(result);
        String result2 = a + b + c;
        System.out.println(result2);


        String x = "10";
        String y = "20";
        String z = x + y;  // z will be 1020 (a String)
        System.out.println(z);

        String d = "10";
        int e = 20;
        String f = d + e;  // f will be 1020 (a String)
        System.out.println(f);

        String nama = "gerald";
        int age1 = 17;
        System.out.println("nama: " + nama);
        System.out.println("umur: " + age1);

        String tes = "20";
        int tes1 = 10;
        String tes2 = tes + tes1;
        System.out.println(tes2); //2010
        System.out.println(tes + tes1); //2010





    }
}
