package com.product.restful.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonPropertyOrder({
        "success",
        "message",
        "data"
})
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonIgnore
    private HttpStatus status;

    public WebResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public WebResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}

