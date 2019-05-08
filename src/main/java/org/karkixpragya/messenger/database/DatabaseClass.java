package org.karkixpragya.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.karkixpragya.messenger.model.Message;
import org.karkixpragya.messenger.model.Profile;

//Not for production -- only while learning Jersey

public class DatabaseClass {
	//Maps id with message
	private static Map<Long, Message> messages = new HashMap<>();
	//Profile name as the key, and profile instance as the value
	private static Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getMessages(){
		return messages;
	}
	
	public static Map<String, Profile> getProfiles(){
		return profiles;
	}
	
}
