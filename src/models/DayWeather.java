package models;

public class DayWeather {
	
	public DayWeather.Type type;
	public float 			temp;
	
	public DayWeather() {
		// TODO Auto-generated constructor stub
	}
	
	public enum Type {
		CLEAR_SKY, FEW_CLOUDS, SCATTERED_CLOUDS, 
		BROKEN_CLOUDS, SHOWER_RAIN, RAIN, 
		THUNDERSTORM, SNOW, MIST
	}
	
	


}
