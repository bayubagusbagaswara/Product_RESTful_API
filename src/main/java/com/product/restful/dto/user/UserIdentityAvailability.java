package com.product.restful.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserIdentityAvailability {

    private Boolean available;
}
