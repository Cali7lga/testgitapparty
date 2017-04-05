package com.herasoft.due.Others;

/**
 * Created by Home on 03/10/2016.
 */

public class FileInfoMsg {

    private String nome;
    private String mensagem;

    public FileInfoMsg() {
    }

/**    public FileInfoMsg(String Mensagem, String Nome) {
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
