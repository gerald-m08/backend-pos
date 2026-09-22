public class Person {
String name; //membuat field name,address,country
String address;
final String country = "Indonesia";

Person (String paramName, String address) { //membuat constructor dengan parameter
    name = paramName; // set data di daalam field mengambil parameter dari constructor
    this.address =  address;
}

Person (String paramName) {
    name = paramName;
}

Person () {

}

void sayHello (String nameParameter) {  //membuat method
    System.out.println("Hello " + nameParameter + ", My name is " + name);
}





}

