package com.example.alberto.testlinq;

import android.content.Context;
import android.view.View;

import java.util.Objects;

public abstract class Command<T> {
    Context mContext;
    Object mObject;

    public Command()
    {}
    public Command(Context context) {
        this.mContext = context;
    }

    public Command(Context context, Object object) {
        this.mContext = context;
        this.mObject = object;
    }

    public abstract void execute(View v);

    public abstract void execute(View v, T input);

    public abstract boolean canIexecute();
}