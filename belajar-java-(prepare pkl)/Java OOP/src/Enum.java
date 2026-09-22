public class Enum {
    enum Level {
        LOW,
        MEDIUM,
        HIGH
    }

    static void main(String[] args) {
        Level myVar = Level.MEDIUM;

        switch(myVar) {
            case LOW :
                System.out.println("Low Level");
                break;

            case MEDIUM:
                System.out.println("Medium Level");
                break;

            case HIGH:
                System.out.println("High Level");
                break;

        }




    }
}
