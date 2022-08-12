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
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private Set<RoleDto> roles;

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), RoleDto.fromEntitySet(user.getRoles()));
    }

    public static List<UserDto> fromEntityList(List<User> userList) {
        return userList.stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

}
