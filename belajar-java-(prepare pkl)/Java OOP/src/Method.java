public class Method {

    public void fullThrottle() { //buat method fullthrothle
        System.out.println("The car is going as fast as it can!");
    }

    public void speed(int maxSpeed) {  //buat method bernama speed, dan add parameter
        System.out.println("Max speed is: " + maxSpeed);
    }


    //didalam main , panggil method di objek car
    static void main(String[] args) {
        Method myCar = new Method(); // buat objek bernama mycar
        myCar.fullThrottle(); // panggil method full throttle
        myCar.speed(200); // panggil method speed
        }


    }
