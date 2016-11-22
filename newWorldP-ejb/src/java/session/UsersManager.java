/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

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
public class UsersManager {

    @PersistenceContext(unitName = "newWorldP-ejbPU")
    private EntityManager em;
    

    public List<Users> getAllUsers() {
        Query query = em.createNamedQuery("Users.findAll");  
        return query.getResultList(); 
    }
    
    public Users getUserByEmail(String email){
        Query query = em.createNamedQuery("Users.findByEmail");
        query.setParameter("email", email);
        try{
            return (Users) query.getSingleResult();
        }catch(Exception e){
            return null;
        }
        
    }

    public Users update(Users user) {
        return em.merge(user);
    }

    public void persist(Object object) {
        em.persist(object);
    }

    
}
