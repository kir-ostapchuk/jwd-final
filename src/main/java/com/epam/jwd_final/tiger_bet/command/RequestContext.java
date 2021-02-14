package com.epam.jwd_final.tiger_bet.command;

public interface RequestContext {

    void setAttribute(String name, Object obj);
    Object getAttribute(String name);
    Object getParameter(String name);
    void invalidateSession();
    void setSessionAttribute(String name, Object obj);
}