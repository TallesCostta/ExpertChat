package br.com.donatti.model;

import java.util.Date;

import com.google.firebase.database.IgnoreExtraProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 18:55:31
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IgnoreExtraProperties
public class BtpPrompt extends BtpPadrao
{
    private String proDscPrompt;

    private Date proDatPrompt;
    
    private String idUsuario;
}
