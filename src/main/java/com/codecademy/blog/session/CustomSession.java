package com.codecademy.blog.session;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is needed for Codecacdemy workspace. Local development can ignore.
 */
public class CustomSession implements HttpSession {
    private final String id;
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();
    private final long creationTime = System.currentTimeMillis();
    private int maxInactiveInterval; // in seconds
    private boolean isNew = true;
    private boolean isValid = true;

    public CustomSession(String id) {
        this.id = id;
        this.maxInactiveInterval = 60 * 30;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        maxInactiveInterval = i;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Override
    public Object getAttribute(String s) {
        return attributes.get(s);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public void setAttribute(String s, Object o) {
        attributes.put(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        attributes.remove(s);
    }

    @Override
    public void invalidate() {
        if (!isValid) {
            throw new IllegalStateException("Session already invalidated");
        }
        // Clear all stored attributes
        attributes.clear();
        // Mark session as invalid
        isValid = false;
        // Optionally, you can add logic to remove this session from your global session store.
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    // Setter so that our filter can mark the session as not new after the first request
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

}