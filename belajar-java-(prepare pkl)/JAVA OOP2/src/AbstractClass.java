 abstract class AbstractClass {

    int age;
    String name;


    public void myMethod () {
        System.out.println("My name is " + name);
        System.out.println("Age : " + age);
    }
}

class tes extends AbstractClass {

}

 void main(String[] args) {
     var myObj = new tes ();
     myObj.age = 18;
     myObj.name = "ALFA";
     myObj.myMethod();
 }