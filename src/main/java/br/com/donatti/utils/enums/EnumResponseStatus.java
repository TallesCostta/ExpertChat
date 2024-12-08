package br.com.donatti.utils.enums;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 16:00:52
 */
public enum EnumResponseStatus
{
    /**
     * Código de status (100) indicando que o cliente pode continuar.
     */
    CONTINUE(100, "Continue"),

    /**
     * Código de status (101) indicando que o servidor está trocando de protocolos
     * conforme o cabeçalho Upgrade.
     */
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),

    /**
     * Código de status (200) indicando que a solicitação foi bem-sucedida
     * normalmente.
     */
    OK(200, "OK"),

    /**
     * Código de status (201) indicando que a solicitação foi bem-sucedida e criou
     * um novo recurso no servidor.
     */
    CREATED(201, "Created"),

    /**
     * Código de status (202) indicando que a solicitação foi aceita para
     * processamento, mas não foi completada.
     */
    ACCEPTED(202, "Accepted"),

    /**
     * Código de status (203) indicando que a meta-informação apresentada pelo
     * cliente não se originou do servidor.
     */
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),

    /**
     * Código de status (204) indicando que a solicitação foi bem-sucedida, mas não
     * há novas informações para retornar.
     */
    NO_CONTENT(204, "No Content"),

    /**
     * Código de status (205) indicando que o agente DEVE reiniciar a visualização
     * do documento que causou o envio da solicitação.
     */
    RESET_CONTENT(205, "Reset Content"),

    /**
     * Código de status (206) indicando que o servidor atendeu à solicitação parcial
     * de GET para o recurso.
     */
    PARTIAL_CONTENT(206, "Partial Content"),

    /**
     * Código de status (300) indicando que o recurso solicitado corresponde a
     * qualquer uma de um conjunto de representações, cada uma com sua própria
     * localização específica.
     */
    MULTIPLE_CHOICES(300, "Multiple Choices"),

    /**
     * Código de status (301) indicando que o recurso foi movido permanentemente
     * para um novo local.
     */
    MOVED_PERMANENTLY(301, "Moved Permanently"),

    /**
     * Código de status (302) indicando que o recurso foi temporariamente movido
     * para outro local.
     */
    MOVED_TEMPORARILY(302, "Moved Temporarily"),

    /**
     * Código de status (302) indicando que o recurso reside temporariamente sob um
     * URI diferente.
     */
    FOUND(302, "Found"),

    /**
     * Código de status (303) indicando que a resposta à solicitação pode ser
     * encontrada sob um URI diferente.
     */
    SEE_OTHER(303, "See Other"),

    /**
     * Código de status (304) indicando que o recurso não foi modificado desde a
     * última solicitação.
     */
    NOT_MODIFIED(304, "Not Modified"),

    /**
     * Código de status (305) indicando que o recurso solicitado DEVE ser acessado
     * através do proxy fornecido.
     */
    USE_PROXY(305, "Use Proxy"),

    /**
     * Código de status (307) indicando que o recurso solicitado reside
     * temporariamente sob um URI diferente.
     */
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),

    /**
     * Código de status (400) indicando que a solicitação enviada pelo cliente
     * estava incorreta.
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * Código de status (401) indicando que a solicitação requer autenticação HTTP.
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * Código de status (402) reservado para uso futuro.
     */
    PAYMENT_REQUIRED(402, "Payment Required"),

    /**
     * Código de status (403) indicando que o servidor se recusou a atender à
     * solicitação.
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * Código de status (404) indicando que o recurso solicitado não foi encontrado.
     */
    NOT_FOUND(404, "Not Found"),

    /**
     * Código de status (405) indicando que o método especificado não é permitido
     * para o recurso.
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /**
     * Código de status (406) indicando que o recurso identificado pela solicitação
     * não pode gerar entidades de resposta aceitáveis.
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),

    /**
     * Código de status (407) indicando que o cliente DEVE se autenticar com o
     * proxy.
     */
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),

    /**
     * Código de status (408) indicando que o cliente não produziu uma solicitação
     * dentro do tempo esperado.
     */
    REQUEST_TIMEOUT(408, "Request Timeout"),

    /**
     * Código de status (409) indicando que a solicitação não pôde ser completada
     * devido a um conflito.
     */
    CONFLICT(409, "Conflict"),

    /**
     * Código de status (410) indicando que o recurso não está mais disponível no
     * servidor.
     */
    GONE(410, "Gone"),

    /**
     * Código de status (411) indicando que a solicitação não pode ser atendida sem
     * um Content-Length definido.
     */
    LENGTH_REQUIRED(411, "Length Required"),

    /**
     * Código de status (412) indicando que a pré-condição dada em um ou mais campos
     * de cabeçalho foi falsa.
     */
    PRECONDITION_FAILED(412, "Precondition Failed"),

    /**
     * Código de status (413) indicando que o servidor está se recusando a processar
     * a solicitação devido ao tamanho.
     */
    REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),

    /**
     * Código de status (414) indicando que o Request-URI é maior do que o servidor
     * está disposto a interpretar.
     */
    REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),

    /**
     * Código de status (415) indicando que o servidor está se recusando a atender à
     * solicitação devido ao formato não suportado.
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    /**
     * Código de status (416) indicando que o servidor não pode atender ao intervalo
     * de bytes solicitado.
     */
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),

    /**
     * Código de status (417) indicando que o servidor não pôde atender à
     * expectativa dada no cabeçalho de solicitação.
     */
    EXPECTATION_FAILED(417, "Expectation Failed"),

    /**
     * Código de status (500) indicando um erro interno no servidor.
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /**
     * Código de status (501) indicando que o servidor não suporta a funcionalidade
     * necessária.
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),

    /**
     * Código de status (502) indicando que o servidor recebeu uma resposta inválida
     * de outro servidor.
     */
    BAD_GATEWAY(502, "Bad Gateway"),

    /**
     * Código de status (503) indicando que o servidor está temporariamente
     * sobrecarregado.
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    /**
     * Código de status (504) indicando que o servidor não recebeu uma resposta
     * oportuna do servidor upstream.
     */
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),

    /**
     * Código de status (505) indicando que o servidor não suporta a versão do
     * protocolo HTTP utilizada.
     */
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");

    private int codigoHttp;

    private String descricao;

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 16:07:15
     *
     * @param codigoHttp
     * @param descricao
     */
    private EnumResponseStatus(int codigoHttp, String descricao)
    {
        this.codigoHttp = codigoHttp;
        this.descricao = descricao;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 16:08:51
     * @return the codigoHttp
     */
    public int getCodigoHttp()
    {
        return codigoHttp;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 16:08:51
     * @param codigoHttp the codigoHttp to set
     */
    public void setCodigoHttp(int codigoHttp)
    {
        this.codigoHttp = codigoHttp;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 16:08:51
     * @return the descricao
     */
    public String getDescricao()
    {
        return descricao;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 08/12/2024 - 16:08:51
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
    
}
