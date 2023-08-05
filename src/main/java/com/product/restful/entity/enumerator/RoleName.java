package com.product.restful.entity.enumerator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.product.restful.entity.enumerator.Permission.*;

@RequiredArgsConstructor
public enum RoleName {

    MANAGER(Set.of(
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_CREATE,
            MANAGER_DELETE
    )),

    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_CREATE,
            ADMIN_DELETE,
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_CREATE,
            MANAGER_DELETE
    )),

    USER(Collections.emptySet())

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(
                        permission.getPermission()
                ))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
