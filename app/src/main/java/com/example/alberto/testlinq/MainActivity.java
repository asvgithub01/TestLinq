package com.example.alberto.testlinq;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alberto.testlinq.databinding.ActivityMainBinding;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.wagnerandade.coollection.Coollection.*;

public class MainActivity extends AppCompatActivity {
    List<PojoSample> pojoSamplesList;
    TextView txt;
    public myQueryCommand queryCommand;
    //region mandatory 4 binding, accesors


    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        txt = binding.txtResults;
        GenerateTest(150);
        queryCommand = new myQueryCommand(txt, pojoSamplesList);
        binding.setVariable(com.example.alberto.testlinq.BR.VM, this);
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
        }

        @Override
        public boolean canIexecute() {
            return true;
        }
    }
}