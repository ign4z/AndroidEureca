package it.unirc.pwm.eureca.activity;

import android.app.Activity;

import it.unirc.pwm.eureca.model.Evento;
import it.unirc.pwm.eureca.model.EventoImpl;
import it.unirc.pwm.eureca.util.Costanti;
import it.unirc.pwm.eureca.util.InternetConnection;

public class EventoActivity extends Activity {


    private void go()
    {
        //Operazione lenta che "blocca" l'applicazione
        //il problema verra' risolto con l'introduzione dell'AsynReq
        InternetConnection internetConnection=new InternetConnection(Costanti.urlBase+Costanti.urlJson+"?id=");
        String jsonString=internetConnection.getHttpSource();

        EventoImpl evContractImpl=new EventoImpl();
        Evento evento = evContractImpl.getEvento(jsonString);

        String[] userListString;
     /*   if(userList.size()==0){
            userListString=new String[1];
            userListString[0]=getString(R.string.nodata);
        } else{
            userListString=new String[userList.size()];
            for(int i=0; i<userList.size(); i++){
                userListString[i]=userList.get(i).toString();
                Log.d(TAG,userListString[i]);
            }
        }
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra(EXTRA_USERS,userListString);
        startActivity(intent);*/
    }

}
