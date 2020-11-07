/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n3_tiendaDeLibros
 * Autor: Equipo Cupi2 2017
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.tiendadelibros.mundo;

import java.util.ArrayList;

/**
 * Clase que representa la tienda de libros.
 */
public class TiendaDeLibros
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Catalogo de libros registrados en la tienda.
     */
    private ArrayList<Libro> catalogo;

    /**
     * Cantidad actual en la caja de la tienda de libros.
     */
    private double caja;
    
    // Mis atributos
    
    private int numeroTrans;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     * Crea una nueva tienda de libros. <br>
     * <b>post:</b>El catalogo de libros fue inicializado. <br>
     * La caja fue inicializada en 1000000.
     */
    public TiendaDeLibros( )
    {
        catalogo = new ArrayList<Libro>( );
        caja = 1000000;
        numeroTrans = 0;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Retorna el catálogo de libros.
     * @return El catálogo de libros.
     */
    public ArrayList<Libro> darCatalogo( )
    {
        return catalogo;
    }

    /**
     * Retorna el valor actual en la caja.
     * @return El valor actual en caja.
     */
    public double darCaja( )
    {
        return caja;
    }

    /**
     * Modifica el valor actual de la caja.
     * @param pCaja El nuevo valor de la caja.
     */
    public void cambiarCaja( double pCaja )
    {
        caja = pCaja;
    }

    /**
     * Busca un libro por el título dado por parámetro.
     * @param pTitulo El titulo del libro que se quiere buscar. pTitulo != null && pTitulo != "".
     * @return Si existe un libro con ese título, lo retorna. En caso contrario, retorna null.
     */
    public Libro buscarLibroPorTitulo( String pTitulo )
    {
        Libro buscado = null;
        int i = 0;
        while( i < catalogo.size( ) && buscado == null )
        {
            Libro actual = catalogo.get( i );
            if( actual.darTitulo( ).equals( pTitulo ) )
            {
                buscado = actual;
            }
            i++;
        }
        return buscado;
    }

    /**
     * Busca un libro por el código ISBN dado por parámetro.
     * @param pIsbn El código ISBN del libro que se quiere buscar. pIsbn != null && pIsbn != "".
     * @return Si existe un libro con ese ISBN, lo retorna. En caso contrario, retorna null.
     */
    public Libro buscarLibroPorISBN( String pIsbn )
    {
        Libro buscado = null;
        for( int i = 0; i < catalogo.size( ) && buscado == null; i++ )
        {
            Libro actual = catalogo.get( i );
            if( actual.darIsbn( ).equals( pIsbn ) )
            {
                buscado = actual;
            }
        }

        return buscado;
    }

    /**
     * Registra un libro en la tienda de libros. <br>
     * <b>post: </b> El libro fue creado y agregado al catálogo.
     * @param pTitulo El título del libro que se quiere agregar. pTitulo != null && pTitulo != "".
     * @param pIsbn El código ISBN del libro que se quiere agregar. pIsbn != null && pIsbn != "".
     * @param pPrecioVenta El precio de venta del libro que se quiere agregar. pPrecioVenta > 0.
     * @param pPrecioCompra El precio de compra del libro que se quiere agregar. pPrecioCompra > 0.
     * @param pRutaImagen La ruta de la imagen del libro. pRutaImagen != null && pRutaImagen != "".
     * @return El nuevo libro registrado en caso de que si se haya podido realizar la operación, null en caso de que el libro ya exista.
     */
    public Libro registrarLibro( String pTitulo, String pIsbn, double pPrecioVenta, double pPrecioCompra, String pRutaImagen )
    {
        // Comprueba si el libro con ese ISBN no ha sido creado
        Libro buscado = buscarLibroPorISBN( pIsbn );
        Libro nuevo = null;
        if( buscado == null )
        {
            nuevo = new Libro( pTitulo, pIsbn, pPrecioVenta, pPrecioCompra, pRutaImagen );
            catalogo.add( nuevo );            
        }
        return nuevo;
    }

    /**
     * Elimina un libro con el ISBN dado por parámetro. Si la cantidad actual de ejemplares es mayor a cero, no se eliminará el libro. <br>
     * <b>post: </b> El libro fue eliminado del catálogo.
     * @param pIsbn El ISBN del libro que se quiere eliminar. pIsbn != null && pIsbn != "".
     * @return Retorna true si se pudo eliminar, false si el libro no existe o si la cantidad actual de ejemplares es mayor a cero.
     */
    public boolean eliminarLibro( String pIsbn )
    {
        boolean eliminado = false;
        Libro buscado = buscarLibroPorISBN( pIsbn );
        if( buscado != null )
        {
            if( buscado.darCantidadActual( ) == 0 )
            {
                /// Cuantas transacciones tiene ese libro
            	// numeroTrans - numero Transacciones de ese libro            	
            	catalogo.remove( buscado );
                eliminado = true;
            }

        }
        return eliminado;
    }

    /**
     * Abastece un libro con la cantidad de ejemplares dada por parámetro. <br>
     * <b>post: </b> Se abasteció el libro con el ISBN dado y se disminuyó la cantidad en caja con el precio final del abastecimiento.
     * @param pIsbn El Código ISBN del libro que se quiere abastecer. pIsbn!= null && pISBN != "".
     * @param pFecha La fecha en la que se realizó la transacción. pFecha != "" && pFecha != null.
     * @param pCantidad La cantidad de ejemplares que se van a abastecer. pCantidad >= 0.
     * @return Retorna true si se pudo abastecer el libro, false en caso contrario.
     */
    public boolean abastecer( String pIsbn, int pCantidad, String pFecha )
    {
        Libro buscado = buscarLibroPorISBN( pIsbn );
        boolean seAbastecio = false;
        if( buscado != null && caja >= pCantidad * buscado.darPrecioCompra( ) )
        {
            buscado.abastecer( pCantidad, pFecha );
            // Disminuye la caja con el valor total de los ejemplares abastecidos
            caja -= pCantidad * buscado.darPrecioCompra( );
            seAbastecio = true;
        }
        return seAbastecio;
    }

    /**
     * Vende la cantidad de ejemplares del libro con el ISBN dado por parámetro. <br>
     * <b>post: </b> Se vendió el libro con el ISBN dado y se aumentó la cantidad en caja con el precio final de la venta.
     * @param pIsbn El Código ISBN del libro que se quiere vender. pIsbn != null && pIsbn != "".
     * @param pCantidad La cantidad de ejemplares que se van a vender.
     * @param pFecha La fecha en la que se realizó la transacción. pFecha != "" && pFecha != null.
     * @return Retorna true en caso de que se pueda vender la cantidad de ejemplares dada por parámetro. False en caso contrario.
     */
    public boolean vender( String pIsbn, int pCantidad, String pFecha )
    {
        boolean vendido = false;
        Libro buscado = buscarLibroPorISBN( pIsbn );
        if( buscado != null )
        {
            vendido = buscado.vender( pCantidad, pFecha );
            // Aumenta la caja con el valor total de los ejemplares vendidos
            caja += pCantidad * buscado.darPrecioVenta( );
         // Calculamos el numero de transacciones
            numeroTrans += 1;
        }
        return vendido;
    }

    /**
     * Busca el libro más costoso, es decir el libro con el mayor precio de venta en el catálogo.
     * @return El libro más costoso. En caso de que el catalogo esté vacío, retorna null
     */
    public Libro darLibroMasCostoso( )
    {
        // Guarda el libro más costoso y su precio
        Libro masCostoso = null;
        double precioMasCostoso = 0.0;

        for( Libro actual : catalogo )
        {
            // Verifica si el libro actual tiene un precio mayor al que está guardado
            if( actual.darPrecioVenta( ) > precioMasCostoso )
            {
                masCostoso = actual;
                precioMasCostoso = actual.darPrecioVenta( );
            }
        }

        return masCostoso;
    }

    /**
     * Busca el libro más económico en el catálogo de libros. El libros más económico es el libro con el menor precio de venta.
     * @return El libro menos costoso. En caso de que el catálogo esté vacío, retorna null.
     */
    public Libro darLibroMasEconomico( )
    {
        Libro menosCostoso = null;
        double precioMenosCostoso = 0.0;

        // Revisa que el catálogo no esté creado
        if( catalogo.size( ) > 0 )
        {
            // Para comenzar el recorrido, se asume que el primer libro es el más económico
            menosCostoso = catalogo.get( 0 );
            precioMenosCostoso = catalogo.get( 0 ).darPrecioVenta( );

            for( int i = 0; i < catalogo.size( ); i++ )
            {
                Libro actual = catalogo.get( i );
                // Pregunta si el precio del libro actual es menos costoso
                if( actual.darPrecioVenta( ) < precioMenosCostoso )
                {
                    menosCostoso = actual;
                    precioMenosCostoso = actual.darPrecioVenta( );
                }
            }
        }
        return menosCostoso;
    }

    /**
     * Busca el libro más vendido, es decir el libro con más transacciones de tipo VENTA.
     * @return El libro más vendido. En caso de que el catálogo esté vacío, retorna null.
     */
    public Libro darLibroMasVendido( )
    {
        Libro masVendido = null;
        int ventas = 0;
        // Recorre el catalogo de libros
        for( Libro libroActual : catalogo )
        {
            int ventasActual = 0;
            // Recorre las transacciones del libro actual
            for( Transaccion transaccionActual : libroActual.darTransacciones( ) )
            {
                if( transaccionActual.darTipo( ).equals( Transaccion.Tipo.VENTA ) )
                {
                    // Cuenta los libros vendidos en la transacción actual.
                    ventasActual += transaccionActual.darCantidad( );
                }
            }
            // Verifica que las ventas actuales sean mayores a las ventas guardadas
            if( ventasActual > ventas )
            {
                masVendido = libroActual;
                ventas = ventasActual;
            }
        }

        return masVendido;
    }

    /**
     * Calcula la cantidad de transacciones de abastecimiento del libro con el ISBN dado por parámetro.
     * @param pIsbn El código ISBN del libro que se quiere buscar. pIsbn != null && pIsbn != "".
     * @return La cantidad de transacciones de abastecimiento. En caso de que no encuentre el libro o no tenga transacciones, retorna cero.
     */
    public int darCantidadTransaccionesAbastecimiento( String pIsbn )
    {
        // Busca el libro con el ISBN dado por parámetro
        Libro buscado = buscarLibroPorISBN( pIsbn );
        int cantidadTransacciones = 0;
        // Verifica que si exista el libro
        if( buscado != null )
        {
            // Guarda las transacciones del libro buscado
            ArrayList<Transaccion> transacciones = buscado.darTransacciones( );
            for( int i = 0; i < transacciones.size( ); i++ )
            {
                Transaccion actual = transacciones.get( i );
                // Verifica y cuenta las transacciones de tipo ABASTECIMIENTO
                if( actual.darTipo( ).equals( Transaccion.Tipo.ABASTECIMIENTO ) )
                {
                    cantidadTransacciones++;
                }
            }
        }
        return cantidadTransacciones;
    }

    // -----------------------------------------------------------------
    // Puntos de Extensión
    // -----------------------------------------------------------------

    /**
     * Método para la extensión 1.
     * @return Respuesta 1.
     */
    public String metodo1(int[] lim_inf, int[] lim_sup, double[] descuentos)
    {	
    	/// Creamos un objeto vacio de la clase Libro
    	Libro libroActual = null;
    	// Obtenemos el tamaño del ArrayList catalogo (objetos de la clase Libro)
    	int tamanoCatalogo = catalogo.size();
    	// Obtenemos el tamaño del Arreglo descuentos (Recordar: es igual al tamaño del arreglo lim_inf y lim_sup)
    	int tamanoDescuentos = descuentos.length;
    	// Variable para obtener el valor de venta del libro
    	double precioActualV = 0, precioConDescuento = 0;
    	// Variables para obtener los limites (inf, sup) y el descuento en determinada posicion
    	int limInf = 0, limSup = 0;
    	double descuentoA = 0; 
    	// Recorremos el ArrayList 'catalogo' para obtener la informacion de cada libro
    	for (int i = 0; i < tamanoCatalogo; i++) {
    		// Reasignamos el objeto en la posicion 'i' del ArrayList 'catalogo' en el objeto 'libroActual'
    		libroActual = catalogo.get(i);
    		// Obtnemos el precioActual del libro
    		precioActualV = libroActual.darPrecioVenta();
    		// Recorrer los descuentos
    		for (int j = 0; j < tamanoDescuentos; j++) {
    			//Obtener los valores de los limites en determinada porsicion (j)
    			limInf = lim_inf[j];
    			limSup = lim_sup[j];
    			// Condicion para saber que descuento vamos a aplicar
    			if (precioActualV >= limInf && precioActualV < limSup) {
    				// Si esta dentro de determinado posiicion (j) obtengo el desceunto en esa posiion
    				descuentoA = descuentos[j];
    				// Calculando el nuevo precio de venta del libro con el descuento 
    				precioConDescuento = precioActualV - (precioActualV * descuentoA);
    				// Actualizando el precio de venta con el descuento aplicado
    				libroActual.cambiarPrecioVenta(precioConDescuento);
    				// Actualizar el libro en el ArrayList 'catalogo' 
    				catalogo.set(i, libroActual);
    			}    			
    		}    		
    	}
    	
    	return "Precios actualizados!!!";
    }

    /**
     * Método para la extensión 2.
     * @return Respuesta 2.
     */
    public Transaccion metodo2( )
    {
    	// Verificando si hay alguna transaccion
    	// Si 'numeroTrans' es 0 no hay transacciones de venta
    	// Si es diferente -> hay trasacciones de venta
    	/// Creamos un objeto vacio de la clase Transaccion
    	Transaccion ventaMasCara = null;
    	/// Creamos un objeto vacio de la clase Libro
    	Libro libroActual = null;
    	// Obtenemos el tamaño del ArrayList catalogo (objetos de la clase Libro)
    	int tamanoCatalogo = catalogo.size();
    	// Creamos un ArrayList para las transacciones guardadas en cada libro
    	ArrayList<Transaccion> transLibroActual = new ArrayList<Transaccion>();
    	// Creamos un objeto vacio de la clase Transaccion
    	Transaccion transActual = null;
    	// Variable para obtener el valor de venta del libro
    	double precioActualV = 0;
    	// Variable para obtener la cantidad de libros vendido
    	int cantLibros = 0;
    	// Variable para calcular la venta Actual 0 precio de venta del libro * cantidad de libros vendidos
    	double ventaActual = 0;
    	// Comparador para saber la mejor venta
    	double mejorVenta = 0;
    	if (numeroTrans != 0) {
    		// Recorremos el ArrayList 'catalogo' para obtener la informacion de cada libro
        	for (int i = 0; i < tamanoCatalogo; i++) {
        		// Reasignamos el objeto en la posicion 'i' del ArrayList 'catalogo' en el objeto 'libroActual'
        		libroActual = catalogo.get(i);
        		// Obtenemos las transacciones de ese libro
        		transLibroActual = libroActual.darTransacciones();
        		// Obtenemos el tamaño del ArrayList transLibroActual (objetos de la clase Transaccion)
            	int tamanoTransaccionesLibroActual = transLibroActual.size();
        		// Recorremos las transacciones del libro actual
        		for (int j = 0; j < tamanoTransaccionesLibroActual; j++) {
        			// Reasignamos el objeto en la posicion 'j' del ArrayList 'transLibroActual' en el objeto 'transActual'
        			transActual = transLibroActual.get(j);
        			// Verifica y cuenta las transacciones de tipo VENTA
                    if( transActual.darTipo( ).equals( Transaccion.Tipo.VENTA ) )
                    {
                    	// Obtengo la cantidad de libros vendidos en la transaccion
                    	cantLibros = transActual.darCantidad();
                    	// Obtnemos el precioActual del libro
                		precioActualV = libroActual.darPrecioVenta();
                		// Calculo la venta total de la transaccion
                		ventaActual = precioActualV * cantLibros;
                		// Comparar si la venta actual es mejor o mayor que la mejor venta
                		if (ventaActual > mejorVenta) {
                			// Actualizamos el valor de la mejor venta
                			mejorVenta = ventaActual;
                			// Actualizamos la mejor transaccion 
                			ventaMasCara = transActual;
                		}
                    }
        		}
        	}
        	// Retornamos la mejor transaccion
    		return ventaMasCara;    		
    	} else {    		
    		// Retornamos ventaMasCara = null
    		return ventaMasCara;
    	}        
    }

}
