//intercface

interface animal2 {
    public void animalSound2();
    public void sleep2();
}

class Cat implements animal2 {
    public void animalSound2() {
        System.out.println("Meow meow");
    }

    public void sleep2() {
        System.out.println("Zzzz");
    }

    class Interface {
        static void main(String[] args) {
            Cat myCat = new Cat();
            myCat.animalSound2();
            myCat.sleep2();
        }
    }


}