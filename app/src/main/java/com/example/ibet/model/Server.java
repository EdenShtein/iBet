package com.example.ibet.model;

import android.util.Log;

import com.example.ibet.model.Team.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Server {

    public String ip = "192.168.1.113";

    public void signUp(String email, String password, Model.SuccessListener listener) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + ip + ":3000/api/users/signup"); //You need to write your IPV4 (cmd ipconfig)
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("email", email);
                    jsonParam.put("password", password);
                    jsonParam.put("passwordConfirm", password);


                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    int status = conn.getResponseCode();
                    if(conn.getResponseCode() == 201) { listener.onComplete(true); }
                    else { listener.onComplete(false); }
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void logIn(String email, String password,Model.LoginListener listener) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + ip +":3000/api/users/login"); //You need to write your IPV4 (cmd ipconfig)
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("email", email);
                    jsonParam.put("password", password);



                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    int code = conn.getResponseCode();
                    String message = conn.getResponseMessage();

                    InputStream is = conn.getInputStream();
                    JSONObject jObj = null;
                    String json = "";


                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                is, "iso-8859-1"), 8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        json = sb.toString();
                    } catch (Exception e) {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                    }

                    // try parse the string to a JSON object
                    try {
                        jObj = new JSONObject(json);
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                    }
                    String token = jObj.getString("token");
                    if(conn.getResponseCode() == 200) {
                        listener.onComplete(true,token);
                        //editor.putString("token", token); // Storing the user token
                    }
                    else { listener.onComplete(false,token); }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


    public void emailToken(String email, Model.SuccessListener listener) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + ip +":3000/api/users/forgotpassword"); //You need to write your IPV4 (cmd ipconfig)
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("email", email);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    int code = conn.getResponseCode();

                    if(conn.getResponseCode() == 200) { listener.onComplete(true); }
                    else { listener.onComplete(false); }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void restPassword(String token,String password, Model.SuccessListener listener) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + ip +":3000/api/users/resetPassword/" + token); //You need to write your IPV4 (cmd ipconfig)
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PATCH");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);


                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("token", token);
                    jsonParam.put("password", password);
                    jsonParam.put("passwordConfirm", password);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    int code = conn.getResponseCode();

                    if(conn.getResponseCode() == 200) { listener.onComplete(true); }
                    else { listener.onComplete(false); }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }


    public void getAlgoResult(Model.TeamDataListener listener) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + ip +":3000/api/algo/standings"); //You need to write your IPV4 (cmd ipconfig)
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");

                    conn.setDoInput(true);


                    InputStream is = conn.getInputStream();
                    JSONObject jObj = null;
                    String json = "";


                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                is, "iso-8859-1"), 8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        json = sb.toString();
                    } catch (Exception e) {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                    }

                    // try parse the string to a JSON object
                    try {
                        jObj = new JSONObject(json);
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                    }
                    JSONArray resultArray = jObj.getJSONArray("data");
                    ArrayList<Team> teamsData = new ArrayList<>(resultArray.length());
                    if(conn.getResponseCode() == 200) {

                        for(int i=0;i<resultArray.length();i++)
                        {
                            String teamName = resultArray.getJSONObject(i).getString("teamName");
                            String wins = resultArray.getJSONObject(i).getString("wins");
                            String losses = resultArray.getJSONObject(i).getString("losses");
                            Boolean isEliminated = resultArray.getJSONObject(i).getBoolean("isEliminated");
                            Team t = new Team(teamName,wins,losses,isEliminated);
                            teamsData.add(t);
                        }
                        listener.onComplete(teamsData);
                    }
                    else {  }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

}
