class OuterClass {
    int x = 10;

    class InnerClass {
        int y = 5;

    }
}


public class InerClass {
    static void main(String[] args) {
        OuterClass myOuter = new OuterClass();
        OuterClass.InnerClass myInner = myOuter.new InnerClass();
        System.out.println(myInner.y);
        System.out.println(myOuter.x);
    }
}


//static inner class, tidak perlu membuat objek outerclass untuk
