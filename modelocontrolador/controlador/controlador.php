<?php
 require_once("../modelo/modelo.php");
 $videojuego = new Videojuego();
 $datos = $videojuego->getVideojuego();
 require_once("../vista/vista.php");
 ?> 