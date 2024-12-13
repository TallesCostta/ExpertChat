package br.com.donatti.model;

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
public class BtpPerfilAcesso extends BtpPadrao
{   
    private String peaDscNome;
    
    private Boolean peaFlgAtivo;
}
