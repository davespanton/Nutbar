package com.davespanton.nutbar.application;

import com.google.inject.Module;
import roboguice.application.RoboApplication;

import java.util.List;

public class NutbarApplication extends RoboApplication {

	private Module module = new NutbarModule(this);
	
	@Override
	protected void addApplicationModules(List<Module> modules) {
		modules.add(module);
	}
	
	public void setModule(Module module) {
		this.module = module;
	}

}
