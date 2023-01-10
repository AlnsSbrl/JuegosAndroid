<html>

<head>
    <meta charset="UTF-8">
    <title>Biblioteca</title>
    <link rel="stylesheet" href="colores.css">
</head>

<body>
    <header>
        </header>
    <div>
        <h3>Lista de juegos</h3>
        <table> <tr>
            <th>TITULO</th>
            <th>DESARROLLADOR</th>
            <th>AÑO</th>
            <th>PRECIO</th>
            </tr>
            <?php
            for ($i = 0; $i < count($datos); $i++) {
            ?>
                <tr>
                    <td><?php echo $datos[$i]["titulo"]; ?></td>
                    <td><?php echo $datos[$i]["desarrollador"]; ?></td>
                    <td><?php echo $datos[$i]["anho"]; ?></td>
                    <td><?php echo $datos[$i]["precio"]; ?></td>
                </tr>
            <?php
            }
            ?>
        </table>
        <a href="../index.php"> Volver a la tienda</a>
        <hr />
    </div>
</body>

</html>