package com.product.restful.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

}
