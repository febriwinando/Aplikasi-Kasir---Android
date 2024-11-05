package com.example.aplikasikasir;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormatWaktu {

    public final static Locale localeID = new Locale("in", "ID");
    public static SimpleDateFormat SIMPLE_FORMAT_TANGGAL = new SimpleDateFormat("yyyy-MM-dd", localeID);
    public static SimpleDateFormat SIMPLE_FORMAT_TANGGAL_INVOICE = new SimpleDateFormat("dd/MM/yyyy HH:mm", localeID);
    public static SimpleDateFormat SIMPLE_FORMAT_JAM = new SimpleDateFormat("HH:mm", localeID);
    public static SimpleDateFormat SIMPLE_FORMAT_INVOICE = new SimpleDateFormat("yyMMddHHmmss", localeID);
    public static SimpleDateFormat TANGGAL_ID = new SimpleDateFormat("yyMMddHH", localeID);
    public static SimpleDateFormat JAM_ID = new SimpleDateFormat("HHmmss", localeID);

//    Locale localeID = new Locale("in", "ID");
    public static NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


}
