/*
 * Copyright (c) 2021, Mathew Official
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.mirabilia.org.hzi.sormas.aggregate;

import com.mirabilia.org.hzi.Util.credentialsManagerUtil;
import com.mirabilia.org.hzi.sormas.doa.ConffileCatcher;
import com.mirabilia.org.hzi.sormas.doa.DbConnector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Mathew Official
 */
public class SendToDHISServer {

    private static String[] _url = ConffileCatcher.fileCatcher("passed");

    private static String httpx = _url[10].toString(); //should come from config file

    public static void SendCasesToDHIS(String cPer, String dSet, String OrgUnit, String dEle, String val, String tDay, String disC, String org_name, String incount, String impor, String death, String recover, String no_outcome,
            String age_1, String age_2, String age_3, String age_4, String age_5, String age_6, String age_7, String Health_Worker, String Occupation_Lab_Staff, String Occupation_unknow_missing,
            String Male, String female, String not_confirmed_lab, String gender_others, String gender_missing, String confirmed_lab, String comfrimed_missing, String Not_Classfied, String Not_Case, String Propable, String Susptected, String Confimed, String Quarantine_other, String Quarantine_Missing, String Quarantine_Home, String quaratine_none, String quaratine_inst, String Not_Classfied_Missing) {

        StringBuilder sb = new StringBuilder();
        System.out.println("URI in use: " + httpx);

        String http = httpx + "/api/dataValueSets";

        HttpURLConnection urlConnection = null;
        String name = credentialsManagerUtil.getDhis_User();
        String password = credentialsManagerUtil.getDhis_pawd();

        String authString = name + ":" + password;

        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        try {
            URL url = new URL(http);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(true);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            JSONObject json = new JSONObject();
            json.put("dataSet", dSet);
            json.put("completeDate", tDay);

            json.put("period", cPer.replaceAll("-", ""));
            json.put("orgUnit", OrgUnit);

            JSONArray array = new JSONArray();
            //if (!"0".equals(val)) {
            JSONObject item = new JSONObject();
            item.put("dataElement", "y914IkI1mcw");
            item.put("value", Integer.parseInt(val));
            array.add(item);
            // }
            //if (!"0".equals(incount)) {
            JSONObject item1 = new JSONObject();
            item1.put("categoryOptionCombo", "yr9KCcp24dI");
            item1.put("dataElement", "LwLDycsBaSC");
            item1.put("value", Integer.valueOf(incount));
            array.add(item1);
            //  }
            //if (!"0".equals(impor)) {
            JSONObject item2 = new JSONObject();
            item2.put("categoryOptionCombo", "CqhKacjKIyG");
            item2.put("dataElement", "LwLDycsBaSC");
            item2.put("value", Integer.valueOf(impor));
            array.add(item2);
            // }
//OUTCOME
            //if (!"0".equals(death)) {
            JSONObject item3 = new JSONObject();
            item3.put("categoryOptionCombo", "HEVkjakWadt");
            item3.put("dataElement", "ObwbuybGqev");
            item3.put("value", Integer.valueOf(death));
            array.add(item3);
            //}
            //if (!"0".equals(recover)) {
            JSONObject item4 = new JSONObject();
            item4.put("categoryOptionCombo", "SNwVO65yKLD");
            item4.put("dataElement", "ObwbuybGqev");
            item4.put("value", Integer.valueOf(recover));
            array.add(item4);
            //}

            //if (!"0".equals(no_outcome)) {
            JSONObject item4_a = new JSONObject();
            item4_a.put("categoryOptionCombo", "ziGYcHoUlxG");
            item4_a.put("dataElement", "ObwbuybGqev");
            item4_a.put("value", Integer.valueOf(no_outcome));
            array.add(item4_a);
            // }

//LAB Confirmation
            //if (!"0".equals(not_confirmed_lab)) {
            JSONObject item5 = new JSONObject();
            item5.put("categoryOptionCombo", "znFDz5MUKFU");
            item5.put("dataElement", "M2lh3zVT85m");

            System.out.println("=============================" + Integer.valueOf(not_confirmed_lab));
            item5.put("value", Integer.valueOf(not_confirmed_lab));
            array.add(item5);
            //  }
            //if (!"0".equals(confirmed_lab)) {
            JSONObject item5_a = new JSONObject();
            item5_a.put("categoryOptionCombo", "zYy56QC7AMh");
            item5_a.put("dataElement", "M2lh3zVT85m");
            System.out.println("----------------------------------------" + Integer.valueOf(confirmed_lab));
            item5_a.put("value", Integer.valueOf(confirmed_lab));
            array.add(item5_a);
            // }
//GENDER
            //if (!"0".equals(gender_others)) {
            JSONObject item6 = new JSONObject();
            item6.put("categoryOptionCombo", "WuSqzHEx8zh");
            item6.put("dataElement", "p5lUq0nikYc");
            item6.put("value", Integer.valueOf(gender_others));
            array.add(item6);
            //}
            //if (!"0".equals(Male)) {
            JSONObject item7 = new JSONObject();
            item7.put("categoryOptionCombo", "XqVYgyPkDDD");
            item7.put("dataElement", "p5lUq0nikYc");
            item7.put("value", Integer.valueOf(Male));
            array.add(item7);
            // }
            //if (!"0".equals(female)) {
            JSONObject item8 = new JSONObject();
            item8.put("categoryOptionCombo", "hzuy9TV8MEW");
            item8.put("dataElement", "p5lUq0nikYc");
            item8.put("value", Integer.valueOf(female));
            array.add(item8);
            //}
            //if (!"0".equals(gender_missing)) {
            JSONObject item9 = new JSONObject();
            item9.put("categoryOptionCombo", "X6mISFBGX0t");
            item9.put("dataElement", "p5lUq0nikYc");
            item9.put("value", Integer.valueOf(gender_missing));
            array.add(item9);
            // }
            //if (!"0".equals(Occupation_Lab_Staff)) {
            JSONObject item10 = new JSONObject();
            item10.put("categoryOptionCombo", "ADVU5rDCqdL");
            item10.put("dataElement", "XUAtpFmcDTN");
            item10.put("value", Integer.valueOf(Occupation_Lab_Staff));
            array.add(item10);

            JSONObject item10_a = new JSONObject();
            item10_a.put("categoryOptionCombo", "nZbakguAkPK");
            item10_a.put("dataElement", "XUAtpFmcDTN");
            item10_a.put("value", Integer.valueOf(Health_Worker));
            array.add(item10_a);

            // }
            //if (!"0".equals(Occupation_unknow_missing)) {
            JSONObject item11 = new JSONObject();
            item11.put("categoryOptionCombo", "Tfjs297bieK");
            item11.put("dataElement", "XUAtpFmcDTN");
            item11.put("value", Integer.valueOf(Occupation_unknow_missing));
            array.add(item11);
            // }
            //AGE
            //if (!"0".equals(age_1)) {
            JSONObject item12 = new JSONObject();
            item12.put("categoryOptionCombo", "fBXLtsRhjmL");
            item12.put("dataElement", "WZNgywrtQsl");
            item12.put("value", Integer.valueOf(age_1));
            array.add(item12);
            //}
            //if (!"0".equals(age_2)) {
            JSONObject item13 = new JSONObject();
            item13.put("categoryOptionCombo", "v7YmpmfRfTj");
            item13.put("dataElement", "WZNgywrtQsl");
            item13.put("value", Integer.valueOf(age_2));
            array.add(item13);
            // }
            //if (!"0".equals(age_3)) {
            JSONObject item14 = new JSONObject();
            item14.put("categoryOptionCombo", "aZR8OrZdxy1");
            item14.put("dataElement", "WZNgywrtQsl");
            item14.put("value", Integer.valueOf(age_3));
            array.add(item14);
            //  }
            //if (!"0".equals(age_4)) {
            JSONObject item15 = new JSONObject();
            item15.put("categoryOptionCombo", "qK08trg18Wr");
            item15.put("dataElement", "WZNgywrtQsl");
            item15.put("value", Integer.valueOf(age_4));
            array.add(item15);
            // }
            //if (!"0".equals(age_5)) {
            JSONObject item16 = new JSONObject();
            item16.put("categoryOptionCombo", "gI6hobBK0Fv");
            item16.put("dataElement", "WZNgywrtQsl");
            item16.put("value", Integer.valueOf(age_5));
            array.add(item16);
            // }
            //if (!"0".equals(age_6)) {
            JSONObject item17 = new JSONObject();
            item17.put("categoryOptionCombo", "NZzOXySPLdW");
            item17.put("dataElement", "WZNgywrtQsl");
            item17.put("value", Integer.valueOf(age_6
            ));
            array.add(item17);
            // }
            //if (!"0".equals(age_7)) {
            JSONObject item18 = new JSONObject();
            item18.put("categoryOptionCombo", "FY8ksWTLuFp");
            item18.put("dataElement", "WZNgywrtQsl");
            item18.put("value", Integer.valueOf(age_7
            ));
            array.add(item18);

            //classficiation patch
            //Not_Classfied, Not_Case, Propable, Susptected, Confimed
            JSONObject item19 = new JSONObject();
            item19.put("categoryOptionCombo", "maWulTsI11M");
            item19.put("dataElement", "OeYjA8YfEmG");
            item19.put("value", Integer.valueOf(Not_Classfied
            ));
            array.add(item19);

            JSONObject item20 = new JSONObject();
            item20.put("categoryOptionCombo", "tjk0rPZG3US");
            item20.put("dataElement", "OeYjA8YfEmG");
            item20.put("value", Integer.valueOf(Not_Case
            ));
            array.add(item20);

            JSONObject item21 = new JSONObject();
            item21.put("categoryOptionCombo", "gAYDDAR5hoH");
            item21.put("dataElement", "OeYjA8YfEmG");
            item21.put("value", Integer.valueOf(Propable
            ));
            array.add(item21);

            JSONObject item22 = new JSONObject();
            item22.put("categoryOptionCombo", "tiufJKy6JK8");
            item22.put("dataElement", "OeYjA8YfEmG");
            item22.put("value", Integer.valueOf(Susptected
            ));
            array.add(item22);

            //QUARANTINE ==Quarantine_other, Quarantine_Missing, Quarantine_Home, quaratine_none, quaratine_inst
            JSONObject item23 = new JSONObject();
            item23.put("categoryOptionCombo", "YWdSGPXe02C");
            item23.put("dataElement", "uIwbb7XCePT");
            item23.put("value", Integer.valueOf(Quarantine_Home
            ));
            array.add(item23);

            JSONObject item24 = new JSONObject();
            item24.put("categoryOptionCombo", "c4pjKQIJJeH");
            item24.put("dataElement", "uIwbb7XCePT");
            item24.put("value", Integer.valueOf(quaratine_inst
            ));
            array.add(item24);

            JSONObject item26 = new JSONObject();
            item26.put("categoryOptionCombo", "HPaCa6VRiAU");
            item26.put("dataElement", "uIwbb7XCePT");
            item26.put("value", Integer.valueOf(quaratine_none
            ));
            array.add(item26);

            JSONObject item27 = new JSONObject();
            item27.put("categoryOptionCombo", "PHEg8zuplZZ");
            item27.put("dataElement", "uIwbb7XCePT");
            item27.put("value", Integer.valueOf(Quarantine_other
            ));
            array.add(item27);

            JSONObject item28 = new JSONObject();
            item28.put("categoryOptionCombo", "RJDECCMQd0V");
            item28.put("dataElement", "uIwbb7XCePT");
            item28.put("value", Integer.valueOf(Quarantine_Missing
            ));
            array.add(item28);//Not_Classfied_Missing

            JSONObject item29 = new JSONObject();
            item29.put("categoryOptionCombo", "AewFMtFllZ9");
            item29.put("dataElement", "OeYjA8YfEmG");
            item29.put("value", Integer.valueOf(Confimed
            ));
            array.add(item29);

            json.put("dataValues", array);

            System.out.println("E8765&&&&&7777: " + json.toString());

            if (1 == 2) {
                System.out.println("E8765&&&&&7777: " + json.toString());
                return;
            }

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(json.toString());
            out.close();

            int HttpResult = urlConnection.getResponseCode();

            System.out.println(HttpResult);

            if (HttpResult == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                if (sb.toString().contains("SUCCESS")) {
                    // response.setStatus(200);
                    String[] wx = sb.toString().split("description\":\"");
                    System.err.println("Response: Successful! " + wx[1]);
                    update_oneParm_X("insert into sync_tracker set json_response = ?, datasource= '" + org_name + "', dataperiod = '" + cPer + "', case_specific_detail = '" + disC + "', status = 'ok', created = now()", sb.toString());
                    return;
                }

                if (sb.toString().contains("WARNING")) {
                    // response.setStatus(300);
                    String[] wx = sb.toString().split("conflicts");
                    System.err.println("WARNING/CONFLICT Found " + sb.toString());
                    update_oneParm_X("insert into sync_tracker set json_response = ?, datasource= '" + org_name + "', dataperiod = '" + cPer + "', case_specific_detail = '" + disC + "', status = 'WARNING', created = now()", sb.toString());
                    return;
                }

                if (sb.toString().contains("ERROR")) {
                    // response.setStatus(300);
                    String[] wx = sb.toString().split("ERROR");
                    System.err.println("ERROR Found " + sb.toString());
                    update_oneParm_X("insert into sync_tracker set json_response = ?, datasource= '" + org_name + "', dataperiod = '" + cPer + "', case_specific_detail = '" + disC + "',  status = 'ERROR', created = now()", sb.toString());
                    return;
                }

            } else {
                System.out.println(urlConnection.getResponseMessage());

                return;
            }
        } catch (MalformedURLException localMalformedURLException) {
        } catch (IOException e) {
            System.err.println("Pushers says..... DHIS2 not found or not working.");
            return;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AggregrateController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AggregrateController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

        }

    }

