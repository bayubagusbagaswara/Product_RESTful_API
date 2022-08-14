package com.product.restful.dto.user;

import com.product.restful.dto.role.RoleDto;
import com.product.restful.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private Set<RoleDto> roles;

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), RoleDto.fromEntitySet(user.getRoles()));
    }

    public static List<UserDTO> fromEntityList(List<User> userList) {
        return userList.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

}
