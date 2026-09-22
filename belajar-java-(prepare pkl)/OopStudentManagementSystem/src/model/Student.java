package model;

public class Student {

    private int id;
    private String name;
    private int age;
    private double utsScore;
    private double uasScore;

    public Student(int id, String name, int age, double utsScore, double uasScore) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.utsScore = utsScore;
        this.uasScore = uasScore;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getUtsScore() {
        return utsScore;
    }

    public double getUasScore() {
        return uasScore;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUtsScore(double utsScore) {
        this.utsScore = utsScore;
    }

    public void setUasScore(double uasScore) {
        this.uasScore = uasScore;
    }


    public double getAverage () {
        return (uasScore + utsScore) / 2 ;
    }


    public Grade getGrade () {
        double average = getAverage();


        if (average >= 85) {
            return Grade.A;
        }
        else if (average >= 70) {
            return Grade.B;
        }
        else if (average >= 60) {
            return Grade.C;
        }
        else if (average >= 50) {
            return Grade.D;
        }
        else {
            return Grade.E;
        }
    }



    @Override
    public String toString() {
        return "ID : " + id +
                "\nNama : " + name +
                "\nUmur : " + age +
                "\nNilai UTS : " + utsScore +
                "\nNilai UAS : " + uasScore +
                "\nAverage : " +getAverage() +
                "\nGrade : " + getGrade() +
                "\n-------------------------";

    }
}