package com.mozido.channels.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @Author Alex Manusovich
 */
public class InternationalizationFilter extends OncePerRequestFilter {
    private LocaleResolver localeResolver;
    private LocaleResolver localeDetector;

    public InternationalizationFilter(LocaleResolver localeResolver,
                                      LocaleResolver localeDetector) {
        this.localeResolver = localeResolver;
        this.localeDetector = localeDetector;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain)
            throws ServletException, IOException {
        final Locale newLocale = localeDetector.resolveLocale(request);
        LocaleContextHolder.setLocale(newLocale);
        localeResolver.setLocale(request, response, newLocale);
        try {
            filterChain.doFilter(request, response);
        } finally {
            LocaleContextHolder.resetLocaleContext();
        }
    }

}