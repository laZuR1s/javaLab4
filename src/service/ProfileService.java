package service;

import entity.Profile;
import entity.ProfileFileData;
import repository.ProfileFileRepository;

import java.util.Collections;
import java.util.Map;

public class ProfileService {
    private final CheckSumService checkSumService;
    private final ProfileFileRepository profileFileRepository;
    private Map<String, Profile> fioToProfile;


    public ProfileService(CheckSumService checkSumService, ProfileFileRepository profileFileRepository) {
        this.checkSumService = checkSumService;
        this.profileFileRepository = profileFileRepository;
    }
    public void loadFile(String fileName) {
        {
            ProfileFileData profileFileData = profileFileRepository.loadFile(fileName);
            checkSumService.validateFileCheckSum(profileFileData);
            fioToProfile = profileFileData.fioToProfile();
        }
    }
}
