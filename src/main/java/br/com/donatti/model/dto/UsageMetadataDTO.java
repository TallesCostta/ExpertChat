package br.com.donatti.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:28:14
 */
public class UsageMetadataDTO
{
    @SerializedName("promptTokenCount")
    private int promptTokenCount;
    
    @SerializedName("totalTokenCount")
    private int totalTokenCount;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:28:35
     * @return the promptTokenCount
     */
    public int getPromptTokenCount()
    {
        return promptTokenCount;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:28:35
     * @param promptTokenCount the promptTokenCount to set
     */
    public void setPromptTokenCount(int promptTokenCount)
    {
        this.promptTokenCount = promptTokenCount;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:28:35
     * @return the totalTokenCount
     */
    public int getTotalTokenCount()
    {
        return totalTokenCount;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:28:35
     * @param totalTokenCount the totalTokenCount to set
     */
    public void setTotalTokenCount(int totalTokenCount)
    {
        this.totalTokenCount = totalTokenCount;
    }
    
}
