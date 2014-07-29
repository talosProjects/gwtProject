package com.mozido.channels.moteaf;

import com.mozido.api.core.activatepersoncredential.ActivatePersonCredential;
import com.mozido.api.core.addmoneycontainer.AddMoneyContainer;
import com.mozido.api.core.addpersoncredential.AddPersonCredential;
import com.mozido.api.core.createemail.CreateEmail;
import com.mozido.api.core.createperson.CreatePerson;
import com.mozido.api.core.getaccountbalance.GetAccountBalance;
import com.mozido.api.core.getcompanyusers.GetCompanyUsers;
import com.mozido.api.core.getmoneycontainers.GetMoneyContainers;
import com.mozido.api.core.getperson.GetPerson;
import com.mozido.api.core.getroles.GetRoles;
import com.mozido.api.core.removemoneycontainer.RemoveMoneyContainer;
import com.mozido.api.core.signon.Signon;
import com.mozido.api.core.updatemoneycontainer.UpdateMoneyContainer;

/**
 * Facade for all moteaf web services
 *
 * @author Manusovich Alexander
 */
public class MoteafServices {
    private Signon signonService;
    private GetPerson getPersonService;
    private CreatePerson createPersonService;
    private AddPersonCredential addPersonCredentialService;
    private ActivatePersonCredential activatePersonCredentialService;
    private CreateEmail createEmailService;
    private GetMoneyContainers getMoneyContainersService;
    private GetAccountBalance getAccountBalanceService;
    private AddMoneyContainer addMoneyContainerService;
    private UpdateMoneyContainer updateMoneyContainerService;
    private RemoveMoneyContainer removeMoneyContainerService;
    
    //new services - Jul21/2014
    private GetCompanyUsers getCompanyUsersService;
    private GetRoles getRolesService;

    public Signon getSignonService() {
        return signonService;
    }

    public void setSignonService(Signon signonService) {
        this.signonService = signonService;
    }

    public GetPerson getGetPersonService() {
        return getPersonService;
    }

    public void setGetPersonService(GetPerson getPersonService) {
        this.getPersonService = getPersonService;
    }

    public CreatePerson getCreatePersonService() {
        return createPersonService;
    }

    public void setCreatePersonService(CreatePerson createPersonService) {
        this.createPersonService = createPersonService;
    }

    public AddPersonCredential getAddPersonCredentialService() {
        return addPersonCredentialService;
    }

    public void setAddPersonCredentialService(AddPersonCredential addPersonCredentialService) {
        this.addPersonCredentialService = addPersonCredentialService;
    }

    public ActivatePersonCredential getActivatePersonCredentialService() {
        return activatePersonCredentialService;
    }

    public void setActivatePersonCredentialService(ActivatePersonCredential activatePersonCredentialService) {
        this.activatePersonCredentialService = activatePersonCredentialService;
    }

    public CreateEmail getCreateEmailService() {
        return createEmailService;
    }

    public void setCreateEmailService(CreateEmail createEmailService) {
        this.createEmailService = createEmailService;
    }

    public GetMoneyContainers getGetMoneyContainersService() {
        return getMoneyContainersService;
    }

    public void setGetMoneyContainersService(GetMoneyContainers getMoneyContainersService) {
        this.getMoneyContainersService = getMoneyContainersService;
    }

    public GetAccountBalance getGetAccountBalanceService() {
        return getAccountBalanceService;
    }

    public void setGetAccountBalanceService(GetAccountBalance getAccountBalanceService) {
        this.getAccountBalanceService = getAccountBalanceService;
    }

    public AddMoneyContainer getAddMoneyContainerService() {
        return addMoneyContainerService;
    }

    public void setAddMoneyContainerService(AddMoneyContainer addMoneyContainerService) {
        this.addMoneyContainerService = addMoneyContainerService;
    }

    public UpdateMoneyContainer getUpdateMoneyContainerService() {
        return updateMoneyContainerService;
    }

    public void setUpdateMoneyContainerService(UpdateMoneyContainer updateMoneyContainerService) {
        this.updateMoneyContainerService = updateMoneyContainerService;
    }

    public RemoveMoneyContainer getRemoveMoneyContainerService() {
        return removeMoneyContainerService;
    }

    public void setRemoveMoneyContainerService(RemoveMoneyContainer removeMoneyContainerService) {
        this.removeMoneyContainerService = removeMoneyContainerService;
    }


	public GetCompanyUsers getGetCompanyUsersService() {
		return getCompanyUsersService;
	}

	public void setGetCompanyUsersService(GetCompanyUsers getCompanyUsersService) {
		this.getCompanyUsersService = getCompanyUsersService;
	}

	public GetRoles getGetRolesService() {
		return getRolesService;
	}

	public void setGetRolesService(GetRoles getRolesService) {
		this.getRolesService = getRolesService;
	}


}
