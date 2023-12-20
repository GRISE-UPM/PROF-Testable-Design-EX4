package es.upm.grise.profundizacion;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ProducDeliveryDate {
    public int getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");	
		Timestamp timestap = new Timestamp(System.currentTimeMillis());
		return Integer.valueOf(sdf.format(timestap));
    }
}
