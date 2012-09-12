package com.irving.proyectos;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity  implements OnClickListener {
	
	private Button ecuaciones;
	private Button matrices;	
	
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ecuaciones=(Button)findViewById(R.id.botonEcuaciones);
        matrices=(Button)findViewById(R.id.botonMatrices);
        
        ecuaciones.setOnClickListener(this);
        matrices.setOnClickListener(this);
        
       
        
       
    }
    
    private void iniciar(int num){
    	Intent intento=new Intent(this, LlenarMatriz.class);
        intento.putExtra("TAM", num);
        this.startActivity(intento);	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onClick(View v) {
			if(v.equals(ecuaciones))
			{
				crearDialogoEcuaciones().show();
				Log.i("OnClick", "Si entro");
				
			}
			else
			{
				Log.i("OnClick", "No entro");
			}
			
	}
	
	private Dialog crearDialogoEcuaciones()
	{
	    final String[] items = {"Matriz 2 x 2", "Matriz 3 x 3", "Matriz 4 x 4", "Matriz N x N"};
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	    builder.setTitle("Tamaño de matriz");
	    builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int item) {
	            Log.i("Dialogos", "Opción elegida: " + items[item]);
	            if(item<3)
	            iniciar(item+2);
	            else
	            	crearDialogoMatricesNxN().show();
	        }
	    });
	 
	    return builder.create();
	}
	
	public Dialog crearDialogoMatricesNxN(){
		    
			final View layout = View.inflate(this, R.layout.entrada_n, null);

		    final EditText savedText = ((EditText) layout.findViewById(R.id.entradaNumero));

		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setIcon(0);
		    builder.setTitle("El tamaño de la matriz N es:");

		    builder.setPositiveButton("Aceptar", new Dialog.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		           int n=rangoNumero(savedText.getText().toString());
		           		if(n>2){
		           		Log.i("MSJ", "El numero es en  if 2el segundo" + n);
		           			iniciar(n);
		           		}
		           		else{Log.i("MSJ", "El numero else 2 en el segundo" + n);}
		           
		        }
		    });
		    builder.setView(layout);
		    return builder.create();

	}

	private int rangoNumero(String numero){
		
		Log.i("MSJ", "Estoy en rango numero");
		Integer n=Integer.valueOf(numero);
		
		Log.i("MSJ", "El numero es" + n);
		
		
		if(n<2){
			Toast.makeText(this,"Debe de ser un numero mayor a 2", Toast.LENGTH_LONG).show();
			Log.i("MSJ", "En if el numero es: " + n);
			return -1;
		}
		else{
			Log.i("MSJ", "En else, el numero es: " + n);
			return n;
		}
		
	}
}
