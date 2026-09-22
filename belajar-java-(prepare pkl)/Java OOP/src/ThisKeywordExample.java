public class ThisKeywordExample {

    String name;
    int age;

    ThisKeywordExample(String name) {
        this (17, name);
    }

    ThisKeywordExample(int age, String name) {
        this.name = name;
        this.age = age;
    }

    public void printInfo () {
        System.out.println(name + " is " + age );
    }

    static void main(String[] args) {
        ThisKeywordExample introducion = new ThisKeywordExample("alfaridzhi");
        ThisKeywordExample introduction2 = new ThisKeywordExample(17, "Gerald");


        introducion.printInfo();
        introduction2.printInfo();
    }


}