package br.com.donatti.utils;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:51:14
 */
public interface LoggerUtil

{

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:51:28
     * @param e
     * @return
     */
    public static String getLinhaSaidaLogException(Exception e)
    {
        StackTraceElement linha = e.getStackTrace()[0];

        return new StringBuilder()
                .append("ERROR : ")
                .append(linha.getClassName())
                .append(" Metodo ")
                .append(linha.getMethodName())
                .append(" Linha - ")
                .append(linha.getLineNumber())
                .toString();
    }

}
