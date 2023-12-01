package az.iktlab.taskmanagement.security;

import az.iktlab.taskmanagement.constants.SecurityConstant;
import az.iktlab.taskmanagement.model.JWTToken;
import az.iktlab.taskmanagement.util.DateHelper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JWTProvider {

    private final JWTVerifier jwtVerifier;
    private final SecurityConstant securityConstants;

    public JWTProvider(SecurityConstant securityConstants) {
        this.securityConstants = securityConstants;
        this.jwtVerifier = JWT.require(Algorithm.HMAC256(securityConstants.getSecretKey()))
                .withSubject("Task Management")
                .withIssuer("Task-Management-System")
                .build();
    }

    public JWTToken getJWTToken(String userId,Boolean isRememberMe) {
        Date createDate = DateHelper.now();
        Date expirationDate = getExpirationDate(isRememberMe?Integer.MAX_VALUE:3);
        String jwtToken = JWT
                .create()
                .withSubject("Task Management")
                .withExpiresAt(expirationDate)
                .withIssuedAt(createDate)
                .withClaim("userId", userId)
                .withIssuer("Task-Management-System")
                .sign(Algorithm.HMAC256(securityConstants.getSecretKey()));

        return buildJWTToken(jwtToken, createDate, expirationDate);
    }


    private static JWTToken buildJWTToken(String jwtToken, Date createDate, Date expirationDate) {
        return JWTToken.builder()
                .token(jwtToken)
                .createDate(createDate.getTime())
                .expirationDate(expirationDate.getTime())
                .build();
    }

    public String extractUserId(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim("userId").asString();
    }


    private Date getExpirationDate(Integer amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, amount);
        return calendar.getTime();
    }

}
