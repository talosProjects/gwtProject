package com.mozido.channels.nextweb.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("/service/gwt/loggingService")
public interface LoggingService extends RemoteService {
    StackTraceElement[] logClientException(final StackTraceElement[] stackTraceElements, final String permutationName);
}