package br.com.donatti.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 17:32:48
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BtpUsuario extends BtpPadrao
{   
    private String usuDscNome;
    
    private String usuDscEmail;

    private String usuDscLogin;
    
    private String usuDscSenha;
    
    private String usuDscTelefone;
    
    private String usuDatNascimento;
    
    private Boolean usuFlgAceitePoliticaPrivacidade;

    private List<String> roles;
    
    private List<String> bots;
}
