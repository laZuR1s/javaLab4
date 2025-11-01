package service;

import entity.Profile;
import entity.ProfileFileData;
import exception.ValidationException;
import repository.ProfileFileRepository;

import java.util.Map;
import java.util.logging.Logger;

public class ProfileService {
    private final CheckSumService checkSumService;
    private final ProfileFileRepository profileFileRepository;
    private Map<String, Profile> fioToProfile;
    private String currentFileName;

    private static final Logger LOGGER = Logger.getLogger(ProfileService.class.getName());

    public ProfileService(CheckSumService checkSumService, ProfileFileRepository profileFileRepository) {
        this.checkSumService = checkSumService;
        this.profileFileRepository = profileFileRepository;
    }

    public void loadFile(String fileName) {

        ProfileFileData profileFileData = profileFileRepository.loadFile(fileName);
        checkSumService.validateFileCheckSum(profileFileData);
        fioToProfile = profileFileData.fioToProfile();
        currentFileName = fileName;
    }

    public void searchProfileByFio(String fio) {
        Profile profile = fioToProfile.entrySet().
                stream().
                filter(entry -> entry.getKey().equalsIgnoreCase(fio))
                .map(Map.Entry::getValue)
                .findFirst().
                orElse(null);
        if (profile != null) {
            System.out.println("Profile found: " + profile);
        } else {
            LOGGER.warning("Profile not found for : " + fio);
        }
    }

    public void addUser(String fullName, int age, String phone, String sexStr, String address) {
        if (currentFileName == null) {
            LOGGER.warning("No file loaded.");
            return;
        }

        if (fioToProfile.keySet().stream().anyMatch(key -> key.equalsIgnoreCase(fullName))) {
            LOGGER.warning("Profile already exists for : " + fullName);
            return;
        }

        fioToProfile.put(fullName, profileFileRepository.addProfile(fullName, age, phone, sexStr, address));
        System.out.println("Profile added for : " + fullName);
    }


    public void removeUser(String nameToRemove) {
        if (currentFileName == null) {
            LOGGER.warning("No file loaded.");
            return;
        }

        if (fioToProfile.keySet().removeIf(key -> key.equalsIgnoreCase(nameToRemove))) {
            System.out.println("Profile removed for : " + nameToRemove);
        } else {
            LOGGER.warning("Profile not found for removal: " + nameToRemove);
        }
    }

    public void saveFile() {
        if (currentFileName == null) {
            LOGGER.warning("No file loaded to save.");
            return;
        }

        profileFileRepository.saveToFile(currentFileName, fioToProfile,
                checkSumService.calculateChecksum(new ProfileFileData(fioToProfile, 0)));

        System.out.println("Profiles saved to file: " + currentFileName);
    }

    public void saveFile(String newFileName) {

        try {
            profileFileRepository.saveToFile(newFileName, fioToProfile,
                    checkSumService.calculateChecksum(new ProfileFileData(fioToProfile, 0)));

            System.out.println("Profiles saved to file: " + currentFileName);
        } catch (ValidationException e) {
            LOGGER.warning("Failed to save file : " + newFileName);
        }

    }
}
