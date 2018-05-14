package com.automation.core.Common;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.openqa.selenium.net.UrlChecker;

public class AppiumServerUtils {
	private static String AppiumNodeFilePath;
	private static String AppiumJavaScriptServerFile;
	private static String AppiumServerAddress;
	private static String AppiumServerPort;

	// This method Is responsible for starting appium server.
	public static void startAppium() throws Exception {
		// Created object of apache CommandLine class.
		// It will start command prompt In background.
		AppiumNodeFilePath = "/usr/local/bin/node";
		AppiumJavaScriptServerFile = "/usr/local/lib/node_modules/appium/build/lib/main.js";
		AppiumServerAddress = "127.0.0.1";
		AppiumServerPort = "4723";
		CommandLine command = new CommandLine(AppiumNodeFilePath);
		// Add different arguments In command line which requires to start appium
		// server.
		command.addArgument(AppiumJavaScriptServerFile);
		command.addArgument("--address");
		command.addArgument(AppiumServerAddress);
		command.addArgument("--port");
		command.addArgument(AppiumServerPort);
		command.addArgument("--no-reset");
		command.addArgument("--session-override");
		// command.addArgument("--log");
		// //Set path to store appium server log file.
		// command.addArgument("./Logs/appiumLogs.txt");
		// Execute command line arguments to start appium server.
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		executor.execute(command, resultHandler);
		// Wait for 15 minutes so that appium server can start properly before going for
		// test execution. // Increase this time If face any error.
		waitUntilAppiumIsRunning();
	}

	private static boolean waitUntilAppiumIsRunning() throws Exception {
		final URL status = new URL("http://" + AppiumServerAddress + ":" + AppiumServerPort + "/wd/hub" + "/sessions");
		long start = System.currentTimeMillis();
		try {
			new UrlChecker().waitUntilAvailable(60, TimeUnit.SECONDS, status);
			long end = System.currentTimeMillis();
			long elapsed = end - start;
			System.out.println("Appium Server Started in :" + ((double) elapsed / 1000));
			return true;
		} catch (UrlChecker.TimeoutException e) {
			return false;
		}
	}

	// This method Is responsible for stopping appium server.
	public static void stopAppium() throws IOException, InterruptedException {
		// Add different arguments In command line which requires to stop appium server.
		Thread.sleep(2000);
		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("pkill -f appium");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
