package com.example.alberto.testlinq;

/**
 * Created by Alberto on 12/04/2016.
 */
public class PojoSample {
    public final String name;
    public final String first;
    public final String second;

    public PojoSample(String n, String f, String s) {
        this.name = n;
        this.first = f;
        this.second = s;
    }


    //todo esto hay q repararlo en el coollection
    public String name() {
        return name;
    }

}
