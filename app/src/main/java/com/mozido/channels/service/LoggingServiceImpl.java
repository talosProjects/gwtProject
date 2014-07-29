package com.mozido.channels.service;

import com.google.gwt.logging.server.StackTraceDeobfuscator;
import com.mozido.channels.nextweb.services.LoggingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@Controller("logService")
public class LoggingServiceImpl implements LoggingService, ServletContextAware {
    private static final Logger logger = Logger.getLogger("com.mozido.gwt");
    private static StackTraceDeobfuscator deobfuscator;

    private ServletContext servletContext;

    private StackTraceDeobfuscator getDeobfuscator() {
        if (deobfuscator == null) {
            String path = servletContext.getRealPath("/WEB-INF/mozidoweb/symbolMaps/");
            deobfuscator = new StackTraceDeobfuscator(path);
        }
        return deobfuscator;
    }


    @Override
    public StackTraceElement[] logClientException(final StackTraceElement[] stackTraceElements,
                                                  final String permutationName) {
        StackTraceElement[] deobfuscated = getDeobfuscator().deobfuscateStackTrace(
                stackTraceElements, permutationName);

        /**
         * Just for logging on the server
         */
        String exception = "";
        for (StackTraceElement s : deobfuscated) {
            exception = exception + s.toString() + "\n\t\t";
        }
        logger.log(Level.SEVERE, exception);

        return deobfuscated;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
