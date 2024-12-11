package br.com.donatti.model.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:00
 */
public class GeminiResponseDTO
{
    @SerializedName("response")
    private List<ResponseDTO> lstResponse;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 15:48:47
     * @return the lstResponse
     */
    public List<ResponseDTO> getLstResponse()
    {
        return lstResponse;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 15:48:47
     * @param lstResponse the lstResponse to set
     */
    public void setLstResponse(List<ResponseDTO> lstResponse)
    {
        this.lstResponse = lstResponse;
    }
    
}
