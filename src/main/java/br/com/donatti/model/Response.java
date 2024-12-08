package br.com.donatti.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 14:29:11
 */
public class Response
{
    @SerializedName("data")
    private Data data;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 15:55:27
     * @return the data
     */
    public Data getData()
    {
        return data;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 15:55:27
     * @param data the data to set
     */
    public void setData(Data data)
    {
        this.data = data;
    }
    
}
