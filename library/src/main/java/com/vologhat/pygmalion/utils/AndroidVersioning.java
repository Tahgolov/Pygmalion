package com.vologhat.pygmalion.utils;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;
import static android.os.Build.VERSION_CODES.N_MR1;
import static android.os.Build.VERSION_CODES.O;
import static android.os.Build.VERSION_CODES.O_MR1;
import static android.os.Build.VERSION_CODES.P;
import static android.os.Build.VERSION_CODES.Q;
import static android.os.Build.VERSION_CODES.R;
import static android.os.Build.VERSION_CODES.S;
import static android.os.Build.VERSION_CODES.S_V2;
import static android.os.Build.VERSION_CODES.TIRAMISU;
import static android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE;

import androidx.annotation.ChecksSdkIntAtLeast;

public class AndroidVersioning
{
    static public int getSdkInt()
    { return SDK_INT; }

    static public boolean isLolipop()
    { return SDK_INT==LOLLIPOP; }

    @ChecksSdkIntAtLeast(api=LOLLIPOP)
    static public boolean isAtLeastLolipop()
    { return SDK_INT>=LOLLIPOP; }

    static public boolean isLolipopMR1()
    { return SDK_INT==LOLLIPOP_MR1; }

    @ChecksSdkIntAtLeast(api=LOLLIPOP_MR1)
    static public boolean isAtLeastLolipopMR1()
    { return SDK_INT>=LOLLIPOP_MR1; }

    static public boolean isMarshmallow()
    { return SDK_INT==M; }

    @ChecksSdkIntAtLeast(api=M)
    static public boolean isAtLeastMarshmallow()
    { return SDK_INT>=M; }

    static public boolean isNougat()
    { return SDK_INT==N; }

    @ChecksSdkIntAtLeast(api=N)
    static public boolean isAtLeastNougat()
    { return SDK_INT>=N; }

    static public boolean isNougatMR1()
    { return SDK_INT==N_MR1; }

    @ChecksSdkIntAtLeast(api=N_MR1)
    static public boolean isAtLeastNougatMR1()
    { return SDK_INT>=N_MR1; }

    static public boolean isOreo()
    { return SDK_INT==O; }

    @ChecksSdkIntAtLeast(api=O)
    static public boolean isAtLeastOreo()
    { return SDK_INT>=O; }

    static public boolean isOreoMR1()
    { return SDK_INT==O_MR1; }

    @ChecksSdkIntAtLeast(api=O_MR1)
    static public boolean isAtLeastOreoMR1()
    { return SDK_INT>=O_MR1; }

    static public boolean isPie()
    { return SDK_INT==P;}

    @ChecksSdkIntAtLeast(api=P)
    static public boolean isAtLeastPie()
    { return SDK_INT>=P; }

    static public boolean isQ()
    { return SDK_INT==Q; }

    @ChecksSdkIntAtLeast(api=Q)
    static public boolean isAtLeastQ()
    { return SDK_INT>=N_MR1; }

    static public boolean isR()
    { return SDK_INT==R; }

    @ChecksSdkIntAtLeast(api=R)
    static public boolean isAtLeastR()
    { return SDK_INT>=R; }

    static public boolean isS()
    { return SDK_INT==S; }

    @ChecksSdkIntAtLeast(api=S)
    static public boolean isAtLeastS()
    { return SDK_INT>=S; }

    static public boolean isSV2()
    { return SDK_INT==S_V2; }

    @ChecksSdkIntAtLeast(api=S_V2)
    static public boolean isAtLeastSV2()
    { return SDK_INT>=S_V2; }

    static public boolean isTiramisu()
    { return SDK_INT==TIRAMISU; }

    @ChecksSdkIntAtLeast(api=TIRAMISU)
    static public boolean isAtLeastTiramisu()
    { return SDK_INT>=TIRAMISU; }

    static public boolean isUpsideDownCake()
    { return SDK_INT==UPSIDE_DOWN_CAKE; }

    @ChecksSdkIntAtLeast(api=UPSIDE_DOWN_CAKE)
    static public boolean isAtLeasUpsideDownCake()
    { return SDK_INT>=UPSIDE_DOWN_CAKE; }
}
