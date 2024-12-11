package br.com.donatti.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 17:47:11
 */
@Getter
@Setter
public abstract class BtpPadrao
{
    private String path;

    private String id;

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 17:54:08
     *
     */
    public BtpPadrao()
    {
        this.path = this.getClass().getSimpleName();
    }

}
