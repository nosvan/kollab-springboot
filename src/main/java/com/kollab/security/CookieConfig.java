package com.kollab.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class CookieConfig {
    @Value("${cookie-config.domain-name}")
    private String domain;
    @Value("${cookie-config.cookie-name}")
    private String cookieName;
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setDomainName(domain);
        serializer.setCookieName(cookieName);
        serializer.setSameSite("Lax");
        serializer.setCookiePath("/");
        serializer.setUseSecureCookie(true);
        return serializer;
    }
}
