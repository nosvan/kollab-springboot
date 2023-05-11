package com.kollab.security;

import jakarta.servlet.http.Cookie;

public interface CookieMaker {
    public static Cookie createCookie(String name, String value){
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain("localhost");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
