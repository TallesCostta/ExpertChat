package br.com.donatti.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:27:01
 */
public class Part
{
    @SerializedName("text")
    private String text;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:27:47
     * @return the text
     */
    public String getText()
    {
        return text;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:27:47
     * @param text the text to set
     */
    public void setText(String text)
    {
        this.text = text;
    }
    
}
