package util;

import exception.InvalidInputException;

public class Validator {


    public static void validateName (String name) {
        if (name == null || name.trim().isBlank()) {
            throw new InvalidInputException("nama tidak bolehh kosong");
        }

    }

    public static void validateAge (int age) {
        if (age <= 0) {
            throw new InvalidInputException("umur tidak boleh kosong");
        }
    }


    public static void validateScore (double quantity)  {
        if (quantity < 0 || quantity > 100) {
            throw new InvalidInputException("Nilai harus 0 - 100");
        }
    }


}
