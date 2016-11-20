/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Country;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author MacBook
 */
@Stateless
@LocalBean
public class CountryManager {

    @PersistenceContext(unitName = "newWorldP-ejbPU")
    private EntityManager em;
    
    
    
    public List<Country> getAllCountries() {
        Query query = em.createNamedQuery("Country.findAll");  
        return query.getResultList(); 
    }

    public Country update(Country country) {
        return em.merge(country); 
    }

    public void persist(Object object) {
        em.persist(object);
    }


}
