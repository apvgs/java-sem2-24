package br.com.fiap.security;

import br.com.fiap.service.TokenService;
import br.com.fiap.service.TokenServiceFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Map;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    private final TokenService tokenService = TokenServiceFactory.create();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        Cookie cookie = requestContext.getCookies().get(CookieName.TOKEN);

        if (isPublicPath(path)) {
            return;
        }

        if (!isValidCookie(cookie)) {
            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(Map.of("error", "Acesso negado."))
                    .build());
        }
    }

    private boolean isPublicPath(String path) {
        return path.endsWith("user/auth/signup") || path.equals("auth/login");
    }

    private boolean isValidCookie(Cookie cookie) {
        if (cookie == null) {
            return false;
        }
        tokenService.getSubject(cookie.getValue());
        return true;
    }
}
