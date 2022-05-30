package com.product.restful.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder@JsonPropertyOrder({
        "code",
        "status",
        "success",
        "message"
})
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("status")
    private String status;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    private T data;
}

