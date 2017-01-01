package entities;

import entities.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-01T11:49:38")
@StaticMetamodel(Recherche.class)
public class Recherche_ { 

    public static volatile SingularAttribute<Recherche, Date> dateTime;
    public static volatile SingularAttribute<Recherche, String> texte;
    public static volatile SingularAttribute<Recherche, Integer> id;
    public static volatile SingularAttribute<Recherche, Users> user;

}