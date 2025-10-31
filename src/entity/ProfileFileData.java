package entity;

import java.util.Map;

public record ProfileFileData(Map<String, Profile> fioToProfile, Integer fileHash) {
}
