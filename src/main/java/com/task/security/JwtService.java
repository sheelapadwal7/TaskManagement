/*
 * package com.task.security;
 * 
 * import java.security.Key; import java.util.Date; import java.util.HashMap;
 * import java.util.Map;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Service;
 * 
 * import com.task.enums.LinkType; import com.task.model.Student;
 * 
 * import io.jsonwebtoken.Claims; import io.jsonwebtoken.Jwts; import
 * io.jsonwebtoken.SignatureAlgorithm; import io.jsonwebtoken.io.Decoders;
 * import io.jsonwebtoken.security.Keys;
 * 
 * 
 * @Service public class JwtService {
 * 
 * @Value("${security.jwt.secret-key}") private String secretKey;
 * 
 * @Value("${security.jwt.expiration-time}") private long jwtExpiration;
 * 
 * 
 * public String generateToken(Student s) { Map<String, Object> extraClaims =
 * new HashMap<>();
 * 
 * extraClaims.put("userType", LinkType.STUDENT); extraClaims.put("userId",
 * s.getId());
 * 
 * return generateToken(s.getUserName(), extraClaims); }
 * 
 * public String generateToken(String userName, Map<String, Object> extraClaims
 * ) { return Jwts .builder() .setClaims(extraClaims) .setSubject(userName)
 * .setIssuedAt(new Date(System.currentTimeMillis())) .setExpiration(new
 * Date(System.currentTimeMillis() + jwtExpiration)) .signWith(getSignInKey(),
 * SignatureAlgorithm.HS256) .compact(); }
 * 
 * public String verifyToken(String token) { Claims claims = Jwts
 * .parserBuilder() .setSigningKey(getSignInKey()) .build()
 * .parseClaimsJws(token) .getBody();
 * 
 * return claims.getSubject(); }
 * 
 * private Key getSignInKey() { byte[] keyBytes =
 * Decoders.BASE64.decode(secretKey); return Keys.hmacShaKeyFor(keyBytes); } }
 */