package repository;

import entity.Profile;
import entity.ProfileFileData;
import entity.Sex;
import exception.ValidationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ProfileFileRepository {

    private static final Logger LOGGER = Logger.getLogger(ProfileFileRepository.class.getName());

    public ProfileFileData loadFile(String fileName) {

        Map<String, Profile> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int fileHash = Integer.parseInt(reader.readLine().trim());

            String line;
            while ((line = reader.readLine()) != null) {
                constructNewProfile(line, map);
            }
            return new ProfileFileData(map, fileHash);

        } catch (FileNotFoundException e) {
            throw new ValidationException("File not found: " + fileName);
        } catch (IOException e) {
            throw new ValidationException("IO error when reading file: " + fileName);
        }
    }

    public Profile addProfile(String fullName, int age, String phoneNumber, String sexStr, String address) {

        if (fullName == null || fullName.isBlank() || fullName.contains(";") ||
                age < 0 ||
                phoneNumber == null || phoneNumber.isBlank() || phoneNumber.contains(";") ||
                !isValidSex(sexStr) ||
                address == null || address.isBlank() || address.contains(";")) {
            throw new ValidationException("Incorrect profile data format");
        }

        Sex sex = Sex.valueOf(sexStr.toUpperCase());

        return new Profile(fullName, age, phoneNumber, sex, address);
    }


    private static void constructNewProfile(String line, Map<String, Profile> map) {
        String[] parts = line.split(";");

        if (parts.length != 5)
            throw new ValidationException("Invalid file format: " + line);

        String name = parts[0].trim();

        int age;
        try {
            age = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid age format: " + parts[1].trim());
        }
        if (age <= 0) {
            throw new ValidationException("Age must be positive: " + age);
        }

        String phone = parts[2].trim();

        Sex sex;
        try {
            sex = Sex.valueOf(parts[3].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid sex format: " + parts[3].trim());
        }

        String address = parts[4].trim();

        if (map.containsKey(name)) {
            LOGGER.warning("Duplicate entry for name: " + name);
        }
        map.put(name, new Profile(name, age, phone, sex, address));
    }

    private boolean isValidSex(String sexStr) {
        if (sexStr == null || sexStr.isBlank()) {
            return false;
        }
        try {
            Sex.valueOf(sexStr.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    public void saveFile(String fileName, Map<String, Profile> fioToProfile) {

    }

}
