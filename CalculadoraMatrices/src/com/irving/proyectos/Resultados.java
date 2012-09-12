package com.irving.proyectos;

import org.jscience.mathematics.number.Complex;

import com.irving.proyectos.util.Polar;
import com.irving.proyectos.util.Rectangular;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class Resultados extends Activity implements OnClickListener{
	
	LinearLayout layout;
	int formaVector[]; //0 Rectangular  //1 Grados   //2Radianes
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        
        layout=(LinearLayout)findViewById(R.id.LayoutResultados);
        AcomodarResultados(this.getIntent().getStringArrayExtra("RESULTADO"));
    }

    private void AcomodarResultados(String[] resultado){
    	
    	int longitud=resultado.length;
    	formaVector=new int[longitud];
    	
    	for(int i=0;i<longitud;i++){
    		Button boton=new Button(this);
    		boton.setText(resultado[i]);
    		boton.setOnClickListener(this);
    		boton.setId(i);
    		formaVector[i]=0;
    		layout.addView(boton);
    	}
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_resultados, menu);
        return true;
    }

	public void onClick(View v) {
		
		int id=v.getId();
		Button aux=(Button)v;
		
		if(formaVector[id]==0){
			Polar pol=Rectangular.aPolar(aux.getText().toString());
			aux.setText(pol.toString());
			formaVector[id]=2;
		}
		else if(formaVector[id]==1){
			
		}
		else if(formaVector[id]==2){
			Complex rec=Polar.aRectangular(aux.getText().toString());
			aux.setText(rec.toString());
			formaVector[id]=0;
		}
	}

}