public class ThisKeyword {
    int modelYear;
    String modelName;

    ThisKeyword(String modelName) {
        this (2020, modelName);
    }

    ThisKeyword (int modelYear, String modelName) {
        this.modelYear = modelYear;
        this.modelName = modelName;
    }

    public void printInfo () {
        System.out.println(modelName + " " + modelYear);
    }

    static void main(String[] args) {
        ThisKeyword car1 = new ThisKeyword("Toyota");
        ThisKeyword car2 = new ThisKeyword (2015, "Avanza");


        car1.printInfo();
        car2.printInfo();
    }


}