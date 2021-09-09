package com.eceakilli.javaworkmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefreshDatabase extends Worker {
    private Call<Map<String, Double>> call;

    Context myContext;


    public RefreshDatabase(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        //shared preferences içerisinde context kullanabilmen içn var olan contexti olusturdugun contexte atadın simdi sharedpreferenceste kullanabilirsin
        this.myContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        call = AppClassApplication.getInstance().getRestInterface().getWorldData();
        call.enqueue(new Callback<Map<String, Double>>() {
            @Override
            public void onResponse(Call<Map<String, Double>> call, Response<Map<String, Double>> response) {
                Log.e("basarılı",""+response.body().size());
                //TODO bildirim gönder telefona ID unıq olcak Localnotification

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("BAŞLIK")
                        .setContentText("textContent")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            }

            @Override
            public void onFailure(Call<Map<String, Double>> call, Throwable t) {
                Log.e("basarısız",""+t.getMessage());


            }
        });
        //bize bir girdi(input verilecek bunu al)
       // Data data = getInputData();
       // int myNumber = data.getInt("intKey",0);

    //  resfreshDatabase(myNumber);
        //work managerın kullanım kolaylıgı returs olarak sonucu success yada failare olarak da döndürebilirsin.
        return Result.success();
    }

    private void resfreshDatabase(int myNumber){

        /*sharetpreferences olustururken activity içerisinde olsaydın "=this.getShare.." demen gerekiyordu fakat burada olusturdugun class içerisindesin
           refreshDatabace methodunun parametresi olan context i kendi olusturdugun mycotexte eşitledin simdi kullanabilirsin. getSharedPreferences içerisine paket ismini koyarak işlemi
           tamamalayabilirsin. Amaç;sharedPreferences içerisine bir numara kaydet ve her seferinde belirttiğin peroyatta +1 artsın*/

       SharedPreferences sharedPreferences = myContext.getSharedPreferences(myContext.getPackageName(),Context.MODE_PRIVATE);
       int mySavedNumber = sharedPreferences.getInt("myNumber",0);
       mySavedNumber = mySavedNumber + myNumber;
       System.out.println(mySavedNumber);
       sharedPreferences.edit().putInt("myNumber",mySavedNumber).apply();


    }

}
