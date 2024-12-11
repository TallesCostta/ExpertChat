package br.com.donatti.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 14:29:11
 */
public class ResponseDTO
{
    @SerializedName("data")
    private DataDTO data;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 15:55:27
     * @return the data
     */
    public DataDTO getData()
    {
        return data;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 15:55:27
     * @param data the data to set
     */
    public void setData(DataDTO data)
    {
        this.data = data;
    }
    
}
