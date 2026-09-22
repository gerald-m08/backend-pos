package service;

import exception.InvalidInputException;
import exception.StudentNotFoundException;
import model.Student;
import util.InputValidator;

import java.util.ArrayList;

public class StudentService {

    private ArrayList<Student> students = new ArrayList<>();

    private int nextId = 1;

    public void addStudent(String name, int age, double utsScore, double uasScore)
            throws InvalidInputException {

        InputValidator.validateName(name);
        InputValidator.validateAge(age);
        InputValidator.validateScore(utsScore);
        InputValidator.validateScore(uasScore);

        Student student = new Student(
                nextId,
                name,
                age,
                utsScore,
                uasScore
        );

        students.add(student);

        nextId++;

        System.out.println("Student berhasil ditambahkan.");
    }

    public void showAllStudents() {

        if (students.isEmpty()) {
            System.out.println("Belum ada data student.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }

    }

    public Student findStudentById(int id)
            throws StudentNotFoundException {

        for (Student student : students) {

            if (student.getId() == id) {
                return student;
            }

        }

        throw new StudentNotFoundException("Student tidak ditemukan.");
    }

    public void updateStudent(int id, String name, int age, double utsScore, double uasScore)
            throws StudentNotFoundException,
            InvalidInputException {

        Student student = findStudentById(id);

        InputValidator.validateName(name);
        InputValidator.validateAge(age);
        InputValidator.validateScore(utsScore);
        InputValidator.validateScore(uasScore);

        student.setName(name);
        student.setAge(age);
        student.setUtsScore(utsScore);
        student.setUasScore(uasScore);

        System.out.println("Student berhasil diupdate.");
    }

    public void deleteStudent(int id)
            throws StudentNotFoundException {

        Student student = findStudentById(id);

        students.remove(student);

        System.out.println("Student berhasil dihapus.");
    }

}