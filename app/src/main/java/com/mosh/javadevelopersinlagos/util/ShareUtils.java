package com.mosh.javadevelopersinlagos.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShareUtils {

    public static void shareCustom(String message, Context context)
    {
        String text = message;
        List<Intent> targetShareIntents=new ArrayList<>();
        Intent shareIntent=new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        List<ResolveInfo> resInfos=context.getPackageManager().queryIntentActivities(shareIntent, 0);

        if(!resInfos.isEmpty()){
            for(ResolveInfo resInfo : resInfos){
                String packageName=resInfo.activityInfo.packageName;

                //Setting the intent parameters and adding it to the targetShareIntents list
                Intent intent=new Intent();
                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_TEXT, text);
                intent.setPackage(packageName);
                targetShareIntents.add(intent);
            }

            //Get intent selected
            if(!targetShareIntents.isEmpty()){
                Intent chooserIntent=Intent.createChooser(targetShareIntents.remove(0), "Share with Friends");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooserIntent);
            }else{
                Toast.makeText(context, "No app to share it", Toast.LENGTH_SHORT).show();
            }
        }
    }
}