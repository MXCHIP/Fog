package com.mxchip.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mxchip.helper.CheckHelper;
import com.mxchip.helper.CommonPara;
import com.mxchip.helper.JsonHelper;
import com.mxchip.helper.SharePreHelper;

import io.fog.callbacks.MiCOCallBack;
import io.fog.fog2sdk.MiCOUser;
import io.fog.helper.MiCO;

/**
 * Created by Rocke on 2016/06/12.
 */
public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharePreHelper sph = new SharePreHelper(SplashActivity.this);

        String foghost = sph.getData(CommonPara._FOG_HOST);
        String fogappid = sph.getData(CommonPara._FOG_APPID);
        if(CheckHelper.checkPara(foghost))
            MiCO.init(foghost);
        if(CheckHelper.checkPara(fogappid))
            CommonPara._APPID = fogappid;

        if(CheckHelper.checkPara(sph.getData(CommonPara.SHARE_TOKEN))){
            MiCOUser miCOUser = new MiCOUser();
            miCOUser.refreshToken(sph.getData(CommonPara.SHARE_TOKEN), new MiCOCallBack() {
                @Override
                public void onSuccess(String message) {
                    SharePreHelper sph = new SharePreHelper(SplashActivity.this);
                    sph.addData(CommonPara.SHARE_TOKEN, JsonHelper.getFogToken(message));

                    Intent intent = new Intent(SplashActivity.this, IndexFragmentActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(int code, String message) {
                    toLogin();
                }
            });

        }else{
            toLogin();
        }
        finish();
    }

    private void toLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
