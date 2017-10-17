package com.example.vb.mycash;

/**
 * Created by VB on 01.03.2017.
 */

public class Cash {

    int id;
    float sum;
    CategoriesCash categoriesCash;
    String note;
    String date;
    MainActivity mainActivity = new MainActivity();

    public Cash()
    {

    }

    public Cash(CategoriesCash categoriesCash, int id, float sum, String note, String date)
    {
        this.id = id;
        this.categoriesCash = categoriesCash;
        this.sum = sum;
        this.note = note;
        this.date = date;
    }

    public float AllCashExpenses()
    {
        float sum = 0;
        for(int i = 0; i<mainActivity.expenses.size(); i++)
        {
            sum = sum + mainActivity.expenses.get(i).sum;
        }
        return sum;
    }

    public float AllCashIncome()
    {
        float sum = 0;
        for(int i = 0; i<mainActivity.incomes.size(); i++)
        {
            sum = sum + mainActivity.incomes.get(i).sum;
        }
        return sum;
    }
}
