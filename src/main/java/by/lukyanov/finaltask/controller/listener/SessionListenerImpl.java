package by.lukyanov.finaltask.controller.listener;

import by.lukyanov.finaltask.command.PagePath;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;

import static by.lukyanov.finaltask.command.ParameterAttributeName.*;

@WebListener
public class SessionListenerImpl implements HttpSessionListener{
    private static final String DEFAULT_LOCALE = "en";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(CURRENT_PAGE, PagePath.MAIN_PAGE);
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
    }
}
