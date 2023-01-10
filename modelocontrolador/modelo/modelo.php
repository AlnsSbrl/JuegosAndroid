<?php
class Videojuego
{
    private $videojuego;
    private $db;
    public function __construct()
    {
        $this->videojuego = array();
        $this->db = new PDO('mysql:host=localhost;dbname=videojuegos', "root", "");
    }
    public function getVideojuego()
    {
        $sql = "SELECT titulo,desarrollador,anho,precio FROM videojuegos";
        foreach ($this->db->query($sql) as $res) {
            $this->videojuego[] = $res;
        }
        return $this->videojuego;
        $this->db = null;
    }
    public function setVideojuego($titulo, $desarrollador, $anho, $precio)
    {
        $sql = "INSERT INTO videojuegos(titulo, desarrollador, anho, precio) VALUES ('" .
            $titulo . "', '" . $desarrollador . "', '" . $anho . "', '" . $precio . "')";
        $result = $this->db->query($sql);
        if ($result) {
            return true;
        } else {
            return false;
        }
    }
}
