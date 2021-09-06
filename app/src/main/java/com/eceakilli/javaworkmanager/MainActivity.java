package com.eceakilli.javaworkmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //refreshDatabase sınıfına göndereceğimiz datayı tanımladık
        Data data = new Data.Builder().putInt("intKey",1).build();

        //bazı constraitler belirtip sartlar koyabiliyoruz. orn:
        Constraints constraints = new Constraints.Builder()
             //   .setRequiredNetworkType(NetworkType.CONNECTED) internete baglı olsun
                 .setRequiresCharging(true)// o sırada sarja bağlı olsun
                // .setRequiresCharging(false) //o sırada sarja baglı olmasın
                  .build();

    /*   periyodik olmayan bir kereye mahsus olan work request
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(RefreshDatabase.class)
                .setConstraints(constraints)
                .setInputData(data)
               // .setInitialDelay(5, TimeUnit.MINUTES) 5 dk sonra bu arttırma işlemi yap demek
               // .addTag("myTag") Taga göre işlem yaptırabilirsin
                .build();

        //work rewuesti işleme almak için work managerın kendisni çagırmak gerekli
        WorkManager.getInstance(this).enqueue(workRequest);


     */
        //periyodik olan work request
        WorkRequest workRequest = new PeriodicWorkRequest.Builder(RefreshDatabase.class,15,TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInputData(data)
                .build();
        WorkManager.getInstance(this).enqueue(workRequest);

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState() == WorkInfo.State.RUNNING){
                    System.out.println("running");
                } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED){
                    System.out.println("succeded");
                } else if (workInfo.getState() == WorkInfo.State.FAILED){
                    System.out.println("failed");
                }
            }
        });
        //WorkManager.getInstance(this).cancelAllWork();   fail oldugnda nasıl iptal edebilirim

        /*
        //bir defaya mahsus arka arkaya sırasıyla işlemyapmak istediğimizde Chaining işlemini kullanırız
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(RefreshDatabase.class)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest) //bununla basla
                .then(oneTimeWorkRequest)       //bununla devam et
                .then(oneTimeWorkRequest)       //bununla devam et
                .enqueue();                     //oluştur

        */
    }
}