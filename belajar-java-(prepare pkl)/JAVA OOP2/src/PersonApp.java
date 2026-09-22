public class PersonApp {
    static void main(String[] args) {

        var person1 = new Person ("Gerald", "Depok"); //membuat objek dan mengisi parameter
        System.out.println(person1.name + " From " + person1.address); //memanggil
        person1.sayHello ("Balqis"); //memanggil method

       var person2 = new Person ("Alfa", "Depok");
        System.out.println(person2.name + " From " + person2.address);
        person2.sayHello("Aldy");

        var person3 = new Person (); //membuat objek menggunakan konstruktor kosong, jadi data harus di set secara manual
        person3.name = "dewa";
        person3.address = "Cilodong";
        System.out.println(person3.name + " From " + person3.address);
        person3.sayHello("Fadhi");



        }


    }
