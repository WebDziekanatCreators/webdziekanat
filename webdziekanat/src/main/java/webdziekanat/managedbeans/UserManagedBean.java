package webdziekanat.managedbeans;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import webdziekanat.Resources.Messages;
import webdziekanat.finders.DatabaseFinder;
import webdziekanat.interfaces.IUserDAO;
import webdziekanat.model.User;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Created by Jakub on 06.02.2015.
 */
@Component("userMB")
@Scope("session")
public class UserManagedBean implements Serializable{

    private static final Logger logger = LogManager.getLogger(UserManagedBean.class);

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    DatabaseFinder finder;

    private User user = new User();

    private boolean isLoggedIn;

    @PostConstruct
    public void init() {
        isLoggedIn = false;
    }

    public String login(){
        User foundUser = userDAO.getByUsernameAndPassword(user.getUsername(),user.getPassword());
        if(foundUser == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", Messages.wrongCredentials));
            logger.error("No matching user found with given credentials: username - " + user.getUsername() + " and password - " + user.getPassword());
        }
        else {
            user = foundUser;
            isLoggedIn = true;
        }

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String logout(){
        user = new User();
        isLoggedIn = false;
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
