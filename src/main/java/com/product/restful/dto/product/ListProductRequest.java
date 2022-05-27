package com.product.restful.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListProductRequest {

    @NotNull(message = "Page Index can not null")
    @Min(value = 0, message = "Page Index must not be less than zero")
    private Integer pageNo;

    @NotNull(message = "Page Size can not null")
    @Min(value = 1, message = "Page Size must not be less than one")
    private Integer pageSize;

    @NotBlank(message = "Property must not be blank")
    private String sortBy;

    @NotBlank(message = "Sort direction must be asc or desc")
    private String sortDir;
}
