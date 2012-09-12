package com.irving.proyectos;

import java.util.ArrayList;

import org.jscience.mathematics.number.Complex;
import org.jscience.mathematics.vector.ComplexMatrix;
import org.jscience.mathematics.vector.ComplexVector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.irving.proyectos.util.Polar;

/**
 * Actividad en donde se llena la matriz para resolver
 * @author  Irving Emmanuel Gonzalez
 * @since  1.0
 * @see http://irvingemmanuelblog.blogspot.mx
 */
public class LlenarMatriz extends Activity implements OnClickListener{
	
	private int tamano;
	private ComplexMatrix matriz;
	private ComplexVector vector;
	private ArrayList<Complex> listaMatriz;
	private ArrayList<Complex> listaVector;
	private View seleccionado;
	private int[] formaMatriz;  //0 Rectangular  //1 Grados   //Radianes
	private int[] formaVector;
	
	/**
	 * Metodo de entrada de la actividad donde se apunta al boton descrito
	 * en el XML, y se le asigna un OnClickListener
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Se recupera el tamaño que viene del intent
        int tam=getIntent().getIntExtra("TAM", 0);
        
        //Si el tamaño es 0 hay un error
        if(tam==0)
        	Toast.makeText(this, "Error creando la matriz", Toast.LENGTH_LONG).show();
        //Si es mayor a 0 lo hacemos
        else
        {
        	tamano=tam*tam;
        	
        	formaMatriz=new int[tamano];
        	formaVector=new int[tam];
        	
        	listaMatriz=new ArrayList<Complex>(tamano);
        
        	listaVector=new ArrayList<Complex>(tam);
        	
        	//agregamos elementos a la lista de la matriz
        	for(int i=0;i<tamano;i++){
        		listaMatriz.add(Complex.ZERO);
        		formaMatriz[i]=0;
        	}
        	
        	//Agregamos elementos al vector
        	for(int i=0;i<tam;i++){
        		listaVector.add(Complex.ZERO);
        		formaVector[i]=0;
        	}
        	
        	setContentView(CrearMatriz(tam));
        	Toast.makeText(this, "Matriz de "+ tam+"x"+tam, Toast.LENGTH_LONG).show();
        }
    }
    
    private TableLayout CrearMatriz(int tam){
    	
    	TableLayout layout=new TableLayout(this);
    	int inc=0;
    	
    	Complex auxComplejo=Complex.ZERO;
    	
    	for(int i=0;i<tam;i++)
    	{
    		
    		TableRow fila=new TableRow(this);
    		
    		for(int j=0;j<tam;j++)
    		{
    			
    			Button btnaux=new Button(this);
    			btnaux.setText(auxComplejo.toString());
    			btnaux.setId(inc);
    			btnaux.setOnClickListener(this);
    			this.registerForContextMenu(btnaux);
    			fila.addView(btnaux);
    			inc++;
   
    		}
    		
    		
    		TextView espacio=new TextView(this);
    		espacio.setText(" | ");
    		
    		Button btnaux=new Button(this);
    		btnaux.setId((tamano+i));
    		btnaux.setText(auxComplejo.toString());
    		btnaux.setOnClickListener(this);
    		this.registerForContextMenu(btnaux);
    		
    		fila.addView(espacio);
    		fila.addView(btnaux);
    		
    		layout.addView(fila);
    		
    	}
    	
    	
    	Button res=new Button(this);
    	res.setText("Calcular");
    	res.setId(100);
    	res.setOnClickListener(this);
    	layout.addView(res);
    	return layout;
    }
    
    private void ObtenerDatos(){
 
    	int tam=(int) Math.sqrt(tamano);
    	int contador=0;
    	
    	Complex elementos[][]=new Complex[tam][tam];
    	
    	for(int i=0;i<tam;i++)	
    		for(int j=0;j<tam;j++)
    		{
    			elementos[i][j]=listaMatriz.get(contador);
    			contador++;
    		}
    	
    	matriz=ComplexMatrix.valueOf(elementos);
    	vector=ComplexVector.valueOf(listaVector);    	
    	
    	Calcular();
    }

    private void Calcular(){
    	
    	ComplexVector resultado=ComplexVector.valueOf(matriz.solve(vector));
    	Integer dimension=resultado.getDimension();
    	Log.i("DIMEN","La dimension es: " + dimension );
    	String[] array=new String[dimension];
    	
    	for(int i=0;i<dimension;i++){
    		array[i]=resultado.get(i).toString();
    	}
    	
    	Intent mostrarResultados=new Intent(this,Resultados.class);
    	mostrarResultados.putExtra("DIMENSION",dimension);
    	mostrarResultados.putExtra("RESULTADO", array);
    	startActivity(mostrarResultados);
    	
    }
    /**
     * Metodo para implementar los menus en esta actividad
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_llenar_matriz, menu);
        return true;
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo){
    	super.onCreateContextMenu(menu, v, menuInfo);
    	
    	seleccionado=v;
    	
    	MenuInflater mi=this.getMenuInflater();
    	mi.inflate(R.menu.menu_botones, menu);
    	
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    		case R.id.menuBRectangulares:{
    			Log.i("MSJ", "Rectangulares");
    			return true;
    		}
    		case R.id.subMenuGRados:{
    			Log.i("MSJ", "grados");
    			return true;
    		}
    		case R.id.subMenuRadianes:{
    			Log.i("MSJ", "Radianes");
    			return true;
    		}
    	}
    	
    	return super.onContextItemSelected(item);
   }

    /**
     * Metodo que es llamado al hacer click en un boton
     * @param v View que manda a llamar a este metodo
     */
	public void onClick(View v) {
		if(v.getId()==100)
			ObtenerDatos();
		else
		LlamarALlenar(v.getId());
	}
	
