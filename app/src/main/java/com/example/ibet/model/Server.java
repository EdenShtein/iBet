package com.example.ibet.model;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

    public String ip = "10.0.0.10";

    public void signUp(String email, String password, Model.SuccessListener listener, Activity mActivity) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://" + ip + ":3000/api/users/signup";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
            jsonParam.put("password", password);
            jsonParam.put("passwordConfirm", password);
        }catch (Exception e) {e.printStackTrace();}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                try {
                    status = response.getString("status");
                    if(status.equals("success")) { listener.onComplete(true); }
                    else { listener.onComplete(false); }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void logIn(String email, String password,Model.LoginListener listener,Activity mActivity) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://" + ip +":3000/api/users/login";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
            jsonParam.put("password", password);
        }catch (Exception e) {e.printStackTrace();}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                String token;
                try {
                    status = response.getString("status");
                    token = response.getString("token");
                    if(status.equals("success")) {
                        listener.onComplete(true,token);
                    }
                    else { listener.onComplete(false,token); }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
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
