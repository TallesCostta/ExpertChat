package br.com.donatti.model;

import java.util.Date;

import com.google.firebase.database.IgnoreExtraProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 18:55:52
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IgnoreExtraProperties
public class BtpResposta extends BtpPadrao
{
    private String resDscResposta;

    private Date resDatResposta;
    
}
