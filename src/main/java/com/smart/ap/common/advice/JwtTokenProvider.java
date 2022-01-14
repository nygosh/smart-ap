package com.smart.ap.common.advice;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

    private final long TOKEN_VALID_MILISECOND = 1000L * 60 * 60 * 24; // 10시간

    @Value("spring.jwt.secret")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now)   // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + TOKEN_VALID_MILISECOND)) // 만료 기간
                .signWith(SignatureAlgorithm.HS512, secretKey) // 암호화 알고리즘, secret 값
                .compact(); // Token 생성
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("Authorization");
    }

    public boolean validateToken(String jwtToken) {
		String[] authorizations = StringUtils.split(jwtToken, " ");
		if (authorizations == null || authorizations.length != 2) {
			return false;
		}

		if (StringUtils.equalsIgnoreCase(authorizations[0], "Bearer") == false) {
			return false;
		}

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authorizations[1]);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
