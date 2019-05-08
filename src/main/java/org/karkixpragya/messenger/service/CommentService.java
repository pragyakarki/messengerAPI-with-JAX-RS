package org.karkixpragya.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.karkixpragya.messenger.database.DatabaseClass;
import org.karkixpragya.messenger.model.Comment;
import org.karkixpragya.messenger.model.ErrorMessage;
import org.karkixpragya.messenger.model.Message;

public class CommentService {
	private Map<Long, Message> messages = DatabaseClass.getMessages();
		
	public List<Comment> getAllComments(long messageID) {
		Map<Long, Comment> comments = messages.get(messageID).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageID, long commentID) {
		ErrorMessage errorMessage = new ErrorMessage("NOT FOUND", 404, "documentations");
		Response response = Response.status(Status.NOT_FOUND)
									.entity(errorMessage)
									.build();		
		Message message =  messages.get(messageID);
		if (message == null) {
			throw new WebApplicationException(response);
		}
		Map<Long, Comment> comments = messages.get(messageID).getComments();
		Comment comment = comments.get(commentID);
		if (comment == null) {
			// Use of inbuilt exception subclasses from WebApplicationException
			// ? the response for NotFoundException does not require status but was not able to do it.
			throw new NotFoundException(response);
		}
		return comment;
	}
	
	public Comment addComment(long messageID, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageID).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);		
		return comment;
	}
	
	public Comment updateComment(long messageID, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageID).getComments();
		if (comment.getId() <= 0) {
			return null;
		}		
		comments.put(comment.getId(), comment);		
		return comment;
	}
	
	public Comment removeComment(long messageID, long commentID) {
		Map<Long, Comment> comments = messages.get(messageID).getComments();
		return comments.remove(commentID);
	}
}
