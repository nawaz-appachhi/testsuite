package com.myntra.apiTests;

import com.myntra.lordoftherings.Initialize;

public class InitializeFramework
{
	public static Initialize init = new Initialize("/Data/configuration");
	
	public void Reinitialize()
	{
		init = new Initialize("/Data/configuration");
	}
}
