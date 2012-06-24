package com.davespanton.nutbar.logging;

import android.content.Context;
import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;
import com.google.code.microlog4android.appender.FileAppender;
import com.google.code.microlog4android.config.PropertyConfigurator;
import com.google.code.microlog4android.format.PatternFormatter;
import com.google.inject.Inject;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class LogConfiguration {

    private static final String LOG_FILE = "nutbar.log";

    public static final Logger mog = LoggerFactory.getLogger("NBAR");

    @Inject
    public LogConfiguration(Context context) {

        PropertyConfigurator.getConfigurator(context).configure();

        PatternFormatter patternFormatter = new PatternFormatter();
    	patternFormatter.setPattern("%c %d [%P] %m %T");

    	FileAppender fileAppender = new FileAppender();
    	fileAppender.setFileName(LOG_FILE);
    	fileAppender.setFormatter(patternFormatter);

        mog.addAppender(fileAppender);

        mog.debug("Mog configured.");
    }
}
