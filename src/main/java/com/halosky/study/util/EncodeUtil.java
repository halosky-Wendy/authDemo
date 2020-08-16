package com.halosky.study.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EncodeUtil {

    private final PasswordEncoder passwordEncoder;
    public EncodeUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(String value){
        return StringUtils.isEmpty(value) ? null : passwordEncoder.encode("123456");
    }

}
