package com.irving.proyectos.util;

import org.jscience.mathematics.number.Complex;


public class Polar {
	
	
	static final int RADIANES=0;
	static final int GRADOS=1;
	
	double magnitud;
	double angulo;
	int tipo;
	
	public Polar(String cad){
		
	}
	
	Polar(double magnitud,double angulo, int tipo){
		
		this.magnitud=magnitud;
		this.angulo=angulo;
		this.tipo=tipo;
		
	}
	
	Polar(Complex complejo){
	
		this.magnitud=complejo.magnitude();
		this.angulo=complejo.argument();
		this.tipo=Polar.RADIANES;
	
	}
	
	public int getTipo(){
		return tipo;
	}

	@Override
	public String toString() {
		
		if(tipo==Polar.GRADOS)
		return magnitud + " ∠ " + angulo + "°" ;
		else if(tipo==Polar.RADIANES)
		return magnitud + " ∠ " + angulo + "r" ;
		else
		return magnitud + " ∠ " + angulo + "" ;
	}

	static public Complex aRectangular(String cad){
		
		Complex complejo;
		Double real;
		Double imaginario;
		
		
		cad.trim();
		int coincidencia=cad.indexOf("∠");
		
		Double magnitud=Double.valueOf(cad.subSequence(0, coincidencia-1).toString());
		Double angulo=Double.valueOf(cad.subSequence(coincidencia+1,cad.length()-1).toString());
		
		if(cad.endsWith("r")){
			real=magnitud*Math.cos(angulo);
			imaginario=magnitud*Math.sin(angulo);
		}
		else if(cad.endsWith("°")){
			angulo=(angulo*Math.PI)/180;
			real=magnitud*Math.cos(angulo);
			imaginario=magnitud*Math.sin(angulo);
		}
		else{
			real=0.0;
			imaginario=0.0;
		}	

		complejo=Complex.valueOf(real, imaginario);
		
		return complejo;
	}
	
	static public Complex aRectangular(Double magnitud,Double angulo){
		
		Double real=magnitud*Math.cos(angulo);
		Double imaginario=magnitud*Math.sin(angulo);
		
		Complex complejo=Complex.valueOf(real, imaginario);
		
		return complejo;
	}
	
}