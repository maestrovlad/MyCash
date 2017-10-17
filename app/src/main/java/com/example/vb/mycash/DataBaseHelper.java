package com.example.vb.mycash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by VB on 06.03.2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    // путь к базе данных вашего приложения
    private static String DB_PATH = "/data/data/com.example.vb.mycash/databases/";
    private static String DB_NAME = "cash.db";
    private SQLiteDatabase myDataBase;
    private final Context mContext;
    private static final String CATEGORY_ID = "Category_id";
    private static final String SUM = "Sum";
    private static final String NOTE = "Note";
    private static final String DATE = "Date";
    int idCash;
    String tableCash = "";
    MainActivity mainActivity = new MainActivity();

    /**
     * Конструктор
     * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    /**
     * Создает пустую базу данных и перезаписывает ее нашей собственной базой
     * */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if(dbExist){
            //ничего не делать - база уже есть
        }else{
            //вызывая этот метод создаем пустую базу, позже она будет перезаписана
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Проверяет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложения
     * @return true если существует, false если не существует
     */
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            Log.d("myPath", myPath);
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //база еще не существует
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Копирует базу из папки assets заместо созданной локальной БД
     * Выполняется путем копирования потока байтов.
     * */
    private void copyDataBase() throws IOException{
        //Открываем локальную БД как входящий поток
        InputStream myInput = mContext.getAssets().open(DB_NAME);

        //Путь ко вновь созданной БД
        String outFileName = DB_PATH + DB_NAME;

        //Открываем пустую базу данных как исходящий поток
        OutputStream myOutput = new FileOutputStream(outFileName);

        //перемещаем байты из входящего файла в исходящий
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //закрываем потоки
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        //открываем БД
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //добавление записи про доходы
    public void addIncome(Cash income) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID, String.valueOf(income.categoriesCash.ID));
        values.put(SUM, String.valueOf(income.sum));
        if(income.note.equals("null"))
            income.note = " ";
        values.put(NOTE,income.note);
        values.put(DATE,String.valueOf(income.date));

        db.insert("Income", null, values);
        db.close();
    }

    //добавление записи про расходы
    public void addExpenses(Cash expenses) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID, String.valueOf(expenses.categoriesCash.ID));
        values.put(SUM, String.valueOf(expenses.sum));
        if(expenses.note.equals("null"))
            expenses.note = " ";
        values.put(NOTE,expenses.note);
        values.put(DATE,String.valueOf(expenses.date));

        db.insert("Expenses", null, values);
        db.close();
    }

    public void editCash(Cash cash, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        idCash = cash.id;
        values.put(CATEGORY_ID, String.valueOf(cash.categoriesCash.ID));
        values.put(SUM, String.valueOf(cash.sum));
        if(cash.note.equals("null"))
            cash.note = " ";
        values.put(NOTE,cash.note);
        tableCash = table;
        /*if(table.equals("Income"))
        {
            tableCash = "Income";
        }
        else if(table.equals("Expenses"))
        {
            tableCash = "Expenses";
        }
        else
        {

        }*/
        Log.d("bla1234qwert", tableCash + "---" + values + "---" + tableCash+"_id = "+idCash  + "---" + "null");
        db.update(tableCash, values, tableCash+"_id = "+idCash, null);
        //db.update(tableCash, values, tableCash+"_id = "+idCash, null);
        //Log.d("bla1234qwert", i + ">>>>>" + tableCash + "---" + values + "---" + tableCash+"_id = "+idCash  + "---" + "null");
        db.close();
    }

    public void deleteCash(int id, String table)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //ContentValues values = new ContentValues();
        //values.put(CATEGORY_ID, String.valueOf(cash.categoriesCash.ID));
        db.delete(table,table+"_id = "+id,null);
    }

    public void deleteCategorie(int id, String table, String column)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table,column+"_id = "+id,null);
    }

    //получение списка категорий доходов
    public void getItemCategoryIncome()
    {
       mainActivity.categoriesIncomes.clear();
        String selectQuery = "SELECT * FROM CategoriesIncome";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(selectQuery, null);
        if(cur.moveToFirst()) {
            do {
                mainActivity.categoriesIncomes.add(new CategoriesCash(Integer.parseInt(cur.getString(0)), cur.getString(1)));
            } while (cur.moveToNext());
            cur.close();
        }
    }

    //получение списка категорий расходов
    public void getItemCategoryExpenses()
    {
        mainActivity.categoriesExpenses.clear();
        String selectQuery = "SELECT * FROM CategoriesExpense";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(selectQuery, null);
        if(cur.moveToFirst()) {
            do {
                mainActivity.categoriesExpenses.add(new CategoriesCash(Integer.parseInt(cur.getString(0)), cur.getString(1)));
            } while (cur.moveToNext());
            cur.close();
        }
    }

    //получение списка расходов
    public void getExpenses(String date)
    {
        mainActivity.expenses.clear();
        String selectQuery = "SELECT Expenses.Expenses_id,Expenses.Sum,Expenses.Note,Expenses.Date,Expenses.Category_id,CategoriesExpense.CategoryName FROM Expenses join CategoriesExpense on Expenses.Category_id=CategoriesExpense.CategoryExpense_id WHERE Date LIKE '%"+ date + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(selectQuery, null);
        if(cur.moveToFirst()) {
            do {
                mainActivity.expenses.add(new Cash(new CategoriesCash(Integer.parseInt(cur.getString(4)),cur.getString(5)), Integer.parseInt(cur.getString(0)), Float.parseFloat(cur.getString(1)), cur.getString(2), cur.getString(3)));
            } while (cur.moveToNext());
            cur.close();
        }
    }

    //получение списка доходов
    public void getIncome(String date)
    {
        mainActivity.incomes.clear();
        String selectQuery = "SELECT Income.Income_id,Income.Sum,Income.Note,Income.Date,Income.Category_id,CategoriesIncome.CategoryName FROM Income join CategoriesIncome on Income.Category_id=CategoriesIncome.CategoryIncome_id WHERE Date LIKE '%"+ date + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery(selectQuery, null);
        if(cur.moveToFirst()) {
            do {
                mainActivity.incomes.add(new Cash(new CategoriesCash(Integer.parseInt(cur.getString(4)),cur.getString(5)), Integer.parseInt(cur.getString(0)), Float.parseFloat(cur.getString(1)), cur.getString(2), cur.getString(3)));
            } while (cur.moveToNext());
            cur.close();
        }
    }
}
