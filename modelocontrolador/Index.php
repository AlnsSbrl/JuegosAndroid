<?php
if ((isset($_POST['titulo'])) && ($_POST['titulo'] != '') && (isset($_POST['desarrollador'])) &&
    ($_POST['desarrollador'] != '') && (isset($_POST['anho'])) && ($_POST['anho'] != '') &&
    (isset($_POST['precio'])) && ($_POST['precio'] != '')
) {
    require "modelo/modelo.php";
    $nuevo = new Videojuego();
    $asd = $nuevo->setVideojuego(
        $_POST['titulo'],
        $_POST['desarrollador'],
        $_POST['anho'],
        $_POST['precio']
    );
}
?>
<html>

<head>
    <meta charset="UTF-8">
    <title>Ejemplo MVC con PHP</title>
    <!--<link href="colores.css" rel="stylesheet"> -->
</head>

<body style="background-color:#10131B;">
    <header>
        <img src="images\Steam_logo.svg.png" style="height: 100px;">
        <a href="controlador/controlador.php" > <img src="images\catalogo.png" height="100px"></a>
        <hr />
        
    </header>
    <nav>

    </nav>
    <div>
        <form action="#" method="post">

            <table style="display: block;
            color: white;
            margin-left: auto;
            margin-right: auto;
            width: 40%;">
                <tr>
                    <td >Titulo:
                    <td><input type="text" name="titulo" />
                <tr>
                    <td>Desarrollador:
                    <td><input type="text" name="desarrollador" />
                <tr>
                    <td>Año de publicación:
                    <td><input type="tel" name="anho" />
                <tr>
                    <td>Precio:
                    <td> <input type="text" name="precio" />
                <tr>
                    <td colspan="2">
                        <input type="image" src="images\Add-To-Cart-Button-PNG-Clipart.png" height=50px border=0 alt="submit" value="Crear" />
            </table>
        </form>       
    </div>
</body>

</html>