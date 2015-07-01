package com.arc.bloodarsenal.common.tinkers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IClientCode
{
    @SideOnly(Side.CLIENT)
    void executeClientCode();
}
