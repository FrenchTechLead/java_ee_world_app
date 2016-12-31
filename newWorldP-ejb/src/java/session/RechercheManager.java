package session;

import entities.Recherche;
import entities.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author MacBook
 */
@Stateless
@LocalBean
public class RechercheManager {

    @PersistenceContext(unitName = "newWorldP-ejbPU")
    private EntityManager em;

    public List<Recherche> getAllRecherches() {
        return em.createNamedQuery("Recherche.findAll").getResultList();
        
    }
    
    public Recherche getRechercheById(int id){
        return em.find(Recherche.class, id);
    }
    
    public void deleteRecherche(int id){
        Recherche re = em.find(Recherche.class, id);
        em.remove(re);
        em.flush();
    }
    
    public List<Recherche> getRecherchesByUser(Users u){
        Users user = em.find(Users.class, u.getId());
        return user.getRecherches();
    }
    
    public Recherche update(Recherche recherche) {
        return em.merge(recherche);
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
