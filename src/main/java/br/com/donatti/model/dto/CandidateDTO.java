package br.com.donatti.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:52
 */
public class CandidateDTO
{
    @SerializedName("content")
    private ContentDTO content;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:26:15
     * @return the content
     */
    public ContentDTO getContent()
    {
        return content;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:26:15
     * @param content the content to set
     */
    public void setContent(ContentDTO content)
    {
        this.content = content;
    }
    
}
