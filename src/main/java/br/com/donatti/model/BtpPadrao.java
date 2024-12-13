package br.com.donatti.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 22:43:34
 */
@Getter
@Setter
public abstract class BtpPadrao
{
    private String id;
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 22:48:27
     */
    public BtpPadrao() {
        if (id == null) {
            id = String.valueOf(UUID.randomUUID());
        }
    }
}
