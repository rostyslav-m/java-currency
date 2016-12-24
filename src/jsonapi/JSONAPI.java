package jsonapi;

import java.util.ArrayList;
import java.util.HashMap;
import utils.NetworksUtils;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.JSONHelper;
public class JSONAPI {

    /**
     * @param args the command line arguments
     */
        

    public static void main(String[] args) {
        
        String jsonResult = NetworksUtils.get("http://resources.finance.ua/ua/public/currency-cash.json");
        
        System.out.println("result is = " + jsonResult);
        
        
        try {
            JSONObject object = new JSONObject(jsonResult);
            ArrayList<String> keys = JSONHelper.getKeys(object);
            System.out.println("keys = " + keys);            
            
            JSONObject currencyObject = object.getJSONObject(keys.get(6));
            System.out.println("currency keys - " + JSONHelper.getKeys(currencyObject));
            
            JSONArray organizations = object.getJSONArray(keys.get(5));
            //System.out.println("organizations - " + organizations.get(0));
            
            HashMap<String,Object> resultMap = new HashMap<>();
            
            // чтобіполучить лучшийкурс получили значение курса долл. первого банка потом будем сравнивать в поисках лучшего

            double minCurrency = Double.parseDouble(((JSONObject)  organizations.get(0))
                    .getJSONObject("currencies")
                    .getJSONObject("USD")
                    .getString("bid"));
            
            resultMap = JSONHelper.createMapFromJSON((JSONObject) organizations.get(0));
            
//            resultMap.put(TITLE_KEY, ((JSONObject) organizations.get(0)).getString("title"));
//                    resultMap.put(PHONE_KEY, ((JSONObject) organizations.get(0)).getString("phone"));
//                    resultMap.put(ADDRESS_KEY, ((JSONObject) organizations.get(0)).getString("address"));
//                    resultMap.put(CURRENCY_KEY, ((JSONObject) organizations.get(0))
//                            .getJSONObject("currencies")
//                            .getJSONObject("USD")
//                            .getString("bid"));
                    
            
            
            for(int i = 0; i< organizations.length(); i++){
                JSONObject org = (JSONObject) organizations.get(i);
                
                try{
                System.out.println("bank - "+ org.getString("title"));
                
                JSONObject curr = org.getJSONObject("currencies");
                System.out.println("EUR = " + curr.getString("EUR"));
                System.out.println("EUR = " + curr.getString("USD"));
                System.out.println(" ----------------------------- ");
            
                String bidUSDCurrency = curr.getJSONObject("USD").getString("bid");
                if ( Double.parseDouble(bidUSDCurrency)<minCurrency){
//                    resultMap.put(TITLE_KEY, org.getString("title"));
//                    resultMap.put(PHONE_KEY, org.getString("phone"));
//                    resultMap.put(ADDRESS_KEY, org.getString("address"));
//                    resultMap.put(CURRENCY_KEY, bidUSDCurrency);
                    resultMap = JSONHelper.createMapFromJSON(org);
                    minCurrency = Double.parseDouble(bidUSDCurrency);
                    
                    
                }
                
                }catch(JSONException e){
                    System.out.println(org.getString("title") + "error!");
                }
                
                
            }

            
                    System.out.println("min currency = " + resultMap.toString());

            
        } catch (JSONException ex) {
            Logger.getLogger(JSONAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
