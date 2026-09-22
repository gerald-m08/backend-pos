package util;

import exception.InvalidInputException;

public class InputValidator {

    public static void validateName(String name)
            throws InvalidInputException {

        if (name == null || name.trim().isBlank()) {
            throw new InvalidInputException("Nama tidak boleh kosong.");
        }

    }

    public static void validateAge(int age)
            throws InvalidInputException {

        if (age < 0) {
            throw new InvalidInputException("Umur tidak boleh negatif.");
        }

    }

    public static void validateScore(double score)
            throws InvalidInputException {

        if (score < 0 || score > 100) {
            throw new InvalidInputException("Nilai harus 0 - 100.");
        }

    }

}

