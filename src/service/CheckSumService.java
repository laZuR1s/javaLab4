package service;

import entity.ProfileFileData;
import exception.ValidationException;

import java.util.Map;

public class CheckSumService {

private static final Integer HASH_LIMIT=10000000;

    public void validateFileCheckSum(ProfileFileData profileFileData) {
        if (profileFileData.fileHash() != calculateChecksum(profileFileData)) {
            throw new ValidationException("File Hash Mismatch. Actual hash: " + calculateChecksum(profileFileData));
        }
    }

    private int calculateChecksum(ProfileFileData profileFileData) {


        // Сортировка по ФИО для стабильного порядка
        return profileFileData.fioToProfile().entrySet().stream()
                .sorted(Map.Entry.comparingByKey(String::compareToIgnoreCase))
                .map(entry -> entry.getKey() + ";" + entry.getValue()) // "ФИО;Возраст;Телефон;Пол;Адрес"
                .mapToInt(CheckSumService::stringHash) // считаем суммарный хэш каждой строки
                .sum(); // объединяем в один int
    }


    private static int stringHash(String s) {
        int hash = 0;
        for (char c : s.toCharArray()) {
            hash = (hash * 31 + c)%HASH_LIMIT;
        }
        return hash;
    }
}
