package br.com.picpay.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADo("Recurso não encontrado", "recurso-nao-encontrado"),
    ERRO_NEGOCIO("Violação de regra de negócio", "erro-negocio"),
    ENTIDADE_DUPLICADA("Entidade duplicada", "entidade-duplicada"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "mensagem-incompreensivel"),
    PARAMETRO_INVALIDO("Parâmetro inválido", "parametro-invalido"),
    ERRO_DE_SISTEMA("Erro de sistema", "erro-de-sistema"),
    DADOS_INVALIDOS("Dados inválidos", "dados-invalidos"),
    SALDO_INSUFICIENTE("Saldo insuficiente", "saldo-insuficiente");


    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://picpay-desafio.com.br/" + path;
    }
}
