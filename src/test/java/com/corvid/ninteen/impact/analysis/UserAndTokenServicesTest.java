package com.corvid.ninteen.impact.analysis;

import com.corvid.ninteen.impact.analysis.mapper.CorvidMapper;
import com.corvid.ninteen.impact.analysis.model.User;
import com.corvid.ninteen.impact.analysis.model.UserPrincipal;
import com.corvid.ninteen.impact.analysis.model.UserToken;
import com.corvid.ninteen.impact.analysis.service.impl.UserAuthDetailsService;
import com.corvid.ninteen.impact.analysis.service.impl.JWTTokenProviderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.PropertySource;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.corvid.ninteen.impact.analysis.util.ConstantUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@PropertySource("classpath:application.properties")
public class UserAndTokenServicesTest {

    @InjectMocks
    UserAuthDetailsService userAuthDetailsService;

    @InjectMocks
    CorvidMapper corvidMapper;

    @InjectMocks
    UserToken userToken;

    @InjectMocks
    User user;

    @InjectMocks
    JWTTokenProviderService jwtTokenProviderService;

    @InjectMocks
    UserAuthDetailsService userDetailsService;

    @Before
    public void before(){
        userAuthDetailsService.user = user;
        jwtTokenProviderService.userDetailsService = userDetailsService;
        jwtTokenProviderService.userDetailsService.user = user;
        jwtTokenProviderService.tokenIssuer = "CORVID-19";
        jwtTokenProviderService.tokenSecret = "CORVID@19";
        jwtTokenProviderService.accessCodeExpiry = 172800;
    }
    @Test
    public void testLoadUserByUsername(){
        userAuthDetailsService.loadUserByUsername("shahzeb");
        assertEquals("shahzeb", new UserPrincipal("shahzeb",null).getUsername());
    }

    @Test
    public void testGenerateAccessToken() throws ParseException {

        user.setClientId("shahzeb");
        user.setRole(USER);


        UserPrincipal userPrincipal = UserPrincipal.create(user);

        String token = jwtTokenProviderService.generateAccessToken(userPrincipal.getUsername(), userPrincipal.getAuthorities());

        assertNotEquals(null, token);
        assertNotEquals("" , token);
    }

    @Test
    public void testLoadUserByUsernameForFilter() throws ParseException {

        user.setClientId("shahzeb");
        user.setRole(USER);

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        jwtTokenProviderService.generateAccessToken(userPrincipal.getUsername(), userPrincipal.getAuthorities());

        userPrincipal = userAuthDetailsService.loadUserByUsernameForFilter(user.getClientId());

        assertEquals(user.getClientId(), userPrincipal.getUsername());
    }

    @Test
    public void testValidateToken() throws InterruptedException, ParseException {

        user.setClientId("shahzeb");
        user.setRole(USER);

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        String tokenOld = jwtTokenProviderService.generateAccessToken(userPrincipal.getUsername(), userPrincipal.getAuthorities());

        assertEquals(true, jwtTokenProviderService.validateToken(tokenOld));

        TimeUnit.SECONDS.sleep(1);

        String tokenNew = jwtTokenProviderService.generateAccessToken(userPrincipal.getUsername(), userPrincipal.getAuthorities());

        assertEquals(false, jwtTokenProviderService.validateToken(tokenOld));

        assertEquals(true, jwtTokenProviderService.validateToken(tokenNew));

    }

    @Test
    public void testGetUserList() throws ParseException {
        user.setClientId("shahzeb");
        user.setRole(USER);

        UserPrincipal userPrincipal = UserPrincipal.create(user);

        String tokenNew = jwtTokenProviderService.generateAccessToken(userPrincipal.getUsername(), userPrincipal.getAuthorities());

        HashMap hashMap = new HashMap<String, Date>();
        hashMap.put(user.getClientId(),null);

        assertEquals(hashMap.keySet(),userLists.keySet());

    }
}


