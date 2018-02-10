package it.unirc.pwm.eureca.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ignaz on 08/02/2018.
 */

public class Evento implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Evento> CREATOR = new Parcelable.Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };
    private int idEvento;
    private String nome;
    private String locandina;
    private String descrizione;
    private String luogo;
    private Date dataEvento;

    public Evento() {
    }

    protected Evento(Parcel in) {
        idEvento = in.readInt();
        nome = in.readString();
        locandina = in.readString();
        descrizione = in.readString();
        luogo = in.readString();
        long tmpDataEvento = in.readLong();
        dataEvento = tmpDataEvento != -1 ? new Date(tmpDataEvento) : null;
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

    public String getLocandina() {
        return locandina;
    }

    public void setLocandina(String locandina) {
        this.locandina = locandina;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idEvento);
        dest.writeString(nome);
        dest.writeString(locandina);
        dest.writeString(descrizione);
        dest.writeString(luogo);
        dest.writeLong(dataEvento != null ? dataEvento.getTime() : -1L);
    }
}
