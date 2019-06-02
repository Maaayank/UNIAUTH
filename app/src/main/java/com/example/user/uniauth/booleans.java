package com.example.user.uniauth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;


public class booleans {

    protected void saveUser(final String userdata, final String password,final Boolean loggedIn,final Boolean authType, final Context context) throws Exception {
        try {
            final File f = new File(context.getFilesDir().getAbsolutePath() + "Userdata.txt");
            final File pass = new File(context.getFilesDir().getAbsolutePath() + "Password.txt");
            final File bool = new File(context.getFilesDir().getAbsolutePath() + "Boolean.txt");

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirm");
            builder.setMessage("Registering A new User will Automatically Unregister the previous User !!! ");
            builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        FileWriter fw = new FileWriter(f, false);
                        fw.write(userdata);
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        FileWriter fq = new FileWriter(pass, false);
                        fq.write(password);
                        fq.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        FileWriter ft = new FileWriter(bool, false);
                        ft.write(loggedIn + ":" + authType);
                        ft.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        dialog.dismiss();
                    }
                    context.startActivity(new Intent(context, MainActivity.class));
                    Toast t = Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_LONG);
                    t.show();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }

            });


            if (f.exists()) {
                AlertDialog alert = builder.create();
                alert.show();
            } else {

                FileWriter fw = new FileWriter(f, false);
                fw.write(userdata);
                fw.close();
                FileWriter fq = new FileWriter(pass, false);
                fq.write(password);
                fq.close();
                FileWriter ft = new FileWriter(bool, false);
                ft.write(loggedIn + ":" + authType);
                ft.close();
                context.startActivity(new Intent(context, MainActivity.class));
                Toast t = Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_LONG);
                t.show();

            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String[] getUser(String fileName, Context context) throws Exception {
        String[] data;
        try {
            File f = new File(context.getFilesDir().getAbsolutePath() + fileName);
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String Data = br.readLine();
                data = Data.split(":");
                br.close();
                return data;
            } else return null;
        } catch (Exception e) {
            throw e;
        }
    }

    public String getPass(Context context) throws Exception {
        try {
            File f = new File(context.getFilesDir().getAbsolutePath() + "Password.txt");
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String Data = br.readLine();
                br.close();
                return Data;
            } else return null;
        } catch (Exception e) {
            throw e;
        }
    }

    public String[] getCond(Context context) throws Exception {
        String[] condn ;
        try {
            File f = new File(context.getFilesDir().getAbsolutePath() + "Boolean.txt");
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String Condn = br.readLine();
                condn = Condn.split(":");
                br.close();
                return condn;
            }
            else return null ;
        }catch (Exception e){
            throw e;
        }
    }

    public void setCond(final Boolean cond,int index,Context context) throws Exception {
        String[] condn;
        try {
            File f = new File(context.getFilesDir().getAbsolutePath() + "Boolean.txt");
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String Condn = br.readLine();
                condn = Condn.split(":");
                br.close();
                FileWriter fw = new FileWriter(f,false);
                condn[index] = cond.toString();
                fw.write(condn[0]+":"+condn[1]);
                fw.close();
            }
            else Toast.makeText(context,"Register Yourself first",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            throw e;
        }
    }
}

