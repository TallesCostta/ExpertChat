package br.com.donatti.model;

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

    private BtpPrompt btpPompt;

    private BtpResposta btpResposta;

    private BtpChatEspecialista btpChatEspecialista;
    
}
