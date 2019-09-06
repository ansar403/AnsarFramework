package com.ansar.wait;

import java.util.concurrent.TimeUnit;
import static com.ansar.baseclass.BaseClass.*;
import static com.ansar.constants.constansts.*;

public class Waits {


public static void implictWait() {
	
	getDriver().manage().timeouts().implicitlyWait(miniWaitTime, TimeUnit.SECONDS);
	
}

public static void explictWait() {
	
}

public static void fluentWait() {
	
}


public static void threadSleep() {
	
}
}
