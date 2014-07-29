package com.mozido.channels.nextweb.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mozido.channels.nextweb.exception.ChannelServiceException;
import com.mozido.channels.nextweb.model.CredentialsDTO;
import com.mozido.channels.nextweb.model.SessionDTO;
import com.mozido.channels.nextweb.model.UserDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("/service/gwt/userService")
public interface UserService extends RemoteService {

    UserDTO login(final CredentialsDTO credentials) throws ChannelServiceException;

    UserDTO readUserInformationById(
            final String token, final String id) throws ChannelServiceException;


    UserDTO readUserInformation(final String token,
                                final String login) throws ChannelServiceException;

    void logout(final SessionDTO sessionDTO);

    UserDTO register(final String firstName, final String lastName, final String password, final String pin,
            final String phone, final String email) throws ChannelServiceException;
}
