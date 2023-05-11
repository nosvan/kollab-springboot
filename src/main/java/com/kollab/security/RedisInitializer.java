package com.kollab.security;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class RedisInitializer extends AbstractHttpSessionApplicationInitializer {
    public RedisInitializer() {
        super(RedisSessionConfig.class);
    }
}
