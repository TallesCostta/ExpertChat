package br.com.donatti.utils;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 18:50:20
 */
public interface ResponseStatus
{
    /**
     * Código de status (100) indicando que o cliente pode continuar.
     */
    int CONTINUE = 100;

    /**
     * Código de status (101) indicando que o servidor está trocando de protocolos conforme o cabeçalho Upgrade.
     */
    int SWITCHING_PROTOCOLS = 101;

    /**
     * Código de status (200) indicando que a solicitação foi bem-sucedida normalmente.
     */
    int OK = 200;

    /**
     * Código de status (201) indicando que a solicitação foi bem-sucedida e criou um novo recurso no servidor.
     */
    int CREATED = 201;

    /**
     * Código de status (202) indicando que a solicitação foi aceita para processamento, mas não foi completada.
     */
    int ACCEPTED = 202;

    /**
     * Código de status (203) indicando que a meta-informação apresentada pelo cliente não se originou do servidor.
     */
    int NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * Código de status (204) indicando que a solicitação foi bem-sucedida, mas não há novas informações para retornar.
     */
    int NO_CONTENT = 204;

    /**
     * Código de status (205) indicando que o agente <em>DEVE</em> reiniciar a visualização do documento que causou o envio da solicitação.
     */
    int RESET_CONTENT = 205;

    /**
     * Código de status (206) indicando que o servidor atendeu à solicitação parcial de GET para o recurso.
     */
    int PARTIAL_CONTENT = 206;

    /**
     * Código de status (300) indicando que o recurso solicitado corresponde a qualquer uma de um conjunto de representações,
     * cada uma com sua própria localização específica.
     */
    int MULTIPLE_CHOICES = 300;

    /**
     * Código de status (301) indicando que o recurso foi movido permanentemente para um novo local, e que referências futuras
     * devem usar um novo URI com suas solicitações.
     */
    int MOVED_PERMANENTLY = 301;

    /**
     * Código de status (302) indicando que o recurso foi temporariamente movido para outro local, mas referências futuras
     * ainda devem usar o URI original para acessar o recurso. Esta definição é mantida para compatibilidade retroativa.
     * FOUND é agora a definição preferida.
     */
    int MOVED_TEMPORARILY = 302;

    /**
     * Código de status (302) indicando que o recurso reside temporariamente sob um URI diferente. Como o redirecionamento
     * pode ser alterado ocasionalmente, o cliente deve continuar a usar o URI de solicitação para futuras solicitações.
     * (HTTP/1.1) Para representar o código de status (302), é recomendado usar essa variável.
     */
    int FOUND = 302;

    /**
     * Código de status (303) indicando que a resposta à solicitação pode ser encontrada sob um URI diferente.
     */
    int SEE_OTHER = 303;

    /**
     * Código de status (304) indicando que uma operação de GET condicional verificou que o recurso estava disponível e não
     * foi modificado.
     */
    int NOT_MODIFIED = 304;

    /**
     * Código de status (305) indicando que o recurso solicitado <em>DEVE</em> ser acessado através do proxy fornecido pelo
     * campo <code><em>Location</em></code>.
     */
    int USE_PROXY = 305;

    /**
     * Código de status (307) indicando que o recurso solicitado reside temporariamente sob um URI diferente. O URI temporário
     * <em>DEVE</em> ser fornecido pelo campo <code><em>Location</em></code> na resposta.
     */
    int TEMPORARY_REDIRECT = 307;

    /**
     * Código de status (400) indicando que a solicitação enviada pelo cliente estava sintaticamente incorreta.
     */
    int BAD_REQUEST = 400;

    /**
     * Código de status (401) indicando que a solicitação requer autenticação HTTP.
     */
    int UNAUTHORIZED = 401;

    /**
     * Código de status (402) reservado para uso futuro.
     */
    int PAYMENT_REQUIRED = 402;

    /**
     * Código de status (403) indicando que o servidor entendeu a solicitação, mas se recusou a atendê-la.
     */
    int FORBIDDEN = 403;

    /**
     * Código de status (404) indicando que o recurso solicitado não está disponível.
     */
    int NOT_FOUND = 404;

