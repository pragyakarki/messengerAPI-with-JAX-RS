package org.karkixpragya.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.karkixpragya.messenger.model.Comment;
import org.karkixpragya.messenger.service.CommentService;

//Infered path from subresource class
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CommentResource {

	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam ("messageID") long messageID){
		return commentService.getAllComments(messageID);
	}
	
	@POST
	public Comment addComment(@PathParam ("messageID") long messageID, Comment comment) {
		return commentService.addComment(messageID, comment);
	}
	
	@PUT
	@Path("/{commentID}")
	public Comment updateComment(@PathParam ("messageID") long messageID, @PathParam ("commentID") long id, Comment comment) {
		comment.setId(id);
		return commentService.updateComment(messageID, comment);
	}
	
	@DELETE
	@Path("/{commentID}")
	public void deleteComment(@PathParam ("messageID") long messageID, @PathParam ("commentID") long commentID) {
		commentService.removeComment(messageID, commentID);
	}
	
	@GET
	@Path("/{commentID}")
	public Comment getComment(@PathParam ("messageID") long messageID, @PathParam ("commentID") long commentID) {
		return commentService.getComment(messageID, commentID);
	}
	
	
	 //this subresource can inherit the messageID from its parent class
//	@GET
//	@Path("/{commentID}")
//	public String test2(@PathParam ("commentID") long commentID,
//						@PathParam ("messageID") long messageID) {
//		return "Comment ID: " + commentID + " MessageID : " + messageID;
//	}
}
