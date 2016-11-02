package com.mobillium.bukoliandroidsdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mobillium.bukoliandroidsdk.utils.BukoliLogger;
import com.mobillium.bukoliandroidsdk.webservice.ServiceCallback;
import com.mobillium.bukoliandroidsdk.webservice.ServiceError;
import com.mobillium.bukoliandroidsdk.webservice.ServiceException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//
public class ServiceOperations {
    public static ProgressDialog pd;
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    public static void serviceReq(final Context mContext, int serviceType,
                                  String offsetUrl, Map<String, String> params,
                                  final ServiceCallback callback) {
        serviceCall(mContext, serviceType, offsetUrl, params, callback, null);

    }

    private static void serviceCall(final Context mContext, int serviceType,
                                    String offsetUrl, final Map<String, String> params,
                                    final ServiceCallback callback, final String pdString) {
        if (isOnline(mContext)) {

            String url = Bukoli.url + offsetUrl;

            if (serviceType == Request.Method.GET) {
                url = url + "?"
                        + encodeParameters(params, DEFAULT_PARAMS_ENCODING);
            }

            BukoliLogger.writeInfoLog(mContext.getString(R.string.sdk_webservice_request_log) + url);
            if (pdString != null) {
                try {
                    pd = ProgressDialog.show(mContext, null, pdString);
                    pd.setCancelable(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            StringRequest request = new StringRequest(serviceType, url,
                    new Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            if (pdString != null) {
                                try {
                                    pd.dismiss();

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                            BukoliLogger.writeInfoLog(mContext.getString(R.string.sdk_webservice_response_log) + response);
                            callback.done(response, null);
                        }
                    }, new ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (pdString != null) {
                        try {
                            pd.dismiss();

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    BukoliLogger.writeInfoLog(mContext.getString(R.string.sdk_webservice_response_log) + error);
                    String decoded = "";
                    ServiceError serviceError = null;

                    try {
                        decoded = new String(
                                error.networkResponse.data, "UTF-8");
                        BukoliLogger.writeInfoLog(mContext.getString(R.string.sdk_webservice_response_log) + decoded);
                        serviceError = Bukoli.getInstance().getGson()
                                .fromJson(decoded, ServiceError.class);

                    } catch (Exception e) {
                        decoded = "";
                        e.printStackTrace();
                    }

                    ServiceException serviceException = null;

                    if (TextUtils.isEmpty(decoded)) {
                        serviceException = new ServiceException(
                                mContext.getString(R.string.sdk_webservice_response_error),
                                false, serviceError);
                    } else {
                        serviceException = new ServiceException(
                                serviceError.getError(), false, serviceError);
                    }


                    if (error instanceof TimeoutError) {
                        //demo icin hata verme
                        Toast.makeText(mContext,
                                mContext.getString(R.string.sdk_webservice_connection_error),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            if (error.networkResponse.statusCode == 503) {
                                //Bakim yapiliyor
                            } else if (error.networkResponse.statusCode == 403) {
                                //gecersiz token
                                Bukoli.getInstance().getCallBack().onAuthError();
                            } else {
                                Toast.makeText(mContext,
                                        serviceException.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            callback.done(null, serviceException);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(mContext,
                                    mContext.getString(R.string.sdk_webservice_connection_error),
                                    Toast.LENGTH_SHORT).show();
                        }


                    }


                }
            }) {

                protected Map<String, String> getParams()
                        throws AuthFailureError {
                    if (params != null) {
                        writeParams(params);
                    }

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("X-Android-Key", Bukoli.getInstance().getApiKey());
                    params.put("X-Android-Package", Bukoli.getInstance().getPackageName());
                    if (!TextUtils.isEmpty(Bukoli.getInstance().getApiToken())) {
                        params.put("X-Integration-Token", Bukoli.getInstance().getApiToken());
                    }
                    return params;
                }


            };

            request.setShouldCache(false);
            request.setTag("BUKOLI");
            request.setRetryPolicy(new DefaultRetryPolicy(15000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Bukoli.getInstance().getMyVolley(mContext).getRequestQueue()
                    .add(request);

        } else {

            Toast.makeText(mContext,
                    mContext.getString(R.string.sdk_webservice_connection_error),
                    Toast.LENGTH_SHORT).show();
            callback.done(null, new ServiceException(
                    mContext.getString(R.string.sdk_webservice_connection_error), true));
        }
    }


    public static void writeParams(Map<String, String> params) {
        Iterator<Map.Entry<String, String>> entries = params.entrySet()
                .iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            BukoliLogger.writeResponseLog("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    public static boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private static String encodeParameters(Map<String, String> params,
                                           String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(),
                        paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(),
                        paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: "
                    + paramsEncoding, uee);
        }
    }


}
