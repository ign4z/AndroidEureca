package it.unirc.pwm.eureca.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
                //ev.setDataEvento(jsonObject.getString(EventoContract.EventoObject.descrizione));
            ev.setLuogo(eventoJson.getString(EventoContract.EventoObject.luogo));

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage()+e.getCause());
        }
        return ev;
    }
}
