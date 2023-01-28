package org.twt.ts.utils;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.twt.ts.model.Account;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.expired}")
    private long expired_time;
    @Value("${jwt.token}")
    private String secret;
    private String header;


    public long getExpired_time() {
        return expired_time;
    }

    public String generateToken(Account user) {
        Map<String, Object> payload = new HashMap<>(6);
        payload.put("id", user.getId());
        payload.put("username", user.getUsername());
        payload.put("nickname",user.getNickname());
        payload.put("permission",user.getPermission());


        Date expirationDate = new Date(System.currentTimeMillis() + expired_time);

        return Jwts.builder()
                .addClaims(payload)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getTokenInfo(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getId(String token) {
        try {
            Claims payload = getTokenInfo(token);
            return (Integer) payload.get("id");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getTokenInfo(token);
            System.out.println(claims);
            System.out.println(token);
            Date expiration = claims.getExpiration();
            System.out.println(expiration);
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
