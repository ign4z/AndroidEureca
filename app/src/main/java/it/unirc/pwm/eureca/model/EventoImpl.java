package it.unirc.pwm.eureca.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import it.unirc.pwm.eureca.util.Varie;

public class EventoImpl implements EventoDao{
    private static final String TAG="JSON Activity";

    @Override
    public Evento getEvento(String jsonString) {
      Evento ev = new Evento();
        try {
            JSONObject eventoJson = new JSONObject(jsonString).getJSONObject(EventoContract.evento);
            ev.setIdEvento(Integer.parseInt(eventoJson.getString(EventoContract.EventoObject.idEvento)));
            ev.setNome(eventoJson.getString(EventoContract.EventoObject.nome));
            ev.setDescrizione(eventoJson.getString(EventoContract.EventoObject.descrizione));
            ev.setLocandina(eventoJson.getString(EventoContract.EventoObject.locandina));
            ev.setLuogo(eventoJson.getString(EventoContract.EventoObject.luogo));
            ev.setDataEvento(Varie.stringToDate(eventoJson.getString(EventoContract.EventoObject.dataEvento)));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage()+e.getCause());
        }
        return ev;
    }
}
