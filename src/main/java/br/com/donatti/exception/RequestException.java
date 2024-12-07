package br.com.donatti.exception;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:57:02
 */
public class RequestException extends RuntimeException
{

    private static final long serialVersionUID = 9093753963434999170L;

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:57:39
     *
     */
    public RequestException()
    {
        super();
    }

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:57:43
     *
     * @param message
     */
    public RequestException(final String message)
    {
        super(message);
    }
    
}