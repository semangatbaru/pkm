package com.staydev.seani.model;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContentKeranjang {

    public static final List<Mkeranjang> KERANJANG_LIST = new ArrayList<>();


    public static void  addAlat(Integer id_alat, String nama_alat, Integer harga, String foto){
        KERANJANG_LIST.add(new Mkeranjang(
                id_alat,
                nama_alat,
                harga,
                foto
        ));

        Log.d("keranjang", String.valueOf(KERANJANG_LIST.size() + "item"));


    }
    public static boolean isAlatSelected(Integer id_alat) {
        for (Mkeranjang mkeranjang : KERANJANG_LIST){
            if (mkeranjang.getId_alat() == id_alat )
                return true;
        }
        return false;
    }

    public static void removeAlat(Mkeranjang item){
        KERANJANG_LIST.remove(item);
    }

    public static double grandTotal()
    {
        double total = 0;
        for (Mkeranjang mkeranjang : KERANJANG_LIST) {
            total += mkeranjang.getHarga();
        }

        return total;
    }


    public static int totalQTY()
    {
        int total = 0;
        for (Mkeranjang mkeranjang : KERANJANG_LIST) {
            total += mkeranjang.getHarga();
        }
        return total;
    }
}
