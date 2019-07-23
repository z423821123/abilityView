# abilityView
仿dotamax能力图
![能力图](https://github.com/z423821123/Image/blob/master/ability.png)


    List<AbilityBean> abilityBeans = new ArrayList<>();
        abilityBeans.add(new AbilityBean("综合","34.2"));
        abilityBeans.add(new AbilityBean("KDA","38.4"));
        abilityBeans.add(new AbilityBean("发育","28.9"));
        abilityBeans.add(new AbilityBean("推进","32.7"));
        abilityBeans.add(new AbilityBean("生存","59.8"));
        abilityBeans.add(new AbilityBean("输出","26.2"));
        abilityView.setNumberColor(0x8897C5FE);
        abilityView.setInLineColor(0x8897C5FE);
        abilityView.setOutLineColor(0x8897C5FE);
        abilityView.setNumberSize(100);
        abilityView.setTextSize(70);
        abilityView.setTextColor(0x8897C5FE);
        abilityView.setAbilityBeans(abilityBeans);
        abilityView.setTYPE(AbilityView.TYPE_VERTICAL);
