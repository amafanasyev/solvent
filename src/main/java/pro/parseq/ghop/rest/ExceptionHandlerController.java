package pro.parseq.ghop.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(TrackNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse requestHandlingTrackNotFound(
			HttpServletRequest request, TrackNotFound e) {

		return new ErrorResponse(new Date(), 404, "Track not found",
				TrackNotFound.class, e.getMessage(), request.getServletPath());
	}
}