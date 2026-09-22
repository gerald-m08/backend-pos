 class Manager {
    String name;

    Manager (String name) {
        this.name = name;
    }


    void sayHello (String name) {

        System.out.println("hi " + name + " my name is " + this.name);
    }
}

class vicePresident extends Manager {
    vicePresident (String nama) {
        super(nama);
    }

}


 void main(String[] args) {

    var manager = new Manager ("Gerald");
    manager.name = "Gerald";
     System.out.println(manager.name);
    manager.sayHello("Alfa");


    var vp = new vicePresident("ALfa");
    vp.name = "Aldy";
     System.out.println(vp.name);
    vp.sayHello("Raja");


 }

