package com.product.restful.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductResponse {

    private List<GetProductResponse> getProductResponses;

    private Integer pageNo;

    private Integer pageSize;

    private Long totalElements;

    private Integer totalPages;

    private boolean last;
}
