package com.alphago.alphago;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sclab on 2018-02-22.
 */

public class TestData {
    public static int dataID[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
    // image는 어떻게 저장하지
    public static String dataLabel[] =
            {"dog","cat","lion",
                    "apple","banana","strawberry","lemon",
                    "bed","chair","desk",
                    "tomato","bean","cucumber","potato","pumpkin"};
    public static String dataCat[] =
            {"animal","animal","animal",
                    "fruit","fruit","fruit","fruit",
                    "furniture","furniture","furniture",
                    "vegetable","vegetable","vegetable","vegetable","vegetable"};
    public static int dataPrior[] = {0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0};

    public static String categoryName(String label){
        /*
        * SELECT CATEGORY FROM IMGDB WHERE LABEL = 'label'
        * */
        String category = "";

        for(int i=0; i<dataCat.length; i++){
            if(label.equals(dataCat[i])){
                category = dataCat[i];
                break;
            }
        }

        return category;
    }

    public static List labelArray(String category){
        /*
        * SELECT LABEL FROM IMGDB WHERE CATEGORY = 'category'
        * */
        List labels = new ArrayList();

        for(int i=0; i<dataLabel.length; i++){
            if(category.equals(dataCat[i])){
                labels.add(dataLabel[i]);
            }
        }
        return labels;
    }
}
