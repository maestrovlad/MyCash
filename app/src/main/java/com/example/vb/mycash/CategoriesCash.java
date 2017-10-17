package com.example.vb.mycash;

import java.util.ArrayList;

/**
 * Created by VB on 08.03.2017.
 */

public class CategoriesCash {

    public String categorie;
    public int ID;

    public CategoriesCash()
    {

    }

    public CategoriesCash(int expenses_ID, String categorie)
    {
        this.ID = expenses_ID;
        this.categorie = categorie;
    }
}
