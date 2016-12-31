/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Recherche;
import entities.Users;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.UsersManager;

/**
 *
 * @author MacBook
 */
@ManagedBean(name="userBean")
@RequestScoped
public  class UsersMBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Users user ;
    @EJB
    private UsersManager usersManager;
    
    public UsersMBean() {
        user = new Users();
    }
    
    public void createUser(){
        Users us = usersManager.getUserByEmail(user.getEmail());
        if(us == null){
            usersManager.persist(user);
            FacesMessage message = new FacesMessage( "Succès de l'inscription !" );
            FacesContext.getCurrentInstance().addMessage( null, message );
        }else{
            FacesMessage message = new FacesMessage( "Un utilisateur avec la même adresse email existe déjà !" );
            FacesContext.getCurrentInstance().addMessage( null, message );
        }
        
        
    }
    
    public String login(){
        FacesContext context = FacesContext.getCurrentInstance();
        Users us = usersManager.getUserByEmail(user.getEmail().toLowerCase());
        if(us == null){
            FacesMessage message = new FacesMessage( "Aucun utilisateur trouvé avec cette adresse mail !" );
            FacesContext.getCurrentInstance().addMessage( null, message );
            return null;
        }

        if(user.getPassword().equals(us.getPassword())){
            context.getExternalContext().getSessionMap().put("user", us);
            return "secure/userhome?faces-redirect=true";
            
        }else{
            FacesMessage message = new FacesMessage( "Mauvais mot de passe !" );
            FacesContext.getCurrentInstance().addMessage( null, message );
            return null;
        }

    }
    
   public List<Recherche> getResearchByUser(){
       FacesContext context = FacesContext.getCurrentInstance();
        Users currentUser = (Users)context.getExternalContext().getSessionMap().get("user");
        List<Recherche> recherches = null;
        recherches = usersManager.getRechercheByUser(currentUser);
        return recherches;
   }
   
   public String deleteResearch(int id){
       usersManager.deleteResearch(id);
       getResearchByUser();
       return null;
   }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }
    
    
    public List<Users>getUsers() {  
        return usersManager.getAllUsers();
    } 
    
    public Users getUtilisateur() {
        return user;
    }
    
    public String showDetails(int userId) {  
        return "UserDetails?idUser=" + userId;    }
}
