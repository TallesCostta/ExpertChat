package br.com.donatti.utils;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import br.com.donatti.enums.EnumResponseStatus;
import br.com.donatti.exception.RequestException;
import br.com.donatti.model.dto.GeminiResponseDTO;

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
    public static String processarResposta(HttpResponse<String> response) throws IOException
    {
        if (response.statusCode() != EnumResponseStatus.OK.getCodigoHttp())
        {
            List<EnumResponseStatus> lstEnumResponseStatus = Arrays.asList(EnumResponseStatus.values());
            
            final String enumResponseStatus = lstEnumResponseStatus.stream()
                    .filter(status -> status.getCodigoHttp() == response.statusCode())
                    .map(EnumResponseStatus::getDescricao)
                    .findFirst()
                    .orElse("Erro");
            
            throw new RequestException(enumResponseStatus + ": " + response.statusCode());
        }
        
        StringBuilder jsonResponse = new StringBuilder();

        new Gson().fromJson(formatarJson(response), GeminiResponseDTO.class).getLstResponse().stream()
                .flatMap(r -> r.getData().getCandidates().stream())
                .flatMap(candidate -> candidate.getContent().getParts().stream()).map(part -> part.getText())
                .forEach(jsonResponse::append);
 
        return jsonResponse.toString();
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 11:36:45
     * @param response
     * @return
     */
    private static String formatarJson(final HttpResponse<String> response)
    {
        String linhasFormatadas = Arrays.stream(response.body().trim().split("\\n"))
                .filter(StringUtil::isNotBlank)
                .map(line -> "{\n" + line.trim() + "\n}")
                .collect(Collectors.joining(",\n"));

        return String.format("{\n\"response\": [\n%s\n]\n}", linhasFormatadas).replace("data:", "\"data\":");
    }
    
}
