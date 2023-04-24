package com.example.caderno;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class python_qa {
    private static List<Questionlist> pythonqa(){
        final List<Questionlist> questionlists = new ArrayList<>();
        final Questionlist question1 = new Questionlist("What is the maximum length of a Python identifier?","32","16","128","No fixed length is specified.","No fixed length is specified.");
        final Questionlist question2 = new Questionlist("How is a code block indicated in Python?","Brackets","Indentation","Key","None of the above","Indentation");
        questionlists.add(question1);
        questionlists.add(question2);
        return questionlists;
    }





    /*public static String questions[]={
            "What is the maximum length of a Python identifier?",
            "What will be the datatype of the var in the below code snippet? var = 10 print(type(var)) var = \"Hello\" print(type(var))",
            "How is a code block indicated in Python?",
            "What will be the output of the following code snippet? a = [1, 2, 3] a = tuple(a) a[0] = 2 print(a)",
            "Which of the following concepts is not a part of Python?",
            "Which of the following statements are used in Exception Handling in Python?",
            " What will be the output of the following code snippet? a = 3 b = 1 print(a, b) a, b = b, a print(a, b)",
            "Which of the following types of loops are not supported in Python?",
            "Which of the following is the proper syntax to check if a particular element is present in a list?",
            "What will be the output of the following code snippet? s = {1, 2, 3, 3, 2, 4, 5, 5} print(s)"
    };

    public static String choices[][]={

            {"32","16","128","No fixed length is specified."},
            {"str and int","int and int","str and str","int and str"},
            {"Brackets","Indentation","Key","None of the above"},
            {"[2, 2, 3]","(2, 2, 3)","(1, 2, 3)","Error"},
            {"Pointers","Loops","Dynamic Typing","All of the above"},
            {"try","except","finally","All of the above"},
            {"3 1    1 3","3 1    3 1","1 3    1 3","1 3    3 1"},
            {"for","while","do-while","None of the above"},
            {"if ele in list","if not ele not in list","Both A and B","None of the above"},
            {"{1, 2, 3, 3, 2, 4, 5, 5}","{1, 2, 3, 4, 5}","None","{1, 5}"}
    };

    public static String correctAnswers[]={
            "No fixed length is specified.",
            "int and str",
            "Indentation",
            "Error",
            "Pointers",
            "All of the above",
            "3 1    1 3",
            "do-while",
            "Both A and B",
            "{1, 2, 3, 4, 5}"

    };*/
    public static List<Questionlist> getquestions(){
        return pythonqa();
    }
}
