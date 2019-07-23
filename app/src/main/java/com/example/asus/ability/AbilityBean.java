package com.example.asus.ability;

/**
 * Created by ZWX
 * Description:
 * on 2019/1/8.
 */
public class AbilityBean {

    String abilityName;
    String abilityCore;

    public AbilityBean(String abilityName, String abilityCore) {
        this.abilityName = abilityName;
        this.abilityCore = abilityCore;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public String getAbilityCore() {
        return abilityCore;
    }

    public void setAbilityCore(String abilityCore) {
        this.abilityCore = abilityCore;
    }
}
