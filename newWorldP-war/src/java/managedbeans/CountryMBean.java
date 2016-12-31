/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.City;
import entities.Country;
import entities.Countrylanguage;
import entities.Users;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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
    private Country selectedCountry = null;
    private String keyWord = "";
    DecimalFormat df = new DecimalFormat("#,###.00");
    
    public CountryMBean() {
    }
    
    
    
    
    public List<Country>getCountries() {  
        return countryManager.getAllCountries();  
    } 
    
    public Country getCountryById(String code) {  
        return countryManager.getCountryById(code);    
    }

    
    
    public void setSelectedCountry(String code) {
        System.out.println("set country : "+code);
        this.selectedCountry = getCountryById(code);
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    
    

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    
    
    
    public List<Country>recherche() {
        FacesContext context = FacesContext.getCurrentInstance();
        Users currentUser = (Users)context.getExternalContext().getSessionMap().get("user");
        return countryManager.recherche(this.keyWord, currentUser); 

    } 
    
    
    public String countryToHtml (){
        
        if (this.selectedCountry == null) return "Veillez selectionner un pays de la liste ! ";
        FacesContext context = FacesContext.getCurrentInstance();
        Users currentUser = (Users)context.getExternalContext().getSessionMap().get("user");
        if(this.keyWord != ""){
            countryManager.registerResearch(keyWord, currentUser);
        }
        String toReturn = "<br/><div class='text-center'><span style=\"font-size:190px\" class=\"flag-icon flag-icon-"+selectedCountry.getFlagCode().toLowerCase()+"\"></span></div>";
        toReturn+="<div class='text-center'><strong style='font-size:35px;color:blue'>"+selectedCountry.getName()+" </strong></div>";
        toReturn+="<br/><label>Type de Gouvenement: </label> "+selectedCountry.getGovernmentForm();
        toReturn+="<br/><label>Président: </label> "+selectedCountry.getHeadOfState();
        toReturn+="<br/><label>Continent: </label> "+selectedCountry.getContinent();
        
        toReturn+="<br/><br/><label>Surface: </label> "+df.format(selectedCountry.getSurfaceArea())+" Km²";
        toReturn+="<br/><label>Population: </label> "+df.format(selectedCountry.getPopulation());    
        toReturn+="<br/><label>Moyenne de vie: </label> "+ df.format(selectedCountry.getLifeExpectancy())+" Ans";
        toReturn+="<br/><label>Produit national Brut: </label> "+ df.format(selectedCountry.getGnp());
        
        
        toReturn+="<br/><br/><label>Langues parlées: </label> ";
        toReturn+="<table class='table talbe-striped sortable table-hover table-responsive'>";
        toReturn+="<thead>" +
                        "<tr>" +
                            "<th>Langue</th>" +
                            "<th>Officielle ?</th>" +
                            "<th>Pourcentage</th>" +
                         "</tr>" +
                   "</thead>"+
                "<tbody>";
        for(Countrylanguage langue : selectedCountry.getCountrylanguageCollection()){
            toReturn+="<tr>";
            toReturn+="<td>"+langue.getCountrylanguagePK().getLanguage()+"</td>";
            if(langue.getIsOfficial()=='T')
                toReturn+="<td class='text-center' style='color:green'><i class=\"fa fa-check-circle\" aria-hidden=\"true\"></i></td>";
            else
                toReturn+="<td class='text-center'><i style='color:red' class=\"fa fa-times-circle\" aria-hidden=\"true\"></i></td>";
            toReturn+="<td class='text-center'>"+langue.getPercentage()+" % </td>";
            toReturn+="</tr>";
        }
        toReturn+="</tbody>" +"</table><br><br>";
        
        toReturn+="<label>Villes: </label>";
        toReturn+="<table class='table talbe-striped sortable table-hover table-responsive'>";
        toReturn+="<thead>" +
                        "<tr>" +
                            "<th>Ville</th>" +
                            "<th>Département</th>" +
                            "<th>Population</th>" +
                         "</tr>" +
                   "</thead>"+
                "<tbody>";
        for(City city : selectedCountry.getCities() ){
            toReturn+="<tr>";
            toReturn+="<td>"+city.getName()+"</td>";
            toReturn+="<td>"+city.getDistrict()+"</td>";            
            toReturn+="<td>"+df.format(city.getPopulation())+"</td>";
            toReturn+="</tr>";
        }
        toReturn+="</tbody>" +"</table><br><br>";
        
        return toReturn;
    }
}
