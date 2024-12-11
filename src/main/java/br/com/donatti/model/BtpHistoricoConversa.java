package br.com.donatti.model;

import java.util.List;

import com.google.firebase.database.IgnoreExtraProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 18:50:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IgnoreExtraProperties
public class BtpHistoricoConversa extends BtpPadrao
{
    private List<BtpConversa> lstBtpConversa;

}
