package br.com.donatti.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.donatti.exception.RequestException;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 18:45:42
 */
public final class GeminiUtils
{
    
    private GeminiUtils() {}

    /**
     * Processa a resposta da requisição ao Gemini.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:45:52
     * @param response
     * @return Texto processado da resposta
     * @throws IOException
     */
    public static final String processarResposta(HttpResponse<String> response) throws IOException
    {
        if (response.statusCode() != ResponseStatus.OK)
        {
            throw new RequestException("Error: " + response.statusCode());
        }

        StringBuilder resposta = new StringBuilder();

        String line;

        try (var escrever = new BufferedReader(new StringReader(response.body())))
        {
            while ((line = escrever.readLine()) != null)
            {
                if (line.isEmpty())
                {
                    continue;
                }
                
                Matcher matcher = Pattern.compile(ConstantsUtils.PADRAO_RESPOSTA).matcher(line.substring(5));

                if (matcher.find())
                {
                    resposta.append(matcher.group(1));
                }
            }
        }

        return resposta.toString().trim().replace("\\n", "");
    }
}
