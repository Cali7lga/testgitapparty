package com.example.home.weddingapp.Others;

/**
 * Created by Home on 03/10/2016.
 */

public class FileInfo {

    private String nome;
    private String mensagem;

    public FileInfo() {
    }

/**    public FileInfo(String Mensagem, String Nome) {
        this.Name = Name;
        this.Mensagem = Mensagem;
    }
**/
    public String getNome() {
        return nome;
    }
    public String getMensagem() {
        return mensagem;
    }

    public void setName(String Name) {
        this.nome = Name;
    }
    public void setMensagem(String Mensagem) {
        this.mensagem = Mensagem;
    }

}
