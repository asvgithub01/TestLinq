package com.example.alberto.testlinq;

import android.content.Context;
import android.database.Observable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appoxee.Appoxee;
import com.appoxee.AppoxeeObserver;
import com.appoxee.asyncs.initAsync;
import com.example.alberto.testlinq.databinding.ActivityMainBinding;
import com.google.repacked.antlr.v4.runtime.misc.ObjectEqualityComparator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import static com.wagnerandade.coollection.Coollection.*;

public class MainActivity extends AppCompatActivity {
    List<PojoSample> pojoSamplesList;
    TextView txt;
    //  public myQueryCommand queryCommand;
    //region mandatory 4 binding, accesors
    public String helloWorldBinding = "Texto de hello worldbinding riau riaujjlkf";

    public String getHelloWorldBinding() {
        return helloWorldBinding;
    }

    public String twoWaysHelloWorldBinding;

    public String getTwoWaysHelloWorldBinding() {
        return twoWaysHelloWorldBinding;
    }

    public void setTwoWaysHelloWorldBinding(String twoWaysHelloWorldBinding) {
        this.twoWaysHelloWorldBinding = twoWaysHelloWorldBinding;

       if( helloSayerChecker.sayHelloOnlyIfYourFriend(this.twoWaysHelloWorldBinding))
           Toast.makeText(this,"HOLA androide "+ twoWaysHelloWorldBinding, Toast.LENGTH_LONG).show();

    }


    //endregion

    //region //appoxee4beni

    private final static String APPOXEE_SDK_KEY = "572a19dcb062a9.84354624";
    private final static String APPOXEE_SDK_SECRET = "572a19dcb063e0.20279470";
    private static final String TAG = "MainActivity";
    //todo esto hay q revisarlo, igual no hace falta tratamiento custom xa custom fields
    private static final String SOME_UNIQUE_USER_IDENTIFIER = "example";
    private static final String SOME_CUSTOM_FIELD = "custom_field";
    ExecutorService threadPool = Executors.newSingleThreadExecutor();

    //https://appoxee-wiki.atlassian.net/wiki/display/MIC/Initialize+Appoxee+SDK+to+your+Android+Application+Code
    public void iniAppoxee() {


        new initAsync(this,
                APPOXEE_SDK_KEY,
                APPOXEE_SDK_SECRET,
                MainActivity.class.getName(), true, appoxeeCallbacksObserver)
                .execute();

    }

    //we'll wait for appoxee to complete registration before using the API
    private AppoxeeObserver appoxeeCallbacksObserver = new AppoxeeObserver() {
        @Override
        public void onRegistrationCompleted() {
            Log.d(TAG, "appoxee is ready");
/*
            //using a background thread, because API method invoke network operations directly
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    //setting device alias
                    boolean success = Appoxee.setDeviceAlias(SOME_UNIQUE_USER_IDENTIFIER);
                    Log.d(TAG, "alias had been set: " + success);

                    //reading device alias value
                    String alias = Appoxee.getDeviceAlias();
                    Log.d(TAG, "alias: " + alias);
                }
            });

            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    //setting a custom fiels
                    boolean success = Appoxee.setCustomField(SOME_CUSTOM_FIELD, "ok");
                    Log.d(TAG, "custom field set success: " +success);

                    ArrayList<String> fieldArray = new ArrayList<String>();
                    fieldArray.add(SOME_CUSTOM_FIELD);
                    //reading custom field value
                    ArrayList<String> fieldValue = Appoxee.getCustomFieldsValues(fieldArray);
                    if (fieldValue != null && fieldValue.size() > 0){
                        Log.d(TAG, "field value: " + fieldValue.get(0));
                    } else {
                        Log.w(TAG, "couldn't get field value");
                    }

                }
            });

            threadPool.submit(new Runnable() {
                @Override
                public void run() {
               //     String[] tagArray = {"example_tag"};
                    //boolean success = Appoxee.addTagsToDevice(new ArrayList<String>(Arrays.asList(tagArray)));
                  //  Log.d(TAG, "custom field set success: " +success);

                  //  ArrayList<String> tags = Appoxee.getTagList();
                  //  Log.d(TAG, "tags list: " + tags);
                }
            });
            */
        }

        @Override
        public void onRegistrationFailure() {
            Log.d(TAG, "appoxee registration failed");
        }

        @Override
        public void onGeoRegistrationFailure() {
            //for future support
        }

        @Override
        public void onMessagesUpdateCompleted() {
            //for custom inbox
        }
    };
    //endregion


