### PRUEBAS - INSTALACIÓN
* Tienes que colocar esta carpeta en el disco local "C:" desde la raíz; sin ninguna otra carpeta (así: ""C:\spomp"")
* No tienes que modificar:
  - El nombre de la carpeta (spomp)
  - El nombre del archivo (sqlserver)
  - La extensión del archivo (.properties)
  
### INFORMACIÓN ADICIONAL
* Este es el único requisito para que el sistema funcione; de lo contrario no se podrá acceder a SQLServer RDBMS
  - La clase ConnectionSQLServer utiliza objetos Properties que leen la data de los archivos .properties
  - En caso de querer hacer algún cambio en el servidor (ip), puerto, nombre de la base de datos, usuario, contraseña y tiempo de espera; se tendría que modificar sólo el archivo .properties, es una ventaja porque dicha modificación no se hace en el código.
