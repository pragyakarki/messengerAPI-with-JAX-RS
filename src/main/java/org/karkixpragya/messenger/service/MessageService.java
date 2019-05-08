package org.karkixpragya.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.karkixpragya.messenger.database.DatabaseClass;
import org.karkixpragya.messenger.exception.DataNotFoundException;
import org.karkixpragya.messenger.model.Message;

//This is the service of Message
public class MessageService {

	private static Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message (1, "Hello wow!", "Pragya"));
		messages.put(2L, new Message (2, "Hello adsfsdfsf!", "Pragya"));
	}
	
	
	//Passing a collection to the ArrayList constructor initialized the List with those elements!
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){
		// Create an empty list to store messages from certain year
		List<Message> messageForYear = new ArrayList<>();
		// Calendar instantiation and initialization
		Calendar cal = Calendar.getInstance();
		// Loop through the message list
		for (Message message : messages.values()) {
			// set calendar time to the message created date
			cal.setTime(message.getCreated());
			// compare it with the year provided by user
			if (cal.get(Calendar.YEAR) == year) {
				//add the ones that match the year
				messageForYear.add(message);
			}
		}
		//return the list of messages from certain year
		return messageForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		//return empty list if ... 
		if (start + size > list.size()) {
			return new ArrayList<Message>();
		}
		return list.subList(start, start + size);
	}	
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if (message == null) {
			throw new DataNotFoundException("Message with id " + id + " not found");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}	
	
	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}	
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
	
//	public List<Message> getAllMessages() {
//		//Data here are hard coded - we can use database instead
//		Message m1 = new Message(1L, "Hello wow!", "Pragya");
//		Message m2 = new Message(2L, "Hello Texas!", "Pragya");
//		List<Message> list = new ArrayList<>();
//		list.add(m1);
//		list.add(m2);
//		return list;
//		
//	}
}
