package br.com.donatti.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 13:52:17
 */
public class Data
{
    @SerializedName("candidates")
    private List<Candidate> candidates;

    @SerializedName("usageMetadata")
    private UsageMetadata usageMetadata;

    @SerializedName("modelVersion")
    private String modelVersion;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:30
     * @return the candidates
     */
    public List<Candidate> getCandidates()
    {
        return candidates;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:30
     * @param candidates the candidates to set
     */
    public void setCandidates(List<Candidate> candidates)
    {
        this.candidates = candidates;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:30
     * @return the usageMetadata
     */
    public UsageMetadata getUsageMetadata()
    {
        return usageMetadata;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:30
     * @param usageMetadata the usageMetadata to set
     */
    public void setUsageMetadata(UsageMetadata usageMetadata)
    {
        this.usageMetadata = usageMetadata;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:30
     * @return the modelVersion
     */
    public String getModelVersion()
    {
        return modelVersion;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 10:25:30
     * @param modelVersion the modelVersion to set
     */
    public void setModelVersion(String modelVersion)
    {
        this.modelVersion = modelVersion;
    }

}