	/**
	 * Metodo el que es ejecutado inmediatamente despues de volver del intet que se mando a llamar
	 * @param requestCode Codigo de solicitud
	 * @param resultado Codigo de resultado si es igual o mayor a 0 todo a salido correctamente
	 * @param datos El intent del que viene, donde se introdujeron los datos del numero complejo
	 */
	public void onActivityResult(int requestCode, int resultado, Intent datos){
		
		if(datos!=null){
			
			
			String numero="";
			Bundle extras= datos.getExtras();
			
			int identificador=extras.getInt("ID");
			Log.v("mios", "El id que regreso es " + identificador);
			
			Complex auxNum=Complex.ZERO;
			
			Double real=0.0;
			Double imaginario=0.0;
			String signo="+";
			
			if(extras.getInt("MODO")==EntradaComplejo.MODO_RECTANGULAR){
			
				real=extras.getDouble("REAL");		//Obteniendo el numero real
					
				signo=extras.getString("SIGNO");	//Obteniendo el signo
				
				imaginario=extras.getDouble("IMAGINARIO");		//Obteniendo el numero imaginario
				
				if(signo.equals("+"))
					auxNum=Complex.valueOf(real, imaginario);
				else
					auxNum=Complex.valueOf(real, imaginario*-1);
			
			}
			else{
			
				auxNum=Polar.aRectangular(extras.getDouble("MODULO"), extras.getDouble("ANGULO"));	
				
			}
			
			numero=auxNum.toString();
			
			if(identificador<tamano)
				listaMatriz.set(identificador, auxNum);
			else
				listaVector.set(identificador-tamano, auxNum);
		
			Button aux=(Button)findViewById(identificador);
			aux.setText(numero);
			
		}
			
	}

	/**
	 * Metodo que llama al intent en donde se registran los numeros complejos a 
	 * calcular
	 * @param id Este es el id que ah llamdo a esta actividad
	 */
	private void LlamarALlenar(int id){
		
		Log.v("mios", "El id que me llevo es " + id);
		Intent i=new Intent(this,EntradaComplejo.class);
		i.putExtra("ID", id);
		this.startActivityForResult(i,0);
	
	}

}
