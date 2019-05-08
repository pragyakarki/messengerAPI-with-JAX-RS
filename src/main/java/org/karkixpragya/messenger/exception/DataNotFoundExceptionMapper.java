package org.karkixpragya.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.karkixpragya.messenger.model.ErrorMessage;

//Register this class in JAX-RS, so that it knows that this class can be used to map exceptions
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 404, "documentations");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}
}
