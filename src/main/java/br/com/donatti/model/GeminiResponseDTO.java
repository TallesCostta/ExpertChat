package br.com.donatti.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:00
 */
public class GeminiResponseDTO
{
    @SerializedName("response")
    private List<Response> lstResponse;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 15:48:47
     * @return the lstResponse
     */
    public List<Response> getLstResponse()
    {
        return lstResponse;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 15:48:47
     * @param lstResponse the lstResponse to set
     */
    public void setLstResponse(List<Response> lstResponse)
    {
        this.lstResponse = lstResponse;
    }
    
}
