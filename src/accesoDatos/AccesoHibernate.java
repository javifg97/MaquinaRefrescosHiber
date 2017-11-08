package accesoDatos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;


public class AccesoHibernate implements I_Acceso_Datos{
	private SessionFactory sf;
	private Session s;
	
	public AccesoHibernate() {
	
		sf = new Configuration().configure().buildSessionFactory();
		s = sf.openSession();
		
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		
		HashMap<Integer, Deposito> coleccionDepositos = new HashMap<Integer, Deposito>();
		int clave;

		 Query q= s.createQuery("select e from Deposito e");
	        List results = q.list();
	        
	        Iterator equiposIterator= results.iterator();

	        while (equiposIterator.hasNext()){
	        	Deposito Dep = (Deposito)equiposIterator.next();
	            
				Deposito depositoAux = new Deposito(Dep.getNombreMoneda(), Dep.getValor(),
						Dep.getCantidad());
				clave = Dep.getValor();

				coleccionDepositos.put(clave, depositoAux);
	            
	        }
		

		
		
		return coleccionDepositos;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> coleccionDispensadores = new HashMap<String, Dispensador>();
		String clave;

		 Query q= s.createQuery("select e from Dispensador e");
	        List results = q.list();
	        
	        Iterator equiposIterator= results.iterator();

	        while (equiposIterator.hasNext()){
	        	Dispensador Dis = (Dispensador)equiposIterator.next();
	            
	        	Dispensador dispensadosAux = new Dispensador(Dis.getClave(), Dis.getNombreProducto(),
						Dis.getPrecio(),Dis.getCantidad());
				clave = Dis.getClave();

				coleccionDispensadores.put(clave, dispensadosAux);
	            
	        }
		

		
		
		return coleccionDispensadores;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		try {
			s.beginTransaction();
			for(Entry<Integer, Deposito> entry : depositos.entrySet()) {

				s.save(entry.getValue());

			}

			
			s.getTransaction().commit();
			s.close();
		} catch (Exception e) {
			todoOK = false;
			System.out.println(e.getMessage());
		}
		
		
		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		// TODO Auto-generated method stub
		return false;
	}

}
