package com.irving.proyectos.util;

import org.jscience.mathematics.number.Complex;

import android.util.Log;

public class Rectangular {

	static public Polar aPolar(Complex complejo){
	
		Double magnitud=complejo.magnitude();
		Double angulo=complejo.argument();
		
	Polar polar=new Polar(magnitud,angulo,Polar.RADIANES);
	return polar;
	}
	
	static public Polar aPolar(String rectangular){
		
		Complex complejo=Complex.valueOf(rectangular);
		Log.i("MSJ", complejo.toString());
		return aPolar(complejo);
	}
}
