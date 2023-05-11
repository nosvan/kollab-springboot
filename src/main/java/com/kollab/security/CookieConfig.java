package com.kollab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class CookieConfig {
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setDomainName("localhost");
        serializer.setCookieName("SESSION");
        serializer.setSameSite("Lax");
        serializer.setCookiePath("/");
        serializer.setUseSecureCookie(true);
        return serializer;
    }
}
