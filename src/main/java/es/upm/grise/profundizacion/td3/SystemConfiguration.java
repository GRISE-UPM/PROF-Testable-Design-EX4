package es.upm.grise.profundizacion.td3;

public class SystemConfiguration {

	private static SystemConfiguration instance;
	
	private static final double handlingPercentage = 0.02;

	// Singleton access method
	public static SystemConfiguration getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new SystemConfiguration();
			return instance;
		}	
	}
	
	public static void setInstance(SystemConfiguration newInstance)
	{
		instance = newInstance;
	}

	public double getHandlingPercentage() {
		return handlingPercentage;
	}
	
	
	
}
