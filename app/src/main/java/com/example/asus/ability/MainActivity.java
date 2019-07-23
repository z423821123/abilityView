package com.example.asus.ability;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AbilityBean> abilityBeans = new ArrayList<>();
    private AbilityView abilityView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        abilityView = (AbilityView)findViewById(R.id.ability);

        //test
        abilityBeans.add(new AbilityBean("综合","34.2"));
        abilityBeans.add(new AbilityBean("KDA","38.4"));
        abilityBeans.add(new AbilityBean("发育","28.9"));
        abilityBeans.add(new AbilityBean("推进","32.7"));
        abilityBeans.add(new AbilityBean("生存","59.8"));
        abilityBeans.add(new AbilityBean("输出","26.2"));
        abilityView.setNumberColor(0x8897C5FE);
        abilityView.setInLineColor(0x8897C5FE);
        abilityView.setOutLineColor(0x8897C5FE);
//        abilityView.setNumberSize(50);
//        abilityView.setTextSize(20);
        abilityView.setTextColor(0x8897C5FE);
        abilityView.setAbilityBeans(abilityBeans);
        abilityView.setTYPE(AbilityView.TYPE_VERTICAL);
    }
}
