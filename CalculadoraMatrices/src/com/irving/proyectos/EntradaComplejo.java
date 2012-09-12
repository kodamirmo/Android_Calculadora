package com.irving.proyectos;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EntradaComplejo extends Activity implements OnClickListener{

	private Button btnsigno1,btnsigno2,btnsigno3,btnsigno4,btnok;
	private RadioButton btnRectangulares;
	private EditText real,imaginario,modulo,angulo;
	private int id;
	private RadioGroup grupo;
	public static int MODO_POLAR=1;
	public static int MODO_RECTANGULAR=0;
	private int modo; //0 para rectangulares
						//1 para polares
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_complejo);
        
        id=this.getIntent().getIntExtra("ID", 0);
        
        modo=EntradaComplejo.MODO_RECTANGULAR;
        
        btnsigno1=(Button)findViewById(R.id.signo1);
        real=(EditText)findViewById(R.id.real);
        btnsigno2=(Button)findViewById(R.id.signo2);
        imaginario=(EditText)findViewById(R.id.imaginario);
        
        btnok=(Button)findViewById(R.id.ok);
        
        btnsigno3=(Button)findViewById(R.id.signo3);
        modulo=(EditText)findViewById(R.id.modulo);
        btnsigno4=(Button)findViewById(R.id.signo4);
        angulo=(EditText)findViewById(R.id.angulo);
        
        btnRectangulares=(RadioButton)findViewById(R.id.rectangulares);
        
        btnsigno1.setText("+");
        btnsigno2.setText("+");
        btnsigno3.setText("+");
        btnsigno4.setText("+");
        
        btnsigno1.setOnClickListener(this);
        btnsigno2.setOnClickListener(this);
        btnsigno3.setOnClickListener(this);
        btnsigno4.setOnClickListener(this);
        
        btnok.setOnClickListener(this);
        
        grupo=(RadioGroup)findViewById(R.id.radioGrupo);
       // ArrayList<View> list=new ArrayList<View>();
        //list.add(btnRectangulares);
        //grupo.addTouchables(list);
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.id.rectangulares){
					modulo.setEnabled(false);
					angulo.setEnabled(false);
					real.setEnabled(true);
					imaginario.setEnabled(true);
					modo=EntradaComplejo.MODO_RECTANGULAR;
				}
				else{
					modulo.setEnabled(true);
					angulo.setEnabled(true);
					real.setEnabled(false);
					imaginario.setEnabled(false);
					modo=EntradaComplejo.MODO_POLAR;
				}
				
			}
		});
        
        modulo.setEnabled(false);
		angulo.setEnabled(false);
         
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_entrada_complejo, menu);
        return true;
    }


	public void onClick(View v) {
		
		if(v.getId()==R.id.ok){
			if(checar())
				EnviarDatos();
		}
		else
			CambiarSigno( v);
	}
	
	private boolean checar(){
	
		boolean bandera=true;
		
		if(modo==0){
			if(real.getText().toString().equals("") || imaginario.getText().toString().equals(""))
				bandera=false;
			
		}
		else{
			
			if(modulo.getText().toString().equals("") || angulo.getText().toString().equals(""))
				bandera=false;
		}
		
		if(!bandera){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Error");
			builder.setMessage("Los campos no pueden estar vacios");
			builder.setPositiveButton("", new android.content.DialogInterface.OnClickListener() {
			
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}

			});		
			builder.create().show();
		}
		
		return bandera;
	}
	
	
	private void CambiarSigno(View v){
		
		Button boton=(Button)v;
		
		if(boton.getText().equals("+"))
			boton.setText("-");
		else
			boton.setText("+");
		
	}
	
	private void EnviarDatos(){
		Intent resultado=new Intent();
		resultado.putExtras(Empaquetar());
		this.setResult(RESULT_OK, resultado);
		this.finish();
	}
	
	private Bundle Empaquetar(){
		
		Bundle paquete=new Bundle();
		paquete.putInt("ID", id);
		paquete.putInt("MODO", modo);
		if(modo==EntradaComplejo.MODO_RECTANGULAR){
		paquete.putDouble("REAL", Double.parseDouble(real.getText().toString()));
		paquete.putString("SIGNO", btnsigno2.getText().toString());
		paquete.putDouble("IMAGINARIO", Double.parseDouble(imaginario.getText().toString()));
		}
		else{
		paquete.putDouble("MODULO", Double.parseDouble(modulo.getText().toString()));
		paquete.putString("SIGNO", btnsigno2.getText().toString());
		paquete.putDouble("ANGULO", Double.parseDouble(angulo.getText().toString()));
		}
			
		return paquete;
		
	}
}