package ru.rompet.shop.mapper.security;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.rompet.shop.dto.security.UserDto;
import ru.rompet.shop.model.security.Authority;
import ru.rompet.shop.model.security.User;

import java.util.Comparator;
import java.util.Set;
import java.util.StringJoiner;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    private final String DELIMITER = ", ";
    private final String PREFIX = "ROLE_";

    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "setAuthorityToStringAuthority")
    public abstract UserDto userToUserDto(User user);

    @Named("setAuthorityToStringAuthority")
    public String setAuthorityToStringAuthority(Set<Authority> authorities) {
        StringJoiner sjAuthorities = new StringJoiner(DELIMITER);
        authorities
                .stream()
                .sorted(Comparator.comparing(Authority::getId))
                .map(Authority::getName)
                .map(authority -> authority.startsWith(PREFIX) ? authority.substring(5) : authority)
                .forEach(sjAuthorities::add);
        return sjAuthorities.toString();
    }
}
