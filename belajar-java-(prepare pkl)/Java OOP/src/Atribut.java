class ManyAtribut {
    String fname = "Gerald";
    String lname = "Marentek";
    int age = 17;

}

public class Atribut {
    int x = 10; //x adalah atribut


    static void main(String[] args) {
        Atribut myObj = new Atribut(); //membuat objek bernama myObj
        Atribut myObj1 = new Atribut();
        myObj.x = 40; //ubah nilai atribut
        System.out.println(myObj.x); //output 40
        System.out.println(myObj1.x); //output 10

        ManyAtribut myObj2 = new ManyAtribut();
        System.out.println("Nama: " + myObj2.fname+ " "+myObj2.lname);
        System.out.println("Umur: " + myObj2.age);


    }
}






