package org.karkixpragya.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.karkixpragya.messenger.model.ErrorMessage;

//Register this class in JAX-RS, so that it knows that this class can be used to map exceptions
//@Provider
//<Throwable> catches all exceptions except DataNotFoundException
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 500, "This is a generic exception mapper");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.build();
	}
}

