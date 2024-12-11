package br.com.donatti.firebase.manager;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 23:07:36
 */
@IgnoreExtraProperties
public abstract class FirebasePathManager
{
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 23:30:40
     * @param object
     * @return
     */
    public static String gerarPathIdentificavel(Object object)
    {
        if (object == null)
        {
            throw new IllegalArgumentException("Entidade não pode ser nula!");
        }
        
        return object.getClass().getSimpleName().concat("/").concat(new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date()));
    }

}
