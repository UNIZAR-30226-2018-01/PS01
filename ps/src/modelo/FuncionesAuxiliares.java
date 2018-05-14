package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.document.Field;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import modelo.excepcion.SesionInexistente;
import modelo.excepcion.LoginInexistente;

public class FuncionesAuxiliares {
	private FuncionesAuxiliares() {}
	
	private static final String DIRECTORIO_SESIONES = 
			"/usr/local/apache-tomcat-9.0.7/webapps/ps/sesions";
	private static final String DIRECTORIO_USUARIOS = 
			"/usr/local/apache-tomcat-9.0.7/webapps/ps/users";
	
	/*
	 * Pre:  ---
	 * Post: Ha devuelto un objeto de conexión del pool de conexiones
	 */
	public static Connection obtenerConexion() throws SQLException {
		try {
			Context initContext = new InitialContext();
			Context c = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) c.lookup("jdbc/pool"); 
			return ds.getConnection();
		}
		catch(NamingException e) {
			System.out.println("Error al obtener conexion del pool");
			System.out.println(e.toString());
			return null;
		}
	}
	
	/*
	 * Pre:
	 * Post: Dado un array de string c y un string s, busca en c si existe una
	 * cookie cuya clave sea s. De ser así, devuelve el valor de esa cookie.
	 * En caso contrario, devuelve null.
	 */
	public static String obtenerCookie(Cookie[] c, String s) {
		for(Cookie i : c) {
			if(i.getName().equals(s)) {
				return i.getValue();
			}
		}
		return null;
	}

	/*
	 * Pre:
	 * Post: Dado un ResultSet, devuelve un vector con todos los valores para
	 * 		 la columna 'c'. Si no había valores, devuelve un Vector vacío
	 */
	public static JSONObject obtenerValorColumna(ResultSet r, String c)
			throws SQLException {
		try {
			JSONObject obj = new JSONObject();
			JSONArray v = new JSONArray();
			r.beforeFirst();
			while(r.next()) {
				v.add(r.getString(c));
			}
			
			obj.put(c, v);
			return obj;
			//return v;
		}
		catch(Exception e) {
			throw e;
		}
	}
		
	
	/*
	 * Pre: ---
	 * Post: Función que a partir de un String genera su hash
	 */
	public static String crearHash(String s) {
		try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(s.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
	}
	
	/*
	 * Pre:  ---
	 * Post: Devuelve falso si:
	 * 			-nombre es nulo
	 * 			-nombre contiene espacios
	 * 			-La longitud de nombre es menor que 4
	 * 			-La longitud de nombre es mayor que 15
	 * 			-nombre no empieza por una letra
	 * 			-nombre contiene caracteres que no son alfanuméricos
	 * 		  En caso contrario, devuelve verdad
	 */
	public static boolean comprobarNombre(String nombre) {
		if(nombre == null || nombre.contains(" ") || nombre.length()<4 ||
				nombre.length()>15 || !Character.isLetter(nombre.charAt(0))) {
			return false;
		}
		else {
			boolean seguir = true;
			for(int i=1; i<nombre.length() && seguir; i++) {
				if(!Character.isLetterOrDigit(nombre.charAt(i))) {
					seguir = false;
				}
			}
			return seguir;
		}
	}
	
	/*
	 * Pre:  ---
	 * Post: Devuelve falso si:
	 * 			-contrasenya es nula
	 * 			-contrasenya contiene espacios
	 * 			-contrasenya tiene menos de 4 caracteres
	 * 			-contrasenya tiene mas de 4 caracteres
	 * 			-contrasenya tiene caracteres que no son alfanumericos
	 * 			-contrasenya no tiene ninguna letra mayuscula
	 * 			-contrasenya no tiene ningún número
	 */
	public static boolean comprobarContrasenya(String contrasenya) {
		if(contrasenya == null || contrasenya.contains(" ") || contrasenya.length()<4 ||
				contrasenya.length()>15) {
			return false;
		}
		else {
			boolean hayNumero = false;
			boolean hayMayuscula = false;
			boolean seguir = true;
			for(int i=0; i<contrasenya.length() && seguir; i++) {
				if(!Character.isLetterOrDigit(contrasenya.charAt(i))) {
					seguir = false;
				}
				else {
					if(Character.isUpperCase(contrasenya.charAt(i))) {
						hayMayuscula = true;
					}
					else if (Character.isDigit(contrasenya.charAt(i))) {
						hayNumero = true;
					}
				}
			}
			return hayNumero && hayMayuscula && seguir;
		}
	}
	
	/*
	 * Pre:  ---
	 * Post: Devuelve verdad si el fichero es una imagen JPG
	 */
	public static Boolean isJPEG(File filename) throws Exception {
	    DataInputStream ins = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
	    try {
	        if (ins.readInt() == 0xffd8ffe0) {
	            return true;
	        } else {
	            return false;

	        }
	    } finally {
	        ins.close();
	    }
	}
	
	/*
	 * Pre:  ---
	 * Post: Ha indexado la sesión mediante la librería lucene
	 */
	public static void indexarSesion(String nombre, String hash) 
			throws Exception {
		try {
			// 1. Abrimos/creamos el índice
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
			Directory index = FSDirectory.open(new File(DIRECTORIO_SESIONES));
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, 
					analyzer);
			IndexWriter w = new IndexWriter(index, config);
			
			// 2. Insertamos en el índice
			Document doc = new Document();
			doc.add(new TextField("nombreUsuario", nombre, Field.Store.YES));
			doc.add(new TextField("hashSesion", hash, Field.Store.YES));
			w.close();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:  nombre != null && hash != null
	 * Post: Si no existe la sesión, lanza un error
	 */ 
	public static void existeSesion(String nombre, String hash) 
			throws SesionInexistente {
		try {
			// 1. Abrimos el índice
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
			DirectoryReader directoryReader = DirectoryReader.open(
					FSDirectory.open(new File(DIRECTORIO_SESIONES)));
			IndexSearcher buscador = new IndexSearcher(directoryReader);
			
			// 2. Creamos la consulta
			QueryParser qp1 = new QueryParser(Version.LUCENE_40,
					"nombreUsuario", analyzer);
			Query q1 = qp1.parse(nombre);
			QueryParser qp2 = new QueryParser(Version.LUCENE_40,
					"hashSesion", analyzer);
			Query q2 = qp2.parse(hash);
			BooleanQuery q = new BooleanQuery();
			q.add(q1, Occur.MUST);
			q.add(q2, Occur.MUST);
			
			// 3. Ejecutamos la consulta
			TopDocs docs = buscador.search(q,1);
			ScoreDoc[] hits = docs.scoreDocs;
			
			// 4. Comprobamos lo devuelto
			if(hits.length != 1) {
				throw new Exception();
			}
		}
		catch(Exception e) {
			throw new SesionInexistente("El usuario no está logeado");
		}
	}
	
	/*
	 * Pre:  nombre != null && hash != null
	 * Post: Devuelve verdad si existe una sesion de nombre y hash
	 */ 
	public static void borrarSesion(String nombre, String hash)
			throws Exception {
		// 1. Abrimos el índice
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		IndexWriter w = new IndexWriter(FSDirectory.open(new File(DIRECTORIO_SESIONES)),
				new IndexWriterConfig(Version.LUCENE_40, analyzer));
		
		// 2. Creamos la consulta
		QueryParser qp1 = new QueryParser(Version.LUCENE_40,
				"nombreUsuario", analyzer);
		Query q1 = qp1.parse(nombre);
		QueryParser qp2 = new QueryParser(Version.LUCENE_40,
				"hashSesion", analyzer);
		Query q2 = qp2.parse(hash);
		BooleanQuery q = new BooleanQuery();
		q.add(q1, Occur.MUST);
		q.add(q2, Occur.MUST);
		
		// 3. Borramos la sesión
		w.deleteDocuments(q);
		w.close();
	}
	
	/*
	 * Pre:  ---
	 * Post: Ha indexado un usuario mediante la libreria lucene
	 */
	public static void indexarUsuario(String nombreUsuario, String hashPass) 
			throws Exception {
		try {
			// 1. Abrimos/creamos el índice
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
			Directory index = FSDirectory.open(new File(DIRECTORIO_USUARIOS));
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, 
					analyzer);
			IndexWriter w = new IndexWriter(index, config);
			
			// 2. Insertamos en el índice
			Document doc = new Document();
			doc.add(new TextField("nombre", nombreUsuario, Field.Store.YES));
			doc.add(new TextField("hashPass", hashPass, Field.Store.YES));
			w.close();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/*
	 * Pre:  ---
	 * Post: Dada la cadena de caracteres 'nombre' busca usuarios cuyo nombre
	 * 		 sea igual o empiece por nombre, devolviéndolos en un JSON
	 * 		 cuya clave es usuarios, cuyo valor asociado es un array de strings
	 *  	 con los nombres encontrados
	 */
	public static JSONObject buscarUsuarios(String nombre, String nombreUsuario) {
		try {
			// 1. Abrimos el índice
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
			DirectoryReader directoryReader = DirectoryReader.open(
					FSDirectory.open(new File(DIRECTORIO_USUARIOS)));
			IndexSearcher buscador = new IndexSearcher(directoryReader);
			
			// 2. Creamos la consulta
			QueryParser qp1 = new QueryParser(Version.LUCENE_40,
					"nombre", analyzer);
			Query q1 = qp1.parse(nombre+"*");
			QueryParser qp2 = new QueryParser(Version.LUCENE_40,
					"nombre", analyzer);
			Query q2 = qp2.parse(nombreUsuario);
			BooleanQuery q = new BooleanQuery();
			q.add(q1, Occur.MUST);
			q.add(q2, Occur.MUST_NOT);
			
			// 3. Ejecutamos la consulta
			TopDocs res = buscador.search(q,1);
			ScoreDoc[] hits = res.scoreDocs;
			
			// 4. Construimos el JSON a partir de lo devuelto
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			for(ScoreDoc i : hits) {
				Document doc = buscador.doc(i.doc);
				array.add(doc.get("nombre"));
			}
			obj.put("usuarios", array);
			return obj;
		}
		catch(Exception e) {
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			obj.put("usuarios", array);
			return obj;
		}
	}
	
	/*
	 * Pre:  nombre != null && hash != null
	 * Post: Borra el usuario correspondiente del índice
	 */ 
	public static void borrarUsuario(String nombre)
			throws Exception {
		// 1. Abrimos el índice
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		IndexWriter w = new IndexWriter(FSDirectory.open(new File(DIRECTORIO_USUARIOS)),
				new IndexWriterConfig(Version.LUCENE_40, analyzer));
		
		// 2. Creamos la consulta
		QueryParser qp = new QueryParser(Version.LUCENE_40,
				"nombre", analyzer);
		Query q = qp.parse(nombre);
		
		// 3. Borramos el usuario
		w.deleteDocuments(q);
		w.close();
	}
}
