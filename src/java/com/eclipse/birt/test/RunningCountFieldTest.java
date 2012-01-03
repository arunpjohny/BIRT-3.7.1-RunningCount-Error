package com.eclipse.birt.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

public class RunningCountFieldTest {

	/**
	 * @param args
	 * @throws BirtException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws BirtException,
			FileNotFoundException {
		IReportEngine engine = null;

		try {
			EngineConfig config = new EngineConfig();

			Platform.startup(config);
			IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			engine = factory.createReportEngine(config);
			engine.changeLogLevel(Level.WARNING);

			File outputFile = new File("src/java/report/op.pdf");
			File designFile = new File("src/java/report/running-count-test.rptdesign");
			System.out.println(designFile.getAbsolutePath());

			IReportRunnable design = engine
					.openReportDesign(designFile.getAbsolutePath());

			IRunAndRenderTask task = engine.createRunAndRenderTask(design);

			PDFRenderOption options = new PDFRenderOption();
			options.setOutputFormat("pdf");
			options.setOutputFileName(outputFile.getAbsolutePath());

			task.setRenderOption(options);
			task.run();
			task.close();

		} finally {
			if (engine != null) {
				engine.destroy();
			}
			Platform.shutdown();
		}
	}

}
