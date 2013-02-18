package ua.com.suntime;

import java.util.ArrayList;

public class SuntimeUtils {
    
    public static String categoriesToString(ArrayList<SuntimeSightCategory> categories) {
        StringBuilder text = new StringBuilder();
        
        if(!categories.isEmpty()) {
            for(int i = 0; i < categories.size(); i++) {
                text.append(categories.get(i).getTitle());
                
                if(i + 1 < categories.size()) {
                    text.append(", ");
                }
            }
        }
        
        return text.toString();
    }
    
}
