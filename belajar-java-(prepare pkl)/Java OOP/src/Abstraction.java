abstract class animal1 {
    public abstract void animalSound (); //abstract method tidak ada isi
    public void sleep (){
        System.out.println("zzzzzz");
    }
}

class Doggie extends animal1 {
    public void animalSound () {
        System.out.println("Guk Guk"); //bisa diisi disini
    }

}

public class Abstraction {
    static void main(String[] args) {
        Doggie myDoggie = new Doggie();
        myDoggie.animalSound();
        myDoggie.sleep();
    }
}
