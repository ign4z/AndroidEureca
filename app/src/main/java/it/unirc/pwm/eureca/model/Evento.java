package it.unirc.pwm.eureca.model;

import android.media.Image;
import android.widget.ImageView;

import java.util.Date;

/**
 * Created by ignaz on 08/02/2018.
 */

public class Evento {

    private int idEvento;
    private String nome;
    private Image locandina;
    private String descrizione;
    private String luogo;
    private Date dataEvento;

    public Evento() {
    }

    public int getIdEvento() {
        return idEvento;
    }
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public String getLuogo() {
        return luogo;
    }
    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }
    public Date getDataEvento() {
        return dataEvento;
    }
    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Image getLocandina() {
        return locandina;
    }

    public void setLocandina(Image locandina) {
        this.locandina = locandina;
    }
}
