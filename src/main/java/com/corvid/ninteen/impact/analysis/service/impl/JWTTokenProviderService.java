package com.corvid.ninteen.impact.analysis.service.impl;

import com.corvid.ninteen.impact.analysis.model.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.corvid.ninteen.impact.analysis.util.ConstantUtil.FORMAT_TOKEN_ISSUE_AT;
import static com.corvid.ninteen.impact.analysis.util.ConstantUtil.userLists;

/**
 * @author shahzeb.latif
 */

@Service
public class JWTTokenProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTTokenProviderService.class);

    @Value("${corvid.token.secret}")
    public String tokenSecret;

    @Value("${corvid.token.issuer}")
    public String tokenIssuer;

    @Value("${corvid.token.access.code.expiry}")
    public int accessCodeExpiry;

    @Autowired
    public UserAuthDetailsService userDetailsService;

    public String generateAccessToken(String clientId, Collection<? extends GrantedAuthority> authorities) throws ParseException {

        List<String> roles = authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Date issuedAt = new Date();

        LOGGER.info("clientId : "+clientId);
        LOGGER.info("issuedAt : "+issuedAt);

        userLists.put(clientId,issuedAt);

        return Jwts
                .builder()
                .setIssuer(tokenIssuer)
                .setIssuedAt(issuedAt)
                .setSubject(clientId)
                .claim("clientId", clientId)
                .claim("Roles",roles)
                .setExpiration(new Date(new Date().getTime() + accessCodeExpiry * 1000))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(jwt);

            String userNameFromToken = this.getUserNameFromToken(jwt);
            UserPrincipal userDetails = userDetailsService.loadUserByUsernameForFilter(userNameFromToken);
            SimpleDateFormat formatTokenIssueAt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");

            //to invalidate old token of same user which is not expired
            if (FORMAT_TOKEN_ISSUE_AT.format(userLists.get(userDetails.getUsername())).compareTo((FORMAT_TOKEN_ISSUE_AT.format(this.getIssueAtFromToken(jwt)))) != 0) {
                return false;
            }
            return true;
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
        }
        return false;


    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Date getIssueAtFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getIssuedAt();
    }


}
