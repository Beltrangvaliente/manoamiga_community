
package com.tecnara.manoamiga.aaa.utils;


public class UtilTextos {
  
    public String concat(String ... values){
        String f="";
        for (int n=0;n<values.length;n++){
            if (values[n]!=null && false=="null".equals(values[n])){
                f+=values[n];    
            }
        }
        return f;
    }
    
    public String concatSep(String sep, String ... values){
        String f="";
        for (int n=0;n<values.length;n++){
            if (values[n]!=null && false=="null".equals(values[n])){
                f+=values[n] + sep;    
            }
        }
        return f;
    }

}
