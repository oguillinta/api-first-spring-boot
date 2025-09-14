package com.iogui.apps.loans.evaluation.security;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


@SuppressWarnings("unchecked")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${keycloak.resource}")
    private String clientId;
    private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {

        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {

            Object realm = jwt.getClaim("realm_access");


            LinkedTreeMap<String, List<String>> realmRoleMap = (LinkedTreeMap<String, List<String>>) realm;

            List<String> realmRoles = new ArrayList<>(realmRoleMap.get("roles"));
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            Object client = resourceAccess.get(clientId);

            LinkedTreeMap<String, List<String>> clientRoleMap = (LinkedTreeMap<String, List<String>>) client;

            Collection<GrantedAuthority> realmListSimpleGrantedAuthority = realmRoles.stream()
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            if (!isNull(clientRoleMap)) {
                List<String> clientRoles = new ArrayList<>(clientRoleMap.get("roles"));
                Collection<GrantedAuthority> resourceListSimpleGrantedAuthority = clientRoles.stream()
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                realmListSimpleGrantedAuthority.addAll(resourceListSimpleGrantedAuthority);
            }

            realmListSimpleGrantedAuthority.forEach(System.out::println);

            return realmListSimpleGrantedAuthority;
        };

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
