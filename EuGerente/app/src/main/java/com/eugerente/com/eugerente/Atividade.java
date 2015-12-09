package com.eugerente.com.eugerente;

/**
 * Created by Anderson on 05/12/2015.
 */
public class Atividade {
    private Long id;
    private String login;
    private String nome;
    private String descricao;
    private String observacao;
    private String status;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Atividade que retornou: ID"+getId()+" Nome: "+getNome()+" Descricao:"+getDescricao()+" Observacao:"+getObservacao();
    }
}
