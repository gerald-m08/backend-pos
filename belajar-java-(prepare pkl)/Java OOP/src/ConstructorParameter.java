public class ConstructorParameter {
    String modelName;
    int modelYear;

    public ConstructorParameter (int year, String name) {
        modelYear = year;
        modelName = name;
    }

    static void main(String[] args) {
        var myCar = new ConstructorParameter(1999, "Mustang");
        System.out.println(myCar.modelName + " " + myCar.modelYear);
    }
}
