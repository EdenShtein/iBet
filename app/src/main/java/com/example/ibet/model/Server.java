package com.example.ibet.model;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ibet.model.Group.Group;
import com.example.ibet.model.Group.GroupViewModel;
import com.example.ibet.model.Match.Match;
import com.example.ibet.model.Team.Team;
import com.example.ibet.model.User.User;
import com.example.ibet.model.User.UserViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Server {

    public String ip = "10.0.0.10";

    public void signUp(String email,String username, String password, Model.SuccessListener listener, Activity mActivity) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "https://ibet-app.herokuapp.com/api/users/signup";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
            jsonParam.put("userName", username);
            jsonParam.put("password", password);
            jsonParam.put("passwordConfirm", password);
        }catch (Exception e) {e.printStackTrace();}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                try {
                    status = response.getString("status");
                    if(status.equals("success")) {
                        JSONObject data = response.getJSONObject("data");
                        JSONObject user_obj = data.getJSONObject("user");
                        String user_id = user_obj.getString("_id");
                        User user = new User(email,username);
                        user.setId(user_id);
                        UserViewModel userViewModel = ViewModelProviders.of((FragmentActivity) mActivity).get(UserViewModel.class);
                        userViewModel.insert(user);
                        listener.onComplete(true);
                    }
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
        final String url = "https://ibet-app.herokuapp.com/api/users/login";

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
                        AppRepository appRepository = new AppRepository(mActivity.getApplication());
                        JSONObject data = response.getJSONObject("data");
                        JSONObject user_obj = data.getJSONObject("user");
                        String user_id = user_obj.getString("_id");
                        String user_name = user_obj.getString("userName");
                        User user = new User(user_id,email,user_name);
                        appRepository.insert(user);
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

    public void emailToken(String email, Model.SuccessListener listener,Activity mActivity) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/users/forgotpassword";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);

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

    public void restPassword(String token,String password, Model.SuccessListener listener,Activity mActivity) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/users/resetPassword/" + token;

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("token", token);
            jsonParam.put("password", password);
            jsonParam.put("passwordConfirm", password);

        }catch (Exception e) {e.printStackTrace();}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonParam, new Response.Listener<JSONObject>() {
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

    /*public void getAlgoResult(Model.TeamDataListener listener) {

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
    }*/

    public void getAlgoResult(Model.TeamDataListener listener,Activity mActivity) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/algo/standings";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                try {
                    status = response.getString("status");
                    if(status.equals("success")) {
                        JSONArray resultArray = response.getJSONArray("data");
                        ArrayList<Team> teamsData = new ArrayList<>(resultArray.length());
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
                    else{

                    }
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

    public void getCurrentUserDetails(Model.UserDetailsListener listener,Activity mActivity,String token) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/users/me";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response1",response.toString());
                String status;
                try {
                    User user;
                    status = response.getString("status");
                    if(status.equals("success")) {
                        JSONObject data = response.getJSONObject("data");
                        String email = data.getString("email");
                        String username = data.getString("userName");
                        user = new User(email,username);
                        listener.onComplete(user);
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error1", error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void updateMe(Model.SuccessListener listener,Activity mActivity,String token,String email,String username) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/users/updateMe";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("email", email);
            jsonParam.put("userName", username);

        }catch (Exception e) {e.printStackTrace();}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonParam, new Response.Listener<JSONObject>() {
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
                Log.e("Error1", error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void changePassword(Model.SuccessListener listener,Activity mActivity,String token,String password,String newPass) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/users/updateMyPassword";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("correntpassword",password);
            jsonParam.put("password", newPass);
            jsonParam.put("passwordConfirm", newPass);

        }catch (Exception e) {e.printStackTrace();}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonParam, new Response.Listener<JSONObject>() {
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
                Log.e("Error1", error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void getTeamData(Model.TeamDataListener listener,Activity mActivity,String token) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/teams";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                try {
                    status = response.getString("status");
                    if(status.equals("success")) {
                        JSONArray resultArray = response.getJSONArray("data");
                        ArrayList<Team> teamsData = new ArrayList<>(resultArray.length());
                        for(int i=0;i<resultArray.length();i++)
                        {
                            String teamName = resultArray.getJSONObject(i).getString("teamName");
                            String wins = resultArray.getJSONObject(i).getString("wins");
                            String losses = resultArray.getJSONObject(i).getString("losses");
                            String id = resultArray.getJSONObject(i).getString("_id");
                            String gamesRemaining = resultArray.getJSONObject(i).getString("remaning");
                            Team t = new Team(id,teamName,wins,losses,gamesRemaining);
                            teamsData.add(t);
                        }
                        listener.onComplete(teamsData);

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void createGroup(Model.GroupListener listener,Activity mActivity,String token,String groupName,int finalMatchWinner,int total) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/groups";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("groupName",groupName);
            JSONObject pointsFormat = new JSONObject();
            pointsFormat.put("FinalMatchWinner", finalMatchWinner);
            pointsFormat.put("Total", total);
            jsonParam.put("pointsFormat",pointsFormat);

        }catch (Exception e) {e.printStackTrace();}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                String group_id;
                String group_name;
                String admin_id;

                try {
                    status = response.getString("status");
                    JSONObject arr = response.getJSONObject("group");
                    group_id = arr.getString("_id");
                    group_name = arr.getString("groupName");
                    admin_id = arr.getString("adminUser");

                    Group group = new Group(group_id,group_name,admin_id);

                    if(status.equals("success")) {
                        listener.onComplete(true,group);
                    }
                    else { listener.onComplete(false,group); }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error1", error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void getGroupData(Model.GroupListener listener,Activity mActivity,String token,String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/groups/"+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                String group_id;
                String group_name;
                String admin_id;
                String current_score;
                String user_name;
                String user_id;

                try {
                    status = response.getString("status");
                    if(status.equals("success")) {
                        JSONObject arr = response.getJSONObject("group");
                        group_id = arr.getString("_id");
                        group_name = arr.getString("groupName");
                        admin_id = arr.getString("adminUser");

                        JSONObject data = arr.getJSONObject("data");
                        JSONArray userGroupBets = data.getJSONArray("userGroupBets");
                        for (int i=0; i<userGroupBets.length(); i++){
                            JSONObject user = userGroupBets.getJSONObject(i);
                            current_score = user.getString("currentScore");
                            user_name = user.getString("userName");
                            user_id = user.getString("user");
                            User new_user = new User();
                            new_user.setId(user_id);
                            new_user.setUserName(user_name);
                            new_user.setScore(current_score);
                        }


                        /*do*/
                        Group group = new Group(group_id,group_name,admin_id);
                        //group.setCurrent_score(current_score);

                        listener.onComplete(true,group);

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void shareGroup(Model.GroupListener listener,Activity mActivity,String token,Group group) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        String id = group.getId();
        final String url = "http://ibet-app.herokuapp.com/api/groups/share/"+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                String shareCode;
                try {
                    status = response.getString("status");
                    if(status.equals("success")) {
                        shareCode = response.getString("groupToken");
                        group.setShareCode(shareCode);
                        listener.onComplete(true,group);
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void joinGroup(Model.SuccessListener listener,Activity mActivity,String token,String groupToken) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/groups/join";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("groupToken",groupToken);
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
                Log.e("Error1", error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void getUpComingGames(Model.MatchListener listener,Activity mActivity,String token) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/upcommingGames";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                try {
                    status = response.getString("status");
                    if(status.equals("success")) {
                        JSONObject result = response.getJSONObject("result");
                        JSONArray resultArray = result.getJSONArray("games");
                        ArrayList<Match> finishedMatches = new ArrayList<>(resultArray.length());
                        ArrayList<Match> thisWeekMatches = new ArrayList<>(resultArray.length());
                        ArrayList<Match> notYetMatches = new ArrayList<>(resultArray.length());
                        for(int i=0;i<resultArray.length();i++)
                        {
                            String gameId = resultArray.getJSONObject(i).getString("gameId");
                            String hTeam = resultArray.getJSONObject(i).getString("hTeam");
                            String vTeam = resultArray.getJSONObject(i).getString("vTeam");
                            String date = resultArray.getJSONObject(i).getString("date");
                            String gameStatus = resultArray.getJSONObject(i).getString("status");
                            int hScore = resultArray.getJSONObject(i).getInt("hScore");
                            int vScore = resultArray.getJSONObject(i).getInt("vScore");

                            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                            Date fdate = fmt.parse(date);
                            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");

                            Match match = new Match(gameId,hTeam,vTeam,fmtOut.format(fdate),gameStatus,hScore,vScore);
                            if(match.getStatus().equals("Finished")){
                                finishedMatches.add(match);
                            }
                            else if(match.getStatus().equals("ThisWeek")){
                                thisWeekMatches.add(match);
                            }
                            else{
                                notYetMatches.add(match);
                            }
                        }
                        listener.onComplete(finishedMatches,thisWeekMatches,notYetMatches);

                    }
                    else{

                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void placeBet(Model.SuccessListener listener,Activity mActivity,String token,String groupId,String finalMatchWinner,String totalPoints,String gameId) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/groups/"+groupId+"/newbet";

        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("finalMatchWinner",finalMatchWinner);
            jsonParam.put("totalPoints",totalPoints);
            jsonParam.put("gameId",gameId);
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
                Log.e("Error1", error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    public void getGroupUsers(Model.UserListListener listener,Activity mActivity,String token,String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity.getApplicationContext());
        final String url = "http://ibet-app.herokuapp.com/api/groups/"+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String status;
                List<String> usersID = new LinkedList<String>();

                try {
                    status = response.getString("status");
                    if(status.equals("success")) {
                        JSONObject arr = response.getJSONObject("group");
                        JSONArray users = arr.getJSONArray("users");
                        for (int i=0;i< users.length(); i++){
                            String id = (String) users.get(i);
                            usersID.add(id);
                        }

                        listener.onComplete(usersID);

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization","Bearer "+token);
                return headers;
            }

        };
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

}
