/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Country;
import entities.Recherche;
import entities.Users;
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

    public Country getCountryById(String code) {
        return em.find(Country.class, code);
    }

    public List<Country> recherche(String keyWord, Users currentUser) {
        keyWord = keyWord.trim();
        if (keyWord == null || keyWord == "") {
            keyWord = "&&&sdddsz";
        }

        String queryString = "(SELECT * FROM Country c WHERE c.Name LIKE '%" + keyWord + "%')"
                + "UNION (SELECT * FROM Country c WHERE c.Continent LIKE '%" + keyWord + "%')"
                + "UNION (SELECT * FROM Country c WHERE c.HeadOFState LIKE '%" + keyWord + "%')"
                + "UNION (SELECT * FROM Country c1 WHERE c1.Code  IN "
                + "(SELECT ci.countryCode FROM city ci WHERE ci.name LIKE '%" + keyWord + "%'))";
        Query query = em.createNativeQuery(queryString, Country.class);
        return (List<Country>) query.getResultList();
    }

    public void registerResearch(String keyWord, Users currentUser) {
        Query query = em.createNativeQuery("select id from recherche where texte = '" + keyWord + "' and id_user ="+currentUser.getId());
        if (query.getResultList().isEmpty()) {
            Recherche recherche = new Recherche(keyWord, currentUser);
            try {
                em.persist(recherche);
                em.flush();
            } catch (Exception e) {

            }
        }
    }
    
    public List<Country> getTopTenGPN(){
        Query q = em.createNativeQuery("SELECT * FROM Country c ORDER BY gnp DESC limit 10",Country.class);
        return (List<Country>)q.getResultList();
    }
    public List<Country> getTopTenLife(){
        Query q = em.createNativeQuery("SELECT * FROM Country c ORDER BY lifeExpectancy DESC limit 10",Country.class);
        return (List<Country>)q.getResultList();
    }
    public Country update(Country country) {
        return em.merge(country);
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
