package com.tenakata.admission.exception;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Created by Julius kimathi.
 */
@ControllerAdvice
@RestController
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<?> handleNotFountExceptions(Exception ex, WebRequest request) {
        Response<?> response = Response.notFound();
        response.setMessage(ex.getMessage());
       //response.addErrorMsgToResponse(ex.getMessage(), ex);
       // return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public final ResponseEntity<?> handleDuplicateException(Exception ex, WebRequest request) {
        Response<?> response = Response.duplicateEntity();
       // response.addErrorMsgToResponse(ex.getMessage(), ex);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiException object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiException(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

  /*  @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(NullPointerException ex,
                                                                  WebRequest request) {

        Response<?> response = Response.internalServerError()
                .setMessage("Null pointer exception")
                .setErrors(ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex,
                                                                 WebRequest request) {

        Response<?> response = Response.accessDenied()
                .setMessage(ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(TooManyRequestsException ex,
                                                                 WebRequest request) {

        Response<?> response = Response.tooManyRequests()
                .setMessage(ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex,
                                                                 WebRequest request) {

        Response<?> response = Response.badRequest()
                .setMessage(ex.getMessage());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiException object
     */


    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiException object
     */


    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiException object
     */


    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
    // * @param ex the ConstraintViolationException
    // * @return the ApiException object
     *
   // @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
        ApiException apiError = new ApiException(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiError);
    }*/

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiException object
     */
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    protected Response<Object> handleConstraintViolation(
            jakarta.validation.ConstraintViolationException ex) {
        Response<Object> response = Response.badRequest();
        response.setMessage("Validation error");
      //  response.addValidationErrors(ex.getConstraintViolations());
        return response;

    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiException object
     */
  /*  @ExceptionHandler(EntityNotFoundException.class)
    protected Response<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        Response<Object> response = Response.notFound();
        response.setErrors(ex.getMessage());
        return response;
    }*/


    /**
     * Handles Duplicateentityecxeption. Created to encapsulate errors with more details on duplicate entries
     *
     * //@param ex the duplicateentityexception
     * @return the ApiException object
     */
   /* @ExceptionHandler(DuplicateEntityException.class)
    protected Response<Object> handleDuplicate(
            DuplicateEntityException ex) {
        Response<Object> response = Response.duplicateEntity();
        response.setMessage(ex.getMessage());
        return response;
    }*/

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiException object
     */

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiException object
     */


    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */


    /**
     * Handle javax.persistence.EntityNotFoundException
     */
    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(jakarta.persistence.EntityNotFoundException ex) {
        return buildResponseEntity(new ApiException(HttpStatus.NOT_FOUND, ex));
    }


    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiException object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiException apiError = new ApiException(BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }




    private ResponseEntity<Object> buildResponseEntity(ApiException apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
