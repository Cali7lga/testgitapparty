package com.example.home.weddingapp.Others;

/**
 * Created by Home on 03/10/2016.
 */

public class FileInfo {

    private String Name;
    private String Mensagem;

    public FileInfo() {
    }

    public FileInfo(String Mensagem, String Nome) {
        this.Name = Name;
        this.Mensagem = Mensagem;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String Mensagem) {
        this.Mensagem = Mensagem;
    }

}
