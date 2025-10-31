package repository;

import entity.Profile;
import entity.ProfileFileData;

import java.util.HashMap;
import java.util.Map;

public class ProfileFileRepository {

    public ProfileFileData loadFile(String fileName) {
        return new ProfileFileData(new HashMap<>(),42);
    }

    public void saveFile(String fileName, Map<String, Profile> fioToProfile) {

    }

}
