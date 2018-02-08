package it.unirc.pwm.eureca.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventoImpl implements EventoDao{
    private static final String TAG="JSON Activity";

    @Override
    public Evento getEvento(String jsonString) {
      Evento ev = new Evento();

        JSONArray jsonArray=null;
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            jsonArray = jsonObject.getJSONArray(EventoContract.arrayName);
            for (int i=0; i<jsonArray.length(); i++) { //dovrebbe essere una sola iterazione
                jsonObject=jsonArray.getJSONObject(i);
                //int id=Integer.parseInt(jsonObject.getString(UserJSONContract.UserObject.id));
                ev.setNome(jsonObject.getString(EventoContract.EventoObject.nome));
                ev.setDescrizione(jsonObject.getString(EventoContract.EventoObject.descrizione));
                //ev.setDataEvento(jsonObject.getString(EventoContract.EventoObject.descrizione));
                ev.setLuogo(jsonObject.getString(EventoContract.EventoObject.luogo));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage()+e.getCause());
        }
        return ev;
    }
}
