package org.karkixpragya.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.karkixpragya.messenger.model.Message;
import org.karkixpragya.messenger.resources.beans.MessageFilterBean;
import org.karkixpragya.messenger.service.MessageService;

//Top-level annotation
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResoure {
	
	//Call the service from MessageResource and return response
	MessageService messageService = new MessageService();
	
	// Produces JSON, Accepts APPLICATION_JSON header
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("JSON output produced.");
		if (filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	// Produces XML, Accepts text/xml header
//	@GET
//	@Produces(MediaType.TEXT_XML)
//	public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean) {
//		System.out.println("XML output produced.");
//		if (filterBean.getYear() > 0) {
//			return messageService.getAllMessagesForYear(filterBean.getYear());
//		}
//		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
//			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
//		}
//		return messageService.getAllMessages();
//	}
	
//	@GET
//	public List<Message> getMessages(@QueryParam ("year") int year,
//									 @QueryParam ("start") int start,
//									 @QueryParam ("size") int size) {
//		if (year > 0) {
//			return messageService.getAllMessagesForYear(year);
//		}
//		if (start >= 0 && size > 0) {
//			return messageService.getAllMessagesPaginated(start, size);
//		}
//		return messageService.getAllMessages();
//	}
	
	@POST	
	//Accept message instance as argument
	// Returns response 201 Created and Location Header
	public Response addMessage(Message message, @Context UriInfo uriInfo) {		
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		// absolutePathBuilder + messageID
		URI uri = uriInfo.getAbsolutePathBuilder()
						 .path(newId)
						 .build();
		// created status with absolutePath of URI
		// pass the newMessage created
		return Response.created(uri)
					   .entity(newMessage)
					   .build();		
	}
	
	@GET
	@Path("/{messageID}")
	// @PathParam anno from jersey helps inject path variable as an argument for method getMessageID
	// Jersey can convert the url variable to required argument type ie; changes the variable to long for messageID
	public Message getMessage(@PathParam ("messageID") long messageID,
								@Context UriInfo uriInfo) {
		Message message = messageService.getMessage(messageID);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()						// base URI
				.path(MessageResoure.class)							// /messages/
				.path(MessageResoure.class, "getCommentResource")	// specifies the method ie., "/"
				.path(CommentResource.class)						//	/comments/
				.resolveTemplate("messageID", message.getId())		// provides the missing value
				.build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()	// base URI
				.path(ProfileResource.class)	// /profiles/
				.path(message.getAuthor())		// /profileName
				.build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()						// base URI
							.path(MessageResoure.class)					// /messages/
							.path(Long.toString(message.getId()))		// /messageID
							.build()
							.toString();
		return uri;
	}
		
	@PUT
	@Path("/{messageID}")
	//API figures out the message ID to be updated, rather than having the client enter it during PUT request
	public Message updateMessage(@PathParam ("messageID") long messageID, Message message) {
		message.setId(messageID);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageID}")
	//does not have a return type, 204 - No Content response after DELETE request
	public void deleteMessage(@PathParam ("messageID") long messageID) {
		messageService.removeMessage(messageID);
	}	
	
	//Works for any Requests	
	@Path("/{messageID}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
