public class StringJava {
    static void main(String[] args) {
        String txt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        System.out.println("The length of the txt string is: " + txt.length());

        String text = "Hello World";
        System.out.println(text.toUpperCase());   // Outputs "HELLO WORLD"
        System.out.println(text.toLowerCase());   // Outputs "hello world"

        String info = "Please locate where 'locate' occurs!";
        System.out.println(info.indexOf("locate")); // Outputs 7

        String txt9 = "Hello";
        System.out.println(txt9.charAt(0));  // H
        System.out.println(txt9.charAt(4));  // o


        String txt1 = "Hello";
        String txt2 = "Hello";

        String txt3 = "Greetings";
        String txt4 = "Great things";

        System.out.println(txt1.equals(txt2));  // true
        System.out.println(txt3.equals(txt4));  // false

        String txt8 = "   Hello World   ";
        System.out.println("Before: [" + txt8 + "]");
        System.out.println("After:  [" + txt8.trim() + "]"); //menghapus spasi
    }
}
