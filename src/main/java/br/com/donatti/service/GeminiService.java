package br.com.donatti.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.donatti.model.PromptRequestDTO;
import br.com.donatti.utils.CollectionUtil;
import br.com.donatti.utils.ConstantsUtils;
import br.com.donatti.utils.GeminiUtils;
import br.com.donatti.utils.StringUtil;
import lombok.Getter;

/**
 * 
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:47:00
 */
@Getter
@Service
public class GeminiService
{
    private List<String> lstHistorico = new ArrayList<>();

    /**
     * Envia uma requisição ao Gemini com o prompt contextualizado com base em um histórico.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:27:35
     * @param promptRequestDTO
     * @return resposta da Gemini
     * @throws Exception 
     */
    public String enviarRequisicao(final PromptRequestDTO promptRequestDTO) throws Exception
    {
        if (StringUtil.isBlank(ConstantsUtils.GEMINI_URL))
        {
            throw new IllegalArgumentException("Não foi possível obter a URL da Google Gemini!");
        }
        
        if (StringUtil.isBlank(ConstantsUtils.GEMINI_API_KEY))
        {
            throw new IllegalArgumentException("Não foi possível obter a cahev de autenticação da Google Gemini!");
        }
        
        // Contexto com base no histórico da conversa
        final String contexto = lstHistorico.stream().collect(Collectors.joining("\n"));
       
        var contextPrompt = new StringBuilder()
                .append(StringUtil.isNotBlank(contexto) ? contexto.concat(", ") : "")
                .append("Sempre que o usuário fizer perguntas que não estejam relacionadas a ")
                .append(promptRequestDTO.getCategoria()).append(" ou a sua especialidade ")
                .append(promptRequestDTO.getEspecialidade())
                .append(", responda de forma educada e clara, informando que você só pode fornecer informações sobre ")
                .append(promptRequestDTO.getCategoria()).append(". ")
                .append("A resposta deverá ser similar a esta: 'Desculpe, mas eu sou especialista em ")
                .append(promptRequestDTO.getCategoria()).append(" com enfâse em")
                .append(promptRequestDTO.getEspecialidade())
                .append(" e assuntos diretamente relacionados a elas. Caso tenha perguntas sobre esse tema, estou à disposição para ajudar!.'")
                .append("Quando a pergunta for relacionada a ").append(promptRequestDTO.getCategoria()).append(" ou ")
                .append(promptRequestDTO.getEspecialidade()).append(", retorne a resposta como texto simples.")
                .append("Pergunta: ").append(promptRequestDTO.getPrompt()).toString();

        atualizarHistoricoConversas(promptRequestDTO.getPrompt(), lstHistorico);
        
        var request = HttpRequest.newBuilder()
                .uri(URI.create(ConstantsUtils.GEMINI_URL + "?alt=sse&key=" + ConstantsUtils.GEMINI_API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(obterJsonRequest(contextPrompt)))
                .build();
        
        return GeminiUtils.processarResposta(HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()));
    }

    /**
     * Atualiza o histórico de conversas com o novo prompt.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:41:29
     * @param prompt
     * @param lstHistorico
     * @return
     */
    private String atualizarHistoricoConversas(final String prompt, List<String> lstHistorico)
    {
        if (CollectionUtil.isNotEmpty(lstHistorico) && ConstantsUtils.LIMITE_HISTORICO.equals(lstHistorico.size()))
        {
            lstHistorico.remove(0);
        }

        lstHistorico.add(prompt);

        return lstHistorico.stream().collect(Collectors.joining("\n"));
    }

    /**
     * Prepara o json com prompt e propriedades para requisição
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:30:41
     * @param promptCompleto
     * @return retorna a string do json com todas as propriedades preenchidas
     */
    private String obterJsonRequest(final String promptCompleto) throws Exception
    {
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> part = new HashMap<>();

        part.put("text", promptCompleto);
        content.put("parts", new Object[] { part });
        content.put("role", "user");

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("contents", new Object[] { content });

        return new ObjectMapper().writeValueAsString(requestBody);
    }
    
}
