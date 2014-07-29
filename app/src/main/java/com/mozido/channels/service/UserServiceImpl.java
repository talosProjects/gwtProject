package com.mozido.channels.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mozido.api.core.addpersoncredential.AddPersonCredentialRequest;
import com.mozido.api.core.addpersoncredential.AddPersonCredentialResponse;
import com.mozido.api.core.createperson.CreatePersonRequest;
import com.mozido.api.core.createperson.CreatePersonResponse;
import com.mozido.api.core.getcompanyusers.GetCompanyUsersRequest;
import com.mozido.api.core.getcompanyusers.GetCompanyUsersResponse;
import com.mozido.api.core.getperson.GetPersonRequest;
import com.mozido.api.core.getperson.GetPersonResponse;
import com.mozido.api.core.getroles.GetRolesRequest;
import com.mozido.api.core.getroles.GetRolesResponse;
import com.mozido.api.core.getroles.Role;
import com.mozido.api.core.personshared.Person;
import com.mozido.api.core.sharedcontext.ContextRequest;
import com.mozido.api.core.sharedcontext.ContextResponse;
import com.mozido.api.core.signon.SignonRequest;
import com.mozido.api.core.signon.SignonResponse;
import com.mozido.channels.moteaf.MoteafServices;
import com.mozido.channels.nextweb.exception.ChannelErrorMessage;
import com.mozido.channels.nextweb.exception.ChannelServiceException;
import com.mozido.channels.nextweb.exception.PlatformErrorData;
import com.mozido.channels.nextweb.model.CredentialsDTO;
import com.mozido.channels.nextweb.model.SessionDTO;
import com.mozido.channels.nextweb.model.UserDTO;
import com.mozido.channels.nextweb.services.UserService;
import com.mozido.channels.nextweb.util.CredentialType;
import com.mozido.channels.util.Exceptions;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * The server side implementation of the RPC service.
 */
@Controller("userService")
@SuppressWarnings("serial")
@RequestMapping("/rest/user")
@Api(value = "User API", description = "User Management API")
public class UserServiceImpl implements UserService {
    @Autowired
    private MoteafServices moteafServices;

    @Value("${settings.platform.tenant}")
    private String tenantId;