    /* @Bindable
     public ObservableInt enteroObservable;

     public ObservableInt getEnteroObservable() {
         return enteroObservable;
     }

     public void setEnteroObservable(ObservableInt enteroObservable) {
         this.enteroObservable = enteroObservable;
     }
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        txt = binding.txtResults; //recoger un view por ID
        GenerateTest(150);
        //queryCommand = new myQueryCommand(txt, pojoSamplesList);
        binding.setVariable(com.example.alberto.testlinq.BR.VM, this);

        iniAppoxee();
    }


    //region generate datamodel
    private void GenerateTest(int SizeList) {

        pojoSamplesList = new ArrayList<PojoSample>();

        for (int i = 0; i < SizeList; i++) {
            pojoSamplesList.add(new PojoSample(getRandomTitle(), getRandomName(), getRandomSurName()));
        }
    }

    public static String getRandomTitle() {
        String[] titles = {
                "Mr",
                "Miss",
                "Mister",
                "Doc",
                "Conde",
                "Duque",
                "Generalisimo",
                "Conde Duque de Olivares"
        };

        return getRandomValueFromArray(titles);
    }

    public static String getRandomName() {
        String[] names = {
                "Grijander",
                "Carlos Jesús",
                "Jesulin",
                "Paquito",
                "Pepito",
                "Dimitri"
        };

        return getRandomValueFromArray(names);
    }

    public static String getRandomSurName() {
        String[] surnames = {
                "Gonzalez",
                "Garcia",
                "Dominguez",
                "Monder",
                "Fernandez"
        };

        return getRandomValueFromArray(surnames);
    }

    public static String getRandomValueFromArray(String[] myRandomArr) {
        int max = myRandomArr.length - 1;

        Random rnd = new Random();
        int idx = rnd.nextInt(max);
        return myRandomArr[idx];
    }
    //endregion

    @BindingAdapter("bind:ClickCommand")
    public static void setClickCommand(View view, final Command command) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (command.canIexecute())
                    command.execute(v, ((Button) v).getText());
            }
        });

    }

    public class myQueryCommand extends Command {

        TextView mTxt;
        List<PojoSample> mPojoSamplesList;

        public myQueryCommand(TextView txt, List<PojoSample> pojoSampleList) {
            this.mTxt = txt;
            this.mPojoSamplesList = pojoSampleList;
        }

        @Override
        public void execute(View v) {

        }

        @Override
        public void execute(View v, Object input) {
            /*
            if (input instanceof String) {
                String str = (String) input;
                int sizeResults = -1;
                try {
                    sizeResults = from(this.mPojoSamplesList).where("name", eq(str)).all().size();
                } catch (Throwable throwable) {
                    Log.i("", throwable.getMessage());
                }
                this.mTxt.setText("Encontrados " + sizeResults + " con 'name'" + str);
            }
            */
        }

        @Override
        public boolean canIexecute() {
            return true;
        }
    }

    private static class helloSayerChecker {
        public static boolean sayHelloOnlyIfYourFriend(String name) {
            String[] myfriendNames = {"rui", "beni", "alberto", "andres", "oscar"};
            List<String> friendNames = Arrays.asList(myfriendNames);

            if (friendNames.contains(name)) return true;
            else return false;

        }
    }
}