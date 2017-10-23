package organization.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException{
	
	public AccessDeniedException(String employeeId, int accessLevelNeeded, int accessLevel) {
		super("Access denied for user " + employeeId + "."
				+ " Access level " + accessLevelNeeded + " or above needed, but was " + accessLevel);
	}
}
