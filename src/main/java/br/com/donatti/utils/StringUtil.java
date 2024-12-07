package br.com.donatti.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:48:17
 */
public final class StringUtil
{
    private StringUtil () {}
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:48:48
     * @param txt
     * @return
     */
    public static final boolean isBlank(final String txt)
    {
        return StringUtils.isBlank(txt);
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:48:52
     * @param txt
     * @return
     */
    public static final boolean isNotBlank(final String txt)
    {
        return StringUtils.isNotBlank(txt);
    }
}

