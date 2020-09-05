package in.afckstechnologies.mail.afckstechnologies.Utils;

import java.util.ArrayList;

/**
 * Created by admin on 2/20/2017.
 */

public class Utils {

    public static String convertArrayListToStringWithComma(ArrayList<String> arrayList){

        //The string builder used to construct the string
        StringBuilder commaSepValueBuilder = new StringBuilder();

        //Looping through the list
        for ( int i = 0; i< arrayList.size(); i++){
            //append the value into the builder
            commaSepValueBuilder.append(arrayList.get(i));

            //if the value is not the last element of the list
            //then append the comma(,) as well
            if ( i != arrayList.size()-1){
                commaSepValueBuilder.append(",");
            }
        }

        return String.valueOf(commaSepValueBuilder);
    }
}
