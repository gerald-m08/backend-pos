public class MethodExample {
    public void fullThrottle () {
        System.out.println("The car is going as fast as it can!");
    }

    public void Speed (int maxSpeed) {
        System.out.println("Maximum speed of car is: " + maxSpeed);
    }


    public void introduction (String nama, int umur) {
        System.out.println(nama + " is " + umur);
    }


    static void main(String[] args) {
        MethodExample myObject = new MethodExample();
        myObject.fullThrottle();
        myObject.Speed(70);



        MethodExample kenalan = new MethodExample();
        kenalan.introduction("Gerald", 17);
    }


}