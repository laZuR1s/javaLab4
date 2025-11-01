package service;

import entity.ProfileFileData;
import exception.ValidationException;

public class CheckSumService {

    public void validateFileCheckSum(ProfileFileData profileFileData) {
        if (profileFileData.fileHash() != profileFileData.fioToProfile().hashCode()) {
            throw new ValidationException("File Hash Mismatch. Actual hash: " + profileFileData.fioToProfile().hashCode());
        }
    }
}
