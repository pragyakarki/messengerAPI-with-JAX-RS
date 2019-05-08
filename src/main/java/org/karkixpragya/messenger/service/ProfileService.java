package org.karkixpragya.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.karkixpragya.messenger.database.DatabaseClass;
import org.karkixpragya.messenger.model.Profile;

public class ProfileService {
	private static Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("bigya", new Profile (1L, "bigya", "Bigya", "Karki"));
	}
	
	//Passing a collection to the ArrayList constructor initialized the List with those elements!
	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}	
	
	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}	
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