    /**
     * Código de status (405) indicando que o método especificado na <code><em>Request-Line</em></code> não é permitido
     * para o recurso identificado pelo <code><em>Request-URI</em></code>.
     */
    int METHOD_NOT_ALLOWED = 405;

    /**
     * Código de status (406) indicando que o recurso identificado pela solicitação só pode gerar entidades de resposta
     * cujas características de conteúdo não são aceitáveis de acordo com os cabeçalhos de aceitação enviados na solicitação.
     */
    int NOT_ACCEPTABLE = 406;

    /**
     * Código de status (407) indicando que o cliente <em>DEVE</em> primeiro se autenticar com o proxy.
     */
    int PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * Código de status (408) indicando que o cliente não produziu uma solicitação dentro do tempo que o servidor estava
     * preparado para esperar.
     */
    int REQUEST_TIMEOUT = 408;

    /**
     * Código de status (409) indicando que a solicitação não pôde ser completada devido a um conflito com o estado atual
     * do recurso.
     */
    int CONFLICT = 409;

    /**
     * Código de status (410) indicando que o recurso não está mais disponível no servidor e nenhum endereço de encaminhamento
     * é conhecido. Esta condição <em>DEVE</em> ser considerada permanente.
     */
    int GONE = 410;

    /**
     * Código de status (411) indicando que a solicitação não pode ser atendida sem um <code><em>Content-Length</em></code>
     * definido.
     */
    int LENGTH_REQUIRED = 411;

    /**
     * Código de status (412) indicando que a pré-condição dada em um ou mais dos campos de cabeçalho da solicitação
     * avaliou-se como falsa quando testada no servidor.
     */
    int PRECONDITION_FAILED = 412;

    /**
     * Código de status (413) indicando que o servidor está se recusando a processar a solicitação porque a entidade da
     * solicitação é maior do que o servidor está disposto ou é capaz de processar.
     */
    int REQUEST_ENTITY_TOO_LARGE = 413;

    /**
     * Código de status (414) indicando que o servidor está se recusando a atender à solicitação porque o
     * <code><em>Request-URI</em></code> é maior do que o servidor está disposto a interpretar.
     */
    int REQUEST_URI_TOO_LONG = 414;

    /**
     * Código de status (415) indicando que o servidor está se recusando a atender à solicitação porque a entidade da
     * solicitação está em um formato não suportado pelo recurso solicitado para o método solicitado.
     */
    int UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * Código de status (416) indicando que o servidor não pode atender ao intervalo de bytes solicitado.
     */
    int REQUESTED_RANGE_NOT_SATISFIABLE = 416;

    /**
     * Código de status (417) indicando que o servidor não pôde atender à expectativa dada no cabeçalho de solicitação Expect.
     */
    int EXPECTATION_FAILED = 417;

    /**
     * Código de status (500) indicando um erro dentro do servidor HTTP que impediu a execução da solicitação.
     */
    int INTERNAL_SERVER_ERROR = 500;

    /**
     * Código de status (501) indicando que o servidor HTTP não suporta a funcionalidade necessária para atender à solicitação.
     */
    int NOT_IMPLEMENTED = 501;

    /**
     * Código de status (502) indicando que o servidor HTTP recebeu uma resposta inválida de um servidor que consultou
     * ao atuar como um proxy ou gateway.
     */
    int BAD_GATEWAY = 502;

    /**
     * Código de status (503) indicando que o servidor HTTP está temporariamente sobrecarregado e não pode lidar com a
     * solicitação.
     */
    int SERVICE_UNAVAILABLE = 503;

    /**
     * Código de status (504) indicando que o servidor não recebeu uma resposta oportuna do servidor upstream enquanto
     * atuava como gateway ou proxy.
     */
    int GATEWAY_TIMEOUT = 504;

    /**
     * Código de status (505) indicando que o servidor não suporta ou se recusa a suportar a versão do protocolo HTTP
     * utilizada na mensagem de solicitação.
     */
    int HTTP_VERSION_NOT_SUPPORTED = 505;

}
