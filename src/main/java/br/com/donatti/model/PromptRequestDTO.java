package br.com.donatti.model;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:09:58
 */
public class PromptRequestDTO
{
    private String prompt;

    private String categoria;

    private String especialidade;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:21:04
     * @return the prompt
     */
    public String getPrompt()
    {
        return prompt;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:21:04
     * @param prompt the prompt to set
     */
    public void setPrompt(String prompt)
    {
        this.prompt = prompt;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:21:04
     * @return the categoria
     */
    public String getCategoria()
    {
        return categoria;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:21:04
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:21:04
     * @return the especialidade
     */
    public String getEspecialidade()
    {
        return especialidade;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:21:04
     * @param especialidade the especialidade to set
     */
    public void setEspecialidade(String especialidade)
    {
        this.especialidade = especialidade;
    }
    
}
