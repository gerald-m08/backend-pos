public class Avanza implements Car, IsMaintenance {
    public void drive() {
        System.out.println("Drive avanza");
    }

    public int getTier () {
        return 4;
    }

    public String getBrand() {
        return "toyota";
    }

    public boolean isMaintenance () {
        return false;
    }



    static void main(String[] args) {
        Car car = new Avanza ();
        System.out.println(car.getTier());
       car.drive();

    }

}
