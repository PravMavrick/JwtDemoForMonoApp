package jwt.demo.seleniumexpress.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class MyAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        String username=null;
        String finalToken=null;

        if(token !=null && token.startsWith("Bearer")) {
            finalToken = token.replace("Bearer ", "");
            try {
                username = this.jwtHelper.extractUsername(finalToken);
                System.out.println(token);

            }catch (IllegalArgumentException e1) {
                e1.printStackTrace();
                logger.info("Illegal Argument Exception..");

            } catch (ExpiredJwtException e2) {
                e2.printStackTrace();
                logger.info("Token is expired..");

            } catch (MalformedJwtException e3) {
                e3.printStackTrace();
                logger.info("Some changes has done to the token..");

            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication()== null){

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(finalToken, userDetails);

            if(validateToken){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,new ArrayList<>());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
                System.out.println("Authentication failed");
            }
        }

        filterChain.doFilter(request,response);

    }
}
