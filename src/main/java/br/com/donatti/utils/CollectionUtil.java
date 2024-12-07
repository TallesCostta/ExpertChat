package br.com.donatti.utils;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:32:01
 */
public final class CollectionUtil
{
    private CollectionUtil() {}

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:32:25
     * @param colecao
     * @return
     */
    public static boolean isNotEmpty(Collection<?> colecao)
    {
        return Objects.nonNull(colecao) && Boolean.FALSE.equals(colecao.isEmpty());
    }

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:32:28
     * @param colecao
     * @return
     */
    public static boolean isEmpty(Collection<?> colecao)
    {
        return Objects.isNull(colecao) || colecao.isEmpty();
    }
}