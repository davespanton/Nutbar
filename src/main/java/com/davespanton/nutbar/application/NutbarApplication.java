package com.davespanton.nutbar.application;

import java.util.List;

import com.google.inject.Module;

import roboguice.application.RoboApplication;

public class NutbarApplication extends RoboApplication {

	private Module module = new NutbarModule();
	
	@Override
	protected void addApplicationModules(List<Module> modules) {
		modules.add(module);
	}
	
	public void setModule(Module module) {
		this.module = module;
	}

}
