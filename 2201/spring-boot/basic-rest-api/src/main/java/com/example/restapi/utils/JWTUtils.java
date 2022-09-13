package com.example.restapi.utils;

import com.example.restapi.props.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@UtilityClass
public class JWTUtils {

    private final static AppProperties prop;
    static {
        prop = ApplicationContextHolder.getContext().getBean(AppProperties.class);
    }


    /* PUBLIC METHODS */
    public static String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        claims.put("amar_naam", "xyz");
        return createToken(claims, username);
    }

    public static String extractUserName(String token){
//        return extractClaim(token, Claims::getSubject);
        return extractAllClaims(token).getSubject();
    }

    public static Date extractExpiration(String token){
//        return extractClaim(token, Claims::getExpiration);
        return extractAllClaims(token).getExpiration();
    }

    public static boolean isTokenInvalidOrExpired(String token){
        return !isTokenValid(StringUtils.trimToEmpty(token), extractUserName(token));
    }

    public static boolean isTokenFormatValid(String bearerToken){
        return StringUtils.isNotBlank(bearerToken)
                && bearerToken.matches(prop.getTokenValidationRegex());
    }

    /* PRIVATES */
    private static String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getExpiryMilli()))
                //Todo: secret and signature should be loaded from Keystore
                .signWith(SignatureAlgorithm.HS256, prop.getJwtSecret())
                .compact();
    }

    private static int getExpiryMilli(){
        return DateTimeUtils.convertToMilli(prop.getTokenExpiryMinute(), Calendar.MINUTE);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractAllClaims(token));
    }

    private static String trimToken(String bearerToken) {
        //removing Bearer prefix from the token
        return (StringUtils.startsWith(bearerToken, prop.getTokenPrefix())
                ? StringUtils.replace(bearerToken, prop.getTokenPrefix(), "")
                : bearerToken
        ).trim();
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(prop.getJwtSecret())
                .parseClaimsJws(trimToken(token))
                .getBody();
    }

    private static boolean isTokenValid(String token, String username){
        boolean isValidUsername = StringUtils.equals(extractUserName(token), username);
        return (isValidUsername && !isTokenExpired(token));
    }

    private static boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
}
