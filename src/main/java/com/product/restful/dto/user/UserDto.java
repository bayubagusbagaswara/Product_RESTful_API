package com.product.restful.dto.user;

import com.product.restful.dto.role.RoleDto;
import com.product.restful.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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

    private String username;

    private Set<RoleDto> roles = new HashSet<>();

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .roles(RoleDto.fromRoles(user.getRoles()))
                .build();
    }

    public static List<UserDto> fromUserList(List<User> userList) {
        return userList.stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList())
                ;
    }

}
