package com.mozido.channels.nextweb.model;

import java.util.List;

import com.mozido.api.core.personshared.PersonRoles;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * User Container
 *
 * @Author Alex Manusovich
 */
@ApiModel(description = "Bean with information about user")
public class UserDTO extends BaseDTO {
    private SessionDTO session;
    private String id;
    private String firstName;
    private String lastName;
    private String tenantName;
    private String personType;
    private String timezone;
    private String timezoneOffset;
    

	protected List<String> roles;
    protected List<String> companyUsers;

    public UserDTO() {
    }

    @ApiModelProperty(notes = "unique identifier")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "platform session container")
    public SessionDTO getSession() {
        return session;
    }

    public void setSession(final SessionDTO session) {
        this.session = session;
    }

    @ApiModelProperty(notes = "user's first name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @ApiModelProperty(notes = "user's last name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFullNameString() {
        return getFirstName() + " " + getLastName();
    }

    @ApiModelProperty(notes = "user's tenant name")
    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @ApiModelProperty(notes = "user's type")
    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    @ApiModelProperty(notes = "user's timezone")
    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @ApiModelProperty(notes = "user's timezone offset")
    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(String timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    @ApiModelProperty(notes = "user's roles")
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    @ApiModelProperty(notes = "users's companies")
	public List<String> getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(List<String> companyUsers) {
		this.companyUsers = companyUsers;
	}

    @Override
	public String toString() {
		return "UserDTO [session=" + session + ", id=" + id + ", firstName="
				+ firstName + ", lastName=" + lastName + ", tenantName="
				+ tenantName + ", personType=" + personType + ", timezone="
				+ timezone + ", timezoneOffset=" + timezoneOffset + ", roles="
				+ roles + ", companyUsers=" + companyUsers + "]";
	}

	
}
