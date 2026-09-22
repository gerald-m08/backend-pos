package service;

import exception.InvalidInputException;
import exception.StudentNotFoundException;
import model.Student;
import util.Validator;

import java.util.ArrayList;

public class StudentService {

    private ArrayList<Student> students = new ArrayList<>();

    private int nextId = 1;

    public void createStudent (
            String name,
            int age,
            double utsScore,
            double uasScore) {

        Student student = new Student(nextId, name, age, utsScore, uasScore);

        for (Student students : students) {
            if (students.getName().equals(name)) {
                throw new InvalidInputException("Student dengan nama tersebut sudah ada");
            }
        }

        Validator.validateName(name);
        Validator.validateAge(age);
        Validator.validateScore(utsScore);
        Validator.validateScore(uasScore);

        student.setName(name);
        student.setAge(age);
        student.setUtsScore(utsScore);
        student.setUasScore(uasScore);

        students.add(student);

        nextId++;
}

public void getAllStudents () {
        for (Student student : students) {
            System.out.println(student);
        }
}

public Student getStudentById (int id)
        throws StudentNotFoundException {

       for (Student student : students) {

           if (student.getId() == id) {
               return student;
           }

       }

       throw new StudentNotFoundException("Student not found");
}

public void updateStudent (int id, String name, int age, double utsScore, double uasScore)
        throws StudentNotFoundException {

        Student student = getStudentById(id);

        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setUtsScore(utsScore);
        student.setUasScore(uasScore);

    System.out.println("data student berhasil di update");
}


public void deleteStudent (int id)
throws StudentNotFoundException {

        Student student = getStudentById(id);

        students.remove(student);

    System.out.println("Student berhasil dihapus");



}


}
