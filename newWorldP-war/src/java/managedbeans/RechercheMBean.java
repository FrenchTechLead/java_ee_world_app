package managedbeans;

import entities.Recherche;
import entities.Users;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Cacheable;
import session.RechercheManager;

/**
 *
 * @author MacBook
 */
@ManagedBean(name = "rechercheMBean")
@RequestScoped
@Cacheable(false)
public class RechercheMBean implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @EJB
    private RechercheManager rechercheManager ;
    
    public RechercheMBean() {
    }
    
    public List<Recherche> getRecherchesByUser(){
        FacesContext context = FacesContext.getCurrentInstance();
        Users currentUser = (Users)context.getExternalContext().getSessionMap().get("user");
        return rechercheManager.getRecherchesByUser(currentUser);
    }
    
    public void deleteRecherche(int id){
        rechercheManager.deleteRecherche(id);
        getRecherchesByUser();
    }
    
}
