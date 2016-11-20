/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Country;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import session.CountryManager;

/**
 *
 * @author MacBook
 */
@Named(value = "countryMBean")
@ViewScoped
public class CountryMBean implements Serializable{

    @EJB
    private CountryManager countryManager;

    public CountryMBean() {
    }
    
    
    public List<Country>getCountries() {  
        return countryManager.getAllCountries();  
    }  
    
    public String showDetails(int countryId) {  
        return "CountryDetails?idCountry=" + countryId;    
    }
}
