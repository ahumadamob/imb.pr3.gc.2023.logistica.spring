package imb.pr3.estetica.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

	public static <T> ResponseEntity<APIResponse<T>> createSuccessResponse(HttpStatus httpStatus, List<String> messages, T data) {
	    APIResponse<T> response = new APIResponse<>(httpStatus.value(), messages, data);
	    return ResponseEntity.status(httpStatus).body(response);
	}

	public static <T> ResponseEntity<APIResponse<T>> createSuccessResponse(HttpStatus httpStatus, T data) {
	    APIResponse<T> response = new APIResponse<>(httpStatus.value(), null, data);
	    return ResponseEntity.status(httpStatus).body(response);
	}

    public static <T> ResponseEntity<APIResponse<T>> createErrorResponse(HttpStatus httpStatus, List<String> messages) {
        APIResponse<T> response = new APIResponse<>(httpStatus.value(), messages, null);
        return ResponseEntity.status(httpStatus).body(response);
    }

}
