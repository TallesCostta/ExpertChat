package br.com.donatti.model;

import com.google.firebase.database.IgnoreExtraProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 09/12/2024 - 23:39:19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IgnoreExtraProperties
public class BtpPerfilAcesso extends BtpPadrao
{
    private Long peaCodPerfilAcesso;
    
    private String peaDscNome;
    
    private Boolean peaFlgAtivo;
}
