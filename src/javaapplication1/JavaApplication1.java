package javaapplication1;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.DoubleUnaryOperator;


public class JavaApplication1 {
    public static String getJSONFromFile(String filename){
        String jsonText="";
        try{
            BufferedReader bufferReader=new BufferedReader(new FileReader(filename));
            String line;
            while((line=bufferReader.readLine())!=null){
                jsonText+=line+"\n";
                
            }
            bufferReader.close();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return jsonText;
    }
    
    public static String getJSONFromURL(String strUrl){
        String jsonText="";
        try{
            URL url=new URL(strUrl);
            InputStream is=url.openStream();
            BufferedReader bufferReader=new BufferedReader(new InputStreamReader(is));
            String line;
            while((line=bufferReader.readLine())!=null){
                jsonText+=line+"\n";
            }
            is.close();
            bufferReader.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return jsonText;
    }
    public static void main(String[] args){
        String strJson=getJSONFromURL("http://api.openweathermap.org/data/2.5/weather?q=" + "London" + "&appid=" + "d94a1a5aa3c285a26a3d45c0e357d771");
        System.out.println(strJson);
        try{
            JSONParser parser=new JSONParser();
            Object object=parser.parse(strJson);
            JSONObject mainJsonObject=(JSONObject) object;
            String base=(String) mainJsonObject.get("base");
            System.out.println("Acessing the base JSON object:"+base);
            JSONObject weatherObj=(JSONObject) mainJsonObject.get("main");
            Double weather=(Double)weatherObj.get("temp");
            System.out.println(weather);
        }
        catch(Exception e){
            e.printStackTrace();;
        }

    }
    
}
