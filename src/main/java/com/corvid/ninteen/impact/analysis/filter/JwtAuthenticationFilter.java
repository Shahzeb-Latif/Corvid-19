package com.corvid.ninteen.impact.analysis.filter;

import com.corvid.ninteen.impact.analysis.model.UserPrincipal;
import com.corvid.ninteen.impact.analysis.service.impl.JWTTokenProviderService;
import com.corvid.ninteen.impact.analysis.service.impl.UserAuthDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.corvid.ninteen.impact.analysis.util.ConstantUtil.*;

/**
 * @author shahzeb.latif
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenProviderService tokenProvider;

    @Autowired
    private UserAuthDetailsService userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("Validating Token!!!!!");

        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt)) {
                if (tokenProvider.validateToken(jwt)) {
                    LOGGER.info("Token is Valid ");

                    String userNameFromToken = tokenProvider.getUserNameFromToken(jwt);

                    UserPrincipal userDetails = userDetailsService.loadUserByUsernameForFilter(userNameFromToken);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else
                    throw new UsernameNotFoundException("Invalid Token!!or Don't use old token!!Use New One");
            }
        } catch (UsernameNotFoundException e) {
            LOGGER.error("Invalid Token", e);
            response.setStatus(401);
            return;
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER + " ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
