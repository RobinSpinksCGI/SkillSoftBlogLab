package com.codecademy.blog.session;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is necessary for the Codecademy Workspace. Local development can remove @Component to ignore
 */
@Component
public class CustomSessionFilter implements Filter {

    private final Map<String, CustomSession> sessionStore = new ConcurrentHashMap<>();
    private static final String SESSION_COOKIE_NAME = "FIXED-SESSION-NAME";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String sessionId = null;
        boolean newSession = false;

        // Look for an existing session cookie
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SESSION_COOKIE_NAME.equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        // If no cookie is found, generate a new session id and create the cookie
        if (sessionId == null) {
            sessionId = "FIXED-SESSION-ID";
            Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            newSession = true;
        }

        // Retrieve or create the session from the in-memory store
        CustomSession session = sessionStore.computeIfAbsent(sessionId, CustomSession::new);

        // If it's not a newly created session, mark it as not new
        if (!newSession) {
            session.setNew(false);
        }

        // Wrap the HttpServletRequest so that calls to getSession() return our custom session
        HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public HttpSession getSession(boolean create) {
                return session;
            }

            @Override
            public HttpSession getSession() {
                return getSession(true);
            }
        };

        chain.doFilter(wrappedRequest, response);
    }
}