    @Override
    @ResponseBody
    @RequestMapping(value = "/signOn", method = RequestMethod.POST)
    @ApiOperation(value = "Sign On Operation", notes = "Sign On Operation Description")
    public UserDTO login(
            @ApiParam(value = "User credentials", required = true)
            @RequestBody final CredentialsDTO credentials) throws ChannelServiceException {

        SignonResponse res;
        try {
            res = getMoteafServices().getSignonService()
                    .signon(createSignOnRequest(credentials));
        } catch (Exception e) {
            throw Exceptions.makeServerException(e);
        }

        checkResponse(res.getContextResponse());

        return readUserInformation(res.getContextResponse().getToken(),
                credentials.getLogin());
    }

    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    @ApiOperation(value = "Logout Operation", notes = "Logout Operation Description")
    public void logout(@RequestBody final SessionDTO sessionDTO) {
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get Person By Id Operation", notes = "Get Person By Id Operation Description")
    public UserDTO readUserInformationById(
            @ApiParam(value = "Platform Session token", required = true)
            @RequestParam final String token,

            @ApiParam(value = "Platform User Id", required = true)
            @PathVariable final String id) throws ChannelServiceException {

    	UserDTO userDTO = null;
    	
        GetPersonRequest getPersonRequest = new GetPersonRequest();
        getPersonRequest.setContextRequest(getContextRequest(token));
        getPersonRequest.setPersonId(id);
        GetPersonResponse response = getMoteafServices()
                .getGetPersonService().getPerson(getPersonRequest);
                
        userDTO = getUserDTO(response.getPerson(), new SessionDTO(token)); 
    
        
    	log("reading entity id:"+ response.getPerson().getEntityId());

        setRolesToUser(userDTO);
        
        setCompanyUsers(userDTO, response.getPerson().getEntityId());
        
        return userDTO;
    }
    
    
    public void setRolesToUser(UserDTO userDTO) { 
    	log("setting roles to user with person id:"+ userDTO.getId());
    	
    	GetRolesRequest getRolesRequest = new GetRolesRequest();
    	getRolesRequest.setContextRequest(getContextRequest(userDTO.getSession().getToken()));
    	
    	GetRolesResponse getRolesResponse = getMoteafServices().getGetRolesService().getRoles(getRolesRequest);
    	
    	List<String> roles = new ArrayList<String>();
    	
    	for(Role role:getRolesResponse.getRoles()){
    		roles.add(role.getRoleName());
    		log("....adding :"+role.getRoleName());
    	}
    	log("end loading person roles:");
    	userDTO.setRoles(roles);
    	
        //person.getPerson().getProfileItems()
        //person.getPerson().getProfileItemsSecure()
        //log("loading person roles:"+person.getPerson().getProfileItems());
        //for (Object object : person.getPerson().getRoles()) {
          //  roles.add(object != null ? object.toString() : null);
            
        //}
        //return userDTO;
    }
    
    public void setCompanyUsers(UserDTO userDTO, int entityId) {
     	log("setting company users to person id:"+ userDTO.getId());
        
    	GetCompanyUsersRequest request = new GetCompanyUsersRequest();
    	request.setContextRequest(getContextRequest(userDTO.getSession().getToken()));
    	request.setEntityId(entityId);
    	
    	GetCompanyUsersResponse response = getMoteafServices().getGetCompanyUsersService().getCompanyUsers(request);
    	log("-----> read company users size:"+ response.getCompanyUsers() != null ? response.getCompanyUsers().size()+"": "0");
    	List<String> userList = new ArrayList<String>();
    	for(Person p: response.getCompanyUsers()){
    		log("----->  :"+ p.getFirstname() + " " + p.getPersonType());
    	    userList.add(p.getFirstname());	
    	}
    	userDTO.setCompanyUsers(userList);
    }


    private void log(String log) {
    java.util.logging.Logger.getLogger(UserServiceImpl.class.getName())
    .log(java.util.logging.Level.INFO, 
         log);
    }
    
    private ContextRequest getContextRequest(String token) {
        ContextRequest contextRequest = new ContextRequest();
        contextRequest.setToken(token);
        return contextRequest;
    }

    @Override
    public UserDTO readUserInformation(final String token,
                                       final String login) throws ChannelServiceException {

        GetPersonRequest getPersonRequest = new GetPersonRequest();
        getPersonRequest.setContextRequest(getContextRequest(token));
        getPersonRequest.setCredential(login);
        getPersonRequest.setCredentialType(CredentialType.stringByLogin(login));
        GetPersonResponse response = getMoteafServices().getGetPersonService().getPerson(getPersonRequest);

        
        
        checkResponse(response.getContextResponse());
        
        
        //Load aditional info of the user - Jul

        return getUserDTO(response.getPerson(), new SessionDTO(token));
    }

    private void checkResponse(ContextResponse response) throws ChannelServiceException {
        if (response != null) {
            // @todo move codes to enum when platform team will expose them
            switch (response.getStatusCode()) {
                case "SECURITY":
                case "VALIDATION":
                case "TRANSACTION_VALIDATION":
                case "NON_SPECIFIC_MOZIDO_ERROR": {
                    ChannelServiceException channelServiceException =
                            new ChannelServiceException(generateError(response));

                    String message = response.getStatusMessage();
                    if (message == null || message.isEmpty()) {
                        message = "Platform error: " + response.getStatusCode();
                    }

                    ArrayList<ChannelErrorMessage> channelMessages = new ArrayList<>();
                    channelMessages.add(new ChannelErrorMessage(message));
                    channelServiceException.setChannelMessages(channelMessages);
                    throw channelServiceException;
                }
            }
        }
    }

    private PlatformErrorData generateError(ContextResponse response) {
        PlatformErrorData error = new PlatformErrorData();
        error.setCode(response.getStatusCode());
        error.setDescription(response.getAdditionalStatusMessage());
        return error;
    }


    private UserDTO getUserDTO(Person p, SessionDTO sessionDTO) {
        UserDTO user = new UserDTO();
        user.setId(p.getId());
        user.setTenantName(p.getTenantName());
        user.setPersonType(p.getPersonType());
        user.setTimezone(p.getTimezone());
        user.setTimezoneOffset(p.getTimezoneOffset());
        user.setSession(sessionDTO);
        user.setFirstName(p.getFirstname());
        user.setLastName(p.getLastname());

        // sets the additional info to user - Jul18/2014
        setRolesToUser(user);
        setCompanyUsers(user, p.getEntityId());
        
        return user;
    }

    private SignonRequest createSignOnRequest(final CredentialsDTO credentials) {
        final SignonRequest request = new SignonRequest();
        request.setLogin(createLogin(credentials));
        request.setPass(createPassword(credentials));
        request.setContextRequest(getContextRequest());
        return request;
    }

    private ContextRequest getContextRequest() {
        ContextRequest contextRequest = new ContextRequest();
        contextRequest.setTenantName(getTenantId());
        return contextRequest;
    }

    private SignonRequest.Login createLogin(final CredentialsDTO credentials) {
        SignonRequest.Login login = new SignonRequest.Login();
        login.setValue(credentials.getLogin());
        login.setCredentialType(CredentialType.stringByLogin(credentials.getLogin()));
        return login;
    }

    private SignonRequest.Pass createPassword(final CredentialsDTO credentials) {
        SignonRequest.Pass pass = new SignonRequest.Pass();
        pass.setValue(credentials.getPassword());
        pass.setSecurityElementType("PASSWORD");
        return pass;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ApiOperation(value = "Registration Operation", notes = "Registration Operation Description")
    public UserDTO register(
            @ApiParam(value = "User's First Name", required = true) final String firstName,
            @ApiParam(value = "User's Last Name", required = true) final String lastName,
            @ApiParam(value = "User's Password", required = true) final String password,
            @ApiParam(value = "User's Pin", required = true) final String pin,
            @ApiParam(value = "User's Phone Number", required = false) final String phone,
            @ApiParam(value = "User's Email", required = true) final String email) throws ChannelServiceException {

        CreatePersonRequest createPersonRequest = new CreatePersonRequest();
        createPersonRequest.setContextRequest(getContextRequest());
        createPersonRequest.setFirstName(firstName);
        createPersonRequest.setLastName(lastName);
        createPersonRequest.setPassword(password);
        createPersonRequest.setPin(pin);
        createPersonRequest.setPersonType("SUBSCRIBER");
        createPersonRequest.setTimezone("5");
        createPersonRequest.setTimezoneOffset("7");
        CreatePersonResponse createPersonResponse = getMoteafServices().getCreatePersonService()
                .createPerson(createPersonRequest);

        checkResponse(createPersonResponse.getContextResponse());

        if (phone != null && !phone.isEmpty()) {
            AddPersonCredentialRequest addPersonCredentialRequest = new AddPersonCredentialRequest();
            addPersonCredentialRequest.setContextRequest(getContextRequest());
            addPersonCredentialRequest.setCredential(phone);
            addPersonCredentialRequest.setCredentialType("PHONE");
            addPersonCredentialRequest.setPersonId(Integer.parseInt(createPersonResponse.getPerson().getId()));
            AddPersonCredentialResponse addPersonCredential = getMoteafServices().getAddPersonCredentialService()
                    .addPersonCredential(addPersonCredentialRequest);

            checkResponse(addPersonCredential.getContextResponse());
        }

        {
            AddPersonCredentialRequest addPersonCredentialRequest = new AddPersonCredentialRequest();
            addPersonCredentialRequest.setContextRequest(getContextRequest());
            addPersonCredentialRequest.setCredential(email);
            addPersonCredentialRequest.setCredentialType("EMAIL");
            addPersonCredentialRequest.setPersonId(Integer.parseInt(createPersonResponse.getPerson().getId()));
            AddPersonCredentialResponse addPersonCredentialResponse = getMoteafServices().getAddPersonCredentialService()
                    .addPersonCredential(addPersonCredentialRequest);

            checkResponse(addPersonCredentialResponse.getContextResponse());
        }

        CredentialsDTO credentials = new CredentialsDTO();
        credentials.setLogin(email);
        credentials.setPassword(password);
        return login(credentials);
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    public MoteafServices getMoteafServices() {
        return moteafServices;
    }

    public void setMoteafServices(MoteafServices moteafServices) {
        this.moteafServices = moteafServices;
    }
}
