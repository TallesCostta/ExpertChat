package br.com.donatti.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:52
 */
public class Candidate
{
    @SerializedName("content")
    private Content content;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:26:15
     * @return the content
     */
    public Content getContent()
    {
        return content;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:26:15
     * @param content the content to set
     */
    public void setContent(Content content)
    {
        this.content = content;
    }
    
}
