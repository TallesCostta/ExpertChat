package br.com.donatti.model.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:26:34
 */
public class ContentDTO
{
    @SerializedName("parts")
    private List<PartDTO> parts;
    
    @SerializedName("role")
    private String role;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:26:50
     * @return the parts
     */
    public List<PartDTO> getParts()
    {
        return parts;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:26:50
     * @param parts the parts to set
     */
    public void setParts(List<PartDTO> parts)
    {
        this.parts = parts;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:26:50
     * @return the role
     */
    public String getRole()
    {
        return role;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:26:50
     * @param role the role to set
     */
    public void setRole(String role)
    {
        this.role = role;
    }
    
}
