package br.com.donatti.model;

import java.util.List;

import com.google.firebase.database.IgnoreExtraProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 18:52:58
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IgnoreExtraProperties
public class BtpConversa extends BtpPadrao
{   
    private String conDscTitulo;
    
    private String conDatConversa;

    private String conFlgArquivada;

    private Boolean conFldArquivada;
    
    private List<String> lstPompt;
    
    private List<String> lstResposta;
    
    private String idBotEspecialista;  
}
