package service;

import entity.Profile;
import entity.ProfileFileData;
import repository.ProfileFileRepository;

import java.util.Map;
import java.util.logging.Logger;

public class ProfileService {
    private final CheckSumService checkSumService;
    private final ProfileFileRepository profileFileRepository;
    private Map<String, Profile> fioToProfile;

    private static final Logger LOGGER = Logger.getLogger(ProfileService.class.getName());

    public ProfileService(CheckSumService checkSumService, ProfileFileRepository profileFileRepository) {
        this.checkSumService = checkSumService;
        this.profileFileRepository = profileFileRepository;
    }

    public void loadFile(String fileName) {

        ProfileFileData profileFileData = profileFileRepository.loadFile(fileName);
        checkSumService.validateFileCheckSum(profileFileData);
        fioToProfile = profileFileData.fioToProfile();


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
        if(fioToProfile.containsKey(fullName)) {
            LOGGER.warning("Profile already exists for : " + fullName);
            return;
        }

        fioToProfile.put(fullName,profileFileRepository.addProfile(fullName, age, phone, sexStr, address));
        System.out.println("Profile added for : " + fullName);
    }


}
