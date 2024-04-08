package __PACKAGE_PREFIX__.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class ErrorData {

	@JsonIgnore
	private HttpStatus statusCode;
	@JsonProperty("ReasonCode")
	private String code;
	@JsonProperty("Message")
	private String message;

}