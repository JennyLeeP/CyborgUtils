package com.cyborgJenn.cyborgUtils.core.utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import com.cyborgJenn.cyborgUtils.CyborgUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class ListEntities {

	public ListEntities()
	{
		Map<Integer, Class<? extends Entity>> IDtoClassMap = EntityList.idToClassMapping;
		try
		{
			
			File file = new File("config/CyborgUtils/entitylist.txt");
			PrintWriter pw = new PrintWriter(file);
			if (!file.exists())
			{
				pw.print("This is the entityList for Mod CyborgCore - WIP");
				pw.close();
			}
			else
			{
				IDtoClassMap.forEach((k, v) -> pw.print(k + " = " + v +"\r\n"));
				pw.close();
			}
		}
		catch (Throwable e)
		{
			CyborgUtils.logger.warn("Unable to read the ENTITYLIST", e);
		}
	}
}
