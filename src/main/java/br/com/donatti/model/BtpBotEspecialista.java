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
public class BtpBotEspecialista extends BtpPadrao
{
    private String boeDscNome;

    private String boeDscCategoria;

    private String boeDscEspecialidade;
    
    private String botDatCadastro;
    
    private String idUsuario;
}
