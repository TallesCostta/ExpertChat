package br.com.donatti.model;

import com.google.firebase.database.IgnoreExtraProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 18:47:49
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IgnoreExtraProperties
public class BtpChatEspecialista extends BtpPadrao
{
    private String cheDscNome;

    private String cheDscCategoria;

    private String cheDscEspecialidade;
    
    private BtpUsuario btpUsuario;
    
}
