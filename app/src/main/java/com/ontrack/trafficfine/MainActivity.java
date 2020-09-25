package com.ontrack.trafficfine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    TextView fineAmount;
    TextInputEditText registrationNumber;
    Button btnShowFine;
    private RequestQueue requestQueue;
    double Fine = 0.0;
    double totalFine = 0.0;
    String RegisternationNumber1 = "";
    String responseval = "";
    String PoliceFineDetailsList = "";
    RecyclerView rvFaqQuestions;
    String number1;
    String BikeNumber = "";
    int numOfBikeFine = 0;
    String vehicleRegistrationNumber = "";
    //  ProgressBar progressBarRecyclerview;
    View dividerView;
    List<AsyncTask<Void, Integer, Void>> asyncTasks = new ArrayList<AsyncTask<Void, Integer, Void>>();
    public ProgressDialog mProgressDialog;

    String Question = "", Answer = "";

    String string;

    ImageView clearText;

    Button copyVehicleFines;
    List<FaqQuestionModel> faqQuestionModelsList;
    Button exportResult;

    CustPrograssbar custPrograssbar;

    StringBuilder data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        registrationNumber = (TextInputEditText) findViewById(R.id.registration_number);
        fineAmount = (TextView) findViewById(R.id.fineAmount);
        btnShowFine = (Button) findViewById(R.id.btnFine);
        rvFaqQuestions = (RecyclerView) findViewById(R.id.activity_faq_rv);
        dividerView = (View) findViewById(R.id.divider_recycler_view);
//        progressBarRecyclerview= (ProgressBar) findViewById(R.id.progressBarRecyclerview);
        clearText = (ImageView) findViewById(R.id.clearText);
        copyVehicleFines = (Button) findViewById(R.id.copyResult);
        //  progressBarRecyclerview.setVisibility(View.GONE);
        exportResult = (Button) findViewById(R.id.exportResult);

        copyVehicleFines.setVisibility(View.GONE);
        exportResult.setVisibility(View.GONE);
        custPrograssbar = new CustPrograssbar(MainActivity.this);
        data = new StringBuilder();
        btnShowFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.this.getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                dividerView.setVisibility(View.VISIBLE);
                custPrograssbar.startLoadingDialog();
                if (!registrationNumber.equals(null)) {
                    //    progressBarRecyclerview.setVisibility(View.VISIBLE);
                    parsedata();
                } else {
                    Toast.makeText(MainActivity.this, "Please Enter Vehicle Number", Toast.LENGTH_LONG).show();
                }

            }
        });

        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationNumber.getText().clear();
            }
        });

        copyVehicleFines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Text", vehicleRegistrationNumber);
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Copied To Clipboard", Toast.LENGTH_LONG).show();
            }
        });

        exportResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportToCsv();
            }
        });

    }

    private void parsedata() {
        faqQuestionModelsList = new ArrayList<>();
        RegisternationNumber1 = registrationNumber.getText().toString().toLowerCase();
        data.append("Registratyion Number, Fine");
        String[] array = RegisternationNumber1.split(",");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
        Log.d("ArrayList", String.valueOf(list));
//            listSize = listSize - 1;
//            Log.e("listSize", String.valueOf(listSize));
        int listSize = list.size();
        for (int j = 0; j < list.size(); j++) {
            number1 = list.get(j);
            int finalJ = j;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://www.karnatakaone.gov.in/PoliceCollectionOfFine/FineDetails?SearchBy=REGNO&SearchValue=" + number1 + "&ServiceCode=BPS", null,
                    response -> {


                        try {
                            JSONObject jsonObject = response.getJSONObject("Response");
                            String resp = jsonObject.getString("ResponseVal");
                            Log.d("jsonOBJ", String.valueOf(resp));

                            if (resp.equals("true")) {

                                JSONArray jsonArray = response.getJSONArray("PoliceFineDetailsList");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject NumberList = jsonArray.getJSONObject(i);
                                    BikeNumber = NumberList.getString("RegistrationNo");
                                    Fine = NumberList.getInt("FineAmount");
                                    totalFine = totalFine + Fine;
                                    Log.d("TotalFine", String.valueOf(totalFine));
                                    //  PoliceFineDetailsList= FAQList.getString("PoliceFineDetailsList");
                                    // progressBarRecyclerview.setVisibility(View.VISIBLE);
//                                        if (listSize <= 0) {
//                                            custPrograssbar.dismissDialog();
//                                        }
                                }

                                //     faqQuestionModelsList.add(new FaqQuestionModel(RegisternationNumber1, String.valueOf(totalFine) ));

                                numOfBikeFine = numOfBikeFine + 1;
                                vehicleRegistrationNumber = vehicleRegistrationNumber + "  " + BikeNumber + " " + totalFine + "\n";
                                Log.e("BikeNumber", vehicleRegistrationNumber);
                                fineAmount.setText(String.valueOf("Number Of Bike Which Have Fine= " + numOfBikeFine));
                                data.append("\n" + BikeNumber + "," + totalFine);
                                // progressBarRecyclerview.setVisibility(View.GONE);
                                faqQuestionModelsList.add(new FaqQuestionModel(BikeNumber, String.valueOf(totalFine)));
                            } else {
//                                String Str = "No Fine For This Vehicle";
//                                 totalFine = 0.0;
//                              //  fineAmount.setText("No Fine Details Found For This Search");
//                              //  faqQuestionModelsList.add(new FaqQuestionModel(number1, String.valueOf(totalFine) ));
//                                //      faqQuestionModelsList.add(new FaqQuestionModel(RegisternationNumber1, Str ));
//
//                                for (int k = finalJ; k <= finalJ; k++) {
//                                    faqQuestionModelsList.add(new FaqQuestionModel(list.get(k).toUpperCase(), Str));
//                                }

                            }


                            //  faqQuestionModelsList.add(new FaqQuestionModel(number1, String.valueOf(totalFine)));
                            FaqAdapater faqAdapater = new FaqAdapater(faqQuestionModelsList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            linearLayoutManager.setReverseLayout(false);
                            rvFaqQuestions.setLayoutManager(linearLayoutManager);
                            rvFaqQuestions.setAdapter(faqAdapater);
                            totalFine = 0;
                            if (finalJ >= listSize - 1) {
                                //   Log.e("listSize", String.valueOf(finalJ));
                                copyVehicleFines.setVisibility(View.VISIBLE);
                                exportResult.setVisibility(View.VISIBLE);
                                custPrograssbar.dismissDialog();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, Throwable::printStackTrace) {

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Content-Type", "application/json;charset=UTF-8");
                    return headers;
                }
            };
            requestQueue.add(request);
        }
        numOfBikeFine = 0;
    }


    public void exportToCsv() {
        try {
            //saving the file into device
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.exportcsv.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
