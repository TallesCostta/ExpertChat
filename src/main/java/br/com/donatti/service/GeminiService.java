package br.com.donatti.service;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.donatti.model.dto.PromptRequestDTO;
import br.com.donatti.utils.CollectionUtil;
import br.com.donatti.utils.ConstantsUtils;
import br.com.donatti.utils.GeminiUtils;
import br.com.donatti.utils.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

/**
 * 
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:47:00
 */
@Getter
@Service
public class GeminiService
{
    private String GEMINI_URL;
    
    private String GEMINI_API_KEY;
    
    private List<String> lstHistorico = new ArrayList<>();
    
    @PostConstruct
    void inicializarCredenciaisGemini()
    {
        try
        {
            Properties properties = new Properties();
            
            properties.load(new FileInputStream("gemini.properties"));
            
            this.GEMINI_URL = properties.getProperty("GEMINI_URL");
            this.GEMINI_API_KEY = properties.getProperty("GEMINI_API_KEY");
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Ocorreu um erro ao carregar as credenciais da Google Gemini!");
        }
    }

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
        if (StringUtil.isBlank(this.GEMINI_URL))
        {
            throw new IllegalArgumentException("Não foi possível obter a URL da Google Gemini!");
        }
        
        if (StringUtil.isBlank(this.GEMINI_API_KEY))
        {
            throw new IllegalArgumentException("Não foi possível obter a cahev de autenticação da Google Gemini!");
        }
        
        // Contexto com base no histórico da conversa
        final String contexto = lstHistorico.stream().collect(Collectors.joining("\n"));
        
        var contextPrompt = new StringBuilder()
                .append(StringUtil.isNotBlank(contexto) ? "Esse é o histórico de conversas nesse chat, pode ser útil para obter o contexto para melhor resposta: ".concat(contexto.concat(", "))  : "")
                .append("Prompt Dinâmico para Especialistas:")
                .append("Você é um assistente virtual especializado em ")
                .append(promptRequestDTO.getCategoria())
                .append(" com ênfase em ")
                .append(promptRequestDTO.getEspecialidade())
                .append(". Suas respostas devem ser claras, assertivas e estruturadas de forma amigável e informativa, sempre relacionadas ao tema principal.")
                .append("Instruções:")
                .append("Estilo Conversacional: Responda de forma natural, amigável e confiante, como um especialista na área conversaria com o usuário.")
                .append("Uso de Markdown: Utilize formatação em Markdown quando necessário, como em listas, tabelas ou exemplos práticos.")
                .append("Foco no Tema: Todas as respostas devem se concentrar exclusivamente na categoria ")
                .append(promptRequestDTO.getCategoria())
                .append(" e na especialidade ")
                .append(promptRequestDTO.getEspecialidade())
                .append("Regras:")
                .append("Perguntas Relacionadas à ")
                .append(promptRequestDTO.getCategoria())
                .append(" e ")
                .append(promptRequestDTO.getEspecialidade())
                .append(":")
                .append("Responda de forma detalhada e assertiva, mantendo o foco no tema.")
                .append("Se aplicável, inclua orientações práticas, exemplos ou dicas estruturadas.")
                .append("Perguntas Fora do Escopo (não relacionadas):")
                .append("Responda educadamente, informando sua limitação de especialidade.")
                .append("Modelo de resposta para perguntas fora do escopo (esse é apenas um modelo, você deve gerar a sua resposta):")
                .append("Desculpe, mas eu sou especialista em ")
                .append(promptRequestDTO.getCategoria())
                .append("com ênfase em ")
                .append(promptRequestDTO.getEspecialidade()) 
                .append(" e só posso responder questões diretamente relacionadas a esses temas. Caso tenha dúvidas sobre isso, ficarei feliz em ajudar!")
                .append("Exemplo de Pergunta e Resposta:")
                .append("Pergunta 1: (Relativa ao tema)")
                .append("Qual o melhor exercício para definir o abdômen?")
                .append("Resposta:")
                .append("Para definir o abdômen, combine exercícios específicos com estratégias para reduzir a gordura corporal. Aqui está um exemplo:")
                .append("- Prancha abdominal: 3 séries de 30-60 segundos.")
                .append("- Abdominal bicicleta: 3 séries de 15-20 repetições.")
                .append("- Levantamento de pernas: 3 séries de 10-15 repetições.")
                .append("Lembre-se de que a dieta desempenha um papel crucial para revelar os músculos abdominais.")
                .append("Pergunta 2: (Fora do tema)")
                .append("Qual é o melhor framework para desenvolvimento front-end?")
                .append("Resposta:")
                .append("Desculpe, mas eu sou especialista em ")
                .append(promptRequestDTO.getCategoria())
                .append("com ênfase em ")
                .append(promptRequestDTO.getEspecialidade())
                .append("e só posso responder questões diretamente relacionadas a esses temas.")
                .append("Pergunta: ")
                .append(promptRequestDTO.getPrompt())
                .toString();

        atualizarHistoricoConversas(promptRequestDTO.getPrompt(), lstHistorico);
        
        var request = HttpRequest.newBuilder()
                .uri(URI.create(this.GEMINI_URL + "?alt=sse&key=" + this.GEMINI_API_KEY))
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