    public static String update_oneParm(String sqq, String sqq_) throws ClassNotFoundException, SQLException {

        PreparedStatement ps = null;
        ResultSet rx = null;
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DbConnector.getConnection();
        String ret = "";
        try {

            ps = conn.prepareStatement(sqq);
            ps.setString(1, sqq_);

            //System.out.println("E234: " + ps.toString());
            rx = ps.executeQuery();

            if (rx.next()) {
                ret = rx.getString(1);
            }

        } finally {
            conn.close();
        }
        //System.out.println("ddddddd"+ret);
        return ret;
    }

    public static void update_oneParm_X(String sqq, String sqq_) throws ClassNotFoundException, SQLException {

        PreparedStatement ps = null;

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DbConnector.getConnection();

        try {

            ps = conn.prepareStatement(sqq);
            ps.setString(1, sqq_);

            ps.executeUpdate();

        } finally {
            conn.close();
        }

    }

    public static void update_PSQL_oneParm_X(String sqq, String sqq_, String sqll__) throws ClassNotFoundException {

        Connection cox = DbConnector.getPgConnection();
        PreparedStatement ps = null;

        try {

            ps = cox.prepareStatement(sqq);
            ps.setString(1, sqq_);
            ps.setString(2, sqll__);

            ps.executeUpdate();
            cox.close();

        } catch (SQLException ex) {
            Logger.getLogger(SendToDHISServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update_PSQL_oneParm_XINT(String sqq, String sqq_, String sqll__) throws ClassNotFoundException {

        Connection cox = DbConnector.getPgConnection();
        PreparedStatement ps = null;

        try {

            ps = cox.prepareStatement(sqq);
            ps.setString(1, sqq_);
            ps.setInt(2, Integer.parseInt(sqll__));

            ps.executeUpdate();
            cox.close();

        } catch (SQLException ex) {
            Logger.getLogger(SendToDHISServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String get_trackEntity(String sqq, String sqq_) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rx = null;
        String rett = "0";

        Connection conn = DbConnector.getPgConnection();

        try {

            ps = conn.prepareStatement(sqq);
            ps.setInt(1, Integer.parseInt(sqq_));

            rx = ps.executeQuery();

            while (rx.next()) {
                rett = rx.getString(1);
            }

        } finally {
            conn.close();
        }
        return rett;

    }

}
