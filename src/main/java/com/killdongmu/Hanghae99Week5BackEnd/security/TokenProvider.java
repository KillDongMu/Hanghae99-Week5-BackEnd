package com.killdongmu.Hanghae99Week5BackEnd.security;

import com.killdongmu.Hanghae99Week5BackEnd.dto.TokenDto;
import com.killdongmu.Hanghae99Week5BackEnd.entity.Members;
import com.killdongmu.Hanghae99Week5BackEnd.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final String BEARER_TYPE = "bearer";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    private final MemberRepository memberRepository;

    private final Key key;

    @Autowired
    public TokenProvider(@Value("#{environment['secret.key']}") String secretKey, MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
        byte[] keyBites = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBites);
    }

    public TokenDto generateTokenDto(Authentication authentication) {
        String authorities = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long nowTime = new Date().getTime();

        Date accessTokenExpires = new Date(nowTime + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpires)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        Date refreshTokenExpires = new Date(nowTime + REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(refreshTokenExpires)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpires.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("?????? ????????? ?????? ???????????????");
        }

        String username = claims.getSubject();
        Members member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username ));
        MemberDetails memberDetails = new MemberDetails(member);
        return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
            return true;

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("JWT??? ???????????? ???????????? ???????????????");
        } catch (ExpiredJwtException e) {
            log.info("JWT??? ??????????????? ?????????????????????");
        } catch (UnsupportedJwtException e) {
            log.info("JWT??? ????????? ?????? ?????? ????????????");
        } catch (PrematureJwtException e) {
            log.info("??? ????????? ?????? ????????? ????????? ????????????. ????????? ????????? ????????? ????????????");
        } catch (ClaimJwtException e) {
            log.info("Jwts??? PAYLOAD ????????? ??????????????????");
        }
        return false;
    }
}
