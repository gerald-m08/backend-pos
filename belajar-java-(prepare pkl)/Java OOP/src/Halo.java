public class Halo {

    String fName;
    String lName;

    Halo (String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    static void main(String[] args) {
        Halo Introduction = new Halo ("Gerald", "Marentek");
        System.out.println("My name is " + Introduction.fName + " " + Introduction.lName);


    }
}
